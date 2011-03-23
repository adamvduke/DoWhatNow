package com.adamvduke.dowhatnow.rest.resources;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;

public abstract class BaseResource {

	OAuthService oauthService;

	/**
	 * Convenience method to get the current user
	 */
	protected User getOAuthUser() throws OAuthRequestException {

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

	public void setOauthService( OAuthService oauthService ) {

		this.oauthService = oauthService;
	}
}
