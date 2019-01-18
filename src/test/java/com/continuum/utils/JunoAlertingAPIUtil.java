package com.continuum.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/*import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;*/
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import continuum.cucumber.testRunner.SendReport;

public class JunoAlertingAPIUtil {


	public static String getJson(HashMap<String, String> dataObj) {
		String json = null;

		JsonObject albums = new JsonObject();
		// add a property calle title to the albums object
		albums.addProperty("idField", Long.parseLong(dataObj.get("ID (alertid)")));
		albums.addProperty("createdOnField", "/Date(1452003726000)/");
		albums.addProperty("siteIDField", Long.parseLong(dataObj.get("Siteid")));
		albums.addProperty("deviceIDField", 52334L);
		albums.addProperty("deviceNameField", dataObj.get("DeviceName (entity)"));
		albums.addProperty("typeIDField", Long.parseLong(dataObj.get("TypeID (alerttypeid)")));
		albums.addProperty("statusIDField", Integer.parseInt(dataObj.get("StatusID (statusid)")));
		albums.addProperty("scheduleStartDateTimeField", "/Date(000)/");
		albums.addProperty("scheduleEndDateTimeField", "/Date(000)/");
		albums.addProperty("priorityIDField", Integer.parseInt(dataObj.get("Priority (severity)")));
		albums.addProperty("assignToField", 1);
		albums.addProperty("assignToUserIDField", 0L);
		albums.addProperty("categoryIDField", 500L);
		albums.addProperty("categoryNameField", dataObj.get("CategoryName (Link)"));
		albums.addProperty("subjectField", dataObj.get("Subject (alertName)"));
		albums.addProperty("referenceTicketIDField", dataObj.get("ReferenceTicketID (entityid)"));
		albums.addProperty("descriptionField", dataObj.get("Description (description)"));
		albums.addProperty("createdByGroupField", dataObj.get("CreatedByGroup (auvikaccountname)"));
		albums.addProperty("createdByUserIDField", 0L);
		albums.addProperty("deviceFriendlyNameField", dataObj.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"));
		albums.addProperty("lastUpdatedByGroupField", "Test Auvik");
		albums.addProperty("lastUpdatedByUserIDField", 0L);
		albums.addProperty("lastUpdatedOnField", "/Date(000)/");
		albums.addProperty("siteCodeField", dataObj.get("SiteCode (siteCode)"));

		// create an array called datasets
		JsonArray datasets = new JsonArray();
		// create a dataset
		JsonObject dataset = new JsonObject();
		// add the property album_id to the dataset
		dataset.addProperty("createdOnField", "/Date(1452003726000)/");
		dataset.addProperty("idField", Long.parseLong(dataObj.get("ID (alertid)")));
		dataset.addProperty("noteDescriptionField", "Test note 1");
		dataset.addProperty("createdByGroupField", dataObj.get("CreatedByGroup (auvikaccountname)"));
		dataset.addProperty("createdByUserIDField", 0L);
		dataset.addProperty("ticketIDField", "1234");
		datasets.add(dataset);
		albums.add("ticketNotesField", datasets);
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		json = gson.toJson(albums);
		return json;
	}

	public static void main(String[] args) throws IOException {
		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\TestData.xls";
		HashMap<String, String> currentRow = new HashMap();
		String inputJson;

		DataUtils.setTestRow(excelFilePath, "StackSwitchPortStatusChange", "AUVIKCR001");

		currentRow.putAll(DataUtils.getTestRow());
		inputJson = JunoAlertingAPIUtil.getJson(currentRow);
		System.out.println(inputJson);
		/*Response response = RestAssured.with().header("TokenID", "53H48H48H48H49H55H53H51")
				.header("Integration", "Alert").contentType("application/json").body(inputJson)
				.post("https://services.dtitsupport247.net/TicketService.svc/json/ticket/createticket");*/
	//	System.out.println(response.getBody().asString());

	}
	
	public void getAlertingMSVersionDetails(String environment){
				//Get Version Number
			if((SendReport.buildNo==null)){
				
				if (environment.equals("QA")){
					Response resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).get("http://internal-qaplatformalertingservice-1206480811.us-east-1.elb.amazonaws.com/alerting/version").andReturn();
					JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
					JsonObject  jobject = jelement.getAsJsonObject();
	
					
					String versionNumber = jobject.get("BuildNumber").getAsString();
					SendReport.buildNo = versionNumber;
					System.out.println(versionNumber);
				}
				else if(environment.equals("DT")){
					Response resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).get("http://internal-intplatformalertingservice-1115287148.ap-south-1.elb.amazonaws.com/alerting/version").andReturn();
					JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
					JsonObject  jobject = jelement.getAsJsonObject();
	
					
					String versionNumber = jobject.get("BuildNumber").getAsString();
					SendReport.buildNo = versionNumber;
					System.out.println(versionNumber);
				}

				
			}
	}

}
