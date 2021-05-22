package com.example.thepirates.error.exception.shop;

import com.example.thepirates.error.exception.common.ExceptionStatus;
import com.example.thepirates.error.exception.common.ThePiratesException;

public class OpenTimeEqualsCloseTimeException extends ThePiratesException {

	public OpenTimeEqualsCloseTimeException() {
		super(ExceptionStatus.OPEN_TIME_EQUALS_CLOSE_TIME);
	}
}
