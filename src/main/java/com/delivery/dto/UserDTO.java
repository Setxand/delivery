package com.delivery.dto;

import com.delivery.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	public String id;

	@JsonProperty("user_email")
	public String email;

	@JsonProperty("user_password")
	public String password;

	@JsonProperty("user_name")
	public String name;

	@JsonProperty("user_role")
	public User.Role role;
}
