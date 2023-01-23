package com.abdelrahman.amin.drone.repository;

import com.abdelrahman.amin.drone.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone,String> {


}
