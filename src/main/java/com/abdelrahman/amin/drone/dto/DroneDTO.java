package com.abdelrahman.amin.drone.dto;

import com.abdelrahman.amin.drone.enums.DroneModel;
import com.abdelrahman.amin.drone.enums.DroneState;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneDTO {

    @NotNull
    private String id;

    private DroneModel model;

    @Max(500)
    private Double weightLimit;

    @Max(100)
    private Double batteryCapacity;

    private DroneState state;

    private List<MedicationDTO> medications;
}
