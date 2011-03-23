package com.adamvduke.jersey.ext.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.jersey.ext.exception.RequestEntityTooLargeException;
import com.adamvduke.jersey.ext.exception.mapper.base.BaseExceptionMapper;

@Provider
public class RequestEntityTooLargeExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <RequestEntityTooLargeException> {

	@Override
	public Response toResponse( RequestEntityTooLargeException exception ) {

		String message = getFormattedException( exception.getMessage() );
		return Response.status( 413 ).entity( message ).type( MediaType.APPLICATION_JSON ).build();
	}
}
