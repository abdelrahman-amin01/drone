package com.abdelrahman.amin.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DroneNotReadyException extends Exception {
    public DroneNotReadyException(String id) {
        super("Drone with id:" + id + " not ready to use.");
    }
}
