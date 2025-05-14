package com.trendist.activity_service.global.feign.user.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.trendist.activity_service.global.feign.user.dto.UserProfileResponse;
import com.trendist.activity_service.global.response.ApiResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {

	@GetMapping("/users/profile")
	ApiResponse<UserProfileResponse> getMyProfile(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
	);

	@GetMapping("/users/profile/{userId}")
	ApiResponse<UserProfileResponse> getUserProfile(
		@PathVariable(name = "userId") UUID userId
	);
}
