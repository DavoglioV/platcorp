package com.platcorp.exception;

public abstract class BusinessException extends Exception {

	private static final long serialVersionUID = 3385138515231324814L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
