package com.ftvtraining.namdp.dto;

public class RefreshResponse {
	private String accessToken;
	private String refreshToken;
	private Long expAt;

	public Long getExpAt() {
		return this.expAt;
	}

	public void setExpAt(Long x) {
		this.expAt = x;
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
	public RefreshResponse(String accessToken, String refreshToken, Long exp) {
		super();
		this.accessToken = accessToken;
		this.expAt = exp;
		this.refreshToken = refreshToken;
	}
}
