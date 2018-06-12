package com.continuum.platform.alerting.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
*/
import continuum.cucumber.DatabaseUtility;

public class CreateAPITest {

	public static void main(String[] args) throws Exception {
		
		
		String output = "{\"status\": 201,\"alertId\": \"6466b553-4ffd-4d49-b1bf-c18e03eb3794\"}";
		
		
		
		/*String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\TestData.xls";
		HashMap<String, String> currentRow = new HashMap<>();
		System.out.println("File Path " + excelFilePath);

		DataUtils.setTestRow(excelFilePath, "PlatformAlertingCreateAlert", "Platform001");

		currentRow.putAll(DataUtils.getTestRow());
		System.out.println(currentRow.get("partners"));
		System.out.println(currentRow.get("sites"));
		System.out.println(currentRow.get("resourceId"));
		System.out.println(currentRow.get("conditionId"));
		System.out.println(currentRow.get("AverageCPUUtilization"));
		System.out.println(currentRow.get("TotalNoOfProcessors"));
		System.out.println(currentRow.get("AverageNumOfProcesses"));
		System.out.println(currentRow.get("AveragePercentIOTime"));
		System.out.println(currentRow.get("AveragePercentSystemTime"));
		System.out.println(currentRow.get("AveragePercentUserTime"));
		System.out.println(currentRow.get("NameOfProcessWithHighestCPUUtilization"));
		
		JsonObject albums = new JsonObject();
		// add a property calle title to the albums object
		albums.addProperty("resourceId", Integer.parseInt(currentRow.get("resourceId")));
		albums.addProperty("conditionId", Integer.parseInt(currentRow.get("conditionId")));
		
		JsonObject dataset = new JsonObject();
		dataset.addProperty("AverageCPUUtilization", Integer.parseInt(currentRow.get("AverageCPUUtilization")));
		dataset.addProperty("TotalNoOfProcessors", Integer.parseInt(currentRow.get("TotalNoOfProcessors")));
		dataset.addProperty("AverageNumOfProcesses", Integer.parseInt(currentRow.get("AverageNumOfProcesses")));
		dataset.addProperty("AveragePercentIOTime", Integer.parseInt(currentRow.get("AveragePercentIOTime")));
		dataset.addProperty("AveragePercentSystemTime", Integer.parseInt(currentRow.get("AveragePercentSystemTime")));
		dataset.addProperty("AveragePercentUserTime", Integer.parseInt(currentRow.get("AveragePercentUserTime")));
		dataset.addProperty("NameOfProcessWithHighestCPUUtilization", currentRow.get("NameOfProcessWithHighestCPUUtilization"));		
		albums.add("alertDetails", dataset);
		System.out.println(albums);
		
		
		String regID = null;
		String query = "select * from PAS_ConditionDetail with(NOLOCK);";
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);
		while (rs.next()) {
			System.out.println(rs.getString("ConditionSchema"));
		}
		
		
		
		Response resp = RestAssured.given()
				.contentType("application/json")				
				.body("{\"resourceId\":53751731,\"conditionId\":17026,\"alertDetails\":{\"AverageCPUUtilization\":85,\"TotalNoOfProcessors\":0,\"AverageNumOfProcesses\":50,\"AveragePercentIOTime\":0,\"AveragePercentSystemTime\":0,\"AveragePercentUserTime\":0,\"NameOfProcessWithHighestCPUUtilization\":\"crs.exe\"}}")
				.post("http://alerting.dtitsupport247.net:8080/alerting/v1/partners/50016358/sites/50110019/alerts")
				.andReturn();
		System.out.println("Send POST command");
		System.out.println("Status Code \n" + resp.getStatusCode());
		System.out.println("Status Body \n" + resp.getBody().asString());
		System.out.println("Time taken to get response is \n" + resp.getTime()+" milli second");
*/		
		JsonElement jelement = new JsonParser().parse(output);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    System.out.println("=====================================================" + jobject.get("status"));
	    System.out.println("=====================================================" + jobject.get("alertId").getAsString());
	    
	
	}
	
	public static ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,
			String query)
					throws Exception {
		Connection conn = DatabaseUtility.createConnection(databaseName, sqlServerURL, username, password);
		ResultSet rs;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Table doesn not exist");
			e.printStackTrace();
			throw e;

		}
		return rs;
	}

}
