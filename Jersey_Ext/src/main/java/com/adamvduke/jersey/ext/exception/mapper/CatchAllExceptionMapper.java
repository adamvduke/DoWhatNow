package com.adamvduke.jersey.ext.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.jersey.ext.exception.mapper.base.BaseExceptionMapper;

@Provider
public class CatchAllExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <Exception> {

	private static final String defaultMessage = "HTTP Error: %d";
	private static final Integer defaultStatus = Integer.valueOf( 400 );

	@Override
	public Response toResponse( Exception exception ) {

		ResponseBuilder customResponseBuilder = Response.status( defaultStatus );
		String exceptionMessage = exception.getMessage();

		// TODO: this is ugly
		if ( exceptionMessage != null && exceptionMessage.contains( "java.lang." ) ) {
			exceptionMessage.replace( "java.lang.", "" );
		}

		if ( exceptionMessage == null ) {
			exceptionMessage = String.format( defaultMessage, defaultStatus );
		}
		String message = getFormattedException( exceptionMessage );
		customResponseBuilder.entity( message );
		customResponseBuilder.type( MediaType.APPLICATION_JSON );
		return customResponseBuilder.build();
	}
}
