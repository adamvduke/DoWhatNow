package com.adamvduke.dowhatnow.resources.exception;

@SuppressWarnings( "serial" )
public class UnauthorizedRequestException extends DoWhatNowExceptionBase {

	private static final String defaultMessage = "Not Authorized";

	/**
	 * Create a HTTP 401 (Unauthorized Request) exception.
	 * 
	 * @param requestPath
	 *            the incoming request path that caused the exception
	 */
	public UnauthorizedRequestException( String requestPath ) {

		super( requestPath, defaultMessage );
	}
}
