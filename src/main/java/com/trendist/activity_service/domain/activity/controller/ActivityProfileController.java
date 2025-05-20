package com.trendist.activity_service.domain.activity.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllBookmarkedResponse;
import com.trendist.activity_service.domain.activity.service.ActivityProfileService;
import com.trendist.activity_service.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/activities")
public class ActivityProfileController {
	private final ActivityProfileService activityProfileService;

	@Operation(
		summary = "북마크한 활동글 조회",
		description = "사용자가 북마크한 모든 활동글을 조회합니다."
	)
	@GetMapping("/bookmark")
	public ApiResponse<Page<ActivityGetAllBookmarkedResponse>> getAllActivitiesBookmarked(
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(activityProfileService.getAllActivitiesBookmarked(page));
	}
}
