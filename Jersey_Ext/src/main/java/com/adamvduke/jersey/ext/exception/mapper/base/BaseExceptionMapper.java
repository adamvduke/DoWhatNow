package com.adamvduke.jersey.ext.exception.mapper.base;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class BaseExceptionMapper {

	private static final String formatMessage = "{\"request\":\"%s\",\"error\":\"%s\"}";

	@Context
	private UriInfo uriInfo;

	protected String getFormattedException( String message ) {

		return String.format( formatMessage, uriInfo.getPath(), message );
	}
}
