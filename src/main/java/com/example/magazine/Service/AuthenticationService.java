package com.example.magazine.Service;

import com.example.magazine.Entity.User;
import com.example.magazine.Exception.CustomException;
import com.example.magazine.Repository.UserRepository;
import com.example.magazine.dtos.LoginUserDto;
import com.example.magazine.dtos.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(RegisterUserDto input) {
        Optional<User> existingUser= userRepository.findByUsername(input.getUsername());
        if (existingUser.isPresent()) {
            throw  new CustomException("User with name '"+ input.getUsername() +"' Already Exist.", HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(input.getPassword());
        User user = new User(input.getUsername(), encodedPassword , input.getEmail());
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
