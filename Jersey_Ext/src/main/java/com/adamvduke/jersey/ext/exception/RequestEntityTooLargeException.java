package com.adamvduke.jersey.ext.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class RequestEntityTooLargeException extends WebApplicationException {

	private static final String defaultMessage = "Request body too large. Maximum request length is %d bytes";
	private final String message;

	/**
	 * Create a HTTP 413 (Request Entity Too Large) exception.
	 * 
	 * @param maxRequestLength
	 *            the maximum length of an incoming request
	 */
	public RequestEntityTooLargeException( int maxRequestLength ) {

		super();
		this.message = String.format( defaultMessage, maxRequestLength );
	}

	@Override
	public String getMessage() {

		return message;
	}
}
