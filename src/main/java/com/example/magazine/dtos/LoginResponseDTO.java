package com.example.magazine.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable {

    private String token;
    private long expiresIn;
    private User user;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, long expiresIn, User user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private long id;
        private String username;
        private String email;

        public User() {
        }

        public User(long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @JsonProperty("username")
        public String getUserName() {
            return username;
        }

        public void setUserName(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
