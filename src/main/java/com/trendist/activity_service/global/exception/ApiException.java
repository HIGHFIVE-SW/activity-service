package com.trendist.activity_service.global.exception;

import com.trendist.activity_service.global.response.dto.ErrorReasonDto;
import com.trendist.activity_service.global.response.status.ErrorStatus;

//발생하는 특정 오류 상황을 명시적으로 표현하기 위함.
public class ApiException extends RuntimeException {

	private final ErrorStatus errorStatus;

	public ApiException(ErrorStatus errorStatus) {
		super(errorStatus.getMessage());
		this.errorStatus = errorStatus;
	}

	public ErrorReasonDto getErrorReason() {
		return this.errorStatus.getReason();
	}

	public ErrorReasonDto getErrorReasonHttpStatus() {
		return this.errorStatus.getReasonHttpStatus();
	}
}
