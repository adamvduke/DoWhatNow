package com.adamvduke.dowhatnow.config.guice;

import com.adamvduke.dowhatnow.config.guice.modules.AppEngineModule;
import com.adamvduke.dowhatnow.config.guice.modules.DWNJerseyModule;
import com.adamvduke.dowhatnow.config.guice.modules.DoWhatNowModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

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

		// each module contains configuration to instruct Guice how to inject dependencies
		return Guice.createInjector( new DWNJerseyModule(), new AppEngineModule(), new DoWhatNowModule() );
	}
}
