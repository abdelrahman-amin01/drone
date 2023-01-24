package com.abdelrahman.amin.drone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Medication")
public class Medication implements Serializable {

    @Id
    @Pattern(regexp = "[A-Z0-9_\\-]*")
    private String code;

    @Pattern(regexp = "[\\w\\-]*")
    private String name;

    @NotNull
    private Double weight;

    private Byte[] image;


}
