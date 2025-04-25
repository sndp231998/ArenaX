package com.arinax.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinax.entities.RedeemRequest;


public interface RedeemRequestRepo extends JpaRepository<RedeemRequest, Long>{
	// For admin – get all pending
    List<RedeemRequest> findByStatus(RedeemRequest.Status status);

    // For user – get all pending by userId
    List<RedeemRequest> findByUserIdAndStatus(Integer userId, RedeemRequest.Status status);

    // For user – get all approved
    List<RedeemRequest> findByUserIdAndStatusOrderByProcessedAtDesc(Integer userId, RedeemRequest.Status status);
}
