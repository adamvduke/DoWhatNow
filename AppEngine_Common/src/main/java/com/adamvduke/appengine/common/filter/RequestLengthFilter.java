package com.adamvduke.appengine.common.filter;

import com.adamvduke.appengine.common.exception.RequestEntityTooLargeException;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Singleton
public class RequestLengthFilter implements ContainerRequestFilter {

	private static final Integer defaultMaximumRequestLength = Integer.valueOf( 300 );

	@Override
	public ContainerRequest filter( ContainerRequest request ) {

		int maxRequestLength;

		try {
			// XXX: make sure to set the appengine.maximum-request-length property
			String maxRequestLengthString = System.getProperty( "appengine.maximum-request-length" );
			maxRequestLength = Integer.parseInt( maxRequestLengthString );
		}
		catch ( NumberFormatException e ) {
			maxRequestLength = defaultMaximumRequestLength;
		}
		if ( request.getMethod().equals( "POST" ) ) {

			String contentLengthHeader = request.getHeaderValue( "Content-Length" );
			int contentLength = Integer.parseInt( contentLengthHeader );
			if ( contentLength > maxRequestLength ) {

				throw new RequestEntityTooLargeException( maxRequestLength );
			}
		}
		return request;
	}
}
