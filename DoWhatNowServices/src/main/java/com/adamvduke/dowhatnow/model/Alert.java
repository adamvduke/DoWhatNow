package com.adamvduke.dowhatnow.model;

import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gson.annotations.Expose;

@PersistenceCapable
public class Alert {

	public Alert( String owner, String title, String detail, long date ) {

		this( owner );
		this.title = title;
		this.detail = detail;
		this.date = date;
	}

	public Alert( Map <String, String> parameterMap, String owner ) {

		this( owner );
		if ( parameterMap == null ) {
			throw new IllegalArgumentException( "parameterMap can not be null" );
		}
		this.title = parameterMap.get( "title" );
		this.detail = parameterMap.get( "detail" );
		this.date = Long.valueOf( parameterMap.get( "date" ) );
	}

	private Alert( String owner ) {

		if ( owner == null ) {
			throw new IllegalArgumentException( "owner can not be null" );
		}
		this.owner = owner;
	}

	@PrimaryKey
	@Persistent( valueStrategy = IdGeneratorStrategy.IDENTITY )
	@Expose
	private Key key;

	@Persistent
	@Expose
	private String owner;

	@Persistent
	@Expose
	private String title;

	@Persistent
	@Expose
	private String detail;

	@Persistent
	@Expose
	private long date;

	public String getOwner() {

		return owner;
	}

	public void setOwner( String owner ) {

		this.owner = owner;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle( String title ) {

		this.title = title;
	}

	public String getDetail() {

		return detail;
	}

	public void setDetail( String detail ) {

		this.detail = detail;
	}

	public long getDate() {

		return date;
	}

	public void setDate( long date ) {

		this.date = date;
	}

	public Key getKey() {

		return key;
	}

	public void setKey( Key key ) {

		this.key = key;
	}
}
