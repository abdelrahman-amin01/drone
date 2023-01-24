package com.abdelrahman.amin.drone.controller;


import com.abdelrahman.amin.drone.controller.api.DronesAPI;
import com.abdelrahman.amin.drone.dto.DroneDTO;
import com.abdelrahman.amin.drone.dto.MedicationDTO;
import com.abdelrahman.amin.drone.entity.Drone;
import com.abdelrahman.amin.drone.exception.*;
import com.abdelrahman.amin.drone.service.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class DroneController implements DronesAPI {

    private DroneService droneService;

    @Override
    public ResponseEntity<Void> createDrone( DroneDTO droneDTO) throws DroneAlreadyExistException {
        Drone drone = droneService.createDrone(droneDTO);
        /* get drone not implemented
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(drone.getId()).toUri();
        return ResponseEntity.created(uri).build();
        */
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Override
    public ResponseEntity<Void> loadMedicationsToDrone(String id,List<String> medicationsCodeList) throws UnSupportedWeightException, DroneNotFoundException, DroneNotReadyException, NoPowerException {
        droneService.loadMedicationsToDrone(id,medicationsCodeList);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<List<MedicationDTO>> getDroneMedications(String id) throws DroneNotFoundException {
        return ResponseEntity.ok(droneService.getDroneMedications(id));
    }

    @Override
    public ResponseEntity<List<DroneDTO>> getAvailableDrones() {
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }

    @Override
    public ResponseEntity<String> getDroneBattery(String id) throws DroneNotFoundException {
        return ResponseEntity.ok(droneService.getDroneBattery(id));
    }

}
