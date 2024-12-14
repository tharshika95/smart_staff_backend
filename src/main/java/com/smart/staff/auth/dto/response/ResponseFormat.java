package com.smart.staff.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ResponseFormat<T>
{
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public static <T> ResponseFormat<T> empty()
    {
        return success(null);
    }

    public static <T> ResponseFormat<T> success(T data)
    {
        return ResponseFormat.<T>builder()
                .message("SUCCESS!")
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseFormat<T> success(T data, String message)
    {
        return ResponseFormat.<T>builder()
                .message(message)
                .data(data)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public static <T> ResponseFormat<T> error(String message, HttpStatus status)
    {
        return ResponseFormat.<T>builder()
                .message(message)
                .httpStatus(status)
                .build();
    }

    public static <T> ResponseFormat<T> error(T data, String message, HttpStatus status)
    {
        return ResponseFormat.<T>builder()
                .message(message)
                .data(data)
                .httpStatus(status)
                .build();
    }


    //https://medium.com/@aedemirsen/generic-api-response-with-spring-boot-175434952086
}