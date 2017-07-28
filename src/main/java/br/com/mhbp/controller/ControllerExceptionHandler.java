package br.com.mhbp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.mhbp.common.ErrorResponse;
import br.com.mhbp.exception.MissingOperatorException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ErrorResponse handleThrowable(final Throwable ex) {
		if (ex instanceof MissingOperatorException) {
			return  new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		return new ErrorResponse("An unexpected internal server error occured", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
