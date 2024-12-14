package com.smart.staff.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class SignUpException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public SignUpException(String message) {
        super(String.format("Failed for : %s", message));
    }

}
