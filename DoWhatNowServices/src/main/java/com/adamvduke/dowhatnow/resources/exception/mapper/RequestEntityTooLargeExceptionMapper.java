package com.adamvduke.dowhatnow.resources.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.dowhatnow.resources.exception.RequestEntityTooLargeException;

@Provider
public class RequestEntityTooLargeExceptionMapper implements ExceptionMapper <com.adamvduke.dowhatnow.resources.exception.RequestEntityTooLargeException> {

	@Override
	public Response toResponse( RequestEntityTooLargeException exception ) {

		return Response.status( 413 ).entity( exception.getMessage() ).type( MediaType.APPLICATION_JSON ).build();
	}
}
