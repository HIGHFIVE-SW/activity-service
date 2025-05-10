package com.trendist.activity_service.domain.activity.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetBookmarkedResponse;
import com.trendist.activity_service.domain.activity.repository.ActivityBookmarkRepository;
import com.trendist.activity_service.domain.activity.repository.ActivityRepository;
import com.trendist.activity_service.global.exception.ApiException;
import com.trendist.activity_service.global.response.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

	private final ActivityBookmarkRepository bookmarkRepository;
	private final ActivityRepository activityRepository;

	// 북마크 추가
	public boolean addBookmark(UUID userId, UUID activityId) {
		Activity activity = activityRepository.findById(activityId)
			.orElseThrow(() -> new ApiException(ErrorStatus._ACTIVITY_NOT_FOUND));

		if (bookmarkRepository.existsByUserIdAndActivity(userId, activity)) {
			return false;
		}

		ActivityBookmark bookmark = ActivityBookmark.of(userId, activity);
		bookmarkRepository.save(bookmark);
		return true;
	}

	// 북마크 삭제
	public boolean removeBookmark(UUID userId, UUID activityId) {
		Activity activity = activityRepository.findById(activityId)
			.orElseThrow(() -> new ApiException(ErrorStatus._ACTIVITY_NOT_FOUND));

		Optional<ActivityBookmark> bookmarkOpt = bookmarkRepository.findByUserIdAndActivity(userId, activity);

		if (bookmarkOpt.isEmpty()) {
			return false;
		}

		bookmarkRepository.delete(bookmarkOpt.get());
		return true;
	}

	// 북마크한 활동글 조회
	@Transactional(readOnly = true)
	public Page<ActivityGetBookmarkedResponse> getBookmarkedActivities(UUID userId, int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
		Page<ActivityBookmark> bookmarks = bookmarkRepository.findAllByUserId(userId, pageable);

		return bookmarks.map(bookmark -> ActivityGetBookmarkedResponse.from(bookmark.getActivity()));
	}

}
