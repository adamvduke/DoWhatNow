package com.adamvduke.dowhatnow.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import com.adamvduke.dowhatnow.model.Alert;
import com.adamvduke.dowhatnow.rest.exception.BadRequestException;
import com.adamvduke.dowhatnow.util.json.JsonSerializer;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;

@Path( "/alerts/*" )
public class AlertResource extends BaseResource {

	@Inject
	private PersistenceManagerFactory persistenceManagerFactory;

	@GET
	@Path( "upcoming.json" )
	@Produces( "application/json" )
	@SuppressWarnings( "unchecked" )
	public String getUpcoming() {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {
			User user = getUser();
			Query alertsQuery = pm.newQuery( Alert.class );
			alertsQuery.setOrdering( "date asc" );
			alertsQuery.setFilter( "owner == ownerParam" );
			alertsQuery.declareParameters( "String ownerParam" );
			List <Alert> alerts = (List <Alert>) alertsQuery.execute( user.getUserId() );
			return JsonSerializer.toJson( alerts ) + "\n";
		}
		catch ( RuntimeException e ) {
			throw new BadRequestException( "/alerts/upcoming.json" );
		}
		finally {
			pm.close();
		}
	}

	@POST
	@Path( "schedule.json" )
	@Produces( "application/json" )
	@Consumes( "application/x-www-form-urlencoded" )
	public String scheduleAlert( MultivaluedMap <String, String> formParams ) {

		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		try {

			User user = getUser();

			Map <String, String> parameterMap = new HashMap <String, String>();

			for ( String key : formParams.keySet() ) {
				String value = formParams.getFirst( key );
				parameterMap.put( key, value );
			}
			Alert alert = new Alert( user.getUserId(), parameterMap );

			// persist the alert
			pm.makePersistent( alert );
			String json = "{\"alert\":" + JsonSerializer.toJson( alert ) + "}\n";
			return json;
		}
		catch ( Exception e ) {

			throw new BadRequestException( "/alerts/schedule.json" );
		}
		finally {
			pm.close();
		}
	}
}
