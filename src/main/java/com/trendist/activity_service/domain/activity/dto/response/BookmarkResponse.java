package com.trendist.activity_service.domain.activity.dto.response;

import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;

import lombok.Builder;

@Builder
public record BookmarkResponse(
	UUID bookmarkId,
	UUID userId,
	UUID activityId,
	Boolean bookmarked
) {
	public static BookmarkResponse of(ActivityBookmark activityBookmark, boolean bookmarked) {
		return BookmarkResponse.builder()
			.bookmarkId(activityBookmark.getId())
			.userId(activityBookmark.getUserId())
			.activityId(activityBookmark.getActivity().getId())
			.bookmarked(bookmarked)
			.build();
	}
}
