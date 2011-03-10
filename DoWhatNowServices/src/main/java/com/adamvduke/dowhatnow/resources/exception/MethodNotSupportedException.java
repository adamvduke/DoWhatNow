package com.adamvduke.dowhatnow.resources.exception;

@SuppressWarnings( "serial" )
public class MethodNotSupportedException extends DoWhatNowExceptionBase {

	private static final String defaultMessage = "Unsupported method: %s";

	/**
	 * Create a HTTP 405 (Method Not Supported) exception.
	 * 
	 * @param requestPath
	 *            the incoming request path that caused the exception
	 * @param method
	 *            the http method used
	 */
	public MethodNotSupportedException( String requestPath, String method ) {

		super( requestPath, defaultMessage, method );
	}
}
