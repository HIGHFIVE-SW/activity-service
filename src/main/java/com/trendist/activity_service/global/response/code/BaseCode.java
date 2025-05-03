package com.trendist.activity_service.global.response.code;

import com.trendist.activity_service.global.response.dto.ReasonDto;

//성공 또는 일반 응답에 관련한 코드와 메시지를 전달하기 위한 것을 정의합니다.
public interface BaseCode {

	public ReasonDto getReason();

	public ReasonDto getReasonHttpStatus();
}
