package com.example.thepirates.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.thepirates.controller.response.ErrorResponse;
import com.example.thepirates.controller.response.ExceptionResponse;
import com.example.thepirates.error.exception.common.ExceptionStatus;
import com.example.thepirates.error.exception.common.ThePiratesException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerController {

	private void errorLogging(Exception ex) {
		log.error("Exception = {} , message = {}", ex.getClass().getSimpleName(),
				ex.getLocalizedMessage());
	}

	/**
	 * CustomException
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ThePiratesException.class)
	private ResponseEntity<ExceptionResponse> handleStatusException (ThePiratesException exception) {
		ExceptionResponse response = ExceptionResponse.of(exception);
		HttpStatus status = exception.getHttpStatus();
		errorLogging(exception);
		return new ResponseEntity<>(response, status);
	}


	/**
	 * @Valid,@Validated 검증으로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		errorLogging(exception);
		return ErrorResponse.of(ExceptionStatus.INVALID_INPUT_VALUE,
				exception.getBindingResult());
	}

	/**
	 * @RequestParam enum type 불일치로 binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		errorLogging(exception);
		return ErrorResponse.of(exception);
	}

	/**
	 * @ModelAttribute 나 RequestBody 로 @Valid 로 Binding 못할 때
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	private ErrorResponse handleBindException(BindException exception) {
		errorLogging(exception);
		return ErrorResponse.of(ExceptionStatus.INVALID_TYPE_VALUE_EXCEPTION,
				exception.getBindingResult());
	}

	/**
	 * 지원하지 않는 HTTP METHOD 를 요청 했을때
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ErrorResponse handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {
		return ErrorResponse.of(exception);
	}

}
