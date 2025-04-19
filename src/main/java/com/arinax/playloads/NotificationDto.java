package com.arinax.playloads;

import java.time.LocalDateTime;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class NotificationDto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

   
    private boolean isRead = false;
     
    private UserDto user;

    private LocalDateTime createdAt = LocalDateTime.now();
}
