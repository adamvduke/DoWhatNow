package com.adamvduke.appengine.common.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.appengine.common.exception.mapper.base.BaseExceptionMapper;

@Provider
public class CatchAllExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <WebApplicationException> {

	private static final String defaultMessage = "HTTP Error: %d";

	@Override
	public Response toResponse( WebApplicationException exception ) {

		Response jerseyResponse = exception.getResponse();
		ResponseBuilder customResponseBuilder = Response.status( jerseyResponse.getStatus() );
		String exceptionMessage = exception.getMessage();

		// TODO: this is ugly
		if ( exceptionMessage != null && exceptionMessage.contains( "java.lang." ) ) {
			exceptionMessage.replace( "java.lang.", "" );
		}

		if ( exceptionMessage == null ) {
			exceptionMessage = String.format( defaultMessage, jerseyResponse.getStatus() );
		}
		String message = getFormattedException( exceptionMessage );
		customResponseBuilder.entity( message );
		customResponseBuilder.type( MediaType.APPLICATION_JSON );
		return customResponseBuilder.build();
	}
}
