package com.skyfervor.framework.trace;

public class CatchErrorException extends Exception {

	private static final long serialVersionUID = 7585653059401640505L;
	
	public CatchErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
