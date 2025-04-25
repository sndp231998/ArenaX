package com.arinax.playloads;

import java.time.LocalDateTime;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedeemRequestDto {

    private Long id;

    
    private Integer userId;

    private Double amount; // kati redeem garna khojeko

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, APPROVED, REJECTED

    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
    
    private String paymentGetway;
    private String accountNo;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    // getters & setters
}

