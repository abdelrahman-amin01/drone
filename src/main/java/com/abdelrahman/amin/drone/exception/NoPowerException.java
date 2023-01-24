package com.abdelrahman.amin.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoPowerException extends Exception {
    public NoPowerException(String id) {
        super("Drone with id:" + id + " has not enough battery to be loaded");
    }
}
