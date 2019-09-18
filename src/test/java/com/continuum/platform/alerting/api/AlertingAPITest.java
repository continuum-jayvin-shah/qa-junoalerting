package com.continuum.platform.alerting.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JsonRespParserUtility;
import com.continuum.utils.JunoAlertingAPIUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import continuum.cucumber.KafkaProducerUtility;
import continuum.cucumber.Utilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class AlertingAPITest {

	private static Logger logger = Logger.getLogger(AlertingAPITest.class);
	private static String alertingUrl, alertDetails, itsmUrl, testName, currentAlert, jasUrl, alertingAPIUrl = "";
	private static Response alertingResponse;
	private static List<String> alertId = new ArrayList<String>();
	private static JSONArray filterArray = new JSONArray();
	private static HashMap<String, String> currentRow = new HashMap<String, String>();
	
	public AlertingAPITest() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getCurrentAlert() {
		return currentAlert;
	}
	public static void setCurrentAlert(String currentAlert) {
		AlertingAPITest.currentAlert = currentAlert;
	}
	
	public static HashMap<String, String> getCurrentRow() {
		return currentRow;
	}
	public static void setCurrentRow(HashMap<String, String> currentRow) {
		AlertingAPITest.currentRow = currentRow;
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
	
	public static String getJasUrl() {
		return jasUrl;
	}
	public static void setJasUrl(String jasUrl) {
		AlertingAPITest.jasUrl = jasUrl;
	}
	
	public Response getAlertDetailsResponse() {
		return alertingResponse;
	}

	public static void setAlertDetailsResponse(Response alertingResponse) {
		AlertingAPITest.alertingResponse = alertingResponse;
	}
	
	public static boolean triggerCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		logger.debug("Alert Details : " + alertDetails);
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingAPIUrl));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Alert Creation Failed with Error Message : " + e.getMessage());
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
		logger.debug(parentAlertDetails.toString());
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
		Thread.sleep(3000);
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Parent Alert Creation Failed with Error Message : " + e.getMessage());
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
			logger.debug(parentAlertDetails.toString());
			AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
			Thread.sleep(3000);
			if(alertingResponse.getStatusCode() == 201) {
				logger.debug(alertingResponse.getBody().asString());
				setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				logger.debug("Alert Created : " + getCurrentAlert());
				i++;
			}else if(alertingResponse.getStatusCode() == 409){
				logger.debug("Parent Alert not Created with Error Conflict");
				return false;
			}else {
				logger.debug("Parent Alert no created with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		}
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Parent Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerUpdateAPI(){
		try {
		preProcessing(getTestName());
		logger.debug("Alert Details : " + alertDetails);
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean triggerDeleteAPI(){
		try {
			int i =0;
			if(alertId.size()>1) {
				for(i=0; i<alertId.size();i++) {
					AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
						if(alertingResponse.getStatusCode() != 204) {
							logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
							return false;
						}
						logger.debug("Alert Deleted : " + alertId.get(i));
					}
			}else {
				AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
				logger.debug("Alert Deletion Called for AlertID : " + alertId.get(i));
				return true;
			}
			return true;
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerChildDeleteAPI(){
		try {
		for(int i=0; i<alertId.size()-1;i++) {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			logger.debug("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		logger.debug("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerChildDeleteAPIForBothParent(){
		try {
		for(int i=0; i<alertId.size()-2;i++) {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			logger.debug("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		logger.debug("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean triggerDeleteAPI(String alertID){
		try {
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertID));
			if(alertingResponse.getStatusCode() != 204) {
				logger.debug("Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		logger.debug("Alert Deleted : " + alertID);
		return true;
		
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public static void preProcessing(String testName) throws Exception{
		
			setCurrentRow(DataUtils.getTestRow("Test", testName));
			logger.debug("Test Data Captured.");
	
			logger.debug("Getting Host URL:" + alertingUrl);

			alertingAPIUrl = alertingUrl + Utilities.getMavenProperties("AlertingUrlSchema")
					.replace("{partners}", currentRow.get("partners"))
					.replace("{clients}", currentRow.get("clients"))
					.replace("{sites}", currentRow.get("sites"))
					.replace("{endpoints}", currentRow.get("endpoints"));
			
				if(currentRow.get("endpoints").isEmpty()) {
					if(currentRow.get("sites").isEmpty()) {
						if(currentRow.get("clients").isEmpty()) {
							alertingAPIUrl = alertingAPIUrl.replace("sites//endpoints//", "");
						}else {
							alertingAPIUrl = alertingAPIUrl.replace("endpoints//", "");
							alertingAPIUrl = alertingAPIUrl.replace("sites/", "clients/{clients}");
							alertingAPIUrl = alertingAPIUrl.replace("{clients}", currentRow.get("clients"));
						}
					}else {
						alertingAPIUrl = alertingAPIUrl.replace("endpoints//", "");
						alertingAPIUrl = alertingAPIUrl.replace("sites", "clients/{clients}/sites");
						alertingAPIUrl = alertingAPIUrl.replace("{clients}", currentRow.get("clients"));
					}
				}
			setAlertDetails(currentRow.get("alertDetails"));
			
			logger.debug(alertingAPIUrl);
		
	}
	
	public static boolean verifyCreateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.debug(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			triggerDeleteAPI(currentAlert);		
			if(alertingResponse.getStatusCode() == 204) {
				alertId.remove(alertId.size()-1);
				Thread.sleep(5000);
				triggerCreateAPI(getTestName());
				return verifyCreateAPIResponse();
			}else {
				logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
				return false;
			}		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.debug(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.debug("Alert Created : " + getCurrentAlert());
			return true;
		}else {
			logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyAlertSuspension(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.debug(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			triggerDeleteAPI(currentAlert);		
			if(alertingResponse.getStatusCode() == 204) {
				alertId.remove(alertId.size()-1);
				Thread.sleep(5000);
				triggerCreateAPI(getTestName());
				return verifyAlertSuspension();
			}else {
				logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
				return false;
			}		
		}else if(alertingResponse.getStatusCode() == 400) {
			logger.debug(alertingResponse.getBody().asString());
			JSONObject suspensionResponse = (JSONObject)JSONSerializer.toJSON(alertingResponse.getBody().asString());
			if(suspensionResponse.getInt("status") == 205) {
			logger.debug("Alert Not Created because of suspension with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
			return true;
			}
			logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
			return false;
		}else {
			logger.debug("Alert Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyNewAlertCreation(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.debug(alertingResponse.getBody().asString());
			logger.debug("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
			return false;		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.debug(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.debug("Alert Created : " + getCurrentAlert());
			return true;
		}else {
			logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyDuplicateAlertCreation(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.debug(alertingResponse.getBody().asString());
			logger.debug("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
			//setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			return true;		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.debug(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.debug("Alert Created : " + getCurrentAlert());
			return false;
		}else {
			logger.debug("Alert Not giving Conflict with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.debug("Duplicate Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyUpdateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				logger.debug("Update of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			logger.debug("Alert Not Updated with Response Code : " + alertingResponse.getStatusCode());
			logger.debug("Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyNonExistingAlertAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 404) {
				if(JsonPath.from(alertingResponse.getBody().asString()).get("status").equals("203"))
				logger.debug("Update/Delete of Alert Failed with Status Code : " + alertingResponse.getStatusCode() + " And Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
				return true;
		}else if(alertingResponse.getStatusCode() == 204){
			logger.debug("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		else {
			logger.debug("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Updation/Deletion Passed : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyUpdateAPIResponseWithSnooze(){

		try {
		if(alertingResponse.getStatusCode() == 202) {
				logger.debug("Update of Alert Snoozed Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else if (alertingResponse.getStatusCode() == 204){
			logger.debug("Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		logger.debug("Alert Update is giving Response Code : " + alertingResponse.getStatusCode());
		return false;
		}catch (Exception e) {
		logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static boolean verifyDeleteAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				logger.debug("Delete of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			logger.debug("Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode());
			logger.debug("Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public static void triggerITSMSimulatorAPI(){
		try {
		itsmPreProcessing();
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public static void itsmPreProcessing() throws Exception{
				
		HashMap<String, String> currentRow = new HashMap<String, String>();
		
		currentRow.putAll(DataUtils.getTestRow());
		logger.debug("Getting Host URL:" + currentRow);
		
	
		itsmUrl = alertingUrl + Utilities.getMavenProperties("ITSMSimulatorUrlSchema")
			.replace("{partners}", currentRow.get("partners"))
			.replace("{clients}", currentRow.get("clients"))
			.replace("{sites}", currentRow.get("sites"))
			.replace("{conditionId}", currentRow.get("conditionId"));
			
		if(currentRow.get("endpoints").isEmpty()) {
			if(currentRow.get("sites").isEmpty()) {
				if(currentRow.get("clients").isEmpty()) {
					itsmUrl = itsmUrl.replace("/clients//sites/", "");
				}else {
					itsmUrl = itsmUrl.replace("/sites/", "");
				}
			}
		}
		
		logger.debug(itsmUrl);
	
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
			logger.debug("Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.debug("Failed to save ITSM Response : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean verifyAlertCreationInJAS() throws InterruptedException{
		Thread.sleep(3000);
		triggerJASGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
					logger.debug("Alert Id " + currentAlert + " Created in JAS.");
					return true;
				}else {
					logger.debug("ALert Id Not Created in JAS!!");
					return false;
				}
				
		}else {
			logger.debug("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.debug("Failed to save JAS Response : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean verifyAlertDeletionInJAS() throws InterruptedException{
		int i = 0;
		while(i<10) {
		Thread.sleep(10000);
		triggerJASGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 404) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(203)) {
					logger.debug("Alert Id " + currentAlert + " Deleted from JAS.");
					return true;
				}else {
					logger.debug("Alert Id Not Deleted from JAS!!");
					return false;
				}
				
		}else {
			logger.debug("Delete Request Pending with Response Code : " + alertingResponse.getStatusCode());
			i++;
			continue;
		}
		}catch (Exception e) {
			logger.debug("Failed to save JAS Response : " + e.getMessage());
			return false;
		}
		}
		logger.debug("Alert ID "+ currentAlert + " Not Deleted!!");
		return false;
	}
	
	public static void triggerJASGetAlertAPI(){
		try {
		jasPreProcessing();
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(jasUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public static void jasPreProcessing() throws Exception{
		
		//HashMap<String, String> currentRow = new HashMap<String, String>();
		
		//currentRow.putAll(DataUtils.getTestRow());
		
		setJasUrl(Utilities.getMavenProperties("DTJASHostUrlV1"));		
		logger.debug("Getting Host URL:" + jasUrl);	
		jasUrl = jasUrl + Utilities.getMavenProperties("GetJASAlertUrlSchema")
			.replace("{partners}", currentRow.get("partners"))
			.replace("{sites}", currentRow.get("sites"))
			.replace("{clients}", currentRow.get("clients"))
			.replace("{alert}", currentAlert);
		
		logger.debug(jasUrl);
	}
	
	public static void triggerAlertingGetAlertAPI(){
		try {
		getAlertPreProcessing();
		AlertingAPITest.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertingAPIUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public static void getAlertPreProcessing() throws Exception{
		
		HashMap<String, String> currentRow = new HashMap<String, String>();
		
		currentRow.putAll(DataUtils.getTestRow());
		
		logger.debug("Getting Host URL:" + alertingUrl);	
		alertingAPIUrl = alertingUrl + Utilities.getMavenProperties("GetAlertUrlSchema")
			.replace("{partners}", currentRow.get("partners"))
			.replace("{sites}", currentRow.get("sites"))
			.replace("{clients}", currentRow.get("clients"))
			.replace("{alert}", currentAlert);
		
		if(currentRow.get("endpoints").isEmpty()) {
			if(currentRow.get("sites").isEmpty()) {
				if(currentRow.get("clients").isEmpty()) {
					alertingAPIUrl = alertingAPIUrl.replace("/clients//sites/", "");
				}else {
					alertingAPIUrl = alertingAPIUrl.replace("/sites/", "");
				}
			}
		}
		
		logger.debug(alertingAPIUrl);
	}
	
	public static boolean verifyAlertCreationInAlertingMS() throws InterruptedException{
		Thread.sleep(3000);
		triggerAlertingGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
					logger.debug("Alert Id " + currentAlert + " Created in JAS.");
					return true;
				}else {
					logger.debug("ALert Id Not Created in JAS!!");
					return false;
				}
				
		}else {
			logger.debug("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.debug("Failed to save JAS Response : " + e.getMessage());
			return false;
		}

	}
	
	public static boolean verifyAlertDeletionInAlertingMS() throws InterruptedException{
		Thread.sleep(3000);
		triggerAlertingGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 404) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(203)) {
					logger.debug("Alert Id " + currentAlert + " Deleted from JAS.");
					return true;
				}else {
					logger.debug("Alert Id Not Deleted from JAS!!");
					return false;
				}
				
		}else {
			logger.debug("Alert ID "+ currentAlert + " Not Deleted!!");
			return false;
		}
		}catch (Exception e) {
			logger.debug("Failed to save JAS Response : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean verifyITSMSimulatorResponse() throws InterruptedException{
		
		JsonPath filterPath = JsonPath.from(filterArray.toString());
		logger.debug(filterPath.getList("action"));
		int i =0;
		
		try {
		if(filterArray.size()>0) {
		while (i < filterArray.size()){
			if(filterArray.getJSONObject(i).get("action").equals("POST")) {
				if(filterArray.getJSONObject(i+1).get("action").equals("PUT")) {
					if(filterArray.getJSONObject(i+2).get("action").equals("DELETE")) {
						logger.debug("All 3 Requests Reached till ITSM");
						i = i+3;
					}else {
						logger.debug("Delete Requests is not reached till ITSM : ");
						return false;
					}
				}else {
					logger.debug("Update Requests is not reached till ITSM : ");
					return false;
				}
			}else {
				logger.debug("Create Requests is not reached till ITSM : ");
				return false;
			}	
		}
		return true;
		}else {
			logger.debug("No Alerts Reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
public static boolean verifyITSMResponseForChildAlert() throws InterruptedException{
		
		try {	
			if(filterArray.isEmpty()) {
				logger.debug("No Child Alerts Reached Till ITSM.");
				return true;
		}else {
			logger.debug("Child alert reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
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
					logger.debug("URL for Remediate or FetchMoreData is Null");
					return false;
				}else {
					logger.debug("Remediate URL : " + payloadObj.get("remediateurl"));
					logger.debug("FetchMore URL : " + payloadObj.get("fetchdataurl"));
					return true;
				}
			}
			i++;
		}
		logger.debug("There is No POST Request in ITSM Simulator");
		return false;
	}
	
	public static boolean triggerManualClosure(String kafkaMessageType) {
		
		String kafkaMessage;
		
		switch (kafkaMessageType) {
		case "AlertID":
			kafkaMessage = "{\"alertId\":\""+getCurrentAlert()+"\"}";
			break;
		case "MetaData":
			kafkaMessage = getManualClosureMetadata();
			break;
		default:
			logger.debug("Message Type is Invalid!!");
			return false;
		}
		
		logger.debug("Posting Kafka Message to Kafka topic : " + kafkaMessage);
		KafkaProducerUtility.postMessage(Utilities.getMavenProperties("KafkaTopic"), kafkaMessage);
		return true;
		
	}
	
	public static String getManualClosureMetadata() {
		
		HashMap<String, String> currentRow = new HashMap<String, String>();
		
		JSONObject manualClosureMessage = new JSONObject();
		manualClosureMessage.put("clientid", currentRow.get("clients"));
		manualClosureMessage.put("siteid", currentRow.get("sites"));
		manualClosureMessage.put("partnerid", currentRow.get("partners"));
		manualClosureMessage.put("resourceid", currentRow.get("resourceid"));
		manualClosureMessage.put("endpointid", currentRow.get("endpoints"));
		manualClosureMessage.put("conditionid", currentRow.get("conditionid"));
		
		return manualClosureMessage.toString();
	}

	public static boolean waitForSnooze(int duration) throws InterruptedException{
		
		TimeUnit.SECONDS.sleep(duration);
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

	
	public static void closeTest() {
		alertId.clear();
		filterArray.clear();
	}

}
