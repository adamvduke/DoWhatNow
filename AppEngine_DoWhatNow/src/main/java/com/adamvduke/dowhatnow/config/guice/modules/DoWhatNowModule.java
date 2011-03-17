package com.adamvduke.dowhatnow.config.guice.modules;

import com.adamvduke.appengine.common.interceptors.TimingInterceptor;
import com.adamvduke.appengine.common.json.AppEngineGsonBuilder;
import com.adamvduke.appengine.common.json.AppEngineJsonProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;

/**
 * Sets up a module to tell Guice how to inject DoWhatNow specific classes
 * 
 * @author adamd
 */
public class DoWhatNowModule implements Module {

	@Override
	public void configure( Binder binder ) {

		// bind a GsonBuilder provider that has been configured
		binder.bind( GsonBuilder.class ).toProvider( AppEngineGsonBuilder.class );

		// bind a Gson provider, the GsonBuilder is constructor injected
		// so that the configuration from DoWhatNowGsonBuilder is used
		binder.bind( Gson.class ).toProvider( AppEngineJsonProvider.class );

		// optionally bind the timing interceptor based on the system property
		// "dowhatnow.all-invocation-interceptor.active"
		String isActive = System.getProperty( "dowhatnow.all-invocation-interceptor.active" );
		boolean active = Boolean.parseBoolean( isActive );
		if ( active ) {
			binder.bindInterceptor( Matchers.any(), Matchers.any(), new TimingInterceptor() );
		}
	}
}
