package com.minify.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private int status;
    private T data;
    private String error;

    public ApiResponse() {}

    public ApiResponse(LocalDateTime timestamp, int status, T data, String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(int status, T data) {
        return new ApiResponse<>(LocalDateTime.now(), status, data, null);
    }

    public static <T> ApiResponse<T> error(int status, String error) {
        return new ApiResponse<>(LocalDateTime.now(), status, null, error);
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public T getData() { return data; }
    public String getError() { return error; }
}