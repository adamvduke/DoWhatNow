package com.adamvduke.spring.oauth;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth.common.OAuthCodec;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetailsService;
import org.springframework.security.oauth.consumer.token.OAuthConsumerToken;

public class CoreOAuthConsumerSupport extends org.springframework.security.oauth.consumer.CoreOAuthConsumerSupport {

	private Map <String, List <String>> validAdditionalOAuthParamNamesMap;

	@Override
	public String getAuthorizationHeader( ProtectedResourceDetails details, OAuthConsumerToken accessToken, URL url, String httpMethod, Map <String, String> additionalParameters ) {

		if ( !details.isAcceptsAuthorizationHeader() ) {
			return null;
		}
		else {
			Map <String, Set <CharSequence>> oauthParams = loadOAuthParameters( details, url, accessToken, httpMethod, additionalParameters );
			String realm = details.getAuthorizationHeaderRealm();

			StringBuilder builder = new StringBuilder( "OAuth " );
			boolean writeComma = false;
			if ( realm != null ) { // realm is optional.
				builder.append( "realm=\"" ).append( realm ).append( '"' );
				writeComma = true;
			}

			for ( Map.Entry <String, Set <CharSequence>> paramValuesEntry : oauthParams.entrySet() ) {
				String key = paramValuesEntry.getKey();

				List <String> validAdditionalOAuthParamNames = validAdditionalOAuthParamNamesMap.get( details.getId() );

				// filtering out the invalid parameters from the authorization header
				if ( !key.startsWith( "oauth_" ) && !validAdditionalOAuthParamNames.contains( key ) ) {
					continue;
				}
				Set <CharSequence> paramValues = paramValuesEntry.getValue();
				CharSequence paramValue = findValidHeaderValue( paramValues );
				if ( paramValue != null ) {
					if ( writeComma ) {
						builder.append( ", " );
					}

					builder.append( paramValuesEntry.getKey() ).append( "=\"" ).append( OAuthCodec.oauthEncode( paramValue.toString() ) ).append( '"' );
					writeComma = true;
				}
			}

			return builder.toString();
		}
	}

	public Map <String, List <String>> getValidAdditionalOAuthParamNamesMap() {

		return validAdditionalOAuthParamNamesMap;
	}

	public void setValidAdditionalOAuthParamNamesMap( Map <String, List <String>> validAdditionalOAuthParamNamesMap ) {

		this.validAdditionalOAuthParamNamesMap = validAdditionalOAuthParamNamesMap;
	}

	@Override
	public void setProtectedResourceDetailsService( ProtectedResourceDetailsService protectedResourceDetailsService ) {

		super.setProtectedResourceDetailsService( protectedResourceDetailsService );
	}
}
