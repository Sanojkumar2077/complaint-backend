package com.smartcomplaint.service;

import com.smartcomplaint.entity.User;
import com.smartcomplaint.repository.UserRepository;
import com.smartcomplaint.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private OtpService otpService;
    @Autowired private JwtUtil jwtUtil;

    public User registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String loginWithOtp(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            String otp = otpService.generateOtp();
            user.setOtp(otp);
            userRepository.save(user);
            otpService.sendOtp(user.getMobileNumber(), otp);
            return "OTP sent to registered mobile number";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

   /* public String validateOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ String compare safe
        if (String.valueOf(user.getOtp()).equals(String.valueOf(otp))) {
            return jwtUtil.generateToken(user.getEmail());
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }*/
    public String validateOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("DB OTP: [" + user.getOtp() + "]");
        System.out.println("Entered OTP: [" + otp + "]");
        System.out.println("Length DB OTP: " + user.getOtp().length());
        System.out.println("Length Entered OTP: " + otp.length());

        if (user.getOtp() != null && otp != null &&
            user.getOtp().trim().equals(otp.trim())) {
            System.out.println("OTP matched successfully!");
            return jwtUtil.generateToken(user.getEmail());
        } else {
            System.out.println("OTP mismatch!");
            throw new RuntimeException("Invalid OTP");
        }
    }

}


