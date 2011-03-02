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

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implements a ServletFilter to check the request and ensure that it is a properly signed OAuth
 * request.
 * 
 * @author adamd
 */
@Singleton
public class OAuthFilter implements Filter {

	@Inject
	OAuthService oauthService;

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain ) throws IOException, ServletException {

		try {
			oauthService.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {

			// this should only happen in the case where the request hasn't been property signed
			// if the request's can be cast to HttpServletRequest and HttpServletResponse
			// handle the unauthorized request and stop the filter chain
			if ( ( request instanceof HttpServletRequest ) && ( response instanceof HttpServletResponse ) ) {
				handleUnauthorizedRequest( (HttpServletRequest) request, (HttpServletResponse) response );
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

	/**
	 * Constructs the appropriate response for a request that has attempted to use access a URL
	 * without proper authentication.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void handleUnauthorizedRequest( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		response.setHeader( "WWW-Authenticate", "OAuth realm=\"http://adamcodez.appspot.com\"" );
		String requestPath = request.getServletPath();
		response.getWriter().write( "{\"request\":\"" + requestPath + "\",\"error\":\"Not Authorized\"}\n" );
	}
}
