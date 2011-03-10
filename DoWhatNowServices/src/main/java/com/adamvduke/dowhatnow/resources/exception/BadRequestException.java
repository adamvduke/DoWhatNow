package com.adamvduke.dowhatnow.resources.exception;

@SuppressWarnings( "serial" )
public class BadRequestException extends DoWhatNowExceptionBase {

	private static final String defaultMessage = "Bad Request";

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 * 
	 * @param requestPath
	 *            the path for the incoming request that caused the exception
	 */
	public BadRequestException( String requestPath ) {

		super( requestPath, defaultMessage );
	}

	/**
	 * Create an HTTP 400 (Bad Request) exception
	 * 
	 * @param requestPath
	 *            the path for the incoming request that caused the exception
	 * @param message
	 *            the message that should be returned as the error
	 */
	public BadRequestException( String requestPath, String message ) {

		super( requestPath, message );
	}
}
