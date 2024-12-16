package com.compression.image.global.response;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        String message,
        T body,
        LocalDateTime timestamp
) {
    public ApiResponse(String message, T body) {
        this(message, body, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("success", null);
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>("success", body);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(message, null);
    }
}