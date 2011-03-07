package com.adamvduke.dowhatnow.config.guice.modules;

import com.adamvduke.dowhatnow.config.guice.interceptors.TimingInterceptor;
import com.adamvduke.dowhatnow.util.json.DoWhatNowJson;
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

		// bind DoWhatNowJson so Guice can inject it
		binder.bind( DoWhatNowJson.class );

		// optionally bind the timing interceptor based on the system property
		// "dowhatnow.all-invocation-interceptor.active"
		String isActive = System.getProperty( "dowhatnow.all-invocation-interceptor.active" );
		boolean active = Boolean.parseBoolean( isActive );
		if ( active ) {
			binder.bindInterceptor( Matchers.any(), Matchers.any(), new TimingInterceptor() );
		}
	}
}
