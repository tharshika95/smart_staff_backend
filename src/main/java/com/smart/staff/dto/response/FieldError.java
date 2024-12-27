package com.smart.staff.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
public class FieldError {
    private String field;
    private String message;

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}

