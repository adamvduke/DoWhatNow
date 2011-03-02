package com.adamvduke.dowhatnow.services;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamvduke.dowhatnow.util.http.HttpErrorHandler;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;

/**
 * Implements a servlet filter to check the request and ensure that it is a properly signed OAuth
 * request.
 * 
 * @author adamd
 */
public class OAuthFilter implements Filter {

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain ) throws IOException, ServletException {

		try {
			OAuthService oauth = OAuthServiceFactory.getOAuthService();
			oauth.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {
			if ( ( request instanceof HttpServletRequest ) && ( response instanceof HttpServletResponse ) ) {
				HttpErrorHandler.handleUnauthorizedRequest( (HttpServletRequest) request, (HttpServletResponse) response );
				return;
			}
			throw new ServletException( "Unknown error occured." );
		}
		filterChain.doFilter( request, response );
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {

	}
}
