package com.abdelrahman.amin.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DroneAlreadyExistException extends Exception {
    public DroneAlreadyExistException(String id) {
        super("Drone with id:" + id + " is already exist.");
    }
}
