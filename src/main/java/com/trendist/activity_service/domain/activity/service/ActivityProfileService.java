package com.trendist.activity_service.domain.activity.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllBookmarkedResponse;
import com.trendist.activity_service.domain.activity.repository.ActivityBookmarkRepository;
import com.trendist.activity_service.global.feign.user.client.UserServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityProfileService {
	private final UserServiceClient userServiceClient;
	private final ActivityBookmarkRepository activityBookmarkRepository;

	public Page<ActivityGetAllBookmarkedResponse> getAllActivitiesBookmarked(int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

		return activityBookmarkRepository.findAllByUserId(userId, pageable)
			.map(ActivityGetAllBookmarkedResponse::from);
	}
}
