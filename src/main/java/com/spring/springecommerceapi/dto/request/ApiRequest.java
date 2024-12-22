package com.spring.springecommerceapi.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.springecommerceapi.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRequest<T> {
    private int code;
    private String message;
    private T result;

    public ApiRequest(ErrorCode invalidUsernameOrPassword, String message, Object result) {
    }

    public ApiRequest(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
