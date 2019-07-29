package com.delivery.security.dto;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;


	public String email;
	public String password;

}
