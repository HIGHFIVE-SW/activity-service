package com.trendist.activity_service.global.response.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

//성공 응답 시 클라이언트에게 반환할 정보를 담는 DTO 입니다.
@Getter
@Builder
public class ReasonDto {

	private HttpStatus httpStatus;
	private final boolean isSuccess;
	private final String code;
	private final String message;

	public boolean getIsSuccess() {
		return isSuccess;
	}
}
