package com.adamvduke.dowhatnow.servlet.filter;

import com.adamvduke.dowhatnow.resources.exception.RequestEntityTooLargeException;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Singleton
public class RequestLengthFilter implements ContainerRequestFilter {

	@Override
	public ContainerRequest filter( ContainerRequest request ) {

		String maxRequestLengthString = System.getProperty( "dowhatnow.maximum-request-length" );
		int maxRequestLength = Integer.parseInt( maxRequestLengthString );
		if ( request.getMethod().equals( "POST" ) ) {

			String contentLengthHeader = request.getHeaderValue( "Content-Length" );
			int contentLength = Integer.parseInt( contentLengthHeader );
			if ( contentLength > maxRequestLength ) {

				throw new RequestEntityTooLargeException( request.getPath(), maxRequestLength );
			}
		}
		return request;
	}
}
