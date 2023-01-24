package com.abdelrahman.amin.drone.service.impl;


import com.abdelrahman.amin.drone.dto.DroneDTO;
import com.abdelrahman.amin.drone.dto.MedicationDTO;
import com.abdelrahman.amin.drone.entity.Drone;
import com.abdelrahman.amin.drone.entity.Medication;
import com.abdelrahman.amin.drone.enums.DroneState;
import com.abdelrahman.amin.drone.exception.*;
import com.abdelrahman.amin.drone.repository.DroneRepository;
import com.abdelrahman.amin.drone.repository.MedicationRepository;
import com.abdelrahman.amin.drone.service.DroneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;
    private ModelMapper modelMapper;


    @Override
    public Drone createDrone(DroneDTO droneDTO) throws DroneAlreadyExistException {
        if (droneRepository.findById(droneDTO.getId()).isPresent()) {
            throw new DroneAlreadyExistException(droneDTO.getId());
        }
        Drone drone = modelMapper.map(droneDTO, Drone.class);
        return droneRepository.save(drone);
    }

    @Transactional
    @Override
    public Drone loadMedicationsToDrone(String id, List<String> medicationsCodeList) throws DroneNotFoundException, DroneNotReadyException, UnSupportedWeightException, NoPowerException {
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (!droneOptional.isPresent()) {
            throw new DroneNotFoundException(id);
        }
        Drone drone = droneOptional.get();
        if (drone.getBatteryCapacity() < 25) {
            throw new NoPowerException(drone.getId());
        }
        if (!DroneState.IDLE.equals(drone.getState())) {
            throw new DroneNotReadyException(drone.getId());
        }
        Map<String, Long> medicationGroupedMap = medicationsCodeList.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println(medicationGroupedMap);
        List<Medication> distinctMedications = medicationRepository.findAllById(medicationGroupedMap.keySet());
        List<Medication> medications = new ArrayList<>();
        distinctMedications.forEach(medication -> {
            for (int i = 0; i < medicationGroupedMap.get(medication.getCode()); i++) {
                medications.add(medication);
            }
        });
        Double totalWeight = medications.stream().reduce(0.0, (total, medicationDTO) -> total + medicationDTO.getWeight(), Double::sum);
        if (drone.getWeightLimit() < totalWeight) {
            throw new UnSupportedWeightException(drone.getId(), drone.getWeightLimit());
        }
        //Should be called if loading process take time (maybe in new thread)
        drone.setState(DroneState.LOADING);
        droneRepository.save(drone);
        //..
        drone.setMedications(medications);
        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);
        return drone;
    }

    @Override
    public List<MedicationDTO> getDroneMedications(String id) throws DroneNotFoundException {
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (!droneOptional.isPresent()) {
            throw new DroneNotFoundException(id);
        }
        return droneOptional.get()
                .getMedications()
                .stream()
                .map(medication -> modelMapper.map(medication, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DroneDTO> getAvailableDrones() {
        return droneRepository.getByStateAndBatteryCapacityGreaterThanEqual(DroneState.IDLE, 25.0)
                .stream()
                .map(drone -> modelMapper.map(drone, DroneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getDroneBattery(String id) throws DroneNotFoundException {
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (!droneOptional.isPresent()) {
            throw new DroneNotFoundException(id);
        }
        return String.format("%.2f%%", droneOptional.get().getBatteryCapacity());
    }


    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void logBatteryLevel() {
        List<Drone> droneList = droneRepository.findAll();
        droneList.forEach(drone -> log.info("The battery capacity for drone with id:\"{}\" is:{}%", drone.getId(), drone.getBatteryCapacity()));
    }

}
