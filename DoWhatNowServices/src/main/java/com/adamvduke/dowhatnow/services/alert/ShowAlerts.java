package com.adamvduke.dowhatnow.services.alert;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamvduke.dowhatnow.model.Alert;
import com.adamvduke.dowhatnow.persistence.PMF;
import com.adamvduke.dowhatnow.services.AbstractDoWhatNowServlet;
import com.adamvduke.dowhatnow.util.json.JsonSerializer;
import com.google.appengine.api.users.User;

@SuppressWarnings( "serial" )
public class ShowAlerts extends AbstractDoWhatNowServlet {

	@Override
	@SuppressWarnings( "unchecked" )
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		User user = super.getOAuthUser( request, response );
		if ( user == null ) {
			return;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query alertsQuery = pm.newQuery( Alert.class );
		alertsQuery.setOrdering( "date asc" );
		alertsQuery.setFilter( "owner == ownerParam" );
		alertsQuery.declareParameters( "String ownerParam" );
		List <Alert> alerts = (List <Alert>) alertsQuery.execute( user.getUserId() );
		response.getWriter().write( JsonSerializer.toJson( alerts ) + "\n" );
	}
}
