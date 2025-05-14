package com.trendist.activity_service.domain.activity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.Keyword;

import lombok.Builder;

@Builder
public record ActivityGetBookmarkedResponse(
	UUID id,
	String name,
	Keyword keyword,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
	public static ActivityGetBookmarkedResponse from(Activity activity) {
		return ActivityGetBookmarkedResponse.builder()
			.id(activity.getId())
			.name(activity.getName())
			.keyword(activity.getKeyword())
			.startDate(activity.getStartDate())
			.endDate(activity.getEndDate())
			.build();
	}
}
