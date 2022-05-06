package com.ftvtraining.namdp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
	public ErrorResponse(boolean b, List<Violation> vlist) {
		this.success = b;
		this.errors = vlist;
		// TODO Auto-generated constructor stub
	}

	boolean success;

	List<Violation> errors;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Violation> getErrors() {
		return errors;
	}

	public void setErrors(List<Violation> errors) {
		this.errors = errors;
	}
	
	
}
