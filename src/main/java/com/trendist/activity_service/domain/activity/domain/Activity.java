package com.trendist.activity_service.domain.activity.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.activity_service.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "activities")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "activity_id")
	private UUID id;

	@Column(name = "activity_name")
	private String name;

	@Column(name = "site_url")
	private String siteUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "activity_type")
	private ActivityType activityType;

	@Enumerated(EnumType.STRING)
	@Column(name = "keyword")
	private Keyword keyword;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "activity_image_url")
	private String imageUrl;

	@Lob
	@Column(name = "activity_content", columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	private ActivitySite activitySite;
}
