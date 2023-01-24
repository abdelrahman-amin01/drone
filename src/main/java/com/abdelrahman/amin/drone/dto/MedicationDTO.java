package com.abdelrahman.amin.drone.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MedicationDTO {

    @NotNull
    @Pattern(regexp = "[A-Z0-9_\\-]*")
    private String code;

    @Pattern(regexp = "[\\w\\-]*")
    private String name;

    @NotNull
    private Double weight;

    private Byte[] image;
}
