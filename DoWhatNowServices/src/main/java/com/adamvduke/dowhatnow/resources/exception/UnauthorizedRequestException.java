package com.adamvduke.dowhatnow.resources.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class UnauthorizedRequestException extends WebApplicationException {

	private final String requestPath;

	/**
	 * Create a HTTP 401 (Unauthorized Request) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 401 response.
	 */
	public UnauthorizedRequestException( String requestPath ) {

		super();
		this.requestPath = requestPath;
	}

	@Override
	public String getMessage() {

		String message = "{\"request\":\"" + requestPath + "\",\"error\":\"Not Authorized\"}";
		return message;
	}
}
