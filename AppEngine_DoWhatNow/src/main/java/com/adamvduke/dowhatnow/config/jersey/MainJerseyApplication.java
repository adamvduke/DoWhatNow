package com.adamvduke.dowhatnow.config.jersey;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.adamvduke.appengine.common.exception.mapper.BadRequestExceptionMapper;
import com.adamvduke.appengine.common.exception.mapper.CatchAllExceptionMapper;
import com.adamvduke.appengine.common.exception.mapper.JDOObjectNotFoundExceptionMapper;
import com.adamvduke.appengine.common.exception.mapper.MethodNotSupportedExceptionMapper;
import com.adamvduke.appengine.common.exception.mapper.RequestEntityTooLargeExceptionMapper;
import com.adamvduke.appengine.common.exception.mapper.UnauthorizedRequestExceptionMapper;
import com.adamvduke.dowhatnow.resources.AlertResource;

/**
 * The main configuration point for Jersey
 * 
 * @author adamd
 */
public class MainJerseyApplication extends Application {

	/**
	 * Override the set of classes that Jersey needs to know about to do it's job
	 */
	@Override
	public Set <Class <?>> getClasses() {

		Set <Class <?>> s = new HashSet <Class <?>>();

		// tell jersey about the resource types it should know about
		s.add( AlertResource.class );

		// tell jersey about the exception mapping classes so it can
		// return the correct responses for exceptional cases
		s.add( BadRequestExceptionMapper.class );
		s.add( MethodNotSupportedExceptionMapper.class );
		s.add( RequestEntityTooLargeExceptionMapper.class );
		s.add( UnauthorizedRequestExceptionMapper.class );
		s.add( CatchAllExceptionMapper.class );
		s.add( JDOObjectNotFoundExceptionMapper.class );
		return s;
	}
}
