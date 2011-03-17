package com.adamvduke.dowhatnow.error;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import com.adamvduke.dowhatnow.integration.patterns.ResponsePatterns;

public class ResponseCodeTest {

	@Test
	public void shouldMatchEmptyJsonList() {

		String response = "[]";

		// []
		Pattern pattern = ResponsePatterns.emptyAlertPattern;
		Matcher matcher = pattern.matcher( response );
		Assert.assertTrue( matcher.matches() );
	}

	@Test
	public void shouldMatchSingleAlert() {

		String response = "{\"alert_id\":39001,\"owner\":\"107288066193919475099\",\"title\":\"Don\u0027t forget\",\"detail\":\"You have that really important thing to do.\",\"date\":12345}";

		// {"alert_id":39001,"owner":"107288066193919475099","title":"Don\u0027t forget","detail":"You have that really important thing to do.","date":12345}
		Pattern pattern = ResponsePatterns.singleAlertPattern;
		Matcher matcher = pattern.matcher( response );
		Assert.assertTrue( matcher.matches() );
	}

	@Test
	public void shouldMatchAlertList() {

		String response = "[{\"alert_id\":39001,\"owner\":\"107288066193919475099\",\"title\":\"Don\u0027t forget\",\"detail\":\"You have that really important thing to do.\",\"date\":12345}]";

		// [{"alert_id":39001,"owner":"107288066193919475099","title":"Don\u0027t forget","detail":"You have that really important thing to do.","date":12345}]
		Pattern pattern = ResponsePatterns.alertListPattern;
		Matcher matcher = pattern.matcher( response );
		Assert.assertTrue( matcher.matches() );
	}

	@Test
	public void shouldMatchAPotentialError() {

		String response = "{\"request\":\"alerts/schedule.json\",\"error\":\"NumberFormatException For input string: 12345a\"}";

		// {"request":"alerts/schedule.json","error":"NumberFormatException For input string: 12345a"}
		Pattern pattern = ResponsePatterns.errorPattern;
		Matcher matcher = pattern.matcher( response );
		Assert.assertTrue( matcher.matches() );
	}

}
