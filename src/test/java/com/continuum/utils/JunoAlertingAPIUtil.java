package com.continuum.utils;

import com.google.gson.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/*import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;*/
//import continuum.cucumber.testRunner.SendReport;

public class JunoAlertingAPIUtil {

	public static Response postWithQueryParameters(String authToken, Map queryParameters, String URI) {
		Response res = setAuthToken(authToken).params(queryParameters).when().post(URI);
		return res;
	}

	public static String generateURIWithPathParameter(String URI, Object param) {
		String s1 = URI;
		String s2 = s1.replace("{pathParameter}", param.toString());
		return s2;
	}

	public static RequestSpecification setAuthToken(String authToken) {
		return given().log().all().headers("iPlanetDirectoryPro", authToken);
	}
	
	public static RequestSpecification setTransactionID() {
		String transactionID = "TEST_" + JunoAlertingUtils.timeStamp() ;
		return given().log().all().headers("X-Request-Id", transactionID ,"Authorization","Api-Token 1cNThoj4R_ytjJ1Y9pnim");
	}

	public static void verifyStatusCode(Response res, int expectedStatusCode) {
		int status = res.getStatusCode();
		res.then().assertThat().statusCode(expectedStatusCode);
	}

	public static RequestSpecification setFormParameters(String formParametersJson) {

		String APIBody = formParametersJson;
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBody(APIBody);
		builder.setContentType(ContentType.JSON);
		RequestSpecification requestSpec = builder.build();
		requestSpec.log();
		return requestSpec;

	}

	public static Response postWithFormParameters(String authToken, String formParametersJson, String URI) {
		Response res = setAuthToken(authToken).spec(setFormParameters(formParametersJson)).when().post(URI);
		return res;
	}

	public static Response postWithFormParameters(String formParametersJson, String URI) {
		
		Response res = setTransactionID().body(formParametersJson).contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().post(URI).andReturn();
		return res;
	}
	
	public static Response putWithFormParameters(String formParametersJson, String URI) {
		
		Response res = setTransactionID().body(formParametersJson).contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().put(URI).andReturn();
		return res;
	}

	public static Response getWithBody(JSONObject formParametersJson, String URI) {
		ConnectionConfig connectionConfig = new ConnectionConfig();
		connectionConfig.closeIdleConnectionsAfterEachResponseAfter(5, TimeUnit.MINUTES);
		System.out.println(formParametersJson);
		// Response res =
		// given().spec(setFormParameters(formParametersJson)).when().get("");
		// Response res =
		// given().spec(setFormParameters(formParametersJson)).when().get(URI);
		Response res = given().contentType(ContentType.JSON).body(formParametersJson).when().get(URI);

		return res;
	}

	public static Response postWithNoBody(String URI) {
		Response res = given().log().all().header("Content-Type","application/json").log().all().when().post(URI);
		return res;
	}

	public static Response postWithNoBody(String URI, String authToken) {
		Response res = setAuthToken(authToken).contentType(ContentType.JSON).when().post(URI);
		return res;
	}

	public static Response getWithQueryparameters(String URI, Map queryParameters) {
		Response res = given().log().all().params(queryParameters).when().get(URI);
		return res;
	}

	public static Response getWithQueryparameters(String URI, String authToken, Map queryParameters) {
		Response res = setAuthToken(authToken).params(queryParameters).when().get(URI);
		return res;
	}

	public static Response putWithQueryparameters(String authToken, Map queryParameters, String URI) {
		Response res = setAuthToken(authToken).params(queryParameters).when().put(URI);
		return res;
	}

	public static Response getWithPathParameters(String authToken, String URI) {
		Response res = setAuthToken(authToken).when().get(URI);
		return res;
	}

	public static Response getWithNoParameters(String URI) {
		Response res = setTransactionID().contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().get(URI);
		return res;
	}
	
	public static Response getWithAuthorizationNoParameters(String URI) {
		Response res = setTransactionID().contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().get(URI);
		return res;
	}

	public static Response getWithNoParameters(String URI, String authToken) {
		Response res = setAuthToken(authToken).when().get(URI);
		return res;
	}

	public static Response putWithFormParameters(String authToken, String formParametersJson, String URI) {
		Response res = setAuthToken(authToken).spec(setFormParameters(formParametersJson)).when().put(URI);
		return res;
	}

