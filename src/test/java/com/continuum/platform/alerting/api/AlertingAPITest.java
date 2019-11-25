package com.continuum.platform.alerting.api;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JsonRespParserUtility;
import com.continuum.utils.JunoAlertingAPIUtil;
import continuum.cucumber.KafkaProducerUtility;
import continuum.cucumber.Utilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlertingAPITest {

	private Logger logger = Logger.getLogger(AlertingAPITest.class);
	private String alertDetails, itsmUrl, testName, currentAlert, jasUrl, alertingAPIUrl = "";
	private static String alertingUrl, kafkaServer;
	private Response alertingResponse;
	private List<String> alertId = new ArrayList<String>();
	private JSONArray filterArray = new JSONArray();
	private HashMap<String, String> currentRow = new HashMap<String, String>();
	
	public AlertingAPITest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCurrentAlert() {
		return currentAlert;
	}
	public void setCurrentAlert(String currentAlert) {
		this.currentAlert = currentAlert;
	}
	
	public HashMap<String, String> getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(HashMap<String, String> currentRow) {
		this.currentRow = currentRow;
	}
	
	public JSONArray getFilterArray() {
		return filterArray;
	}

	public void setFilterArray(JSONArray filterArray) {
		this.filterArray.addAll(filterArray);
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public List<String> getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId.add(alertId);
	}
	
	public static String getalertingUrl() {
		return alertingUrl;
	}

	public static void setalertingUrl(String alertingUrl) {
		AlertingAPITest.alertingUrl = alertingUrl;
	}
	
	public String getAlertDetails() {
		return alertDetails;
	}

	public void setAlertDetails(String alertDetails) {
		this.alertDetails = alertDetails;
	}
	
	public String getItsmUrl() {
		return itsmUrl;
	}

	public void setItsmUrl(String itsmUrl) {
		this.itsmUrl = itsmUrl;
	}
	
	public String getJasUrl() {
		return jasUrl;
	}
	public void setJasUrl(String jasUrl) {
		this.jasUrl = jasUrl;
	}
	
	public Response getAlertDetailsResponse() {
		return alertingResponse;
	}

	public void setAlertDetailsResponse(Response alertingResponse) {
		this.alertingResponse = alertingResponse;
	}
	
	public static String getKafkaServer() {
		return kafkaServer;
	}

	public static void setKafkaServer(String kafkaServer) {
		AlertingAPITest.kafkaServer = kafkaServer;
	}
	
	public boolean triggerCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		logger.info("Alert Details : " + alertDetails);
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingAPIUrl));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public boolean triggerParentCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		JSON json = JSONSerializer.toJSON(alertDetails);
		JSONObject parentAlertDetails = (JSONObject)json;
		JSONArray alerts = new JSONArray();
		alerts.addAll(alertId);
		parentAlertDetails.put("alerts", alerts);
		logger.info(parentAlertDetails.toString());
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
		Thread.sleep(3000);
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Parent Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public boolean triggerParentWithSameConditionCreateAPI(String testName){
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
			logger.info(parentAlertDetails.toString());
			this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
			Thread.sleep(3000);
			if(alertingResponse.getStatusCode() == 201) {
				logger.info(alertingResponse.getBody().asString());
				setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				logger.info("Alert Created : " + getCurrentAlert());
				i++;
			}else if(alertingResponse.getStatusCode() == 409){
				logger.info("Parent Alert not Created with Error Conflict");
				return false;
			}else {
				logger.info("Parent Alert no created with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		}
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Parent Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public boolean triggerUpdateAPI(){
		try {
		preProcessing(getTestName());
		logger.info("Alert Details : " + alertDetails);
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	public boolean triggerDeleteAPI(){
		try {
			int i =0;
			if(alertId.size()>1) {
				for(i=0; i<alertId.size();i++) {
					this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
						if(alertingResponse.getStatusCode() != 204) {
							logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
							return false;
						}
						logger.info("Alert Deleted : " + alertId.get(i));
					}
			}else {
				this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
				logger.info("Alert Deletion Called for AlertID : " + alertId.get(i));
				return true;
			}
			return true;
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public boolean triggerChildDeleteAPI(){
		try {
		for(int i=0; i<alertId.size()-1;i++) {
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			logger.info("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		logger.info("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public boolean triggerChildDeleteAPIForBothParent(){
		try {
		for(int i=0; i<alertId.size()-2;i++) {
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
			if(alertingResponse.getStatusCode() != 204) {
				logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
			logger.info("Alert Deleted : " + alertId.get(i));
			Thread.sleep(2000);
		}
		logger.info("Alerts Deleted!!");
		return true;
		
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public boolean triggerDeleteAPI(String alertID){
		try {
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertID));
			if(alertingResponse.getStatusCode() != 204) {
				logger.info("Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode());
				return false;
			}
		logger.info("Alert Deleted : " + alertID);
		return true;
		
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public void preProcessing(String testName) throws Exception{
		
			setCurrentRow(DataUtils.getTestRow("Test", testName));
			logger.info("Test Data Captured.");
	
			logger.info("Getting Host URL:" + alertingUrl);

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
			
			logger.info(alertingAPIUrl);
		
	}
	
	public boolean verifyCreateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.info(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			triggerDeleteAPI(currentAlert);		
			if(alertingResponse.getStatusCode() == 204) {
				alertId.remove(alertId.size()-1);
				Thread.sleep(5000);
				triggerCreateAPI(getTestName());
				return verifyCreateAPIResponse();
			}else {
				logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
				return false;
			}		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.info(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.info("Alert Created : " + getCurrentAlert());
			return true;
		}else {
			logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyAlertSuspension(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.info(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			triggerDeleteAPI(currentAlert);		
			if(alertingResponse.getStatusCode() == 204) {
				alertId.remove(alertId.size()-1);
				Thread.sleep(5000);
				triggerCreateAPI(getTestName());
				return verifyAlertSuspension();
			}else {
				logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
				return false;
			}		
		}else if(alertingResponse.getStatusCode() == 400) {
			logger.info(alertingResponse.getBody().asString());
			JSONObject suspensionResponse = (JSONObject)JSONSerializer.toJSON(alertingResponse.getBody().asString());
			if(suspensionResponse.getInt("status") == 205) {
			logger.info("Alert Not Created because of suspension with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
			return true;
			}
			logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
			return false;
		}else {
			logger.info("Alert Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyNewAlertCreation(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			if(JsonPath.from(alertingResponse.getBody().asString()).get("alertId").equals(currentAlert)){
				logger.info(alertingResponse.getBody().asString());
				logger.info("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());				
				return false;
			} else {
				logger.info(alertingResponse.getBody().asString());
				setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
				triggerDeleteAPI(currentAlert);		
				if(alertingResponse.getStatusCode() == 204) {
					alertId.remove(alertId.size()-1);
					Thread.sleep(5000);
					triggerCreateAPI(getTestName());
					return verifyNewAlertCreation();
				} else {
					logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
					return false;
				}
			}		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.info(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.info("Alert Created : " + getCurrentAlert());
			return true;
			
		}else {
			logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyDuplicateAlertCreation(){

		try {
		if(alertingResponse.getStatusCode() == 409) {
			logger.info(alertingResponse.getBody().asString());
			logger.info("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
			//setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			return true;		
		}else if(alertingResponse.getStatusCode() == 201) {
			logger.info(alertingResponse.getBody().asString());
			setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
			logger.info("Alert Created : " + getCurrentAlert());
			return false;
		}else {
			logger.info("Alert Not giving Conflict with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.info("Duplicate Alert Verification Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyUpdateAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				logger.info("Update of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			logger.info("Alert Not Updated with Response Code : " + alertingResponse.getStatusCode());
			logger.info("Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyNonExistingAlertAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 404) {
				if(JsonPath.from(alertingResponse.getBody().asString()).get("status").equals("203"))
				logger.info("Update/Delete of Alert Failed with Status Code : " + alertingResponse.getStatusCode() + " And Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
				return true;
		}else if(alertingResponse.getStatusCode() == 204){
			logger.info("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		else {
			logger.info("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Updation/Deletion Passed : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyUpdateAPIResponseWithSnooze(){

		try {
		if(alertingResponse.getStatusCode() == 202) {
				logger.info("Update of Alert Snoozed Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else if (alertingResponse.getStatusCode() == 204){
			logger.info("Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		logger.info("Alert Update is giving Response Code : " + alertingResponse.getStatusCode());
		return false;
		}catch (Exception e) {
		logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public boolean verifyDeleteAPIResponse(){

		try {
		if(alertingResponse.getStatusCode() == 204) {
				logger.info("Delete of Alert Done with Status Code : " + alertingResponse.getStatusCode());
				return true;
		}else {
			logger.info("Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode());
			logger.info("Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
			return false;
		}
		}catch (Exception e) {
		logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
		return false;
		}

	}
	
	public void triggerITSMSimulatorAPI(){
		try {
		itsmPreProcessing();
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public void itsmPreProcessing() throws Exception{
				
		//HashMap<String, String> currentRow = new HashMap<String, String>();
		
		//currentRow.putAll(DataUtils.getTestRow());
		logger.info("Getting Host URL:" + alertingUrl);
		
	
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
		
		logger.info(itsmUrl);
	
}
	
	public boolean getITSMSimulatorResponse() throws InterruptedException{
		Thread.sleep(5000);
		triggerITSMSimulatorAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
				for(String alertID : alertId)
				setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
				return true;
		}else {
			logger.info("Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.info("Failed to save ITSM Response : " + e.getMessage());
			return false;
		}

	}
	
	public boolean verifyAlertCreationInJAS() throws InterruptedException{
		Thread.sleep(3000);
		triggerJASGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
					logger.info("Alert Id " + currentAlert + " Created in JAS.");
					return true;
				}else {
					logger.info("ALert Id Not Created in JAS!!");
					return false;
				}
				
		}else {
			logger.info("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.info("Failed to save JAS Response : " + e.getMessage());
			return false;
		}

	}
	
	public boolean verifyAlertDeletionInJAS() throws InterruptedException{
		int i = 0;
		while(i<10) {
		Thread.sleep(10000);
		triggerJASGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 404) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(203)) {
					logger.info("Alert Id " + currentAlert + " Deleted from JAS.");
					return true;
				}else {
					logger.info("Alert Id Not Deleted from JAS!!");
					return false;
				}
				
		}else {
			logger.info("Delete Request Pending with Response Code : " + alertingResponse.getStatusCode());
			i++;
			continue;
		}
		}catch (Exception e) {
			logger.info("Failed to save JAS Response : " + e.getMessage());
			return false;
		}
		}
		logger.info("Alert ID "+ currentAlert + " Not Deleted!!");
		return false;
	}
	
	public void triggerJASGetAlertAPI(){
		try {
		jasPreProcessing();
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(jasUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public void jasPreProcessing() throws Exception{
		
		//HashMap<String, String> currentRow = new HashMap<String, String>();
		
		//currentRow.putAll(DataUtils.getTestRow());
		
		setJasUrl(Utilities.getMavenProperties("DTJASHostUrlV1"));		
		logger.info("Getting Host URL:" + jasUrl);	
		jasUrl = jasUrl + Utilities.getMavenProperties("GetJASAlertUrlSchema")
			.replace("{partners}", currentRow.get("partners"))
			.replace("{sites}", currentRow.get("sites"))
			.replace("{clients}", currentRow.get("clients"))
			.replace("{alert}", currentAlert);
		
		logger.info(jasUrl);
	}
	
	public void triggerAlertingGetAlertAPI(){
		try {
		getAlertPreProcessing();
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertingAPIUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public void getAlertPreProcessing() throws Exception{
		
		//HashMap<String, String> currentRow = new HashMap<String, String>();
		
		//currentRow.putAll(DataUtils.getTestRow());
		
		logger.info("Getting Host URL:" + alertingUrl);	
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
		
		logger.info(alertingAPIUrl);
	}
	
	public boolean verifyAlertCreationInAlertingMS() throws InterruptedException{
		Thread.sleep(3000);
		triggerAlertingGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 200) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
					logger.info("Alert Id " + currentAlert + " Created in JAS.");
					return true;
				}else {
					logger.info("ALert Id Not Created in JAS!!");
					return false;
				}
				
		}else {
			logger.info("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
			return false;
		}
		}catch (Exception e) {
			logger.info("Failed to save JAS Response : " + e.getMessage());
			return false;
		}

	}
	
	public boolean verifyAlertDeletionInAlertingMS() throws InterruptedException{
		Thread.sleep(5000);
		triggerAlertingGetAlertAPI();
		try {
		if(alertingResponse.getStatusCode() == 404) {
				JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
				if(jasResponse.get("status").equals(203)) {
					logger.info("Alert Id " + currentAlert + " Deleted from JAS.");
					return true;
				}else {
					logger.info("Alert Id Not Deleted from JAS!!");
					return false;
				}
				
		}else {
			logger.info("Alert ID "+ currentAlert + " Not Deleted!!");
			return false;
		}
		}catch (Exception e) {
			logger.info("Failed to save JAS Response : " + e.getMessage());
			return false;
		}
	}
	
	public boolean verifyITSMSimulatorResponse() throws InterruptedException{
		
		JsonPath filterPath = JsonPath.from(filterArray.toString());
		logger.info(filterPath.getList("action"));
		int i =0;
		
		try {
		if(filterArray.size()>0) {
		while (i < filterArray.size()){
			if(filterArray.getJSONObject(i).get("action").equals("POST")) {
				if(filterArray.getJSONObject(i+1).get("action").equals("PUT")) {
					if(filterArray.getJSONObject(i+2).get("action").equals("DELETE")) {
						logger.info("All 3 Requests Reached till ITSM");
						i = i+3;
					}else {
						logger.info("Delete Requests is not reached till ITSM : ");
						return false;
					}
				}else {
					logger.info("Update Requests is not reached till ITSM : ");
					return false;
				}
			}else {
				logger.info("Create Requests is not reached till ITSM : ");
				return false;
			}	
		}
		return true;
		}else {
			logger.info("No Alerts Reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
public boolean verifyITSMResponseForChildAlert() throws InterruptedException{
		
		try {	
			if(filterArray.isEmpty()) {
				logger.info("No Child Alerts Reached Till ITSM.");
				return true;
		}else {
			logger.info("Child alert reached till ITSM!!");
			return false;
		}
		}catch (Exception e) {
			logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	public boolean verifyRemediateURLITSMRequest() throws InterruptedException{
		
		int i = 0;
		while(i < filterArray.size()) {
			JSONObject filterObj = filterArray.getJSONObject(i);
			if(filterObj.get("action").equals("POST")) {
				JSONObject payloadObj = filterObj.getJSONObject("payload");
				if(payloadObj.get("remediateurl").equals(null) || payloadObj.get("fetchdataurl").equals(null)) {
					logger.info("URL for Remediate or FetchMoreData is Null");
					return false;
				}else {
					logger.info("Remediate URL : " + payloadObj.get("remediateurl"));
					logger.info("FetchMore URL : " + payloadObj.get("fetchdataurl"));
					return true;
				}
			}
			i++;
		}
		logger.info("There is No POST Request in ITSM Simulator");
		return false;
	}
	
	public boolean triggerManualClosure(String kafkaMessageType) {
		
		String kafkaMessage;
		
		switch (kafkaMessageType) {
		case "AlertID":
			kafkaMessage = "{\"alertId\":\""+getCurrentAlert()+"\",\"transactionId\":\"TEST\"}";
			break;
		case "MetaData":
			kafkaMessage = getManualClosureMetadata();
			break;
		default:
			logger.info("Message Type is Invalid!!");
			return false;
		}
		
		logger.info("Posting Kafka Message to Kafka topic : " + kafkaMessage);
		KafkaProducerUtility.postMessage(kafkaServer, Utilities.getMavenProperties("KafkaTopic"), kafkaMessage);
		return true;
		
	}
	
	public String getManualClosureMetadata() {
		
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

	public boolean waitForSnooze(int duration) throws InterruptedException{
		
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


/*	public ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,String query) throws Exception {

		//DatabaseUtility.getListByQuery(databaseName, sqlServerURL, username, password, query, column);
	}*/

	
	public void closeTest() {
		alertId.clear();
		filterArray.clear();
	}

}
