package com.delivery.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	@JsonProperty("user_token")
	public String token;

	public JwtResponse(String token) {
		this.token = token;
	}
}
