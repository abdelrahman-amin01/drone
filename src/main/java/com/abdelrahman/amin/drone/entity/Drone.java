package com.abdelrahman.amin.drone.entity;

import com.abdelrahman.amin.drone.enums.DroneModel;
import com.abdelrahman.amin.drone.enums.DroneState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Drone")
public class Drone implements Serializable {

    @Id
    @Column(length = 100)
    private String id;

    @Enumerated(value = EnumType.STRING)
    private DroneModel model;

    @Size(max = 500)
    private Double weightLimit;
    @Size(max = 100)
    private Double batteryCapacity;

    @Enumerated(value = EnumType.STRING)
    private DroneState state;

    @ManyToMany
    @JoinTable(
            joinColumns = { @JoinColumn(name = "drone_id") },
            inverseJoinColumns = { @JoinColumn(name = "medication_code") }
    )
    private List<Medication> medications;



}
