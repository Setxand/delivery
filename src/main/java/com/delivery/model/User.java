package com.delivery.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

	public enum Role {
		USER,
		COURIER,
		ADMIN
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	@Column(unique = true)
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String name;
}
