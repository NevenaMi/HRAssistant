package com.intenstask.codingtask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such candidate")
public class CandidateNotFoundException extends Exception{

    public CandidateNotFoundException(String message) {
        super(message);
    }
}
