package com.example.thepirates.controller.response;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.thepirates.error.exception.common.ExceptionStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

	private String message;
	private String status;
	private List<ErrorDetailResponse> errors;

	private ErrorResponse(ExceptionStatus exceptionStatus, List<ErrorDetailResponse> errors) {
		this.message = exceptionStatus.getMessage();
		this.status = String.valueOf(exceptionStatus.getStatus());
		this.errors = errors;
	}

	public static ErrorResponse of(ExceptionStatus code, BindingResult bindingResult) {
		return new ErrorResponse(code, ErrorDetailResponse.of(bindingResult));
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException ex) {
		String value = ex.getValue() == null ? "" : ex.getValue().toString();
		List<ErrorDetailResponse> errors = ErrorDetailResponse.of(ex.getName(), value, ex.getErrorCode());
		return new ErrorResponse(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION, errors);
	}

	public static ErrorResponse of(HttpRequestMethodNotSupportedException ex) {
		String supportedMethods = Arrays.stream(Objects.requireNonNull(ex.getSupportedMethods()))
			.map(String::toString)
			.collect(Collectors.joining(", "));
		List<ErrorDetailResponse> details = ErrorDetailResponse.of(ex.getLocalizedMessage(),
			"입력한 HTTP Method = " + ex.getMethod(),
			"지원 가능한 HTTP Method = " + supportedMethods);
		return new ErrorResponse(ExceptionStatus.METHOD_NOT_SUPPORT, details);
	}
}
