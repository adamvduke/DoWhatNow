package com.adamvduke.dowhatnow.resources.exception;

import javax.ws.rs.WebApplicationException;

@SuppressWarnings( "serial" )
public abstract class DoWhatNowExceptionBase extends WebApplicationException {

	private final String requestPath;
	private final String message;
	private final Object[] formatParams;

	private static final String defaultMessage = "Bad Request";
	private static final String messageFormat = "{\"request\":\"%s\",\"error\":\"%s\"}";

	/**
	 * Create a generic exception
	 * 
	 * @param requestPath
	 */
	protected DoWhatNowExceptionBase( String requestPath ) {

		super();
		this.requestPath = requestPath;
		this.message = defaultMessage;
		this.formatParams = null;
	}

	/**
	 * Create a generic exception that keeps the requestPath and a message
	 * 
	 * @param requestPath
	 *            the path for the incoming request that caused the exception
	 * @param message
	 *            the message that should be returned as the error
	 */
	protected DoWhatNowExceptionBase( String requestPath, String message ) {

		super();
		this.requestPath = requestPath;
		this.message = message;
		this.formatParams = null;
	}

	/**
	 * Create a generic exception that keeps the requestPath and a message
	 * 
	 * @param requestPath
	 *            the path for the incoming request that caused the exception
	 * @param message
	 *            the message that should be returned as the error
	 * @param formatParams
	 *            optional format parameters for the message
	 */
	protected DoWhatNowExceptionBase( String requestPath, String message, Object... formatParams ) {

		super();
		this.requestPath = requestPath;
		this.message = message;
		this.formatParams = formatParams;
	}

	@Override
	public String getMessage() {

		String formattedMessage = message;
		if ( formatParams != null ) {
			formattedMessage = String.format( message, formatParams );
		}
		String completeMessage = String.format( messageFormat, requestPath, formattedMessage );
		return completeMessage;
	}
}
