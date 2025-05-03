package com.trendist.activity_service.global.response.code;

import com.trendist.activity_service.global.response.dto.ErrorReasonDto;

//에러 관련한 코드와 메시지를 전달하기 위한 것을 정의합니다.
//ErrorStatus 등 에러 관련한 enum 이나 클래스가 일정하게 정보를 제공할 수 있게 합니다.
public interface BaseErrorCode {

	public ErrorReasonDto getReason();

	public ErrorReasonDto getReasonHttpStatus();
}
