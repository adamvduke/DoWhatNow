package com.adamvduke.dowhatnow.config.guice.modules;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Sets up a module to tell Guice how to inject app engine specific classes, i.e. things that would
 * be created by calling app engine factory methods
 * 
 * @author adamd
 */
public class AppEngineModule implements Module {

	@Override
	public void configure( Binder binder ) {

		// make guice aware of the JDO persistence manager factory
		PersistenceManagerFactory pmFactory = JDOHelper.getPersistenceManagerFactory( "transactions-optional" );
		binder.bind( PersistenceManagerFactory.class ).toInstance( pmFactory );

		// make guice aware of the OAuthUserService
		OAuthService oauthService = OAuthServiceFactory.getOAuthService();
		binder.bind( OAuthService.class ).toInstance( oauthService );
	}
}
