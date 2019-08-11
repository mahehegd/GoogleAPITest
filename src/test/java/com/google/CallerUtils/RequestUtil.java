package com.google.CallerUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import lombok.extern.log4j.Log4j;

@Log4j
public class RequestUtil {

	private static final String key = "AIzaSyB0OH4mhEOLORkbf9rC6L2Tyy50khUSrwk";
	private static final HttpClient client = new DefaultHttpClient();
	private static final String BASE_URI = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
	
	public static HttpResponse call(String address) throws Exception {
		HttpUriRequest request = new HttpGet(getURI(address));
		log.info(String.format("Calling URI: %s", request.getURI()));
		return client.execute(request);
		
	}
	
	private static String getURI(String address) {
		return String.format(BASE_URI, address, key);
	}
}
