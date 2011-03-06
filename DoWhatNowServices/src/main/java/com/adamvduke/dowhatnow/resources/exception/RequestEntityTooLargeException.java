package com.adamvduke.dowhatnow.resources.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class RequestEntityTooLargeException extends WebApplicationException {

	private final String requestPath;
	private final int maxRequestLength;

	/**
	 * Create a HTTP 413 (Request Entity Too Large) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 413 response.
	 */
	public RequestEntityTooLargeException( String requestPath, int maxRequestLength ) {

		super();
		this.requestPath = requestPath;
		this.maxRequestLength = maxRequestLength;
	}

	@Override
	public String getMessage() {

		String message = "{\"request\":\"" + requestPath + "\",\"error\":\"Request body too large. Maximum request length is " + maxRequestLength + " bytes\"}";
		return message;
	}
}
