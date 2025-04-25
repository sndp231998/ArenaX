package com.arinax.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RedeemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Double amount; // kati redeem garna khojeko

    private String paymentGetway;
    private String accountNo;
    
    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, APPROVED, REJECTED

    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    // getters & setters
}
