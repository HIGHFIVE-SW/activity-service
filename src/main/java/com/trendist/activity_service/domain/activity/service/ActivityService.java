package com.trendist.activity_service.domain.activity.service;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendist.activity_service.domain.activity.domain.Activity;
import com.trendist.activity_service.domain.activity.domain.ActivityBookmark;
import com.trendist.activity_service.domain.activity.domain.ActivityDocument;
import com.trendist.activity_service.domain.activity.domain.ActivityType;
import com.trendist.activity_service.domain.activity.domain.Keyword;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetAllResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivityGetResponse;
import com.trendist.activity_service.domain.activity.dto.response.ActivitySearchResponse;
import com.trendist.activity_service.domain.activity.dto.response.BookmarkResponse;
import com.trendist.activity_service.domain.activity.repository.ActivityBookmarkRepository;
import com.trendist.activity_service.domain.activity.repository.ActivityRepository;
import com.trendist.activity_service.global.exception.ApiException;
import com.trendist.activity_service.global.feign.user.client.UserServiceClient;
import com.trendist.activity_service.global.response.status.ErrorStatus;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {
	private final ActivityRepository activityRepository;
	private final ActivityBookmarkRepository activityBookmarkRepository;
	private final UserServiceClient userServiceClient;
	private final ElasticsearchOperations esOps;

	public Page<ActivityGetAllResponse> getActivities(
		Keyword keyword,
		ActivityType activityType,
		int page) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();
		Pageable pageable = PageRequest.of(page, 12, Sort.by("startDate").ascending());

		Page<Activity> activities;

		if (keyword != null && activityType != null) {
			activities = activityRepository.findAllByKeywordAndActivityType(keyword, activityType, pageable);
		} else if (keyword != null) {
			activities = activityRepository.findByKeyword(keyword, pageable);
		} else if (activityType != null) {
			activities = activityRepository.findByActivityType(activityType, pageable);
		} else {
			activities = activityRepository.findAll(pageable);
		}

		List<UUID> activityIds = activities.stream()
			.map(Activity::getId)
			.toList();

		List<ActivityBookmark> bookmarks = activityBookmarkRepository.findAllByUserIdAndActivity_IdIn(userId,
			activityIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(ActivityBookmark::getActivity)
			.map(Activity::getId)
			.collect(Collectors.toSet());

		return activities.map(activity ->
			ActivityGetAllResponse.of(activity, bookmarkIds.contains(activity.getId())));
	}

	public ActivityGetResponse getActivity(UUID id) {
		Activity activity = activityRepository.findById(id)
			.orElseThrow(() -> new ApiException(ErrorStatus._ACTIVITY_NOT_FOUND));

		return ActivityGetResponse.from(activity);
	}

	public List<ActivityGetAllResponse> getActivitiesByKeyword(Keyword keyword) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		List<Activity> allActivities = activityRepository.findByKeywordAndEndDateAfter(
			keyword,
			LocalDateTime.now()
		);

		Collections.shuffle(allActivities);
		List<Activity> activities = allActivities.stream()
			.limit(4)
			.collect(Collectors.toList());

		List<UUID> activityIds = activities.stream()
			.map(Activity::getId)
			.toList();

		List<ActivityBookmark> bookmarks = activityBookmarkRepository.findAllByUserIdAndActivity_IdIn(userId,
			activityIds);
		Set<UUID> bookmarkIds = bookmarks.stream()
			.map(ActivityBookmark::getActivity)
			.map(Activity::getId)
			.collect(Collectors.toSet());

		return activities.stream()
			.map(activity -> ActivityGetAllResponse.of(activity, bookmarkIds.contains(activity.getId())))
			.collect(Collectors.toList());
	}

	@Transactional
	public BookmarkResponse toggleBookmark(UUID activityId) {
		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		Activity activity = activityRepository.findById(activityId)
			.orElseThrow(() -> new ApiException(ErrorStatus._ACTIVITY_NOT_FOUND));

		ActivityBookmark bookmark;
		boolean bookmarked;

		if (!activityBookmarkRepository.existsByUserIdAndActivity_Id(userId, activityId)) {
			bookmark = ActivityBookmark.builder()
				.userId(userId)
				.activity(activity)
				.build();

			activityBookmarkRepository.save(bookmark);
			bookmarked = true;
		} else {
			bookmark = activityBookmarkRepository.findByUserIdAndActivity_Id(userId, activityId)
				.orElseThrow(() -> new ApiException(ErrorStatus._BOOKMARK_NOT_FOUND));

			activityBookmarkRepository.deleteByUserIdAndActivity_Id(userId, activityId);
			bookmarked = false;
		}

		return BookmarkResponse.of(bookmark, bookmarked);
	}

	public Page<ActivitySearchResponse> searchActivities(String keyword, int page) {
		Pageable pageable = PageRequest.of(page, 12);

		NativeQuery query = NativeQuery.builder()
			.withQuery(q -> q
				.wildcard(w -> w
					.field("activity_name.keyword")
					.value("*" + keyword + "*")
				)
			)
			.withSort(s -> s.field(f -> f
					.field("created_at.keyword")
					.order(SortOrder.Desc)
				)
			)
			.withPageable(pageable)
			.build();

		SearchHits<ActivityDocument> hits = esOps.search(query, ActivityDocument.class);

		UUID userId = userServiceClient.getMyProfile("").getResult().id();

		List<ActivitySearchResponse> content = hits.getSearchHits().stream()
			.map(hit -> {
				ActivityDocument doc = hit.getContent();

				byte[] bytes = Base64.getDecoder().decode(doc.getId());
				ByteBuffer bb = ByteBuffer.wrap(bytes);
				UUID uuid = new UUID(bb.getLong(), bb.getLong());

				boolean bookmarked = activityBookmarkRepository
					.existsByUserIdAndActivity_Id(userId, uuid);
				return ActivitySearchResponse.of(doc, bookmarked);
			})
			.toList();

		return new PageImpl<>(content, pageable, hits.getTotalHits());
	}
}