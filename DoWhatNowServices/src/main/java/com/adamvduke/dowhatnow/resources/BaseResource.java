package com.adamvduke.dowhatnow.resources;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;

public abstract class BaseResource {

	OAuthService oauthService;

	@Inject
	protected BaseResource( OAuthService oauthService ) {

		this.oauthService = oauthService;
	}

	/**
	 * Convenience method to get the current user
	 */
	protected User getUser() throws OAuthRequestException {

		User oUser;
		try {
			oUser = oauthService.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {

			// This should never happen assuming the
			// OAuthFilter is in place.
			throw e;
		}
		return oUser;
	}
}
