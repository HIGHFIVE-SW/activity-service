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
	_OK(HttpStatus.OK, "COMMON200", "성공입니다.");

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
