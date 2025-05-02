package com.example.magazine.Controller;

import com.example.magazine.Entity.User;
import com.example.magazine.Service.AuthenticationService;
import com.example.magazine.Service.JwtService;
import com.example.magazine.dtos.LoginResponseDTO;
import com.example.magazine.dtos.LoginResponseDTO;
import com.example.magazine.dtos.LoginUserDto;
import com.example.magazine.dtos.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return authenticationService.registerUser(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        //create the inner User DTO and set it
        LoginResponseDTO.User userDto = new LoginResponseDTO.User(
                authenticatedUser.getId(),
                authenticatedUser.getUsername(),
                authenticatedUser.getEmail()
        );
        loginResponse.setUser(userDto);

        return ResponseEntity.ok(loginResponse);
    }
}


