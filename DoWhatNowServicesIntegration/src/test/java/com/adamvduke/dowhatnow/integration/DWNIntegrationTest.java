package com.adamvduke.dowhatnow.integration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;

import com.adamvduke.dowhatnow.integration.patterns.ResponsePatterns;

public class DWNIntegrationTest extends IntegrationSupport {

	@Test
	public void shouldResetAlerts() throws Exception {

		OAuthConsumerToken accessToken = getValidAccessToken();
		URL resetUrl = new URL( resetUrlString );
		URL postURL = support.configureURLForProtectedAccess( resetUrl, accessToken, "POST", null );

		// first connection will delete everything
		HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput( true );
		OutputStream outStream = connection.getOutputStream();
		outStream.write( (byte) 0 );
		InputStream inputStream = connection.getInputStream();
		String json = convertStreamToString( inputStream );

		// second connection will retrieve nothing, that's what's being verified
		connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput( true );
		outStream = connection.getOutputStream();
		outStream.write( (byte) 0 );
		inputStream = connection.getInputStream();
		json = convertStreamToString( inputStream );

		Pattern pattern = ResponsePatterns.emptyAlertPattern;
		Matcher matcher = pattern.matcher( json );
		assertTrue( matcher.matches() );
	}

	@Test
	public void shouldRecieveUpcomingAlerts() throws Exception {

		shouldResetAlerts();
		shouldScheduleAlertAndSucceed();
		OAuthConsumerToken accessToken = getValidAccessToken();
		URL upcomingUrl = new URL( upcomingUrlString );
		InputStream inputStream = support.readProtectedResource( upcomingUrl, accessToken, "GET" );
		String json = convertStreamToString( inputStream );

		Pattern pattern = ResponsePatterns.alertListPattern;
		Matcher matcher = pattern.matcher( json );
		assertTrue( matcher.matches() );
	}

	@Test
	public void shouldScheduleAlertAndSucceed() throws Exception {

		String date = "12345";
		String detail = "You have that really important thing to do.";
		String title = "Don't forget";
		Map <String, String> additionalParams = new HashMap <String, String>();
		additionalParams.put( "date", date );
		additionalParams.put( "detail", detail );
		additionalParams.put( "title", title );

		String encodedPostBody = getPostBody( additionalParams );
		OAuthConsumerToken accessToken = getValidAccessToken();
		URL resetUrl = new URL( scheduleAlertUrlString );
		URL postURL = support.configureURLForProtectedAccess( resetUrl, accessToken, "POST", additionalParams );
		HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput( true );
		connection.getOutputStream().write( encodedPostBody.getBytes() );
		InputStream inputStream = connection.getInputStream();
		String json = convertStreamToString( inputStream );

		Pattern pattern = ResponsePatterns.singleAlertPattern;
		Matcher matcher = pattern.matcher( json );
		assertTrue( matcher.matches() );
	}

	@Test
	@SuppressWarnings( "unused" )
	public void shouldScheduleAlertAndFailWithNumberFormatError() throws Exception {

		String date = "12345a";
		String detail = "You have that really important thing to do.";
		String title = "Don't forget";
		Map <String, String> additionalParams = new HashMap <String, String>();
		additionalParams.put( "date", date );
		additionalParams.put( "detail", detail );
		additionalParams.put( "title", title );

		String encodedPostBody = getPostBody( additionalParams );
		OAuthConsumerToken accessToken = getValidAccessToken();
		URL resetUrl = new URL( scheduleAlertUrlString );
		URL postURL = support.configureURLForProtectedAccess( resetUrl, accessToken, "POST", additionalParams );
		HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput( true );
		connection.getOutputStream().write( encodedPostBody.getBytes() );
		try {
			InputStream inputStream = connection.getInputStream();
			fail();
		}
		catch ( Exception e ) {
			InputStream inputStream = connection.getErrorStream();
			String json = convertStreamToString( inputStream );

			// {"request":"alerts/schedule.json","error":"NumberFormatException For input string: 12345a"}
			Pattern pattern = ResponsePatterns.errorPattern;
			Matcher matcher = pattern.matcher( json );
			Assert.assertTrue( matcher.matches() );
		}
	}
}
