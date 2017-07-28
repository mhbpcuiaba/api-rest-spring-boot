package br.com.mhbp.common;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private final HttpStatus status;
	private String message;

	public ErrorResponse(final String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public static ErrorResponse of(final String message, HttpStatus status) {
		return new ErrorResponse(message, status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
}
