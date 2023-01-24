package com.abdelrahman.amin.drone.service;


import com.abdelrahman.amin.drone.dto.DroneDTO;
import com.abdelrahman.amin.drone.dto.MedicationDTO;
import com.abdelrahman.amin.drone.entity.Drone;
import com.abdelrahman.amin.drone.exception.*;

import java.util.List;

public interface DroneService {

    Drone createDrone(DroneDTO droneDTO) throws DroneAlreadyExistException;

    Drone loadMedicationsToDrone(String id, List<String> medicationsCodeList) throws DroneNotFoundException, DroneNotReadyException, UnSupportedWeightException, NoPowerException;

    List<MedicationDTO> getDroneMedications(String id) throws DroneNotFoundException;

    List<DroneDTO> getAvailableDrones();

    String getDroneBattery(String id) throws DroneNotFoundException;

}
