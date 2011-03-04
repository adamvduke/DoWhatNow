package com.adamvduke.dowhatnow.resources;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.adamvduke.dowhatnow.model.Alert;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

@SuppressWarnings( "all" )
public class AlertTest {

	private LocalServiceTestHelper helper;
	private UserService userService;

	OAuthService oauthService;
	Query alertsQuery;
	PersistenceManager persistenceManager;
	PersistenceManagerFactory persistenceManagerFactory;

	@Before
	public void setUp() {

		helper = new LocalServiceTestHelper( new LocalUserServiceTestConfig() );
		helper.setEnvIsLoggedIn( true );
		helper.setEnvEmail( "example@test.com" );
		helper.setEnvAuthDomain( "" );
		helper.setUp();
		oauthService = mock( OAuthService.class );
		alertsQuery = mock( Query.class );
		persistenceManager = mock( PersistenceManager.class );
		persistenceManagerFactory = mock( PersistenceManagerFactory.class );
		userService = UserServiceFactory.getUserService();
	}

	@After
	public void tearDown() {

		helper.tearDown();
	}

	@Test
	public void shouldDoGetAndRecieveEmptyJsonArray() throws OAuthRequestException {

		User user = userService.getCurrentUser();

		// given
		given( oauthService.getCurrentUser() ).willReturn( user );
		given( alertsQuery.execute( user.getUserId() ) ).willReturn( new ArrayList <Alert>() );
		given( persistenceManager.newQuery( Alert.class ) ).willReturn( alertsQuery );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory );

		// then
		Assert.assertEquals( "[]\n", alertResource.getUpcoming() );
	}

	@Test
	public void shouldDoGetAndRecieveJsonArrayWithOneElement() throws OAuthRequestException {

		User user = userService.getCurrentUser();

		Alert alert = new Alert( user.getEmail(), "Test Title", "Some details", 1234 );
		List <Alert> alerts = new ArrayList <Alert>();
		alerts.add( alert );

		// given
		given( oauthService.getCurrentUser() ).willReturn( user );
		given( alertsQuery.execute( user.getUserId() ) ).willReturn( alerts );
		given( persistenceManager.newQuery( Alert.class ) ).willReturn( alertsQuery );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory );

		// then
		Assert.assertEquals( "[{\"owner\":\"example@test.com\",\"title\":\"Test Title\",\"detail\":\"Some details\",\"date\":1234}]\n", alertResource.getUpcoming() );
	}

	@Test
	public void shouldDoGetAndRecieveJsonArrayWithTwoElements() throws OAuthRequestException {

		User user = userService.getCurrentUser();

		Alert alert1 = new Alert( user.getEmail(), "Test Title 1", "Some details 1", 1234 );
		Alert alert2 = new Alert( user.getEmail(), "Test Title 2", "Some details 2", 12345 );
		List <Alert> alerts = new ArrayList <Alert>();
		alerts.add( alert1 );
		alerts.add( alert2 );

		// given
		given( oauthService.getCurrentUser() ).willReturn( user );
		given( alertsQuery.execute( user.getUserId() ) ).willReturn( alerts );
		given( persistenceManager.newQuery( Alert.class ) ).willReturn( alertsQuery );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory );

		// then
		Assert.assertEquals( "[{\"owner\":\"example@test.com\",\"title\":\"Test Title 1\",\"detail\":\"Some details 1\",\"date\":1234},"
				+ "{\"owner\":\"example@test.com\",\"title\":\"Test Title 2\",\"detail\":\"Some details 2\",\"date\":12345}]\n", alertResource.getUpcoming() );
	}
}
