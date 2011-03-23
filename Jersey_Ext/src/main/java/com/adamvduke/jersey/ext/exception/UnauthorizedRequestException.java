package com.adamvduke.jersey.ext.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class UnauthorizedRequestException extends WebApplicationException {

	private static final String defaultMessage = "Not Authorized";
	private final String message;

	/**
	 * Create a HTTP 401 (Unauthorized Request) exception.
	 */
	public UnauthorizedRequestException() {

		super();
		this.message = defaultMessage;
	}

	/**
	 * Create a HTTP 401 (Unauthorized Request) exception.
	 * 
	 * @param message
	 *            the message that should be returned as the error
	 */
	public UnauthorizedRequestException( String message ) {

		super();
		this.message = message;
	}

	@Override
	public String getMessage() {

		return message;
	}
}
