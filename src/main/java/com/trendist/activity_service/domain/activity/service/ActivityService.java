package com.trendist.activity_service.domain.activity.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByKeywordResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByTypeResponse;
import com.trendist.activity_service.domain.activity.dto.response.BookmarkResponse;
import com.trendist.activity_service.domain.activity.repository.ActivityBookmarkRepository;
import com.trendist.activity_service.domain.activity.repository.ActivityRepository;
import com.trendist.activity_service.global.exception.ApiException;
import com.trendist.activity_service.global.feign.user.client.UserServiceClient;
import com.trendist.activity_service.global.response.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {
	private final ActivityRepository activityRepository;
	private final ActivityBookmarkRepository activityBookmarkRepository;
	private final UserServiceClient userServiceClient;

	// 모든 활동글 조회
	public Page<ActivityGetAllResponse> getAllActivities(int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("createdAt").descending());

		Page<Activity> activities = activityRepository.findAll(pageable);
		List<UUID> acitivityIds = activities.stream()
			.map(Activity::getId)
			.toList();

		List<ActivityBookmark> bookmarks = activityBookmarkRepository.findAllByUserIdAndActivity_IdIn(userId, acitivityIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(ActivityBookmark::getActivity)
			.map(Activity::getId)
			.collect(Collectors.toSet());

		return activities.map(activity ->
			ActivityGetAllResponse.of(activity, bookmarkIds.contains(activity.getId())));
	}

	// 특정 타입의 활동글 조회
	public Page<ActivityGetByTypeResponse> getActivitiesByType(ActivityType activityType, int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("createdAt").descending());

		Page<Activity> activities = activityRepository.findByActivityType(activityType, pageable);
		List<UUID> activityIds = activities.stream()
			.map(Activity::getId)
			.toList();

		List<ActivityBookmark> bookmarks = activityBookmarkRepository.findAllByUserIdAndActivity_IdIn(userId,
			activityIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(ActivityBookmark::getActivity)
			.map(Activity::getId)
			.collect(Collectors.toSet());

		return activities.map(activity ->
			ActivityGetByTypeResponse.of(activity, bookmarkIds.contains(activity.getId())));
	}

	// 특정 키워드의 활동글 조회
	public Page<ActivityGetByKeywordResponse> getActivitiesByKeyword(Keyword keyword, int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("createdAt").descending());

		Page<Activity> activities = activityRepository.findByKeyword(keyword, pageable);
		List<UUID> activityIds = activities.stream()
			.map(Activity::getId)
			.toList();

		List<ActivityBookmark> bookmarks = activityBookmarkRepository.findAllByUserIdAndActivity_IdIn(userId,
			activityIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(ActivityBookmark::getActivity)
			.map(Activity::getId)
			.collect(Collectors.toSet());

		return activities.map(activity ->
			ActivityGetByKeywordResponse.of(activity, bookmarkIds.contains(activity.getId())));
	}

	@Transactional
	public BookmarkResponse toggleBookmark(UUID activityId) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Activity activity = activityRepository.findById(activityId)
			.orElseThrow(() -> new ApiException(ErrorStatus._ACTIVITY_NOT_FOUND));

		ActivityBookmark bookmark;
		boolean bookmarked;

		if (!activityBookmarkRepository.existsByUserIdAndActivity_Id(userId, activityId)) {
			bookmark = ActivityBookmark.builder()
				.userId(userId)
				.activity(activity)
				.build();

			activityBookmarkRepository.save(bookmark);
			bookmarked = true;
		} else {
			bookmark = activityBookmarkRepository.findByUserIdAndActivity_Id(userId, activityId)
				.orElseThrow(() -> new ApiException(ErrorStatus._BOOKMARK_NOT_FOUND));

			activityBookmarkRepository.deleteByUserIdAndActivity_Id(userId, activityId);
			bookmarked = false;
		}

		return BookmarkResponse.of(bookmark, bookmarked);
	}
}
