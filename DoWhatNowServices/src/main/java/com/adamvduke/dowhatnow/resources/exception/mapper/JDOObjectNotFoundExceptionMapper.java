package com.adamvduke.dowhatnow.resources.exception.mapper;

import javax.jdo.JDOObjectNotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class JDOObjectNotFoundExceptionMapper implements ExceptionMapper <JDOObjectNotFoundException> {

	private static final String formatMessage = "{\"request\":\"%s\",\"error\":\"%s\"}";

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse( JDOObjectNotFoundException arg0 ) {

		String message = String.format( formatMessage, uriInfo.getPath(), "No alert scheduled with that ID." );

		return Response.status( 404 ).entity( message ).type( MediaType.APPLICATION_JSON ).build();
	}
}