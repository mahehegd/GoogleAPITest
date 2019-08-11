package com.google.CallerUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.model.GeoData;

import lombok.extern.log4j.Log4j;

@Log4j
public class ResponseUtil {

	public static GeoData getGeoDataFromHttpResponse(HttpResponse response) throws Exception {

		JSONParser jsonParser = new JSONParser();
		
		HttpEntity entity = response.getEntity();
		String responseBody = EntityUtils.toString(entity);
		
		try {
			//Read JSON response and construct an object of GeoData
			JSONObject resultObject = (JSONObject) jsonParser.parse(responseBody);

			JSONArray resultArray = (JSONArray) resultObject.get("results");

			JSONObject firstObject = (JSONObject) resultArray.get(0);
			String formatted_address = firstObject.get("formatted_address").toString();
			//resultArray.get(0).getAsJsonObject().get("formatted_address").getAsString();
			log.info("Formatted address= "+ formatted_address);

			JSONObject geometry = (JSONObject) firstObject.get("geometry");

			JSONObject location = (JSONObject) geometry.get("location");

			Double lattitude = (double) (Math.round(Double.valueOf(location.get("lat").toString())*100)) /100;

			Double longitude = (double) (Math.round(Double.valueOf(location.get("lng").toString()) *100)) /100;

			return new GeoData(formatted_address, lattitude, longitude);

		} catch(Exception e) {
			log.trace("Error while parsing json in response", e);
			throw e;
		}


	}

}
