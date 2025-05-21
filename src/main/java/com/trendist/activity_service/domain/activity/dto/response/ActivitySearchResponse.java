package com.trendist.activity_service.domain.activity.dto.response;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import com.trendist.activity_service.domain.activity.domain.ActivityDocument;

import lombok.Builder;

@Builder
public record ActivitySearchResponse(
	UUID id,
	String name,
	String siteUrl,
	String imageUrl,
	String keyword,
	String activityType,
	String startDate,
	String endDate,
	Boolean bookmarked
) {
	public static ActivitySearchResponse of(ActivityDocument activityDocument, Boolean bookmarked) {
		byte[] bytes = Base64.getDecoder().decode(activityDocument.getId());
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		UUID uuid = new UUID(bb.getLong(), bb.getLong());

		return ActivitySearchResponse.builder()
			.id(uuid)
			.name(activityDocument.getName())
			.siteUrl(activityDocument.getSiteUrl())
			.imageUrl(activityDocument.getImageUrl())
			.keyword(activityDocument.getKeyword())
			.activityType(activityDocument.getActivityType())
			.startDate(activityDocument.getStartDate())
			.endDate(activityDocument.getEndDate())
			.bookmarked(bookmarked)
			.build();
	}
}
