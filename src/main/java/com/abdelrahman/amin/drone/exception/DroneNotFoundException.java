package com.abdelrahman.amin.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends Exception {
    public DroneNotFoundException(String id) {
        super("Drone with id:" + id + " Not found.");
    }
}
