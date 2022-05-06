package com.ftvtraining.namdp.models;

import java.util.List;

/*
 * This class is only a mock, not a real entity. Used for JWTs authentication demo.
 * */
public class MockUser {
	private String username;
	private String password;
	private List<String> roles;
	private String refreshToken;
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void removeRefreshToken() {
		this.refreshToken = "";
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public MockUser(String username, String password, List<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	public List<String> getRoles() {
		return this.roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
