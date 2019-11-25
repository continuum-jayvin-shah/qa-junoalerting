package com.continuum.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonRespParserUtility {

	private static final String OUTPUT_DATA_ALERT_ID = "outdata.alertid";


	/**
	 * @author Jayvin Shah
	 * @param jsonResponse
	 * @param identifier
	 * @return
	 */
	public static JSONArray parseResponseData(JSONObject jsonResponse, String identifier)  throws Exception{
		
		JSONArray filterArray = new JSONArray();
		System.out.println("Identifier::" + identifier);
		
		JSONArray respArray = (JSONArray) jsonResponse.get("outdata");
		
		if(respArray==null) {
			System.out.println("No Data Present in response");
			return respArray;
		}
		
		int i = 0;
		
		while(i < respArray.size()) {
			JSONObject resObject = respArray.getJSONObject(i);
			if(identifier.equalsIgnoreCase(resObject.get("alertid").toString())) {
				filterArray.add(resObject);
			}
			i++;
		}
		System.out.println("Filter Array : " + filterArray);
		return filterArray;
		
		/*
		 * if (path == null) { return respArray; } List<Object> objects = (List<Object>)
		 * path.get(OUTPUT_DATA_ALERT_ID); System.out.println(objects);
		 * //System.out.println(objects); if (CollectionUtils.isEmpty(objects)) { return
		 * desObj; } int index = 0; for (Object object : objects) { if (object == null)
		 * { index++; continue; } else if
		 * (identifier.equalsIgnoreCase(object.toString())) {
		 * System.out.println("Getting JSON : ");
		 * System.out.println(path.getJsonObject("outdata[1]").toString());
		 * desObj.add(path.getJsonObject("outdata[1]").toString());
		 * System.out.println(desObj);
		 * 
		 * index++; } else index++; }
		 * System.out.println("No Entries Present for Alert ID Present in ITSM"); return
		 * desObj;
		 */
	}
	
}
