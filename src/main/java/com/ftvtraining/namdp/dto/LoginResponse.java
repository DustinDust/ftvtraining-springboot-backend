package com.ftvtraining.namdp.dto;

public class LoginResponse {
	private String accessToken;
	private String refreshToken;
	private Long expAt;

	public Long getExpAt() {
		return this.expAt;
	}

	public void setExpAt(Long expAt) {
		this.expAt = expAt;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public LoginResponse(String accessToken, String refreshToken, Long expAt) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expAt = expAt;
	}
}
