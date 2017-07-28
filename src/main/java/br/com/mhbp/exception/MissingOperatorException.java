package br.com.mhbp.exception;

public class MissingOperatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingOperatorException(String message) {
		super(message);
	}
}
