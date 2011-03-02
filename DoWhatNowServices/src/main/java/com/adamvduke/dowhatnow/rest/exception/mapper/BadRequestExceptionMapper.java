package com.adamvduke.dowhatnow.rest.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.dowhatnow.rest.exception.BadRequestException;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper <com.adamvduke.dowhatnow.rest.exception.BadRequestException> {

	@Override
	public Response toResponse( BadRequestException exception ) {

		return Response.status( 400 ).entity( exception.getMessage() ).type( MediaType.APPLICATION_JSON ).build();
	}
}
