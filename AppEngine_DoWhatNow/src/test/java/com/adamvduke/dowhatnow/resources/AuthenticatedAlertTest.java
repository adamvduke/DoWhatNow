package com.adamvduke.dowhatnow.resources;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.adamvduke.appengine.common.exception.BadRequestException;
import com.adamvduke.appengine.common.json.AppEngineGsonBuilder;
import com.adamvduke.dowhatnow.model.Alert;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.gson.Gson;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@SuppressWarnings( "all" )
public class AuthenticatedAlertTest {

	private LocalServiceTestHelper helper;
	private UserService userService;

	private OAuthService oauthService;
	private Query alertsQuery;
	private PersistenceManager persistenceManager;
	private PersistenceManagerFactory persistenceManagerFactory;
	private UriInfo uriInfo;
	private Gson gson;

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
		uriInfo = mock( UriInfo.class );
		userService = UserServiceFactory.getUserService();
		gson = new AppEngineGsonBuilder().get().create();
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
		given( uriInfo.getPath() ).willReturn( "alerts/upcoming.json" );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		String result = alertResource.getUpcoming( uriInfo );

		// then
		String expectedResult = "[]";
		Assert.assertEquals( expectedResult, result );
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
		given( uriInfo.getPath() ).willReturn( "alerts/upcoming.json" );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		String result = alertResource.getUpcoming( uriInfo );

		// then
		String expectedResult = "[{\"owner\":\"example@test.com\",\"title\":\"Test Title\",\"detail\":\"Some details\",\"date\":1234}]";
		Assert.assertEquals( expectedResult, result );
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
		given( uriInfo.getPath() ).willReturn( "alerts/upcoming.json" );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		String result = alertResource.getUpcoming( uriInfo );

		// then
		String expectedResult = "[{\"owner\":\"example@test.com\",\"title\":\"Test Title 1\",\"detail\":\"Some details 1\",\"date\":1234},"
				+ "{\"owner\":\"example@test.com\",\"title\":\"Test Title 2\",\"detail\":\"Some details 2\",\"date\":12345}]";

		Assert.assertEquals( expectedResult, result );
	}

	@Test
	public void shouldDoScheduleAndRecieveJsonArrayAlert() throws OAuthRequestException {

		User user = userService.getCurrentUser();

		// given
		given( oauthService.getCurrentUser() ).willReturn( user );
		given( uriInfo.getPath() ).willReturn( "alerts/schedule.json" );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		MultivaluedMap <String, String> formParams = new MultivaluedMapImpl();
		formParams.putSingle( "title", "Dont forget" );
		formParams.putSingle( "date", "12345" );
		formParams.putSingle( "detail", "You have that really important thing to do." );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		String result = alertResource.scheduleAlert( uriInfo, formParams );

		// then
		String expectedResult = "{\"title\":\"Dont forget\",\"detail\":\"You have that really important thing to do.\",\"date\":12345}";
		Assert.assertEquals( expectedResult, result );
	}

	@Test( expected = BadRequestException.class )
	public void shouldDoScheduleAndExpectBadRequestException() throws OAuthRequestException {

		User user = userService.getCurrentUser();

		// given
		given( oauthService.getCurrentUser() ).willReturn( user );
		given( uriInfo.getPath() ).willReturn( "alerts/schedule.json" );
		given( persistenceManagerFactory.getPersistenceManager() ).willReturn( persistenceManager );

		MultivaluedMap <String, String> formParams = new MultivaluedMapImpl();
		formParams.putSingle( "title", "Dont forget" );
		formParams.putSingle( "date", "abc" );
		formParams.putSingle( "detail", "You have that really important thing to do." );

		// when
		AlertResource alertResource = new AlertResource( oauthService, persistenceManagerFactory, gson );
		String result = alertResource.scheduleAlert( uriInfo, formParams );
	}
}
