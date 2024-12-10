package com.smart.staff.advice;

import com.smart.staff.exception.ErrorDetails;
import com.smart.staff.exception.Status;
import lombok.*;

@Setter
@Getter
@Builder
public class ApiResponse<T> {
    private Status status;
    private T data;
    private ErrorDetails error;

    public ApiResponse(Status status, T data, ErrorDetails error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> failure(ErrorDetails error) {
        return new ApiResponse<>(Status.FAILED, null, error);
    }

    public static <T> ApiResponse<T> partialSuccess(T data, ErrorDetails error) {
        return new ApiResponse<>(Status.PARTIAL_SUCCESS, data, error);
    }

    // Getters and Setters
}

