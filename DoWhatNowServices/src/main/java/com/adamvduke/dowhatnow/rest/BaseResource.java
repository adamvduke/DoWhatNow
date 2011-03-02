package com.adamvduke.dowhatnow.rest;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;

public class BaseResource {

	@Inject
	OAuthService oauthService;

	/**
	 * Convenience method to get the current user
	 */
	protected User getUser() {

		User oUser;
		try {
			oUser = oauthService.getCurrentUser();
		}
		catch ( OAuthRequestException e ) {

			// This should never happen assuming the
			// OAuthFilter is in place.
			return null;
		}
		return oUser;
	}
}
