package com.waes.rodrigues.carlos.assignment.controller.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.waes.rodrigues.carlos.assignment.bundle.MessageBundle;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffException;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@Autowired
	private MessageBundle bundle;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataAccessException.class)
	public @ResponseBody ExceptionInfo dataAccessException(Exception e) {
		return new ExceptionInfo(e.getMessage(), bundle.getString("error.msg.unknown"));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class})
	public @ResponseBody ExceptionInfo runtimeException(Exception e) {
		return new ExceptionInfo(e.getMessage(), bundle.getString("error.msg.unknown"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public @ResponseBody ExceptionInfo illegalStateException(IllegalStateException e) {
		return new ExceptionInfo(e.getMessage());
	}	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody ExceptionInfo illegalArgumentException(IllegalArgumentException e) {
		return new ExceptionInfo(e.getMessage());
	}	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DiffException.class)
	public @ResponseBody ExceptionInfo diffException(DiffException e) {
		return new ExceptionInfo(e.getMessage());
	}
}
