package com.trendist.activity_service.domain.activity.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;

import lombok.Builder;

@Builder
public record ActivityGetAllResponse(
	UUID id,
	String name,
	ActivityType activityType,
	Keyword keyword,
	String siteUrl,
	String imageUrls,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
	public static ActivityGetAllResponse from(Activity activity) {
		return ActivityGetAllResponse.builder()
			.id(activity.getId())
			.name(activity.getName())
			.activityType(activity.getActivityType())
			.keyword(activity.getKeyword())
			.siteUrl(activity.getSiteUrl())
			.imageUrls(activity.getImageUrl())
			.startDate(activity.getStartDate())
			.endDate(activity.getEndDate())
			.build();
	}
}
