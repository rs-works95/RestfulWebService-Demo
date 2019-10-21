package com.restful.web.services.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
	
	private Date timestamp;
	private String message;
	private String details;
	
}
