package com.study.experience.exception;

import lombok.Data;

@Data
public class BrickException extends RuntimeException {

	private String errorCode;

	public BrickException(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
