package com.raghav.xclone.common.exception;

import com.raghav.xclone.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.module.ResolutionException;
import java.util.List;

public class GlobalExceptionHandler{
    @ExceptionHandler(ResolutionException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(
            ResolutionException exception
    ){
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                exception.getMessage(),
                null,
                List.of(exception.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Validation failed",
                null,
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }
}
