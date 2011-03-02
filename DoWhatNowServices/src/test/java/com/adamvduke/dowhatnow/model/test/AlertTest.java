package com.adamvduke.dowhatnow.model.test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.adamvduke.dowhatnow.model.Alert;

public class AlertTest {

	@Test( expected = IllegalArgumentException.class )
	public void constructorShouldThrowExceptionForNullMapTest() {

		Alert alert = new Alert( null, "SomeUserId" );
		Assert.assertNull( alert );
	}

	@Test( expected = IllegalArgumentException.class )
	@SuppressWarnings( "all" )
	public void constructorShouldThrowExceptionForNullOwner() {

		Alert alert = new Alert( new HashMap(), null );
		Assert.assertNull( alert );
	}
}
