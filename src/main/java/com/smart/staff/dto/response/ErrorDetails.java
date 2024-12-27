package com.smart.staff.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class ErrorDetails {
    private String code;
    private String message;
    private List<FieldError> fieldErrors;

    public ErrorDetails(String code, String message) {
        this.code = code;
        this.message = message;
        this.fieldErrors = new ArrayList<>();
    }

    public ErrorDetails(String code, String message, List<FieldError> fieldErrors) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}

