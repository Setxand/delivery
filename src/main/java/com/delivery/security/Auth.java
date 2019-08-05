package com.delivery.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auth {

	public static void user() {
		checkRole("ROLE_USER");
	}

	public static void courier() {
		checkRole("ROLE_COURIER");
	}

	public static void admin() {
		checkRole("ROLE_ADMIN");
	}

	private static void checkRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String roleForCheck = authentication.getAuthorities().toString();
		if (!roleForCheck.contains(role)) throw new AccessDeniedException("Access denied");
	}
}
