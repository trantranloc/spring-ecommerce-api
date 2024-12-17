package com.spring.springecommerceapi.dto.response;

public class AuthResponse {
    private boolean isAuthenticated;
    private String token;

    // Constructor mặc định
    public AuthResponse() {}

    // Constructor với tham số
    public AuthResponse(boolean isAuthenticated, String token) {
        this.isAuthenticated = isAuthenticated;
        this.token = token;
    }

    // Getter và Setter
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Lớp Builder
    public static class Builder {
        private boolean isAuthenticated;
        private String token;

        public Builder isAuthenticated(boolean isAuthenticated) {
            this.isAuthenticated = isAuthenticated;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(isAuthenticated, token);
        }
    }
}
