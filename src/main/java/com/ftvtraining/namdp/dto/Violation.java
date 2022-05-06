package com.ftvtraining.namdp.dto;

import lombok.Data;

@Data
public class Violation {
	public Violation(String string, String message2) {
		this.name = string;
		this.message = message2;
		// TODO Auto-generated constructor stub
	}

	String name;
	String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
