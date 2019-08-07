package com.continuum.platform.alerting.api;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialJavaObject;

import org.testng.Assert;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JsonRespParserUtility;
import com.continuum.utils.JunoAlertingAPIUtil;

/*import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
 */
import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.Utilities;
import continuum.cucumber.webservices.RestServicesUtility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class AlertingAPITest {

	private static String alertingUrl, alertDetails, itsmUrl, testName, currentAlert;
	private static List<String> alertId = new ArrayList<String>();
	private static JSONArray filterArray = new JSONArray();public static String getCurrentAlert() {
		return currentAlert;
	}
	public static void setCurrentAlert(String currentAlert) {
		AlertingAPITest.currentAlert = currentAlert;
	}
	public AlertingAPITest() {
		// TODO Auto-generated constructor stub
	}
	public static JSONArray getFilterArray() {
		return filterArray;
	}

	public static void setFilterArray(JSONArray filterArray) {
		AlertingAPITest.filterArray.addAll(filterArray);
	}

	public static String getTestName() {
		return testName;
	}

	public static void setTestName(String testName) {
		AlertingAPITest.testName = testName;
	}

	private static Response alertingResponse;
	
	
	public static List<String> getAlertId() {
		return alertId;
	}

	public static void setAlertId(String alertId) {
		AlertingAPITest.alertId.add(alertId);
	}
	
	public static String getalertingUrl() {
		return alertingUrl;
	}

	public static void setalertingUrl(String alertingUrl) {
		AlertingAPITest.alertingUrl = alertingUrl;
	}
	
	public static String getAlertDetails() {
		return alertDetails;
	}

	public static void setAlertDetails(String alertDetails) {
		AlertingAPITest.alertDetails = alertDetails;
	}
	
	public static String getItsmUrl() {
		return itsmUrl;
	}

	public static void setItsmUrl(String itsmUrl) {
		AlertingAPITest.itsmUrl = itsmUrl;
	}
	
	public static boolean triggerCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		System.out.println("Alert Details : " + alertDetails);
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingUrl));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerParentCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		JSON json = JSONSerializer.toJSON(alertDetails);
		JSONObject parentAlertDetails = (JSONObject)json;
		JSONArray alerts = new JSONArray();
		alerts.addAll(alertId);
		parentAlertDetails.put("alerts", alerts);
		System.out.println(parentAlertDetails.toString());
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingUrl));
		Thread.sleep(3000);
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Parent Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerParentWithSameConditionCreateAPI(String testName){
		try {
		for(int i=0;i<4;i++) {
			setTestName(testName);
			preProcessing(getTestName());
			JSON json = JSONSerializer.toJSON(alertDetails);
			JSONObject parentAlertDetails = (JSONObject)json;
			JSONArray alerts = new JSONArray();
			alerts.add(alertId.get(i));
			alerts.add(alertId.get(i+1));
			parentAlertDetails.put("alerts", alerts);
			System.out.println(parentAlertDetails.toString());
			AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingUrl));
			Thread.sleep(3000);
			if(alertingResponse.getStatusCode() == 201) {
				System.out.println(alertingResponse.getBody().asString());
				setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				System.out.println("Alert Created : " + getCurrentAlert());
				i++;
			}else if(alertingResponse.getStatusCode() == 409){
				System.out.println("Parent Alert not Created with Error Conflict");
				return false;
			}else {
				System.out.println("Parent Alert no created with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		}
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Parent Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerUpdateAPI(){
		try {
		preProcessing(getTestName());
		System.out.println("Alert Details : " + alertDetails);
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingUrl + "/" + getCurrentAlert()));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Alert Updation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerDeleteAPI(){
		try {
		for(int i=0; i<alertId.size();i++) {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				System.out.println("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			System.out.println("Alert Deleted : " + alertId.get(i));
		}
		System.out.println("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerChildDeleteAPI(){
		try {
		for(int i=0; i<alertId.size()-1;i++) {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				System.out.println("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			System.out.println("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		System.out.println("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerChildDeleteAPIForBothParent(){
		try {
		for(int i=0; i<alertId.size()-2;i++) {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				System.out.println("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			System.out.println("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		System.out.println("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerDeleteAPI(String alertID){
		try {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingUrl + "/" + alertID));
			if(alertingResponse.getStatusCode() != 204) {
				System.out.println("Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		System.out.println("Alert Deleted : " + alertID);
		return true;
		
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static void preProcessing(String testName) throws Exception{
		
			DataUtils.setTestRow("Test", testName);
			System.out.println("Test Data Captured.");
			
			HashMap<String, String> currentRow = new HashMap<String, String>();
			
			currentRow.putAll(DataUtils.getTestRow());
			System.out.println("Getting Host URL:" + currentRow);
			
			if(!alertingUrl.contains("alertingservice")) {

			alertingUrl = alertingUrl + Utilities.getMavenProperties("AlertingUrlSchema")
					.replace("{partners}", currentRow.get("partners"))
					.replace("{sites}", currentRow.get("sites"))
					.replace("{endpoints}", currentRow.get("endpoints"));
			
				if(currentRow.get("endpoints").isEmpty()) {
					if(currentRow.get("sites").isEmpty()) {
						if(currentRow.get("clients").isEmpty()) {
							alertingUrl = alertingUrl.replace("sites//endpoints//", "");
						}else {
							alertingUrl = alertingUrl.replace("endpoints//", "");
							alertingUrl = alertingUrl.replace("sites/", "clients/{clients}");
							alertingUrl = alertingUrl.replace("{clients}", currentRow.get("clients"));
						}
					}else {
						alertingUrl = alertingUrl.replace("endpoints//", "");
						alertingUrl = alertingUrl.replace("sites", "clients/{clients}/sites");
						alertingUrl = alertingUrl.replace("{clients}", currentRow.get("clients"));
					}
				}
			
			}
			
			setAlertDetails(currentRow.get("alertDetails"));
			
			System.out.println(alertingUrl);
		
	}
	
	public static boolean verifyCreateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			System.out.println(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			triggerDeleteAPI(currentAlert);		
			if(alertingResponse.getStatusCode() == 204) {
				alertId.remove(alertId.size()-1);
				triggerCreateAPI(getTestName());
				verifyCreateAPIResponse();
				return true;
			}else {
				System.out.println("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
				return false;
			}		
		}else if(alertingResponse.getStatusCode() == 201) {
			System.out.println(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			System.out.println("Alert Created : " + getCurrentAlert());
			return true;
		}else {
			System.out.println("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		System.out.println("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyNewAlertCreation(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			System.out.println(alertingResponse.getBody().asString());
			System.out.println("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
			return false;		
		}else if(alertingResponse.getStatusCode() == 201) {
			System.out.println(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			System.out.println("Alert Created : " + getCurrentAlert());
			return true;
		}else {
			System.out.println("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		System.out.println("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyUpdateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				System.out.println("Update of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			System.out.println("Alert Not Updated with Response Code : " + alertingResponse.getStatusCode());
			System.out.println("Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		System.out.println("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyUpdateAPIResponseWithSnooze(){

		try {
		if(alertingResponse.getStatusCode() == 202) {
				System.out.println("Update of Alert Snoozed Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else if (alertingResponse.getStatusCode() == 204){
			System.out.println("Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		System.out.println("Alert Update is giving Response Code : " + alertingResponse.getStatusCode());
		return false;
		}catch (Exception e) {
		System.out.println("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyDeleteAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				System.out.println("Delete of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			System.out.println("Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode());
			System.out.println("Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static void triggerITSMSimulatorAPI(){
		try {
		itsmPreProcessing();
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public static void itsmPreProcessing() throws Exception{
				
		HashMap<String, String> currentRow = new HashMap<String, String>();
		
		currentRow.putAll(DataUtils.getTestRow());
		System.out.println("Getting Host URL:" + currentRow);
		
		if(!itsmUrl.contains("itsmsimulator")) {
		
			itsmUrl = itsmUrl + Utilities.getMavenProperties("ITSMSimulatorUrlSchema")
				.replace("{partners}", currentRow.get("partners"))
				.replace("{clients}", currentRow.get("clients"))
				.replace("{sites}", currentRow.get("sites"))
				.replace("{conditionId}", currentRow.get("conditionId"))
				.replace("//", "/0/");
		
		}else {
			
			if (Utilities.getMavenProperties("Environment").trim().equals("QA")) {
				AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("QAHostUrlV1"));
			} else if (Utilities.getMavenProperties("Environment").trim().equals("DT")) {
				AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("DTHostUrlV1"));
			} else if (Utilities.getMavenProperties("Environment").trim().equals("PROD")) {
				AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("PRODHostUrl"));
			}
			
			itsmUrl = itsmUrl + Utilities.getMavenProperties("ITSMSimulatorUrlSchema")
			.replace("{partners}", currentRow.get("partners"))
			.replace("{clients}", currentRow.get("clients"))
			.replace("{sites}", currentRow.get("sites"))
			.replace("{conditionId}", currentRow.get("conditionId"));
			
		}
		
		System.out.println(itsmUrl);
	
}
	
	public static boolean getITSMSimulatorResponse() throws InterruptedException{
		Thread.sleep(3000);
		triggerITSMSimulatorAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
				for(String alertID : alertId)
				setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
				return true;
		}else {
			System.out.println("Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			System.out.println("Failed to save ITSM Response : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean verifyITSMSimulatorResponse() throws InterruptedException{
		
		JsonPath filterPath = JsonPath.from(filterArray.toString());
		System.out.println(filterPath.getList("action"));
		int i =0;
		
		try {
		if(filterArray.size()>0) {
		while (i < filterArray.size()){
			if(filterArray.getJSONObject(i).get("action").equals("POST")) {
				if(filterArray.getJSONObject(i+1).get("action").equals("PUT")) {
					if(filterArray.getJSONObject(i+2).get("action").equals("DELETE")) {
						System.out.println("All 3 Requests Reached till ITSM");
						i = i+3;
					}else {
						System.out.println("Delete Requests is not reached till ITSM : ");
						return false;
					}
				}else {
					System.out.println("Update Requests is not reached till ITSM : ");
					return false;
				}
			}else {
				System.out.println("Create Requests is not reached till ITSM : ");
				return false;
			}	
		}
		return true;
		}else {
			System.out.println("No Alerts Reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
public static boolean verifyITSMResponseForChildAlert() throws InterruptedException{
		
		try {	
			if(filterArray.isEmpty()) {
				System.out.println("No Child Alerts Reached Till ITSM.");
				return true;
		}else {
			System.out.println("Child alert reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			System.out.println("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean verifyRemediateURLITSMRequest() throws InterruptedException{
		
		int i = 0;
		while(i < filterArray.size()) {
			JSONObject filterObj = filterArray.getJSONObject(i);
			if(filterObj.get("action").equals("POST")) {
				JSONObject payloadObj = filterObj.getJSONObject("payload");
				if(payloadObj.get("remediateurl").equals(null) || payloadObj.get("fetchdataurl").equals(null)) {
					System.out.println("URL for Remediate or FetchMoreData is Null");
					return false;
				}else {
					System.out.println("Remediate URL : " + payloadObj.get("remediateurl"));
					System.out.println("FetchMore URL : " + payloadObj.get("fetchdataurl"));
					return true;
				}
			}
			i++;
		}
		System.out.println("There is No POST Request in ITSM Simulator");
		return false;
	}

	public static boolean waitForSnooze() throws InterruptedException{
		
		Thread.sleep(10000);
		return true;
		
	}
	
	/*
	 * public void verifyGETApiResponse() throws ParseException{
	 * 
	 * JSONParser j_parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE); JSONObject
	 * j_obj = (JSONObject) j_parser.parse(alertingResponse.toString());
	 * 
	 * if(j_obj.containsKey("PartnerId"))
	 * Assert.assertEquals((String)j_obj.get("summary"), "fetch from db");
	 * 
	 * }
	 */


/*	public static ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,String query) throws Exception {

		//DatabaseUtility.getListByQuery(databaseName, sqlServerURL, username, password, query, column);
	}*/

	public Response getAlertDetailsResponse() {
		return alertingResponse;
	}

	public static void setAlertDetailsResponse(Response alertingResponse) {
		AlertingAPITest.alertingResponse = alertingResponse;
	}
	
	public static void closeTest() {
		alertId.clear();
		filterArray.clear();
	}

}
