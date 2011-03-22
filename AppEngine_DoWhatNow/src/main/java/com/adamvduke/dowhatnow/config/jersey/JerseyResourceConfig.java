package com.adamvduke.dowhatnow.config.jersey;

import java.util.Set;

import com.adamvduke.appengine.common.exception.mapper.JDOObjectNotFoundExceptionMapper;
import com.adamvduke.dowhatnow.rest.resources.AlertResource;

/**
 * The main configuration point for Jersey
 * 
 * @author adamd
 */
public class JerseyResourceConfig extends com.adamvduke.jersey.ext.config.JerseyResourceConfig {

	/**
	 * Override the set of classes that Jersey needs to know about to do it's job
	 */
	@Override
	public Set <Class <?>> getClasses() {

		Set <Class <?>> s = super.getClasses();

		// tell jersey about the resource types it should know about
		s.add( AlertResource.class );

		// add any app engine specific classes
		s.add( JDOObjectNotFoundExceptionMapper.class );
		return s;
	}
}
