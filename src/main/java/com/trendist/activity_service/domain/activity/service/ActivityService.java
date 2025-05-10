package com.trendist.activity_service.domain.activity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByKeywordResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetByTypeResponse;
import com.trendist.activity_service.domain.activity.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {
	private final ActivityRepository activityRepository;

	// 모든 활동글 조회
	public Page<ActivityGetAllResponse> getAllActivities(int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
		return activityRepository.findAll(pageable)
			.map(ActivityGetAllResponse::from);
	}

	// 특정 타입의 활동글 조회
	public Page<ActivityGetByTypeResponse> getActivitiesByType(ActivityType activityType, int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
		return activityRepository.findByActivityType(activityType, pageable)
			.map(ActivityGetByTypeResponse::from);
	}

	// 특정 키워드의 활동글 조회
	public Page<ActivityGetByKeywordResponse> getActivitiesByKeyword(Keyword keyword, int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
		return activityRepository.findByKeyword(keyword, pageable)
			.map(ActivityGetByKeywordResponse::from);
	}
}
