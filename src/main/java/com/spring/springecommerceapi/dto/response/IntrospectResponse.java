package com.spring.springecommerceapi.dto.response;

public class IntrospectResponse {
    private boolean valid;

    public IntrospectResponse() {
    }

    public IntrospectResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    // Lớp Builder
    public static class Builder {
        private boolean valid;

        public Builder valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public IntrospectResponse build() {
            return new IntrospectResponse(valid);
        }
    }
}
