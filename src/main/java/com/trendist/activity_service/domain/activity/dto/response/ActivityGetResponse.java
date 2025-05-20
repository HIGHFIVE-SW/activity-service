package com.trendist.activity_service.domain.activity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;

import lombok.Builder;

@Builder
public record ActivityGetResponse(
	UUID id,
	String name,
	String siteUrl,
	Keyword keyword,
	ActivityType activityType,
	LocalDateTime startDate,
	LocalDateTime endDate,
	String imageUrl
) {
	public static ActivityGetResponse from(Activity activity) {
		return ActivityGetResponse.builder()
			.id(activity.getId())
			.name(activity.getName())
			.siteUrl(activity.getSiteUrl())
			.keyword(activity.getKeyword())
			.activityType(activity.getActivityType())
			.startDate(activity.getStartDate())
			.endDate(activity.getEndDate())
			.imageUrl(activity.getImageUrl())
			.build();
	}
}
