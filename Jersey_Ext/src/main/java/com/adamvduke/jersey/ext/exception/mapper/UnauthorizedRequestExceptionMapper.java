package com.adamvduke.jersey.ext.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import com.adamvduke.jersey.ext.exception.UnauthorizedRequestException;
import com.adamvduke.jersey.ext.exception.mapper.base.BaseExceptionMapper;

public class UnauthorizedRequestExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <UnauthorizedRequestException> {

	@Override
	public Response toResponse( UnauthorizedRequestException exception ) {

		String message = getFormattedException( exception.getMessage() );

		ResponseBuilder builder = Response.status( 401 );
		builder.entity( message );
		builder.type( MediaType.APPLICATION_JSON );
		builder.header( "WWW-Authenticate", "OAuth realm=\"http://adamcodez.appspot.com\"" );
		return builder.build();
	}
}
