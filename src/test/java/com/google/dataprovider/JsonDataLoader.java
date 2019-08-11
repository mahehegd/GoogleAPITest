package com.google.dataprovider;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.model.GeoData;

import lombok.extern.log4j.Log4j;


@Log4j
public class JsonDataLoader {
	public static final String INPUT_FILE_PATH = "/src/test/resources/";
	public static final String VALID_DATA_FILE = "ValidData.json";
	public static final String INVALID_DATA_FILE = "InvalidData.json";
	
	public static  Gson gson= new Gson();
	@DataProvider(name = "ProvideValidData")
	public static Iterator<Object[]> getValidDataList() throws Exception {
		
		return getGeoDataList(VALID_DATA_FILE);
		
	}
	
	@DataProvider(name = "ProvideInvalidData")
	public static Iterator<Object[]> getInvalidDataList() throws Exception {
		
		return getGeoDataList(INVALID_DATA_FILE);
		
	}
	
	public static Iterator<Object[]> getGeoDataList(String filename) throws Exception {

		JSONParser parser = new JSONParser();
		String current = new java.io.File( "." ).getCanonicalPath();
		List<GeoData> inputDataList = new ArrayList<GeoData>();
	
        try {          
            JSONArray array = (JSONArray) parser.parse(new FileReader(current + INPUT_FILE_PATH + filename));
           
            //Fetch json array list from file
            for(Object obj: array) {
            	JSONObject req = (JSONObject) obj;
            	String jsonObj = req.toJSONString();
            	GeoData geoData = gson.fromJson(jsonObj, GeoData.class);
            	inputDataList.add(geoData);
            }
            
            //Convert the json data list to Object[][]
            final Collection<Object[]> data = new ArrayList<Object[]>();
            inputDataList.forEach(item -> data.add(new Object[]{item}));
            return data.iterator();
            
        } catch (Exception e) {
        	throw e;
        }
    }
}
