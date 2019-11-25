package com.continuum.platform.alerting.api;

import com.continuum.utils.DataUtils;
import continuum.cucumber.Utilities;
import continuum.cucumber.webservices.RestServicesUtility;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/*import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
 */

public class AlertDetailAPITest {

	private String alertDetailsUrl;
	private JSONObject alertDetailsResponse;

	public void triggerAlertDetailsGETrequest(String fileName,String arg1,String arg2){

		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + fileName;		
		try {
			DataUtils.setTestRow(excelFilePath, arg1, arg2);

			HashMap<String, String> currentRow = new HashMap<>();

			currentRow.putAll(DataUtils.getTestRow());

			alertDetailsUrl = Utilities.getMavenProperties("PlatformAlertUrlSchema")
					.replace("{partner}", currentRow.get("PartnerId"))
					.replace("{site}", currentRow.get("SiteID"))
					.replace("{regid}", currentRow.get("resourceId"));

			alertDetailsResponse = RestServicesUtility.getJsonResponseofWebService(alertDetailsUrl);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void verifyGETApiResponse() throws ParseException{

		JSONParser j_parser = new JSONParser();
		JSONObject j_obj = (JSONObject) j_parser.parse(alertDetailsResponse.toString());

		if(j_obj.containsKey("PartnerId"))
			Assert.assertEquals((String)j_obj.get("summary"), "fetch from db");

	}


/*	public static ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,String query) throws Exception {

		//DatabaseUtility.getListByQuery(databaseName, sqlServerURL, username, password, query, column);
	}*/

	public String getAlertDetailsUrl() {
		return alertDetailsUrl;
	}

	public void setAlertDetailsUrl(String alertDetailsUrl) {
		this.alertDetailsUrl = alertDetailsUrl;
	}

	public JSONObject getAlertDetailsResponse() {
		return alertDetailsResponse;
	}

	public void setAlertDetailsResponse(JSONObject alertDetailsResponse) {
		this.alertDetailsResponse = alertDetailsResponse;
	}

}
