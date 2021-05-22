package com.example.thepirates.controller.response;

import com.example.thepirates.error.exception.common.ThePiratesException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {

	private final String status;
	private final String message;

	public static ExceptionResponse of(ThePiratesException exception) {
		return new ExceptionResponse(exception.getStatus(), exception.getMessage());
	}
}
