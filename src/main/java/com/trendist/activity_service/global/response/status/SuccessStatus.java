package com.trendist.activity_service.global.response.status;

import org.springframework.http.HttpStatus;

import com.trendist.activity_service.global.response.code.BaseCode;
import com.trendist.activity_service.global.response.dto.ReasonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//성공 응답에 대한 상태 코드를 정의합니다.
@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	// 일반적인 응답
	_OK(HttpStatus.OK, "COMMON200", "성공입니다."),

	// 북마크 관련 응답
	_BOOKMARK_ADDED(HttpStatus.CREATED, "BOOKMARK201", "북마크가 추가되었습니다."),
	_BOOKMARK_REMOVED(HttpStatus.OK, "BOOKMARK200", "북마크가 삭제되었습니다."),
	_BOOKMARK_ALREADY_EXISTS(HttpStatus.OK, "BOOKMARK202", "이미 북마크에 추가된 활동입니다."),
	_BOOKMARK_NOT_FOUND(HttpStatus.OK, "BOOKMARK204", "북마크를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDto getReason() {
		return ReasonDto.builder()
			.isSuccess(true)
			.code(code)
			.message(message)
			.build();
	}

	@Override
	public ReasonDto getReasonHttpStatus() {
		return ReasonDto.builder()
			.httpStatus(httpStatus)
			.isSuccess(true)
			.code(code)
			.message(message)
			.build();
	}
}
