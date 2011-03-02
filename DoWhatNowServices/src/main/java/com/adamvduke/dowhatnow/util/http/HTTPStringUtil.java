package com.adamvduke.dowhatnow.util.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPStringUtil {

	/**
	 * Builds a String that can be used as the value of an "Allow" header for http requests
	 * 
	 * @param supportedMethods
	 *            The enumeration of HTTP methods that should go in the header
	 * @return
	 */
	public static String buildSupportedMethods( String... supportedMethods ) {

		StringBuffer buffer = new StringBuffer();
		int numberOfMethods = supportedMethods.length;
		for ( int processedMethods = 0; processedMethods < numberOfMethods; processedMethods++ ) {
			buffer.append( supportedMethods[processedMethods] );
			appendComma( buffer, numberOfMethods, processedMethods );
		}
		return buffer.toString();
	}

	/**
	 * Creates a map of key/value pairs from a url query string
	 * 
	 * @param decoded
	 *            the query string to operate on
	 * @return
	 */
	public static Map <String, String> parameterMap( String decoded ) {

		String[] tuples = decoded.split( "&" );
		Map <String, String> parameterMap = new HashMap <String, String>();
		for ( String tuple : tuples ) {
			String name = tuple.split( "=" )[0];
			String value = tuple.split( "=" )[1];
			parameterMap.put( name, value );
		}
		return parameterMap;
	}

	private static void appendComma( StringBuffer buffer, int numberOfEntries, int currentEntries ) {

		if ( currentEntries < numberOfEntries - 1 ) {
			buffer.append( ", " );
		}
	}

}
