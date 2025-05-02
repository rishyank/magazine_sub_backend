package com.example.magazine.dtos;

import jakarta.validation.constraints.NotBlank;

public class RegisterUserDto {

    @NotBlank(message = "username is required.")
    String username;
    @NotBlank(message = "password is required.")
    String password;
    @NotBlank(message = "email is required.")
    String email;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
