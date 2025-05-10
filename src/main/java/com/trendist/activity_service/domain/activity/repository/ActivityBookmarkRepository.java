package com.trendist.activity_service.domain.activity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;

public interface ActivityBookmarkRepository extends JpaRepository<ActivityBookmark, UUID> {
	boolean existsByUserIdAndActivity(UUID userId, Activity activity);

	Optional<ActivityBookmark> findByUserIdAndActivity(UUID userId, Activity activity);

	Page<ActivityBookmark> findAllByUserId(UUID userId, Pageable pageable);
}
