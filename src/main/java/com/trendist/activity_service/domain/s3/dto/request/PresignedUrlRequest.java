package com.trendist.activity_service.domain.s3.dto.request;

import java.util.List;

public record PresignedUrlRequest(
	List<String> imageNames
) {
}
