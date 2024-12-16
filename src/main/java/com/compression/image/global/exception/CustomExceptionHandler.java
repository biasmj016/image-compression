package com.compression.image.global.exception;

import com.compression.image.global.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.compression.image.global.response.ApiResponse.fail;


@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<String>> handleInvalidRequestException(Exception ex) {
        logger.error("[{}] contents :{}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.badRequest().body(fail(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("[MethodArgumentNotValidException] contents :{}", ex.getMessage());
        return ResponseEntity.badRequest().body(fail(ex.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse<String>> handleSQLException(SQLException ex) {
        logger.error("[SQLException] contents :{}", ex.getMessage());
        return ResponseEntity.internalServerError().body(fail(ex.getMessage()));
    }
}