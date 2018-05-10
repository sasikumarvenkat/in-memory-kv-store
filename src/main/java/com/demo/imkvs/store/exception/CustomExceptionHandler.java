package com.demo.imkvs.store.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom Exception Handler for Spring Web to manipulate the exceptions
 * 
 * @author Sasikumar Venkatesh
 *
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class);
	private static final int GENERIC_APP_ERROR_CODE = 5000;

	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {

		if (ex instanceof BusinessException) {
			errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
		} else {
			errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // defaults
		}
	}

	/**
	 * Handler for {@link BusinessException}
	 * 
	 * @param ex
	 * @param request
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<ErrorMessage> handleBusinessExceptions(BusinessException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		setHttpStatus(ex, errorMessage);
		errorMessage.setCode(ex.getCode());
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		errorMessage.setLink(ex.getLink());
		errorMessage.setDeveloperMessage(ex.getDeveloperMessage());
		LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handler for {@link SystemException}
	 * 
	 * @param ex
	 * @param request
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(SystemException.class)
	public final ResponseEntity<ErrorMessage> handleSystemExceptions(SystemException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		setHttpStatus(ex, errorMessage);
		errorMessage.setCode(GENERIC_APP_ERROR_CODE);
		errorMessage.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		// TODO Check if debug and then add development message
		errorMessage.setDeveloperMessage(errorStackTrace.toString());
		// Set the technical reference document
		errorMessage.setLink(null);
		LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
