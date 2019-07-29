package com.delivery.exception;

public class ErrorResponse {

	public String message;
	public String code;
	public Object data;

	public ErrorResponse(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public ErrorResponse(String message, String code, Object data) {
		this.message = message;
		this.code = code;
		this.data = data;
	}
}
