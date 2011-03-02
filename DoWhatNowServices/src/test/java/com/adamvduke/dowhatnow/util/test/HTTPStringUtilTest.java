package com.adamvduke.dowhatnow.util.test;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.adamvduke.dowhatnow.util.http.HTTPStringUtil;

public class HTTPStringUtilTest {

	@Test
	public void shouldBuildValidAllowHeaderOneVerb() {

		String commaSeperatedList = HTTPStringUtil.buildSupportedMethods( "GET" );
		String expectedValue = "GET";
		Assert.assertEquals( expectedValue, commaSeperatedList );
	}

	@Test
	public void shouldBuildValidAllowHeaderTwoVerbs() {

		String commaSeperatedList = HTTPStringUtil.buildSupportedMethods( "GET", "POST" );
		String expectedValue = "GET, POST";
		Assert.assertEquals( expectedValue, commaSeperatedList );
	}

	@Test
	public void shouldBuildValidAllowHeaderThreeVerbs() {

		String commaSeperatedList = HTTPStringUtil.buildSupportedMethods( "GET", "POST", "PUT" );
		String expectedValue = "GET, POST, PUT";
		Assert.assertEquals( expectedValue, commaSeperatedList );
	}

	@Test
	public void shouldBuildValidAllowHeaderWithMultipleVerbs() {

		String commaSeperatedList = HTTPStringUtil.buildSupportedMethods( "GET", "POST", "PUT", "DELETE" );
		String expectedValue = "GET, POST, PUT, DELETE";
		Assert.assertEquals( expectedValue, commaSeperatedList );
	}

	@Test
	public void shouldBuildParameterMapFromDecodedString() {

		String decoded = "date=1298675552&detail=You have that really important thing to do.&title=Don't forget";
		Map <String, String> parameterMap = HTTPStringUtil.parameterMap( decoded );
		Assert.assertEquals( parameterMap.get( "date" ), "1298675552" );
		Assert.assertEquals( parameterMap.get( "detail" ), "You have that really important thing to do." );
		Assert.assertEquals( parameterMap.get( "title" ), "Don't forget" );
	}
}
