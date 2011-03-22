package com.adamvduke.appengine.common.filter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.junit.Test;

import com.adamvduke.jersey.ext.exception.UnauthorizedRequestException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.sun.jersey.spi.container.ContainerRequest;

public class OAuthFilterTest {

	@Test( expected = UnauthorizedRequestException.class )
	public void shouldThrowUnauthorizedException() throws OAuthRequestException {

		OAuthService oauthService = mock( OAuthService.class );
		ContainerRequest request = mock( ContainerRequest.class );

		given( oauthService.getCurrentUser() ).willThrow( new OAuthRequestException( "" ) );

		OAuthFilter filter = new OAuthFilter();
		filter.setOauthService( oauthService );
		filter.filter( request );
	}

	@Test
	public void shouldForwardRequest() throws OAuthRequestException {

		OAuthService oauthService = mock( OAuthService.class );
		ContainerRequest request = mock( ContainerRequest.class );

		OAuthFilter filter = new OAuthFilter();
		filter.setOauthService( oauthService );
		ContainerRequest forwardedRequest = filter.filter( request );
		Assert.assertSame( request, forwardedRequest );
	}
}
