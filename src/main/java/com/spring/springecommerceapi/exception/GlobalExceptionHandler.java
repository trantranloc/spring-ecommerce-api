package com.spring.springecommerceapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.spring.springecommerceapi.request.ApiRequest;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiRequest<Void>> runtimeException(RuntimeException e) {
        ApiRequest<Void> apiRequest = new ApiRequest<>();
        apiRequest.SetCode(404);
        apiRequest.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiRequest<Void>> appException(AppException e) {
        ApiRequest<Void> apiRequest = new ApiRequest<>();
        ErrorCode errorCode = e.getErrorCode();
        apiRequest.SetCode(errorCode.getCode());
        apiRequest.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRequest<Void>> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ApiRequest<Void> apiRequest = new ApiRequest<>();
        String key = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(key);
        apiRequest.SetCode(errorCode.getCode());
        apiRequest.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRequest);
    }
}
