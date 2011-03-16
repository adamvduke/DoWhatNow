package com.adamvduke.dowhatnow.resources.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.dowhatnow.resources.exception.BadRequestException;
import com.adamvduke.dowhatnow.resources.exception.mapper.base.BaseExceptionMapper;

@Provider
public class BadRequestExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <BadRequestException> {

	@Override
	public Response toResponse( BadRequestException exception ) {

		String message = getFormattedException( exception.getMessage() );
		return Response.status( 400 ).entity( message ).type( MediaType.APPLICATION_JSON ).build();
	}
}
