package com.adamvduke.dowhatnow.guice;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.inject.Binder;
import com.google.inject.Module;

public class DoWhatNowTestDependenciesModule implements Module {

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
