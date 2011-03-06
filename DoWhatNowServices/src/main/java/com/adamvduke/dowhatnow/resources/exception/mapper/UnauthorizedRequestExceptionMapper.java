package com.adamvduke.dowhatnow.resources.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import com.adamvduke.dowhatnow.resources.exception.UnauthorizedRequestException;

public class UnauthorizedRequestExceptionMapper implements ExceptionMapper <com.adamvduke.dowhatnow.resources.exception.UnauthorizedRequestException> {

	@Override
	public Response toResponse( UnauthorizedRequestException exception ) {

		ResponseBuilder builder = Response.status( 401 );
		builder.entity( exception.getMessage() );
		builder.type( MediaType.APPLICATION_JSON );
		builder.header( "WWW-Authenticate", "OAuth realm=\"http://adamcodez.appspot.com\"" );
		return builder.build();
	}
}
