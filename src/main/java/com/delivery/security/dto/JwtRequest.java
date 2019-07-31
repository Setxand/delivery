package com.delivery.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@JsonProperty("user_email")
	public String email;

	@JsonProperty("user_password")
	public String password;

}
