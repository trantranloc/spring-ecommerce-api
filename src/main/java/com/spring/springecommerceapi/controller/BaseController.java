package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.dto.request.ApiRequest;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiRequest<T>> createApiResponse(ErrorCode errorCode, T result) {
        ApiRequest<T> response =new ApiRequest<>();
                response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        response.setResult(result);

        return ResponseEntity.ok(response);
    }
}
