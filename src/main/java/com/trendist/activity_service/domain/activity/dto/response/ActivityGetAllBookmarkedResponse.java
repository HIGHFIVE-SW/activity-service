package com.trendist.activity_service.domain.activity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;

import lombok.Builder;

@Builder
public record ActivityGetAllBookmarkedResponse(
	UUID bookmarkId,
	UUID activityId,
	String name,
	ActivityType activityType,
	Keyword keyword,
	String siteUrl,
	String imageUrl,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
	public static ActivityGetAllBookmarkedResponse from(ActivityBookmark bookmark) {
		return ActivityGetAllBookmarkedResponse.builder()
			.activityId(bookmark.getActivity().getId())
			.name(bookmark.getActivity().getName())
			.activityType(bookmark.getActivity().getActivityType())
			.keyword(bookmark.getActivity().getKeyword())
			.startDate(bookmark.getActivity().getStartDate())
			.endDate(bookmark.getActivity().getEndDate())
			.bookmarkId(bookmark.getId())
			.build();
	}
}
