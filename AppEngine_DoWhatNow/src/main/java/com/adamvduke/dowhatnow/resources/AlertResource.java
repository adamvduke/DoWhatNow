package com.adamvduke.dowhatnow.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.adamvduke.appengine.common.exception.BadRequestException;
import com.adamvduke.appengine.common.exception.UnauthorizedRequestException;
import com.adamvduke.dowhatnow.model.Alert;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.google.inject.Inject;

@Path( "/alerts/*" )
public class AlertResource extends BaseResource {

	private final PersistenceManagerFactory persistenceManagerFactory;
	private final Gson gson;

	@Inject
	public AlertResource( OAuthService oauthService, PersistenceManagerFactory persistenceManagerFactory, Gson gson ) {

		super( oauthService );
		this.persistenceManagerFactory = persistenceManagerFactory;
		this.gson = gson;
	}

	@GET
	@Path( "upcoming.json" )
	@Produces( "application/json" )
	@SuppressWarnings( "unchecked" )
	public String getUpcoming( @Context UriInfo uriInfo ) throws OAuthRequestException {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			User user = getOAuthUser();
			Query alertsQuery = pm.newQuery( Alert.class );
			alertsQuery.setOrdering( "date asc" );
			alertsQuery.setFilter( "owner == ownerParam" );
			alertsQuery.declareParameters( "String ownerParam" );
			List <Alert> alerts = (List <Alert>) alertsQuery.execute( user.getUserId() );
			return gson.toJson( alerts );
		}
		catch ( RuntimeException e ) {

			throw new BadRequestException( e.getMessage() );
		}
		finally {
			pm.close();
		}
	}

	@GET
	@Path( "show/{alert_id}.json" )
	@Produces( "application/json" )
	public String showAlert( @Context UriInfo uriInfo, @PathParam( "alert_id" ) long alert_id ) throws OAuthRequestException {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			User user = getOAuthUser();
			Alert alert = pm.getObjectById( Alert.class, alert_id );
			if ( !user.getUserId().equals( alert.getOwner() ) ) {

				// if the current user's id doesn't match the owner of the alert
				// throw an unauthorized exception
				throw new UnauthorizedRequestException( uriInfo.getPath() );
			}
			return gson.toJson( alert );
		}
		catch ( UnauthorizedRequestException unauthorizedRequestException ) {
			throw unauthorizedRequestException;
		}
		catch ( JDOObjectNotFoundException notFoundException ) {
			throw notFoundException;
		}
		catch ( RuntimeException e ) {

			throw new BadRequestException( e.getMessage() );
		}
		finally {
			pm.close();
		}
	}

	@POST
	@Path( "schedule.json" )
	@Produces( "application/json" )
	@Consumes( "application/x-www-form-urlencoded" )
	public String scheduleAlert( @Context UriInfo uriInfo, MultivaluedMap <String, String> formParams ) throws OAuthRequestException {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {

			User user = getOAuthUser();

			Map <String, String> parameterMap = new HashMap <String, String>();

			for ( String key : formParams.keySet() ) {
				String value = formParams.getFirst( key );
				parameterMap.put( key, value );
			}
			Alert alert = new Alert( user.getUserId(), parameterMap );

			// persist the alert
			pm.makePersistent( alert );
			String json = gson.toJson( alert );
			return json;
		}
		catch ( NumberFormatException numberFormatException ) {

			String message = numberFormatException.getClass().getSimpleName() + " " + numberFormatException.getMessage();
			message = message.replace( "\"", "" );
			throw new BadRequestException( message );
		}
		catch ( RuntimeException e ) {

			throw new BadRequestException( e.getMessage() );
		}
		finally {
			pm.close();
		}
	}

	@POST
	@Path( "destroy/{alert_id}.json" )
	@Produces( "application/json" )
	public String deleteAlert( @Context UriInfo uriInfo, @PathParam( "alert_id" ) long alert_id ) throws OAuthRequestException {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			User user = getOAuthUser();
			Alert alert = pm.getObjectById( Alert.class, alert_id );
			if ( !user.getUserId().equals( alert.getOwner() ) ) {

				// if the current user's id doesn't match the owner of the alert
				// throw an unauthorized exception
				throw new UnauthorizedRequestException( uriInfo.getPath() );
			}
			pm.deletePersistent( alert );
			return gson.toJson( alert );
		}
		catch ( UnauthorizedRequestException unauthorizedRequestException ) {
			throw unauthorizedRequestException;
		}
		catch ( JDOObjectNotFoundException notFoundException ) {
			throw notFoundException;
		}
		catch ( RuntimeException e ) {

			throw new BadRequestException( e.getMessage() );
		}
		finally {
			pm.close();
		}
	}

	@POST
	@Path( "reset.json" )
	@Produces( "application/json" )
	@SuppressWarnings( "unchecked" )
	public String resetCalendar( @Context UriInfo uriInfo ) throws OAuthRequestException {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			User user = getOAuthUser();
			Query alertsQuery = pm.newQuery( Alert.class );
			alertsQuery.setFilter( "owner == ownerParam" );
			alertsQuery.declareParameters( "String ownerParam" );
			List <Alert> alerts = (List <Alert>) alertsQuery.execute( user.getUserId() );
			pm.deletePersistentAll( alerts );
			return gson.toJson( alerts );
		}
		catch ( RuntimeException e ) {

			throw new BadRequestException( e.getMessage() );
		}
		finally {
			pm.close();
		}
	}
}
