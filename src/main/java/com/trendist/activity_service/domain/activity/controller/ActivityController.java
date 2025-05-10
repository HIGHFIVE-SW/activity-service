package com.trendist.activity_service.domain.activity.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByKeywordResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByTypeResponse;
import com.trendist.activity_service.domain.activity.service.ActivityService;
import com.trendist.activity_service.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {
	private final ActivityService activityService;

	@Operation(
		summary = "활동글 전체 조회",
		description = "전체 활동글을 조회합니다."
	)
	@GetMapping
	public ApiResponse<Page<ActivityGetAllResponse>> getAllActivities(@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(activityService.getAllActivities(page));
	}

	@Operation(
		summary = "특정 종류 활동글 조회",
		description = "사용자가 특정 종류의 활동글을 조회합니다."
	)
	@GetMapping("/type/{activityType}")
	public ApiResponse<Page<ActivityGetByTypeResponse>> getActivitiesByType(
		@PathVariable ActivityType activityType,
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(activityService.getActivitiesByType(activityType, page));
	}

	@Operation(
		summary = "특정 키워드 활동글 조회",
		description = "사용자가 특정 키워드에 해당하는 활동글을 조회합니다."
	)
	@GetMapping("/keyword/{keyword}")
	public ApiResponse<Page<ActivityGetByKeywordResponse>> getActivitiesByKeyword(
		@PathVariable Keyword keyword,
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(activityService.getActivitiesByKeyword(keyword, page));
	}

}
