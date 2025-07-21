package com.ecommerce.ecommerce.dto.auth;

public record AuthResponseDto(String token) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseDto build() {
            return new AuthResponseDto(token);
        }
    }
}
