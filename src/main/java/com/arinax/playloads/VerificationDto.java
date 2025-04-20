package com.arinax.playloads;

import java.time.Instant;

import lombok.Data;

@Data
public class VerificationDto {

	private String email;
    private String otp;
    private Instant timestamp;
	
	
}
