package com.abdelrahman.amin.drone.entity;

import com.abdelrahman.amin.drone.enums.DroneModel;
import com.abdelrahman.amin.drone.enums.DroneState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
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

    @Max(500)
    private Double weightLimit;

    @Max( 100)
    @NotNull
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
