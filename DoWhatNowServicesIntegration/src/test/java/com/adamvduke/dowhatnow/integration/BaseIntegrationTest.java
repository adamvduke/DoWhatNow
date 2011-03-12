package com.adamvduke.dowhatnow.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.oauth.consumer.OAuthConsumerSupport;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;

public class BaseIntegrationTest {

	protected static ClassPathXmlApplicationContext context;
	protected OAuthConsumerSupport support;

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
	}

	protected OAuthConsumerToken getAccessToken() throws Exception {

		OAuthConsumerToken accessToken = new OAuthConsumerToken();
		accessToken.setResourceId( "dowhatnowalerts_dev" );
		accessToken.setAccessToken( true );
		accessToken.setValue( "1/-ovI4wOxEBuY_ZDhVYhnEUZUca92uFF_nFGj5GbEwzQ" );
		accessToken.setSecret( "sCu1sZCGn1IRAYsOxoryUAW6" );
		return accessToken;
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
