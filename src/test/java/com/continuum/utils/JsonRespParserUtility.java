package com.continuum.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

		JSONArray respArray = null ;

		try {
			respArray = (JSONArray) jsonResponse.get("outdata");
		}catch(Exception e){

		}
		
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

	public static JSONArray parseResponseDataCondition(JSONObject jsonResponse, String identifier)  throws Exception{
		JSONArray filterArray = new JSONArray();
		System.out.println("Identifier::" + identifier);
		JSONArray respArray = null ;
		try {
			respArray = (JSONArray) jsonResponse.get("outdata");
		}catch(Exception e){

		}
		if(respArray==null) {
			System.out.println("No Data Present in response");
			return respArray;
		}
		int i = 0;
		while(i < respArray.size()) {
			JSONObject resObject = respArray.getJSONObject(i);
			if(identifier.equalsIgnoreCase(resObject.get("conditionid").toString())) {
				filterArray.add(resObject);
			}
			i++;
		}
		System.out.println("Filter Array : " + filterArray);
		return filterArray;
	}

	public static String CreateJSONNormal(String timeStamp) {
		String errMsg = "";
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\ConditionFilePath\\" +
				"NormalCondition.json"  ;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("conditionId", timeStamp);
		//jsonObject.put("closingcondition", false);
		jsonObject.put("conditionFamily", "CreateAutomation");
		//jsonObject.put("conditionname", "Regression_Test");
		jsonObject.put("conditionpayload", null);
		jsonObject.put("configurable", null);
		jsonObject.put("consolidation", false);
		jsonObject.put("consolidationsleeptime", null);
		jsonObject.put("description", null);
		jsonObject.put("downstream", "itsm");
		jsonObject.put("fetchdata", null);
		jsonObject.put("issnooze", false);
		jsonObject.put("permanent_consolidation", null);
		jsonObject.put("priority", 1);
		jsonObject.put("remediation", null);
		jsonObject.put("sendtodownstream", false);
		jsonObject.put("snooze", null);
		//jsonObject.put("sourcesystem", "2.0");
		jsonObject.put("underresearch", null);
		jsonObject.put("updatedtime", null);
		try {
			FileWriter file = new FileWriter(path);
			file.write(jsonObject.toString());
			file.close();
		} catch (IOException e) {
			errMsg = "Error in JSON Create.";
			e.printStackTrace();
			return errMsg ;
		}
		System.out.println("JSON file created: " + path);
		errMsg = zipFile(path,System.getProperty("user.dir") + "\\src\\test\\resources\\" +
				"ConditionFilePath\\" + "NormalCondition.zip");
		return errMsg ;
	}

	public static String zipFile(String filePath, String zipName) {
		String errMsg = "";
		try {
			File file = new File(filePath) ;
			String zipFileName = zipName ;
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.putNextEntry(new ZipEntry(file.getName()));
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			zos.write(bytes, 0, bytes.length);
			zos.closeEntry();
			zos.close();
		} catch (Exception ex) {
			errMsg = "[Exception Occured while creating ZIP]";
			System.out.println("Exception occur : " + ex);
		}
		return errMsg;
	}
	
}
