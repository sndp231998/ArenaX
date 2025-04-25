package com.arinax.services;

import java.util.List;

import com.arinax.playloads.RedeemRequestDto;

public interface RedeemRequestService {

	List<RedeemRequestDto> getUserApprovedRequests(Integer userId);

	List<RedeemRequestDto> getUserPendingRequests(Integer userId);

	List<RedeemRequestDto> getAllPendingRequests();

	void approveRedeemRequest(Long requestId);

	RedeemRequestDto RedeemBalance(RedeemRequestDto redeemRequestDto, Integer userId, Double balance);

}
