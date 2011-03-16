package com.adamvduke.dowhatnow.error;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

public class ErrorCodeTest {

	@Test
	public void shouldMatchAPotentialError() {

		String potentialError = "{\"request\":\"alerts/schedule.json\",\"error\":\"NumberFormatException For input string: 12345a\"}";

		// {"request":"alerts/schedule.json","error":"NumberFormatException For input string: 12345a"}
		String regex = "\\{\"request\":\".*\",\"error\":\".*\"\\}";
		Pattern pattern = Pattern.compile( regex );
		Matcher matcher = pattern.matcher( potentialError );
		Assert.assertTrue( matcher.matches() );
	}
}
