package com.delivery.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoginSession {

	@Id
	private String id;
	private String userId;
	private boolean disabled;
	private LocalDateTime createdAt = LocalDateTime.now();

	public LoginSession(String userId) {
		this.userId = userId;
	}
}
