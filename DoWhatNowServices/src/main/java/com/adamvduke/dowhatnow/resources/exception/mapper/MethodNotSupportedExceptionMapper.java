package com.adamvduke.dowhatnow.resources.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.dowhatnow.resources.exception.MethodNotSupportedException;

@Provider
public class MethodNotSupportedExceptionMapper implements ExceptionMapper <com.adamvduke.dowhatnow.resources.exception.MethodNotSupportedException> {

	@Override
	public Response toResponse( MethodNotSupportedException exception ) {

		return Response.status( 405 ).entity( exception.getMessage() ).type( MediaType.APPLICATION_JSON ).build();
	}
}
