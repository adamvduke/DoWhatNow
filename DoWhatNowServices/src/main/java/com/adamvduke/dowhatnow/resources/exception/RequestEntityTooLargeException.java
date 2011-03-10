package com.adamvduke.dowhatnow.resources.exception;

@SuppressWarnings( "serial" )
public class RequestEntityTooLargeException extends DoWhatNowExceptionBase {

	private static final String defaultMessage = "Request body too large. Maximum request length is %d bytes";

	/**
	 * Create a HTTP 413 (Request Entity Too Large) exception.
	 * 
	 * @param requestPath
	 *            the incoming request path that caused the exception
	 */
	public RequestEntityTooLargeException( String requestPath, int maxRequestLength ) {

		super( requestPath, defaultMessage, maxRequestLength );
	}
}
