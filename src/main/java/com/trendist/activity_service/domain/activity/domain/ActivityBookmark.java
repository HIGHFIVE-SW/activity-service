package com.trendist.activity_service.domain.activity.domain;

import java.util.UUID;

import com.trendist.activity_service.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "bookmarks",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "activity_id"})
	}
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBookmark extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "bookmark_id")
	private UUID id;

	@Column(name = "user_id")
	private UUID userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id")
	private Activity activity;

	public static ActivityBookmark of(UUID userId, Activity activity) {
		return ActivityBookmark.builder()
			.userId(userId)
			.activity(activity)
			.build();
	}
}
