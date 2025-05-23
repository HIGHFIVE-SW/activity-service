package com.trendist.activity_service.domain.activity.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByKeywordResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByTypeResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivitySearchResponse;
import com.trendist.activity_service.domain.activity.dto.response.BookmarkResponse;
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
		summary = "특정 활동 정보 조회",
		description = "특정 활동의 정보를 조회합니다."
	)
	@GetMapping("/{activityId}")
	public ApiResponse<ActivityGetResponse> getActivity(@PathVariable(name = "activityId") UUID activityId) {
		return ApiResponse.onSuccess(activityService.getActivity(activityId));
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

	@Operation(
		summary = "활동 북마크 등록/해제",
		description = "사용자가 특정 활동글을 북마크 등록하거나 해제합니다."
	)
	@PostMapping("{id}/bookmark")
	public ApiResponse<BookmarkResponse> toggleBookmark(@PathVariable(name = "id") UUID id) {
		return ApiResponse.onSuccess(activityService.toggleBookmark(id));
	}

	@Operation(
		summary = "활동 검색",
		description = "사용자가 활동에 특정 단어를 입력하여 해당하는 활동을 검색합니다"
	)
	@GetMapping("/search")
	public ApiResponse<Page<ActivitySearchResponse>> searchActivities(
		@RequestParam String keyword,
		@RequestParam(defaultValue = "0") int page
	) {
		return ApiResponse.onSuccess(activityService.searchActivities(keyword, page));
	}
}
