package com.google.apitest;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.CallerUtils.RequestUtil;
import com.google.CallerUtils.ResponseUtil;
import com.google.dataprovider.JsonDataLoader;
import com.google.model.GeoData;
import com.google.validator.ValidatorUtil;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j;

/**
 * 
 * @author Mahesh Hegde
 * 
 *Test cases to test lattitude and longitude of Geo Addresses
 */

@Log4j
public class TestGeoCode {

	private HttpResponse response = null;

	@BeforeMethod
	public void beforeMethod() {
		Reporter.log("Executing before test method");

	}

	@AfterMethod
	public void afterMethod() throws Exception {
		Reporter.log("Executing after test method");
		closeConnection(response);

	}
	
	@Test
	public void testResponseCode() throws Exception {
		//Get random GeoData
		GeoData geoData = getRandomGeoData();
		
		//Call geoCode API
		response = RequestUtil.call(geoData.getAddress());
		log.info(String.format("Response received with code %s", response.getStatusLine().getStatusCode()));
		
		//Verify response code
		Assert.assertEquals(response.getStatusLine().getStatusCode(), (HttpStatus.SC_OK));
	}
	
	@Test
	public void testResponseType() throws Exception {
		GeoData geoData = getRandomGeoData();
		response = RequestUtil.call(geoData.getAddress());
		log.info(String.format("Response received with code %s", response.getStatusLine().getStatusCode()));
		
		
		//Verify media type to be intended one
		Assert.assertEquals(ContentType.getOrDefault(response.getEntity()).getMimeType(), "application/json");
	}
	
	@Test(dataProvider="ProvideValidData", dataProviderClass=JsonDataLoader.class)
	public void testGeoCodeCorrectness(GeoData geoData) throws Exception {
		response = RequestUtil.call(geoData.getAddress());
		GeoData obtainedGeoData = ResponseUtil.getGeoDataFromHttpResponse(response);
		
		Assert.assertTrue(validate(obtainedGeoData, geoData));		
	}
	
	private GeoData getRandomGeoData() throws Exception {
		return new GeoData("Cambridge+Layout,+Jogupalya,+Bengaluru,+Karnataka+560007",
						   30.00,
						   46.77);
	}
	
	private void closeConnection(HttpResponse response) throws Exception{
		HttpEntity entity = response.getEntity();
        if (entity != null)
        	entity.consumeContent();
	}
	
	private boolean validate(GeoData expected, GeoData actual) throws Exception {
		ValidatorUtil validatorUtil= new ValidatorUtil();
		List<String> errorMessages = validatorUtil.validate(expected, actual, GeoData.class);
		if(errorMessages.size() > 0) 
			return false;
		else
			return true;
	}
}
