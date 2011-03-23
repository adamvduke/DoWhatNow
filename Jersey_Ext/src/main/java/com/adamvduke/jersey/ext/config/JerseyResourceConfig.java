package com.adamvduke.jersey.ext.config;

import java.util.HashSet;
import java.util.Set;

import com.adamvduke.jersey.ext.exception.mapper.BadRequestExceptionMapper;
import com.adamvduke.jersey.ext.exception.mapper.CatchAllExceptionMapper;
import com.adamvduke.jersey.ext.exception.mapper.MethodNotSupportedExceptionMapper;
import com.adamvduke.jersey.ext.exception.mapper.RequestEntityTooLargeExceptionMapper;
import com.adamvduke.jersey.ext.exception.mapper.UnauthorizedRequestExceptionMapper;
import com.adamvduke.jersey.ext.exception.mapper.WebApplicationExceptionMapper;
import com.sun.jersey.api.core.DefaultResourceConfig;

/**
 * The main configuration point for Jersey
 * 
 * @author adamd
 */
public class JerseyResourceConfig extends DefaultResourceConfig {

	/**
	 * Override the set of classes that Jersey needs to know about to do it's job
	 */
	@Override
	public Set <Class <?>> getClasses() {

		Set <Class <?>> s = new HashSet <Class <?>>();

		// tell jersey about the exception mapping classes so it can
		// return the correct responses for exceptional cases
		s.add( BadRequestExceptionMapper.class );
		s.add( CatchAllExceptionMapper.class );
		s.add( MethodNotSupportedExceptionMapper.class );
		s.add( RequestEntityTooLargeExceptionMapper.class );
		s.add( UnauthorizedRequestExceptionMapper.class );
		s.add( WebApplicationExceptionMapper.class );
		return s;
	}
}
