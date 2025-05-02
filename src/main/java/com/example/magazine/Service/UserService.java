package com.example.magazine.Service;

import com.example.magazine.Entity.User;
import com.example.magazine.Exception.CustomException;
import com.example.magazine.Repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
       return  userRepository.findByUsername(username).orElseThrow(()->
        new CustomException("No users found", HttpStatus.NOT_FOUND));
    }
    public List<User> allUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                throw new CustomException("No users found", HttpStatus.NO_CONTENT); // Optional
            }
            return users;
        } catch (Exception e) {
            throw new CustomException("Error retrieving users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("User not found",HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException("Old password is incorrect",HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
