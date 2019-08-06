package com.delivery.controller;

import com.delivery.exception.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponse handleAccessDenied(AccessDeniedException ex) {
		log.warn("Access denied", ex);
		return new ErrorResponse(ex.getMessage(), "ACCESS_DENIED");
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
		log.warn("Illegal argument", ex);
		return new ErrorResponse(ex.getMessage(), "ILLEGAL_ARGUMENT");
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ErrorResponse handleAuthException(InternalAuthenticationServiceException ex) {
		log.warn("Auth error", ex);
		return new ErrorResponse(ex.getMessage(), "AUTH_FAILED");
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleInternalException(Exception ex) {
		log.warn("Internal error", ex);
		return new ErrorResponse(ex.getMessage(), "INTERNAL_ERROR");
	}
}
