package org.intermine.webservice.server.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ISO8601DateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -5686664384857682278L;
	private static final DateFormat instance = new ISO8601DateFormat(); 

	public ISO8601DateFormat() {
		super("yyyy-MM-dd'T'HH:mm:ssZ");
	}

	public static DateFormat getFormatter() {
		return instance;
	}
}