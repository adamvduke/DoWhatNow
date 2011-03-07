package com.adamvduke.dowhatnow.config.guice.modules;

import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Set up a JerseyServletModule with Jersey's GuiceContainer at the root
 * 
 * @author adamd
 */
public class DWNJerseyModule extends JerseyServletModule {

	@Override
	public void configureServlets() {

		// configure the list of ContainerFilters
		StringBuilder filtersBuilder = new StringBuilder();
		filtersBuilder.append( "com.adamvduke.dowhatnow.servlet.filter.RequestLengthFilter" ).append( ";" );
		filtersBuilder.append( "com.adamvduke.dowhatnow.servlet.filter.OAuthFilter" );

		// setting Jersey up inside Guice's injector
		// indicating to jersey what resources it should be interested in takes place in
		// MainJerseyApplication.java
		final Map <String, String> params = new HashMap <String, String>();
		params.put( "javax.ws.rs.Application", "com.adamvduke.dowhatnow.config.jersey.MainJerseyApplication" );

		// add the container request filters, they are executed in the order they are declared
		params.put( "com.sun.jersey.spi.container.ContainerRequestFilters", filtersBuilder.toString() );

		// disable WADL publishing, possibly a problem when running in app engine
		params.put( "com.sun.jersey.config.feature.DisableWADL", "true" );

		// serve all uri's with the GuiceContainer(Jersey's GuiceContainer)
		// pass the params Map to let the magic happen
		serve( "/*" ).with( GuiceContainer.class, params );
	}
}
