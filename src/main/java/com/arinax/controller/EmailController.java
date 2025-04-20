package com.arinax.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.arinax.services.impl.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-otp")
    public String sendOtp(@RequestParam("to") String to) {
        String otp = emailService.generateOtp();
        String subject = "Your OTP Code";
        String message = "Your OTP code is: " + otp;

        emailService.sendOtpEmail(to, subject, message);

        return "OTP sent to " + to + " : " + otp;
    }
}
