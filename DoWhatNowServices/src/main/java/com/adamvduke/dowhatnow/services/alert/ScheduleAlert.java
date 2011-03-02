package com.adamvduke.dowhatnow.services.alert;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamvduke.dowhatnow.model.Alert;
import com.adamvduke.dowhatnow.persistence.PMF;
import com.adamvduke.dowhatnow.services.AbstractDoWhatNowServlet;
import com.adamvduke.dowhatnow.util.http.HTTPStringUtil;
import com.adamvduke.dowhatnow.util.http.HttpErrorHandler;
import com.adamvduke.dowhatnow.util.json.JsonSerializer;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.common.base.Charsets;

@SuppressWarnings( "serial" )
public class ScheduleAlert extends AbstractDoWhatNowServlet {

	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		User user = super.getOAuthUser( request, response );
		if ( user == null ) {
			HttpErrorHandler.handleGenericFailure( request, response );
			return;
		}

		// TODO: check the content length isn't too big
		// possibly set a max size for the alert's properties?
		// might be better accomplished as a servlet filter
		int contentLength = request.getContentLength();
		byte[] content = new byte[contentLength];
		request.getInputStream().read( content );
		String requestBody = new String( content );

		String decoded = URLDecoder.decode( requestBody, Charsets.UTF_8.displayName() );
		Map <String, String> parameterMap = HTTPStringUtil.parameterMap( decoded );

		Alert alert = new Alert( parameterMap, user.getUserId() );

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent( alert );
			response.setStatus( HttpServletResponse.SC_OK );
			response.setContentType( "application/json" );
			response.setCharacterEncoding( Charsets.UTF_8.displayName() );
			String json = "{\"alert\":" + JsonSerializer.toJson( alert ) + "}\n";
			response.getWriter().write( json );
		}
		catch ( Exception e ) {
			HttpErrorHandler.handleGenericFailure( request, response );
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException {

		HttpErrorHandler.handleUnsupportedHTTPMethod( request, response, "POST" );
	}
}
