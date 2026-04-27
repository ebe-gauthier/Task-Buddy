package com.taskbuddy.taskbuddyapi.dto;

public class AuthDtos {

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public String id;
        public String name;
        public String email;

        public AuthResponse(String token, String id, String name, String email) {
            this.token = token;
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
