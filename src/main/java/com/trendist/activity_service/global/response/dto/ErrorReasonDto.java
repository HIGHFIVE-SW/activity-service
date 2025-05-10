package com.trendist.activity_service.global.response.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

//에러 발생 시 클라이언트에게 반환할 에러의 세부정보를 담는 DTO 입니다.
//ErrorStatus 에서 만들어져서 ApiException 에서 사용됩니다.
@Getter
@Builder
public class ErrorReasonDto {

	private final boolean isSuccess;
	private final String code;
	private final String message;
	private HttpStatus httpStatus;

	public boolean getIsSuccess() {
		return isSuccess;
	}
}
