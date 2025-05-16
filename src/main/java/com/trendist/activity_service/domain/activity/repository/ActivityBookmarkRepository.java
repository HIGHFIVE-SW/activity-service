package com.trendist.activity_service.domain.activity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;

public interface ActivityBookmarkRepository extends JpaRepository<ActivityBookmark, UUID> {
	Optional<ActivityBookmark> findByUserIdAndActivity_Id(UUID userId, UUID activityId);

	Boolean existsByUserIdAndActivity_Id(UUID userId, UUID activityId);

	void deleteByUserIdAndActivity_Id(UUID userId, UUID activityId);

	Page<ActivityBookmark> findAllByUserId(UUID userId, Pageable pageable);

	List<ActivityBookmark> findAllByUserIdAndActivity_IdIn(UUID userId, List<UUID> activityIds);

}
