package com.delivery.security.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;


	public String token;

	public JwtResponse(String token) {
		this.token = token;
	}
}
