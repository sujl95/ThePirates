package com.example.thepirates.controller.response;

import com.example.thepirates.error.exception.common.ThePiratesException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponseInfo {
	private final String status;
	private final String message;

	public static ExceptionResponseInfo of(ThePiratesException thePiratesException) {
		return new ExceptionResponseInfo(thePiratesException.getStatus(), thePiratesException.getMessage());
	}
}
