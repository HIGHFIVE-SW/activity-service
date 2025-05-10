package com.trendist.activity_service.domain.s3.service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.trendist.activity_service.domain.s3.dto.response.PresignedUrlResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresignedUrlService {
	private final AmazonS3 amazonS3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Value("${aws.s3.prefix}")
	private String prefix;

	@Transactional
	public PresignedUrlResponse getPreSignedUrl(String originalFilename) {
		String path = createPath(originalFilename);
		GeneratePresignedUrlRequest request = getGeneratePreSignedUrlRequest(bucketName, path);
		URL presignedUrl = amazonS3.generatePresignedUrl(request);

		return PresignedUrlResponse.from(presignedUrl.toString());
	}

	private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
		return new GeneratePresignedUrlRequest(bucket, fileName)
			.withMethod(HttpMethod.PUT)
			.withExpiration(getPreSignedUrlExpiration());
	}

	private Date getPreSignedUrlExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 2; // 2분 유효기간
		expiration.setTime(expTimeMillis);
		return expiration;
	}

	private String createFileId() {
		return UUID.randomUUID().toString();
	}

	private String createPath(String fileName) {
		String fileId = createFileId();
		return String.format("%s/%s", prefix, fileId + fileName);
	}
}