	public static Response putWithPathParameters(String authToken, String URI) {
		Response res = setAuthToken(authToken).when().put(URI);
		return res;
	}

	public static Response deleteWithPathParameters(String authToken, String URI) {
		Response res = setAuthToken(authToken).when().delete(URI);
		return res;
	}

	public static Response deletePathParameters(String URI) {
		Response res = setTransactionID().contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().delete(URI);
		return res;
	}

	public static Response deleteWithBody(String formParametersJson, String URI) {
		Response res = setTransactionID().body(formParametersJson).contentType(ContentType.JSON).config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).when().delete(URI).andReturn();
		return res;
	}

	/*
	 * 
	 */
	public static void verifyNodevalue(Response res, String node, Object expectedNodeValue) {
		res.then().body(node, equalTo(expectedNodeValue));

	}

	public static void verifyNodeExists(Response res, String node) {
		Assert.assertEquals(isNodePresent(res, node), true);
	}

	public static boolean VerifyStringContains(String str, String subStr, boolean caseSensitive) {
		if (caseSensitive) {
			if (str.contains(subStr)) {
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNodePresent(Response res, String node) {
		JsonPath j = new JsonPath(res.asString());
		if (j.getJsonObject(node) == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void assertNodePresent(Response res, String node) {
		Assert.assertEquals(isNodePresent(res, node), true);
	}

	public static void assertNodeNotPresent(String node, Response res) {
		Assert.assertEquals(isNodePresent(res, node), false);
	}

	public static Object getNodeValue(Response res, String node) {
		String json = res.asString();
		return JsonPath.with(json).get(node);
	}

	public static List getNodeValues(Response res, String node) {
		String json = res.asString();
		return JsonPath.with(json).get(node);
	}

	public static boolean isInList(List li, Object o) {
		return li.contains(o);
	}

	public static RequestSpecification setFormParameters() {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();
		// requestSpec.log().all();
		return requestSpec;
	}

	public static Response postWithPathParameters(String authToken, String URI) {
		Response res = setAuthToken(authToken).spec(setFormParameters()).when().post(URI);
		return res;
	}

	public static Response deleteWithQueryParameters(String authToken, Map queryParameters, String URI) {
		Response res = setAuthToken(authToken).params(queryParameters).when().delete(URI);
		return res;
	}

	public static void wait(int timeInSec) throws InterruptedException {
		Thread.sleep(10 * 1000);
	}

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
		/*
		 * Response response = RestAssured.with().header("TokenID",
		 * "53H48H48H48H49H55H53H51") .header("Integration",
		 * "Alert").contentType("application/json").body(inputJson) .post(
		 * "https://services.dtitsupport247.net/TicketService.svc/json/ticket/createticket"
		 * );
		 */
		// System.out.println(response.getBody().asString());

	}

	
	  public void getAlertingMSVersionDetails(String environment){ //Get Version
	 /* Number if((SendReport.buildNo==null)){
	  
	  if (environment.equals("QA")){ Response resp =
	  RestAssured.given().log().all().contentType("application/json").config(com.
	  jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.
	  config.EncoderConfig.encoderConfig().
	  appendDefaultContentCharsetToContentTypeIfUndefined(false))).get(
	  "http://internal-qaplatformalertingservice-1206480811.us-east-1.elb.amazonaws.com/alerting/version"
	  ).andReturn(); JsonElement jelement = new
	  JsonParser().parse(resp.getBody().asString()); JsonObject jobject =
	  jelement.getAsJsonObject();
	  
	  
	  String versionNumber = jobject.get("BuildNumber").getAsString();
	  SendReport.buildNo = versionNumber; System.out.println(versionNumber); } else
	  if(environment.equals("INT")){ Response resp =
	  RestAssured.given().log().all().contentType("application/json").config(com.
	  jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.
	  config.EncoderConfig.encoderConfig().
	  appendDefaultContentCharsetToContentTypeIfUndefined(false))).get(
	  "http://internal-intplatformalertingservice-1115287148.ap-south-1.elb.amazonaws.com/alerting/version"
	  ).andReturn(); JsonElement jelement = new
	  JsonParser().parse(resp.getBody().asString()); JsonObject jobject =
	  jelement.getAsJsonObject();
	  
	  
	  String versionNumber = jobject.get("BuildNumber").getAsString();
	  SendReport.buildNo = versionNumber; System.out.println(versionNumber); } }*/
	  
	  
	  }
	 

}
