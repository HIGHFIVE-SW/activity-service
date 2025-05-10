package com.trendist.activity_service.domain.activity.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetBookmarkedResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByKeywordResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByTypeResponse;
import com.trendist.activity_service.domain.activity.service.ActivityService;
import com.trendist.activity_service.domain.activity.service.BookmarkService;
import com.trendist.activity_service.global.response.ApiResponse;
import com.trendist.activity_service.global.response.status.ErrorStatus;
import com.trendist.activity_service.global.response.status.SuccessStatus;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {
	private final ActivityService activityService;
	private final BookmarkService bookmarkService;

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

	@Operation(
		summary = "활동글 북마크 추가",
		description = "사용자가 활동글을 북마크에 추가합니다."
	)
	@PostMapping("/{activityId}/bookmark")
	public ResponseEntity<ApiResponse<Void>> bookmarkActivity(
		@PathVariable UUID activityId,
		@RequestHeader("User-Id") UUID userId) {
		boolean added = bookmarkService.addBookmark(userId, activityId);
		if (added) {
			return ResponseEntity.status(SuccessStatus._BOOKMARK_ADDED.getHttpStatus())
				.body(ApiResponse.of(SuccessStatus._BOOKMARK_ADDED, null));
		} else {
			return ResponseEntity.status(ErrorStatus._BOOKMARK_ALREADY_EXISTS.getHttpStatus())
				.body(ApiResponse.onFailure(ErrorStatus._BOOKMARK_ALREADY_EXISTS.getCode(),
					ErrorStatus._BOOKMARK_ALREADY_EXISTS.getMessage(), null));
		}
	}

	@Operation(
		summary = "활동글 북마크 삭제",
		description = "사용자가 북마크한 활동글을 북마크에서 삭제합니다."
	)
	@DeleteMapping("/{activityId}/bookmark")
	public ResponseEntity<ApiResponse<Void>> removeBookmark(
		@PathVariable UUID activityId,
		@RequestHeader("User-Id") UUID userId) {
		boolean removed = bookmarkService.removeBookmark(userId, activityId);
		if (removed) {
			return ResponseEntity.status(SuccessStatus._BOOKMARK_REMOVED.getHttpStatus())
				.body(ApiResponse.of(SuccessStatus._BOOKMARK_REMOVED, null));
		} else {
			return ResponseEntity.status(ErrorStatus._BOOKMARK_NOT_FOUND.getHttpStatus())
				.body(ApiResponse.onFailure(ErrorStatus._BOOKMARK_NOT_FOUND.getCode(),
					ErrorStatus._BOOKMARK_NOT_FOUND.getMessage(), null));
		}
	}

	@Operation(
		summary = "북마크한 활동글 조회",
		description = "사용자가 북마크한 모든 활동글을 조회합니다."
	)
	@GetMapping("/bookmarks")
	public ApiResponse<Page<ActivityGetBookmarkedResponse>> getBookmarkedActivities(
		@RequestHeader("User-Id") UUID userId,
		@RequestParam(defaultValue = "0") int page) {
		return ApiResponse.onSuccess(bookmarkService.getBookmarkedActivities(userId, page));
	}
}
