package com.example.thepirates.error.exception.common;

import org.springframework.http.HttpStatus;

public class ThePiratesException extends RuntimeException{

	private final ExceptionStatus exceptionStatus;

	public ThePiratesException(ExceptionStatus exceptionStatus) {
		super(exceptionStatus.getMessage());
		this.exceptionStatus = exceptionStatus;
	}

	public HttpStatus getHttpStatus() {
		return exceptionStatus.getHttpStatus();
	}

	public String getStatus() {
		return String.valueOf(exceptionStatus.getStatus());
	}
}
