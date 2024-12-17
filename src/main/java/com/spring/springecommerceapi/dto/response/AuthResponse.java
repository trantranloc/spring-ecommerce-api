package com.spring.springecommerceapi.dto.response;

public class AuthResponse {
    boolean isAuthenticated;
    private String token;

    public AuthResponse(){}
    public AuthResponse(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}
