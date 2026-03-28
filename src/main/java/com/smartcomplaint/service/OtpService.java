package com.smartcomplaint.service;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
    public String generateOtp() {
        int otp = (int)(Math.random() * 9000) + 1000; // 4 digit OTP
        return String.valueOf(otp);
    }

    public void sendOtp(String mobileNumber, String otp) {
        // यहाँ आप SMS API (Twilio, Fast2SMS आदि) integrate कर सकते हैं
        System.out.println("Sending OTP " + otp + " to " + mobileNumber);
    }
}

