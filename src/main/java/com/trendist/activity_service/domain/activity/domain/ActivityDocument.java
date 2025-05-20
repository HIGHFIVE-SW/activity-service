package com.trendist.activity_service.domain.activity.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Document(indexName = "mysql-trendist.trendist.activities")
@Builder
@Getter
public class ActivityDocument {
	@Id
	private String id;

	@MultiField(
		mainField = @Field(name = "name", type = FieldType.Text, analyzer = "standard"),
		otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword)
		}
	)
	private String name;

	@Field(name = "image_url",type = FieldType.Keyword)
	private String imageUrl;

	@Field(name = "site_url", type = FieldType.Keyword)
	private String siteUrl;

	@Field(name = "keyword",type = FieldType.Keyword)
	private String keyword;

	@Field(name = "activity_type",type = FieldType.Keyword)
	private String activityType;

	@Field(name = "start_date", type = FieldType.Keyword)
	private String startDate;

	@Field(name = "end_date", type = FieldType.Keyword)
	private String endDate;

	@Field(name = "created_at", type = FieldType.Keyword)
	private String createAt;
}
