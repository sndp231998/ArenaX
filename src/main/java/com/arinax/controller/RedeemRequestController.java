package com.arinax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arinax.playloads.RedeemRequestDto;
import com.arinax.services.RedeemRequestService;

@RestController
@RequestMapping("/api/v1/redeem")
public class RedeemRequestController {

	@Autowired
    private RedeemRequestService redeemService;

	
	// ✅ 1. User redemption request (create redeem request)
    @PostMapping("/user/{userId}")
    public ResponseEntity<RedeemRequestDto> redeemBalance(
            @PathVariable Integer userId,
            @RequestParam Double amount) {

        RedeemRequestDto dto = redeemService.RedeemBalance(new RedeemRequestDto(), userId, amount);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // ✅ 2. Admin approves a redeem request
    @PutMapping("/admin/approve/{requestId}")
    public ResponseEntity<String> approveRedeem(@PathVariable Long requestId) {
        redeemService.approveRedeemRequest(requestId);
        return ResponseEntity.ok("Redeem request approved successfully.");
    }
	
	
    // Admin: Get all pending
    @GetMapping("/admin/pending")
    public ResponseEntity<List<RedeemRequestDto>> getAllPending() {
        return ResponseEntity.ok(redeemService.getAllPendingRequests());
    }

    // User: Get pending by userId
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<RedeemRequestDto>> getUserPending(@PathVariable Integer userId) {
        return ResponseEntity.ok(redeemService.getUserPendingRequests(userId));
    }

    // User: Get approved by userId
    @GetMapping("/user/{userId}/approved")
    public ResponseEntity<List<RedeemRequestDto>> getUserApproved(@PathVariable Integer userId) {
        return ResponseEntity.ok(redeemService.getUserApprovedRequests(userId));
    }
}
