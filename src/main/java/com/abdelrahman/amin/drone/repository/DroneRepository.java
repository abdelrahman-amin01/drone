package com.abdelrahman.amin.drone.repository;

import com.abdelrahman.amin.drone.entity.Drone;
import com.abdelrahman.amin.drone.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone,String> {

    List<Drone> getByStateAndBatteryCapacityGreaterThanEqual(DroneState droneState,Double batteryCap);

}
