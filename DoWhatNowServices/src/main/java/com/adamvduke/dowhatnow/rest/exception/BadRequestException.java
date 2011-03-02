package com.adamvduke.dowhatnow.rest.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class BadRequestException extends WebApplicationException {

	private final String requestPath;

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 400 response.
	 */
	public BadRequestException( String requestPath ) {

		super();
		this.requestPath = requestPath;
	}

	@Override
	public String getMessage() {

		String message = "{\"request\":\"" + requestPath + "\",\"error\":\"Bad Request\"}\n";
		return message;
	}
}
