package com.adamvduke.appengine.common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AppEngineJsonProvider implements Provider <Gson> {

	private final GsonBuilder builder;

	@Inject
	public AppEngineJsonProvider( GsonBuilder builder ) {

		this.builder = builder;
	}

	@Override
	public Gson get() {

		return builder.create();
	}
}
