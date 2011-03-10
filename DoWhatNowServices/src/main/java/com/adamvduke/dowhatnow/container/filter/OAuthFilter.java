package com.adamvduke.dowhatnow.container.filter;

import com.adamvduke.dowhatnow.resources.exception.UnauthorizedRequestException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * Implements a ServletFilter to check the request and ensure that it is a properly signed OAuth
 * request.
 * 
 * @author adamd
 */
@Singleton
public class OAuthFilter implements ContainerRequestFilter {

	@Inject
	OAuthService oauthService;

	@Override
	public ContainerRequest filter( ContainerRequest request ) {

		try {
			oauthService.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {

			// this should only happen in the case where the request hasn't been property signed
			throw new UnauthorizedRequestException( request.getPath() );
		}
		return request;
	}
}
