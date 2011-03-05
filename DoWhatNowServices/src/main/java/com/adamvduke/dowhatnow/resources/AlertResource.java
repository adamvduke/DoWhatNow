package com.adamvduke.dowhatnow.resources;

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
import com.adamvduke.dowhatnow.resources.exception.BadRequestException;
import com.adamvduke.dowhatnow.util.json.JsonSerializer;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.users.User;
import com.google.inject.Inject;

@Path( "/alerts/*" )
public class AlertResource extends BaseResource {

	private final PersistenceManagerFactory persistenceManagerFactory;

	@Inject
	public AlertResource( OAuthService oauthService, PersistenceManagerFactory persistenceManagerFactory ) {

		super( oauthService );
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

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
			return JsonSerializer.toJson( alerts );
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
			String json = JsonSerializer.toJson( alert );
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
