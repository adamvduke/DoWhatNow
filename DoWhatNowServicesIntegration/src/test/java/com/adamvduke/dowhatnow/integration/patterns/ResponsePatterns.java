package com.adamvduke.dowhatnow.integration.patterns;

import java.util.regex.Pattern;

public class ResponsePatterns {

	public static final Pattern emptyAlertPattern = Pattern.compile( "\\[\\]" );
	public static final Pattern singleAlertPattern = Pattern.compile( "\\{\"alert_id\":.*,\"owner\":\".*\",\"title\":\".*\",\"detail\":\".*\",\"date\":.*\\}" );
	public static final Pattern alertListPattern = Pattern.compile( "\\[\\{\"alert_id\":.*,\"owner\":\".*\",\"title\":\".*\",\"detail\":\".*\",\"date\":.*\\}\\]" );
	public static final Pattern errorPattern = Pattern.compile( "\\{\"request\":\".*\",\"error\":\".*\"\\}" );
}
