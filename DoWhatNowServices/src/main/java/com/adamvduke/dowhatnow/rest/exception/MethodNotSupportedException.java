package com.adamvduke.dowhatnow.rest.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class MethodNotSupportedException extends WebApplicationException {

	private final String requestPath;
	private final String method;

	/**
	 * Create a HTTP 405 (Method Not Supported) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 405 response.
	 */
	public MethodNotSupportedException( String requestPath, String method ) {

		super();
		this.requestPath = requestPath;
		this.method = method;
	}

	@Override
	public String getMessage() {

		String message = "{\"request\":\"" + requestPath + "\",\"error\":\"Unsupported method: " + method + "\"}\n";
		return message;
	}
}
