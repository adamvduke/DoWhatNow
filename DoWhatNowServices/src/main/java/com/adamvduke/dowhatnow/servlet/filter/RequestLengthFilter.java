package com.adamvduke.dowhatnow.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class RequestLengthFilter implements Filter {

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain ) throws IOException, ServletException {

		String maxRequestLengthString = System.getProperty( "dowhatnow.maximum-request-length" );
		int maxRequestLength = Integer.parseInt( maxRequestLengthString );

		// if the request's content is too long stop the filter chain
		if ( request.getContentLength() > maxRequestLength ) {
			handleRequestEntityTooLarge( (HttpServletRequest) request, (HttpServletResponse) response, maxRequestLength );
			return;
		}
		filterChain.doFilter( request, response );
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init( FilterConfig config ) throws ServletException {

	}

	/**
	 * Constructs the appropriate response for a request that has failed without a detailed
	 * explanation.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleRequestEntityTooLarge( HttpServletRequest request, HttpServletResponse response, int maxRequestLength ) throws IOException {

		response.setStatus( HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE );
		String requestPath = request.getServletPath();
		response.getWriter().write( "{\"request\":\"" + requestPath + "\",\"error\":\"Request body too large. Maximum request length is " + maxRequestLength + " bytes\"}" );
	}
}
