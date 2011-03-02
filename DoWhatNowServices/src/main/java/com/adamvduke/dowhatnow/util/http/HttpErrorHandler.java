package com.adamvduke.dowhatnow.util.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpErrorHandler {

	/**
	 * Constructs the appropriate response for a request that has attempted to use access a URL
	 * without proper authentication.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void handleUnauthorizedRequest( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		response.setHeader( "WWW-Authenticate", "OAuth realm=\"http://adamcodez.appspot.com\"" );
		String requestPath = request.getServletPath();
		response.getWriter().write( "{\"request\":\"" + requestPath + "\",\"error\":\"Not Authorized\"}\n" );
	}

	/**
	 * Constructs the appropriate response for a request that has attempted to use an HTTP method
	 * that is not supported for that particular URL.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void handleUnsupportedHTTPMethod( HttpServletRequest request, HttpServletResponse response, String... supportedMethods ) throws IOException {

		String requestPath = request.getServletPath();
		String method = request.getMethod();
		response.setStatus( HttpServletResponse.SC_METHOD_NOT_ALLOWED );
		response.setHeader( "Allow", HTTPStringUtil.buildSupportedMethods( supportedMethods ) );
		response.getWriter().write( "{\"request\":\"" + requestPath + "\",\"error\":\"Unsupported method: " + method + "\"}\n" );
	}

	/**
	 * Constructs the appropriate response for a request that has failed without a detailed
	 * explanation.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void handleGenericFailure( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		response.setStatus( 400 );
		String requestPath = request.getServletPath();
		response.getWriter().write( "{\"request\":\"" + requestPath + "\",\"error\":\"Bad Request\"}\n" );
	}
}
