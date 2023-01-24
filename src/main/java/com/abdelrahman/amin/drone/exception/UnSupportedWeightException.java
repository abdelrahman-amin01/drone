package com.abdelrahman.amin.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UnSupportedWeightException extends Exception {
    public UnSupportedWeightException(String id, Double weightLimit) {
        super("Drone with id:" + id + " can't hold this weight, Max weight is:" + weightLimit);
    }
}
