package com.adamvduke.appengine.common.filter;

import com.adamvduke.jersey.ext.exception.UnauthorizedRequestException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * Implements a ServletFilter to check the request and ensure that it is a properly signed OAuth
 * request.
 * 
 * @author adamd
 */
public class OAuthFilter implements ContainerRequestFilter {

	OAuthService oauthService;

	@Override
	public ContainerRequest filter( ContainerRequest request ) {

		try {
			oauthService.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {

			// this should only happen in the case where the request hasn't been property signed
			throw new UnauthorizedRequestException( e.getMessage() );
		}
		return request;
	}

	public void setOauthService( OAuthService oauthService ) {

		this.oauthService = oauthService;
	}
}
