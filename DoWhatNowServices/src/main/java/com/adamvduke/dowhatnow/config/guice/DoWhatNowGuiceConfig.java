package com.adamvduke.dowhatnow.config.guice;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.adamvduke.dowhatnow.servlet.filter.OAuthFilter;
import com.adamvduke.dowhatnow.servlet.filter.RequestLengthFilter;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Main configuration point for Guice
 * 
 * @author adamd
 */
public class DoWhatNowGuiceConfig extends GuiceServletContextListener {

	/**
	 * Sets up the Injector that Guice will use for the applications dependencies
	 */
	@Override
	protected Injector getInjector() {

		// setting Jersey up inside Guice's injector
		// indicating to jersey what resources it should be interested in
		// takes place in "MainJerseyApplication"
		final Map <String, String> params = new HashMap <String, String>();
		params.put( "javax.ws.rs.Application", "com.adamvduke.dowhatnow.config.jersey.MainJerseyApplication" );
		params.put( "com.sun.jersey.config.feature.DisableWADL", "true" );

		return Guice.createInjector(

		new JerseyServletModule() {

			// anonymous JerseyServeltModule sets jersey's GuiceContainer up
			@Override
			public void configureServlets() {

				// set up the filters to be used
				filter( "/*" ).through( RequestLengthFilter.class );
				filter( "/*" ).through( OAuthFilter.class );

				// serve all uri's with the GuiceContainer(Jersey's GuiceContainer)
				// pass the params Map to let the magic happen
				serve( "/*" ).with( GuiceContainer.class, params );
			}
		},

		new Module() {

			// anonymous Module sets up the other dependencies that Guice will inject
			@Override
			public void configure( Binder binder ) {

				// make guice aware of the JDO persistence manager factory
				PersistenceManagerFactory pmFactory = JDOHelper.getPersistenceManagerFactory( "transactions-optional" );
				binder.bind( PersistenceManagerFactory.class ).toInstance( pmFactory );

				// make guice aware of the OAuthUserService
				OAuthService oauthService = OAuthServiceFactory.getOAuthService();
				binder.bind( OAuthService.class ).toInstance( oauthService );
			}
		},

		new AbstractModule() {

			// anonymous Abstract module to set up AOP interceptors
			@Override
			protected void configure() {

				String isActive = System.getProperty( "dowhatnow.all-invocation-interceptor.active" );
				boolean active = Boolean.parseBoolean( isActive );
				if ( active ) {
					bindInterceptor( Matchers.any(), Matchers.any(), new AnyInvocationInterceptor() );
				}
			}
		} );
	}
}
