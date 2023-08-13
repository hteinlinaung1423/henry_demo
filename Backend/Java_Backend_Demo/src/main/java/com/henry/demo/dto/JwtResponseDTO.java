package com.henry.demo.dto;

import java.util.List;

public class JwtResponseDTO {

	private Integer id;
	private String username;
	private String email;
	private String accessToken;
	private List<String> roles;
	private String refreshToken;
	
	public JwtResponseDTO(Integer id, String username, String email, String accessToken,List<String> roles,String refreshToken) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.accessToken = accessToken;
		this.roles=roles;
		this.refreshToken=refreshToken;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	

}
