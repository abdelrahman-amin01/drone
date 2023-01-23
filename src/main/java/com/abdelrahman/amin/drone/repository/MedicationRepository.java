package com.abdelrahman.amin.drone.repository;

import com.abdelrahman.amin.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication,String> {


}
