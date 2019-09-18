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

public class ParallelAPITest {

	private Logger logger = Logger.getLogger(ParallelAPITest.class);
	private String alertDetails, itsmUrl, testName, currentAlert, jasUrl, alertingAPIUrl = "";
	private static String alertingUrl;
	private Response alertingResponse;
	private List<String> alertId = new ArrayList<String>();
	private JSONArray filterArray = new JSONArray();
	private HashMap<String, String> currentRow = new HashMap<String, String>();
	
	public ParallelAPITest() {
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
		ParallelAPITest.alertingUrl = alertingUrl;
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
	
	public boolean triggerCreateAPI(String testName){
		try {
		setTestName(testName);
		preProcessing(getTestName());
		logger.debug("Alert Details : " + alertDetails);
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingAPIUrl));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Alert Creation Failed with Error Message : " + e.getMessage());
			return false;
		}
	}
	
	
	public boolean triggerUpdateAPI(){
		try {
		preProcessing(getTestName());
		logger.debug("Alert Details : " + alertDetails);
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
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
							logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
							return false;
						}
						logger.debug("Alert Deleted : " + alertId.get(i));
					}
			}else {
				this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
				logger.debug("Alert Deletion Called for AlertID : " + alertId.get(i));
				return true;
			}
			return true;
		}catch (Exception e) {
			logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
			return false;
		}

	}
	
	
	public boolean triggerDeleteAPI(String alertID){
		try {
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertID));
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
	
	public void preProcessing(String testName) throws Exception{
		
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
	
	public boolean verifyCreateAPIResponse(){

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
	
	
	public boolean verifyUpdateAPIResponse(){

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
	
	
	public boolean verifyDeleteAPIResponse(){

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
	
	public void triggerITSMSimulatorAPI(){
		try {
		itsmPreProcessing();
		this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
		}
	}
	
	public void itsmPreProcessing() throws Exception{
				
		//HashMap<String, String> currentRow = new HashMap<String, String>();
		
		//setCurrentRow(DataUtils.getTestRow());
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
	
	public boolean getITSMSimulatorResponse() throws InterruptedException{
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
	
public boolean verifyITSMSimulatorResponse() throws InterruptedException{
		
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
	
	public void closeTest() {
		alertId.clear();
		filterArray.clear();
	}

}
