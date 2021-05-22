package com.example.thepirates.error.exception.common;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {

	OPEN_TIME_EQUALS_CLOSE_TIME(4001, "오픈 시간과 마감 시간이 같습니다.", BAD_REQUEST),
	SHOP_NOT_FOUND(4002, "점포가 존재하지 않습니다.", NOT_FOUND),



	// Common
	RUN_TIME_EXCEPTION(500, "런타임 에러", INTERNAL_SERVER_ERROR),
	NOT_FOUND_EXCEPTION(404, "요청한 리소스가 존재하지 않습니다.", NOT_FOUND),
	INVALID_TYPE_VALUE_EXCEPTION(400, "유효하지 않는 Type의 값입니다. 입력 값을 확인 해주세요.", BAD_REQUEST),
	INVALID_FORMAT_EXCEPTION(400, "유효하지 않는 Type 입니다. Type을 확인 해주세요.", BAD_REQUEST),
	INVALID_INPUT_VALUE(400, "유효하지 않는 입력 값입니다.", BAD_REQUEST),
	METHOD_NOT_SUPPORT(405, "지원하지 않은 HTTP Method 입니다.", METHOD_NOT_ALLOWED)
	;


	private final int status;
	private final String message;
	private final HttpStatus httpStatus;
}
