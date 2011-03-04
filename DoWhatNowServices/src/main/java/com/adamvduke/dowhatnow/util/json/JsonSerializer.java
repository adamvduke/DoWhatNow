package com.adamvduke.dowhatnow.util.json;

import java.util.List;

import com.adamvduke.dowhatnow.model.Alert;
import com.adamvduke.dowhatnow.util.json.adapters.DatastoreKeyAdapter;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {

	// TODO: MOVE ALL OF THIS STUFF INTO GUICE
	public static String toJson( Alert alert ) {

		String json = configuredGson().toJson( alert );
		return json;
	}

	public static Alert fromJson( String json ) {

		Gson gson = new Gson();
		return gson.fromJson( json, Alert.class );
	}

	public static String toJson( List <Alert> alertList ) {

		String json = configuredGson().toJson( alertList );
		return json;
	}

	private static Gson configuredGson() {

		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		// TODO: set up a system property to turn pretty printing on and off
		// builder.setPrettyPrinting();
		registerTypeAdapters( builder );
		Gson gson = builder.create();
		return gson;
	}

	private static void registerTypeAdapters( GsonBuilder builder ) {

		builder.registerTypeAdapter( Key.class, new DatastoreKeyAdapter() );
	}
}
