package com.abdelrahman.amin.drone.service;


import com.abdelrahman.amin.drone.dto.DroneDTO;
import com.abdelrahman.amin.drone.dto.MedicationDTO;
import com.abdelrahman.amin.drone.entity.Drone;
import com.abdelrahman.amin.drone.entity.Medication;
import com.abdelrahman.amin.drone.enums.DroneModel;
import com.abdelrahman.amin.drone.enums.DroneState;
import com.abdelrahman.amin.drone.exception.*;
import com.abdelrahman.amin.drone.repository.DroneRepository;
import com.abdelrahman.amin.drone.repository.MedicationRepository;
import com.abdelrahman.amin.drone.service.impl.DroneServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DroneServiceImplTest {
    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private DroneServiceImpl droneService;

    private Optional<Drone> droneOptional;
    private DroneDTO droneDTO;

    @Before
    public void setUp() {

        Drone drone = new Drone();
        drone.setId("drone");
        drone.setBatteryCapacity(100.0);
        drone.setWeightLimit(500.0);
        drone.setModel(DroneModel.HEAVY_WEIGHT);
        drone.setState(DroneState.IDLE);
        drone.setState(DroneState.IDLE);
        Medication medication = new Medication();
        medication.setCode("MED1");
        medication.setWeight(100.0);
        drone.setMedications(Arrays.asList(medication));
        droneOptional = Optional.of(drone);

        droneDTO = new DroneDTO();
        droneDTO.setId("drone");
        droneDTO.setBatteryCapacity(100.0);
        droneDTO.setWeightLimit(500.0);
        droneDTO.setModel(DroneModel.HEAVY_WEIGHT);
        droneDTO.setState(DroneState.IDLE);
        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setCode("MED1");
        medicationDTO.setWeight(100.0);
        droneDTO.setMedications(Arrays.asList(medicationDTO));

    }

    @Test
    public void createDrone_notExist_returnDrone() throws DroneAlreadyExistException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(droneOptional.get());
        Drone drone = droneService.createDrone(droneDTO);
        Assert.assertEquals(drone.getId(), droneOptional.get().getId());
    }

    @Test(expected = DroneAlreadyExistException.class)
    public void createDrone_droneExist_throw() throws DroneAlreadyExistException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        droneService.createDrone(droneDTO);
    }

    @Test
    public void getAvailableDrones_getIdle_returnDrone() {
        Mockito.when(droneRepository.getByStateAndBatteryCapacityGreaterThanEqual(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(droneOptional.get()));
        List<DroneDTO> drone = droneService.getAvailableDrones();
        Assert.assertEquals(drone.get(0).getId(), droneOptional.get().getId());
    }


    @Test
    public void getDroneBattery_droneExist_returnDroneBattery() throws DroneNotFoundException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        String droneBattery = droneService.getDroneBattery(droneDTO.getId());
        Assert.assertEquals(droneBattery, String.format("%.2f%%", droneOptional.get().getBatteryCapacity()));
    }

    @Test(expected = DroneNotFoundException.class)
    public void getDroneBattery_droneNotExist_throw() throws DroneNotFoundException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        droneService.getDroneBattery(droneDTO.getId());
    }

    @Test
    public void getDroneMedications_droneExist_returnDroneMedications() throws DroneNotFoundException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        List<MedicationDTO> droneBattery = droneService.getDroneMedications(droneDTO.getId());
        Assert.assertEquals(droneBattery.get(0).getCode(), droneOptional.get().getMedications().get(0).getCode());
    }

    @Test(expected = DroneNotFoundException.class)
    public void getDroneMedications_droneNotExist_throw() throws DroneNotFoundException {
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        droneService.getDroneMedications(droneDTO.getId());
    }


    @Test
    public void loadMedicationsToDrone_droneExist_loadDrone() throws DroneNotFoundException, NoPowerException, DroneNotReadyException, UnSupportedWeightException {
        List<String> medicationsCodeList = Arrays.asList("MED1");
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        Mockito.when(droneRepository.save(Mockito.any())).thenReturn(droneOptional.get());
        Mockito.when(medicationRepository.findAllById(Mockito.anySet())).thenReturn(droneOptional.get().getMedications());
        Drone drone = droneService.loadMedicationsToDrone(droneDTO.getId(), medicationsCodeList);
        Assert.assertEquals(drone.getMedications().get(0).getCode(), droneOptional.get().getMedications().get(0).getCode());
    }

    @Test(expected = UnSupportedWeightException.class)
    public void loadMedicationsToDrone_droneExist_throw() throws DroneNotFoundException, NoPowerException, DroneNotReadyException, UnSupportedWeightException {
        List<String> medicationsCodeList = Arrays.asList("MED1", "MED1", "MED1", "MED1", "MED1");
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        Medication medications = droneOptional.get().getMedications().get(0);
        Mockito.when(medicationRepository.findAllById(Mockito.anySet())).thenReturn(Arrays.asList(medications, medications, medications, medications, medications));
        droneService.loadMedicationsToDrone(droneDTO.getId(), medicationsCodeList);
    }

    @Test(expected = DroneNotFoundException.class)
    public void loadMedicationsToDrone_droneNotExist_throw() throws DroneNotFoundException, NoPowerException, DroneNotReadyException, UnSupportedWeightException {
        List<String> medicationsCodeList = Arrays.asList("MED1");
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        droneService.loadMedicationsToDrone(droneDTO.getId(), medicationsCodeList);
    }

    @Test(expected = NoPowerException.class)
    public void loadMedicationsToDrone_noPower_throw() throws DroneNotFoundException, NoPowerException, DroneNotReadyException, UnSupportedWeightException {
        List<String> medicationsCodeList = Arrays.asList("MED1");
        droneOptional.get().setBatteryCapacity(5.0);
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        droneService.loadMedicationsToDrone(droneDTO.getId(), medicationsCodeList);
    }

    @Test(expected = DroneNotReadyException.class)
    public void loadMedicationsToDrone_droneNotReady_throw() throws DroneNotFoundException, NoPowerException, DroneNotReadyException, UnSupportedWeightException {
        List<String> medicationsCodeList = Arrays.asList("MED1");
        droneOptional.get().setState(DroneState.LOADED);
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(droneOptional);
        droneService.loadMedicationsToDrone(droneDTO.getId(), medicationsCodeList);
    }


}