package com.adamvduke.dowhatnow.util.json;

import java.util.List;

import com.adamvduke.dowhatnow.model.Alert;
import com.adamvduke.dowhatnow.util.json.adapters.DatastoreKeyAdapter;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;

@Singleton
public class DoWhatNowJson {

	public String toJson( Alert alert ) {

		String json = configuredGson().toJson( alert );
		return json;
	}

	public String toJson( List <Alert> alertList ) {

		String json = configuredGson().toJson( alertList );
		return json;
	}

	private Gson configuredGson() {

		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		registerTypeAdapters( builder );
		Gson gson = builder.create();
		return gson;
	}

	private void registerTypeAdapters( GsonBuilder builder ) {

		builder.registerTypeAdapter( Key.class, new DatastoreKeyAdapter() );
	}
}
