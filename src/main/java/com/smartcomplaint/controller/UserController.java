package com.smartcomplaint.controller;

import com.smartcomplaint.entity.User;
import com.smartcomplaint.service.UserService;
import com.smartcomplaint.dto.OtpRequest;   // ✅ DTO import
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ Login with email + password (OTP sent to mobile)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        String message = userService.loginWithOtp(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(message);
    }

    // ✅ Validate OTP (email + otp in body)
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpRequest request) {
        try {
            String token = userService.validateOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

}
