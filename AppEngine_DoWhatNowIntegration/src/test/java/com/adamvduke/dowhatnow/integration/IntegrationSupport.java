package com.adamvduke.dowhatnow.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.oauth.common.OAuthCodec;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;

public class IntegrationSupport {

	protected static ClassPathXmlApplicationContext context;
	protected OAuthConsumerSupport support;
	protected boolean production;
	protected String resource_id;
	protected String upcomingUrlString;
	protected String resetUrlString;
	protected String scheduleAlertUrlString;

	/**
	 * Setup the Spring context for any subclasses
	 */
	@BeforeClass
	public static void setupContext() {

		context = new ClassPathXmlApplicationContext( "classpath:/test-application-context.xml" );
	}

	@Before
	public void setup() throws Exception {

		this.support = (OAuthConsumerSupport) context.getBean( "oauthConsumerSupport" );

		String propertyValue = System.getProperty( "testProduction" );
		if ( propertyValue != null ) {
			production = Boolean.parseBoolean( propertyValue );
		}
		else {
			production = false;
		}

		if ( production ) {
			resource_id = "dowhatnowalerts_prod";
			upcomingUrlString = "https://adamcodez.appspot.com/alerts/upcoming.json";
			resetUrlString = "https://adamcodez.appspot.com/alerts/reset.json";
			scheduleAlertUrlString = "https://adamcodez.appspot.com/alerts/schedule.json";
		}
		else {
			resource_id = "dowhatnowalerts_dev";
			upcomingUrlString = "http://localhost:8888/alerts/upcoming.json";
			resetUrlString = "http://localhost:8888/alerts/reset.json";
			scheduleAlertUrlString = "http://localhost:8888/alerts/schedule.json";
		}
	}

	protected OAuthConsumerToken getValidAccessToken() throws Exception {

		OAuthConsumerToken accessToken = new OAuthConsumerToken();
		accessToken.setAccessToken( true );
		accessToken.setResourceId( resource_id );

		// avdpresence tokens
		accessToken.setValue( "1/nRxjSQwNswhdrA-_ZUFaqncrtEVCEt8sHDePHXWgt_8" );
		accessToken.setSecret( "MdX8KVWh5nvx12_Eae3k0rYC" );

		// adam.v.duke tokens
		// accessToken.setValue( "1/-ovI4wOxEBuY_ZDhVYhnEUZUca92uFF_nFGj5GbEwzQ" );
		// accessToken.setSecret( "sCu1sZCGn1IRAYsOxoryUAW6" );
		return accessToken;
	}

	protected OAuthConsumerToken getBadAccessToken() throws Exception {

		OAuthConsumerToken accessToken = new OAuthConsumerToken();
		accessToken.setAccessToken( true );
		accessToken.setResourceId( resource_id );
		accessToken.setValue( "XXXXXXXXX" );
		accessToken.setSecret( "XXXXXXXXX" );
		return accessToken;
	}

	protected String getPostBody( Map <String, String> additionalParams ) {

		StringBuilder builder = new StringBuilder();
		int i = additionalParams.size();
		for ( Map.Entry <String, String> entry : additionalParams.entrySet() ) {
			builder.append( entry.getKey() );
			builder.append( "=" );
			builder.append( OAuthCodec.oauthEncode( entry.getValue() ) );
			if ( i > 1 ) {
				builder.append( "&" );
				i--;
			}
		}
		return builder.toString();
	}

	protected String convertStreamToString( InputStream is ) throws IOException {

		/*
		 * To convert the InputStream to String we use the Reader.read(char[] buffer) method. We
		 * iterate until the Reader return -1 which means there's no more data to read. We use the
		 * StringWriter class to produce the string.
		 */
		if ( is != null ) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );
				int n;
				while ( ( n = reader.read( buffer ) ) != -1 ) {
					writer.write( buffer, 0, n );
				}
			}
			finally {
				is.close();
			}
			return writer.toString();
		}
		else {
			return "";
		}
	}
}
