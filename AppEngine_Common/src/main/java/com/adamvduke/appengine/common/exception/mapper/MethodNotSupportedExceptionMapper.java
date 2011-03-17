package com.adamvduke.appengine.common.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adamvduke.appengine.common.exception.MethodNotSupportedException;
import com.adamvduke.appengine.common.exception.mapper.base.BaseExceptionMapper;

@Provider
public class MethodNotSupportedExceptionMapper extends BaseExceptionMapper implements ExceptionMapper <MethodNotSupportedException> {

	@Override
	public Response toResponse( MethodNotSupportedException exception ) {

		String message = getFormattedException( exception.getMessage() );
		return Response.status( 405 ).entity( message ).type( MediaType.APPLICATION_JSON ).build();
	}
}
