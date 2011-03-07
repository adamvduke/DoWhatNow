package com.adamvduke.dowhatnow.util.json;

import com.adamvduke.dowhatnow.util.json.adapters.DatastoreKeyAdapter;
import com.google.appengine.api.datastore.Key;
import com.google.gson.GsonBuilder;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class DoWhatNowGsonBuilder implements Provider <GsonBuilder> {

	@Override
	public GsonBuilder get() {

		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		registerTypeAdapters( builder );
		return builder;
	}

	private void registerTypeAdapters( GsonBuilder builder ) {

		builder.registerTypeAdapter( Key.class, new DatastoreKeyAdapter() );
	}
}
