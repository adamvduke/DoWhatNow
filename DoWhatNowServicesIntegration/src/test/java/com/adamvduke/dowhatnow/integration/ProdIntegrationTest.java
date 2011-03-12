package com.adamvduke.dowhatnow.integration;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;

public class ProdIntegrationTest extends BaseIntegrationTest {

	private static final String upcomingUrlString = "https://adamcodez.appspot.com/alerts/upcoming.json";
	private static final String resetUrlString = "https://adamcodez.appspot.com/alerts/reset.json";

	@Test
	public void getUpcoming() throws Exception {

		OAuthConsumerToken accessToken = getAccessToken();
		URL upcomingUrl = new URL( upcomingUrlString );
		InputStream inputStream = support.readProtectedResource( upcomingUrl, accessToken, "GET" );
		String json = convertStreamToString( inputStream );
		System.out.println( json );
	}

	@Test
	public void resetAlerts() throws Exception {

		OAuthConsumerToken accessToken = getAccessToken();
		URL resetUrl = new URL( resetUrlString );
		URL postURL = support.configureURLForProtectedAccess( resetUrl, accessToken, "POST", null );
		HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput( true );
		connection.getOutputStream().write( (byte) 0 );
		InputStream inputStream = connection.getInputStream();
		String json = convertStreamToString( inputStream );
		System.out.println( json );
	}
}
