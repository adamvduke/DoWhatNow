package com.adamvduke.jersey.ext.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public class MethodNotSupportedException extends WebApplicationException {

	private static final String defaultMessage = "Unsupported http method";
	private final String message;

	/**
	 * Create a HTTP 405 (Method Not Supported) exception.
	 * 
	 * @param method
	 *            the http method used
	 */
	public MethodNotSupportedException() {

		super();
		this.message = String.format( defaultMessage );
	}

	/**
	 * Create a HTTP 405 (Method Not Supported) exception.
	 * 
	 * @param method
	 *            the http method used
	 */
	public MethodNotSupportedException( String method ) {

		super();
		this.message = defaultMessage + ": " + method;
	}

	/**
	 * Create a HTTP 405 (Method Not Supported) exception.
	 * 
	 * @param method
	 *            the http method used
	 */
	public MethodNotSupportedException( String method, String message ) {

		super();
		this.message = message + ": " + method;
	}

	@Override
	public String getMessage() {

		return message;
	}
}
