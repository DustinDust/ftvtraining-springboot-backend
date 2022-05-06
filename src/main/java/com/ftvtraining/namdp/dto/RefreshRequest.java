package com.ftvtraining.namdp.dto;

public class RefreshRequest {
	private String refreshToken;
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public void setRefreshToken(String token) {
		this.refreshToken = token;
	}
}
