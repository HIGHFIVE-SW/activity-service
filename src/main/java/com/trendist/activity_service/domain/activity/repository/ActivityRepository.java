package com.trendist.activity_service.domain.activity.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
	Page<Activity> findByActivityType(ActivityType activityType, Pageable pageable);

	Page<Activity> findByKeyword(Keyword keyword, Pageable pageable);

	Page<Activity> findAllByKeywordAndActivityType(
		Keyword keyword,
		ActivityType activityType,
		Pageable pageable
	);
}
