package com.adamvduke.jersey.ext.exception.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.jersey.ext.exception.mapper.base.BaseExceptionMapper;

@Provider
public class WebApplicationExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <WebApplicationException> {

	private static final String defaultMessage = "HTTP Error: %d";

	@Override
	public Response toResponse( WebApplicationException exception ) {

		Response jerseyResponse = exception.getResponse();
		ResponseBuilder customResponseBuilder = Response.status( jerseyResponse.getStatus() );
		String exceptionMessage = exception.getMessage();

		if ( exceptionMessage == null ) {
			exceptionMessage = String.format( defaultMessage, jerseyResponse.getStatus() );
		}
		String message = getFormattedException( exceptionMessage );
		customResponseBuilder.entity( message );
		customResponseBuilder.type( MediaType.APPLICATION_JSON );
		return customResponseBuilder.build();
	}
}
