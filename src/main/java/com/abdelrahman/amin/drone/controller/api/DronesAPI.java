package com.abdelrahman.amin.drone.controller.api;


import com.abdelrahman.amin.drone.dto.DroneDTO;
import com.abdelrahman.amin.drone.dto.MedicationDTO;
import com.abdelrahman.amin.drone.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@RequestMapping("/api/v1/drones")
@Validated
public interface DronesAPI {

    @PostMapping
    ResponseEntity<Void> createDrone(@Valid @RequestBody DroneDTO droneDTO) throws DroneAlreadyExistException;

    @PostMapping("/{id}/medications")
    ResponseEntity<Void> loadMedicationsToDrone(@PathVariable String id,@RequestBody @NotEmpty List<String> medicationsCodeList) throws UnSupportedWeightException, DroneNotFoundException, DroneNotReadyException, NoPowerException;

    @GetMapping("/{id}/medications")
    ResponseEntity<List<MedicationDTO>> getDroneMedications(@PathVariable String id) throws DroneNotFoundException;

    @GetMapping("/available")
    ResponseEntity<List<DroneDTO>> getAvailableDrones();

    @GetMapping("/{id}/battery")
    ResponseEntity<String> getDroneBattery(@PathVariable String id) throws DroneNotFoundException;


}
