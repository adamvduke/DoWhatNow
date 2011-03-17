package com.adamvduke.dowhatnow.resources;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;

import com.adamvduke.appengine.common.json.DoWhatNowGsonBuilder;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.gson.Gson;

public class UnauthenticatedAlertTest {

	private OAuthService oauthService;
	private PersistenceManager persistenceManager;
	private PersistenceManagerFactory persistenceManagerFactory;
	private UriInfo uriInfo;
	private Gson gson;

	@Before
	public void setUp() {

		persistenceManager = mock( PersistenceManager.class );
		persistenceManagerFactory = mock( PersistenceManagerFactory.class );
		oauthService = mock( OAuthService.class );
		uriInfo = mock( UriInfo.class );
		gson = new DoWhatNowGsonBuilder().get().create();
	}

	@Test( expected = OAuthRequestException.class )
	public void shouldDoGetAndExpectOAuthRequestException() throws OAuthRequestException {

		// given
		given( oauthService.getCurrentUser() ).willThrow( new OAuthRequestException( "" ) );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		alertResource.getUpcoming( uriInfo );
	}
}
