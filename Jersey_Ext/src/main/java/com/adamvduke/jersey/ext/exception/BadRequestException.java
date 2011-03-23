package com.adamvduke.jersey.ext.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class BadRequestException extends WebApplicationException {

	private static final String defaultMessage = "Bad Request";
	private final String message;

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 */
	public BadRequestException() {

		super();
		this.message = defaultMessage;
	}

	/**
	 * Create an HTTP 400 (Bad Request) exception
	 * 
	 * @param message
	 *            the message that should be returned as the error
	 */
	public BadRequestException( String message ) {

		super();
		this.message = message;
	}

	@Override
	public String getMessage() {

		return message;
	}
}
