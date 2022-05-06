package com.ftvtraining.namdp.exceptions;

public class RefreshTokenException extends RuntimeException {
	String message;
	String type;

	public RefreshTokenException(String message, String type) {
		// TODO Auto-generated constructor stub
		super();
		this.message= message;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
		
	}
}
