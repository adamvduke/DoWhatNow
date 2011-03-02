package com.adamvduke.dowhatnow.services;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamvduke.dowhatnow.util.http.HttpErrorHandler;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;

@SuppressWarnings( "serial" )
public abstract class AbstractDoWhatNowServlet extends HttpServlet {

	/**
	 * Checks with the OAuthService to verify that the incoming request has been correctly signed by
	 * a valid user of the application. If the authentication fails a response is constructed that
	 * indicates that the user in not authorized.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected User getOAuthUser( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		User user = null;
		try {
			OAuthService oauth = OAuthServiceFactory.getOAuthService();
			user = oauth.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {
			HttpErrorHandler.handleUnauthorizedRequest( request, response );
			return null;
		}
		return user;
	}
}
