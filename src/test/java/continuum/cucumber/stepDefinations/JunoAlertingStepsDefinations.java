package continuum.cucumber.stepDefinations;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JunoAlertingUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.DriverFactory;
import continuum.cucumber.Utilities;
import continuum.noc.pages.AuvikPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class JunoAlertingStepsDefinations extends AuvikPageFactory{
	
	public static Scenario scenario;
	
	@Before
	 public void readScenario(Scenario scenario) {
		JunoAlertingStepsDefinations.scenario = scenario;
		String environment = Utilities.getMavenProperties("Environment").trim();
		setFileName("TestData_" + environment + ".xls");
		if (environment.equals("QA")) {
			setHostURL(Utilities.getMavenProperties("QAHostUrl"));
			setDbHost(Utilities.getMavenProperties("QADBHost"));
			setDbUserName(Utilities.getMavenProperties("QADBUserName"));
			setDbPassword(Utilities.getMavenProperties("QADBPassword"));
		} else if (environment.equals("DT")) {
			setHostURL(Utilities.getMavenProperties("DTHostUrl"));
			setDbHost(Utilities.getMavenProperties("DTDBHost"));
			setDbUserName(Utilities.getMavenProperties("DTDBUserName"));
			setDbPassword(Utilities.getMavenProperties("DTDBPassword"));
		} else {
			Assert.assertTrue(hostUrl != null ,"Host URL is not defined for environment : " + environment);
		}
	 }
	
	
	/* @After
	    public void embedScreenshot(Scenario scenario) {
	        if(scenario.isFailed()) {
	        try {
	            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	            scenario.embed(screenshot, "image/png");
	        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
	            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
	        }
	        
	        }
	        driver.quit();
	    }*/
	 
	private String transactionID;
	JsonObject jsonObject = new JsonObject();
	private String databaseUserName;
	private String databasePassword;
	private String databaseHost;
	private String URL;
	private String fileName;
	private String hostUrl;
	private String currentTime;
	private String dUpDcDtime1 = null;
	private JsonObject alertDetailJson;
	
	private void setJsonData(JsonObject jObject){
		jsonObject = jObject;
	}
	
	private JsonObject getJsonData(){
		return jsonObject;
	}
	
	private void setalertDetailJson(JsonObject jObject){
		alertDetailJson = jObject;
	}
	
	private JsonObject getalertDetailJson(){
		return alertDetailJson;
	}
	
	private void setAlertID(String inTransactionID){
		transactionID = inTransactionID;
	}
	
	private String getAlertID(){
		return transactionID;
	}
	
	private String getFileName(){
		return fileName;
	}
	
	private void setFileName(String file){
		fileName = file;
	}
	
	private void setURL(String url){
		URL = url;
	}
	
	private String getURL(){
		return URL;
	}
	
	private void setHostURL(String hosturl){
		hostUrl = hosturl;
	}
	
	private String getHostURL(){
		return hostUrl;
	}
	
	private String getDbHost(){
		return databaseHost;
	}
	
	private void setDbHost(String dbHost){
		databaseHost = dbHost;
	}
	
	private String getDbUserName(){
		return databaseUserName;
	}
	
	private void setDbUserName(String userCreds){
		databaseUserName = userCreds;
	}
	
	private String getDbPassword(){
		return databasePassword;
	}
	
	private void setDbPassword(String userPassword){
		databasePassword = userPassword;
	}
	
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API$")
	public void i_trigger_create_alert_API(String arg1, String arg2) throws Throwable {		
			
		
		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + getFileName();		
		DataUtils.setTestRow(excelFilePath, arg1, arg2);
		HashMap<String, String> currentRow = new HashMap<>();
		
		currentRow.putAll(DataUtils.getTestRow());		
		String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchema")
				.replace("{partners}", currentRow.get("partners"))
				.replace("{sites}", currentRow.get("sites"))
				.replace("{HostUrl}", getHostURL());
								
		JsonObject albums = new JsonObject();
		albums.addProperty("resourceId", Integer.parseInt(currentRow.get("resourceId")));
		albums.addProperty("conditionId", Integer.parseInt(currentRow.get("conditionId")));
		
		JsonObject dataset = new JsonObject();
	
		String[] alertDetails = currentRow.get("alertDetails").split(";");
	
		for(String adetails : alertDetails){
			String[] alertDetail = adetails.split(",");
			String dataType = alertDetail[0];
			String[] keyVal = alertDetail[1].split("=");
			String key = keyVal[0];
			String val = keyVal[1];
			
			if(dataType.trim().equalsIgnoreCase("Int")){
				dataset.addProperty(key, Integer.parseInt(val));
			}
			if(dataType.trim().equalsIgnoreCase("Str")){
				dataset.addProperty(key, val);
			}	
		}		
		setalertDetailJson(dataset);
		albums.add("alertDetails", dataset);		
		setJsonData(albums);
		URL = createAlertUrl;
		setURL(URL);		
		scenario.write(URL);		
		System.out.println(albums);
		System.out.println(URL);
		
		Response resp = RestAssured.given().contentType("application/json").body(albums.toString()).post(getURL()).andReturn();
		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");
		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
	    JsonObject  jobject = jelement.getAsJsonObject();
	    System.out.println("Status =====================================================" + jobject.get("status"));
	    String apiStatusID = jobject.get("status").toString();
	    System.out.println(jobject.get("alertId"));
	    Assert.assertEquals(apiStatusID, "201", "Create alert API execution failed, the api status ID is " + apiStatusID + " and API message body is ");
	    System.out.println("AlertID =====================================================" + jobject.get("alertId").getAsString());
	    setAlertID(jobject.get("alertId").getAsString());
	}
	
	@Given("^I naviagte to ITS portal$")
	public void user_naviagte_to_Ticket_portal() throws Throwable {		
		//Log.assertThat(iTSLoginPage.navigateToTicketPortal(), "Navigated to ITS portal page", "Failed to Navigate to ITS portal page", DriverFactory.getDriver());		
	}
	
	@When("^I login to ITS portal$")
	public void i_login_to_ITS_portal() throws Throwable {
		iTSLoginPage.loginToTicketPortal();
	}
	
	@When("^I naviagte to NOC portal$")
	public void user_navigate_to_NOC_Portal() throws Throwable {
		nOClogin.navigateToNOC();
	}
	
	@When("^I login to Noc portal$")
	public void i_login_to_Noc_portal_and() throws Throwable {
		nOChome = nOClogin.loginToNOC();
		//Log.message("I login to Noc portal", DriverFactory.getDriver());
	}
	
	@When("^I navigate to New ticket window$")
	public void i_navigate_to_New_ticket_window() throws Throwable {
		iTSHomePage.clickTab("Tickets");
		iTSHomePage.navigateToTeamQueueNewTicket();
	}
	
	@When("^I search ticket id on report page$")
	public void i_serach_ticket_id_on_report_page() throws Throwable {
		iTSHomePage.searchTicketID("201706290172344");
	}
	
	@When("^I verify searched ticket details$")
	public void i_verify_searched_ticket_details() throws Throwable {
		iTSHomePage.verifySearchTicketDetails();
	}
	
	@Then("^I navigate to ticket detail page for ticket$")
	public void i_navigate_to_ticket_detail_page_for_ticket() throws Throwable {
		iTSTicketDetailsPage = iTSHomePage.navigateToTicketDetailsPage();
		//Log.message("Navigated to 'Ticket details page'", DriverFactory.getDriver());
	}
	
	@Then("^I verify ticket details on ticket details page of ITS portal$")
	public void i_verify_ticket_details_on_tickt_details_page_of_ITS_portal() throws Throwable {
		iTSTicketDetailsPage.verifyTicketDetails();
	}
	
	@Given("^I verify create alert api request in PAS_ReqQueue table$")
	public void i_verify_create_alert_api_request_in_PAS_ReqQueue_table() throws Exception {
		String query = "select * from PAS_ReqQueue where CorrelationID like '" + getAlertID() + "'";
		JsonParser parser = new JsonParser();
		System.out.println(getDbHost());
		System.out.println(getDbUserName());
		System.out.println(getDbPassword());
		
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost() , getDbUserName(), getDbPassword(), query);		
		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());				
		String dRegId = null, dConditionId = null, dSiteId = null, dMemberId = null, dInputReq = null, dOperation = null, dUpDcDtime = null;
		while (rs.next()) {
			dRegId = rs.getString("RegId").trim();
			dConditionId = rs.getString("ConditionId").trim();
			dSiteId = rs.getString("SiteId").trim();
			dMemberId = rs.getString("MemberId").trim();
			
			dInputReq = rs.getString("InputReq").trim();
			dOperation = rs.getString("Operation").trim();
			String dDcDtime = rs.getString("DcDtime").trim();
			dUpDcDtime1 = rs.getString("UpDcDtime").trim();
		}		
		Assert.assertTrue(currentRow.get("resourceId").trim().equalsIgnoreCase(dRegId), 
				"Resource ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("resourceId") + ", Actual :" + dRegId);
		Assert.assertTrue(currentRow.get("conditionId").trim().equalsIgnoreCase(dConditionId), 
				"Condition ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("conditionId") + ", Actual :" + dConditionId);
		Assert.assertTrue(currentRow.get("sites").trim().equalsIgnoreCase(dSiteId), 
				"Site ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("sites") + ", Actual :" + dSiteId);
		Assert.assertTrue(currentRow.get("partners").trim().equalsIgnoreCase(dMemberId), 
				"Partner ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("partners") + ", Actual :" + dMemberId);		
		Assert.assertEquals(parser.parse(dInputReq).getAsJsonObject(), getalertDetailJson(),
				"InputReq does not match in PAS_ReqQueue_table, Expected " + getalertDetailJson() + " ,Actual " + dInputReq);
		Assert.assertTrue(dOperation.equals("1"), 
				"Operation ID does not match in PAS_ReqQueue_table, Expected '1', Actual :" + dOperation);
		
		System.out.println("dUpDcDtime ReqQue : " + dUpDcDtime1);
		dUpDcDtime1 = dUpDcDtime1.substring(0, 19);
		
		
		long timeElapsed = JunoAlertingUtils.getDateDifference(currentTime, dUpDcDtime1);
		
		if(timeElapsed > 30000){
			Assert.fail("Alert processing taken time more than expected value of 30 seconds.");
		}else
			System.out.println("***Processing done within time limits - " + timeElapsed + " milliseconds");
		//System.out.println("Time Lapse +++++++++++++++++++++++++++++++++++++++++++++  " + getDateDifference(getCurrentTime("America/Los_Angeles"),dDcDtime));
		
	}
	
	@Given("^I verify create alert api request is deleted from pas_reqcons table$")
	public void i_verify_create_alert_api_request_is_deleted_from_pas_reqcons_table() throws Exception {
		String query = "select * from PAS_ReqQueue where CorrelationID like '" + getAlertID() + "'";
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);		
		int count = 0 ;
		while (rs.next()) {
			count++;
		}
		System.out.println("Number of cons entry_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+  " + count);
	}
	
	@Given("^I verify create alert request is archived in PAS_ReqQueueArchive table$")
	public void i_verify_create_alert_request_is_archived_in_PAS_ReqQueueArchive_table() throws Exception {		
		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());				
		String query = "select * from PAS_ReqQueueArchive where CorrelationID like '" + getAlertID() + "'";	
		int count = 0 ;
		while(count <= 0){			
			ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost(), getDbUserName(), getDbPassword(), query);
			while (rs.next()) {
					count++;
			}		
			System.out.println("Number Of Rows in a system +++++++++++++++++++++++++++++++++ " + count);
			Thread.sleep(500);
		}		
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);	
		String dRegId = null, dConditionId = null, dSiteId = null, dMemberId = null, dInputReq, dOperation, dDcDtime = null, dUpDcDtime=null, dInsertedOn=null;
		while (rs.next()) {
			dRegId = rs.getString("RegId");
			dConditionId = rs.getString("ConditionId");
			dSiteId = rs.getString("SiteId");
			dMemberId = rs.getString("MemberId");
			dInputReq = rs.getString("InputReq");
			dOperation = rs.getString("Operation");
			dDcDtime = rs.getString("DcDtime");
			dUpDcDtime = rs.getString("UpDcDtime");
			dInsertedOn = rs.getString("InsertedOn");
		}		
		Assert.assertTrue(currentRow.get("resourceId").trim().equalsIgnoreCase(dRegId), 
				"Resource ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("resourceId") + ", Actual :" + dRegId);
		Assert.assertTrue(currentRow.get("conditionId").trim().equalsIgnoreCase(dConditionId), 
				"Condition ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("conditionId") + ", Actual :" + dConditionId);
		Assert.assertTrue(currentRow.get("sites").trim().equalsIgnoreCase(dSiteId), 
				"Site ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("sites") + ", Actual :" + dSiteId);
		Assert.assertTrue(currentRow.get("partners").trim().equalsIgnoreCase(dMemberId), 
				"Partner ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("partners") + ", Actual :" + dMemberId);
		
		System.out.println("dUpDcDtime ReqQueArchive: " + dInsertedOn);
		dInsertedOn= dInsertedOn.substring(0, 19);
		
		long timeElapsed = JunoAlertingUtils.getDateDifference(dInsertedOn,dUpDcDtime1);
				
		if(timeElapsed > 30000){
			Assert.fail("Alert processing taken time more than expected value of 30 seconds.");
		}else{
			System.out.println("***Processing done within time limits - " + timeElapsed + " milliseconds" );
		}
	}
	
	@Given("^I verify an alert entry is created in pas_reqcons table on successful processing of an Alert request$")
	public void i_verify_an_alert_entry_is_created_in_pas_reqcons_table_on_successful_processing_of_an_Alert_request() throws Exception {
		
		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());				
		System.out.println("RegId " + currentRow.get("resourceId"));
		System.out.println("ConditionId " + currentRow.get("conditionId"));
		System.out.println("SiteId " + currentRow.get("sites"));
		System.out.println("MemberId " + currentRow.get("partners"));
		
		
		String query = "select * from PAS_ReqCons with(NOLOCK) where LastStatus = '" + getAlertID() + "'";
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost(), getDbUserName(), getDbPassword(), query);		
		int count = 0 ;
		
		HashMap<String, String> dbValues = new HashMap<String, String>(); 
		while (rs.next()) {
			dbValues.put("MemberId", rs.getString("MemberId")); //done
			dbValues.put("SiteId", rs.getString("SiteId")); // done
			dbValues.put("ParentUniqueId", rs.getString("ParentUniqueId")); //done
			dbValues.put("ChildUniqueId", rs.getString("ChildUniqueId"));
			dbValues.put("ParentRegId", rs.getString("ParentRegId"));
			dbValues.put("ChildRegid", rs.getString("ChildRegid"));
			dbValues.put("LastStatus", rs.getString("LastStatus"));
			dbValues.put("LastStatusDate", rs.getString("LastStatusDate"));
			dbValues.put("Description", rs.getString("Description"));
			dbValues.put("RefDatetime", rs.getString("RefDatetime"));
			dbValues.put("RegType", rs.getString("RegType"));
			dbValues.put("ThreshValue", rs.getString("ThreshValue"));
			dbValues.put("ConditionId", rs.getString("ConditionId"));
			dbValues.put("ConsLevel", rs.getString("ConsLevel"));
			dbValues.put("AlertId", rs.getString("AlertId"));
			dbValues.put("TicketId", rs.getString("TicketId"));
			dbValues.put("CallQId", rs.getString("CallQId"));
			dbValues.put("CallQRefId", rs.getString("CallQRefId"));
			dbValues.put("MsgbId", rs.getString("MsgbId"));
			dbValues.put("AssignToGrp", rs.getString("AssignToGrp"));
			dbValues.put("NocActionId", rs.getString("NocActionId"));
			dbValues.put("DcDtime", rs.getString("DcDtime"));
			dbValues.put("UpDcDtime", rs.getString("UpDcDtime"));
			count++;
		}
		
		assertReqConTables(currentRow,dbValues); //asserting DB column values
	}
	
	

	@Given("^I verify an archived alert entry is created in PAS_ReqConsArchive table on successfull processing of close alert request$")
	public void i_verify_an_archived_alert_entry_is_created_in_PAS_ReqConsArchive_table_on_successfull_processing_of_close_alert_request() throws Exception {

		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());				
		System.out.println("RegId " + currentRow.get("resourceId"));
		System.out.println("ConditionId " + currentRow.get("conditionId"));
		System.out.println("SiteId " + currentRow.get("sites"));
		System.out.println("MemberId " + currentRow.get("partners"));
		
		/*System.out.println("RegId " + currentRow.get("TotalPhysicalMemoryInMB"));
		System.out.println("RegId " + currentRow.get("AverageMemoryInUseInMB"));
		System.out.println("RegId " + currentRow.get("AverageAvailableMemoryInMB"));
		System.out.println("RegId " + currentRow.get("ProcessConsumingHighestMemory"));*/		
		
		String query = "select * from PAS_ReqConsArchive with(NOLOCK) where LastStatus = '" + getAlertID() + "'";
		
		int count = 0 ;
		while(count <= 0){			
			ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);
			while (rs.next()) {
					count++;
			}
			System.out.println("Count " + count);			
			System.out.println("Number Of Rows in a system +++++++++++++++++++++++++++++++++ " + count);
			Thread.sleep(500);
		}
		
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);
		HashMap<String, String> dbValues = new HashMap<String, String>(); 
		while (rs.next()) {
			dbValues.put("MemberId", rs.getString("MemberId")); //done
			dbValues.put("SiteId", rs.getString("SiteId")); // done
			dbValues.put("ParentUniqueId", rs.getString("ParentUniqueId")); //done
			dbValues.put("ChildUniqueId", rs.getString("ChildUniqueId"));
			dbValues.put("ParentRegId", rs.getString("ParentRegId"));
			dbValues.put("ChildRegid", rs.getString("ChildRegid"));
			dbValues.put("LastStatus", rs.getString("LastStatus"));
			dbValues.put("LastStatusDate", rs.getString("LastStatusDate"));
			dbValues.put("Description", rs.getString("Description"));
			dbValues.put("RefDatetime", rs.getString("RefDatetime"));
			dbValues.put("RegType", rs.getString("RegType"));
			dbValues.put("ThreshValue", rs.getString("ThreshValue"));
			dbValues.put("ConditionId", rs.getString("ConditionId"));
			dbValues.put("ConsLevel", rs.getString("ConsLevel"));
			dbValues.put("AlertId", rs.getString("AlertId"));
			dbValues.put("TicketId", rs.getString("TicketId"));
			dbValues.put("CallQId", rs.getString("CallQId"));
			dbValues.put("CallQRefId", rs.getString("CallQRefId"));
			dbValues.put("MsgbId", rs.getString("MsgbId"));
			dbValues.put("AssignToGrp", rs.getString("AssignToGrp"));
			dbValues.put("NocActionId", rs.getString("NocActionId"));
			dbValues.put("DcDtime", rs.getString("DcDtime"));
			dbValues.put("UpDcDtime", rs.getString("UpDcDtime"));
			count++;
		}
		
		assertReqConTables(currentRow, dbValues);
	}
	
	@Given("^I trigger auto close alert API$")
	public void i_trigger_auto_close_alert_API() {
		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());
		/*String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchema")
				.replace("{partners}", currentRow.get("partners"))
				.replace("{sites}", currentRow.get("sites"))
				.replace("{HostUrl}", getHostURL());*/
		Response resp = RestAssured.given().delete(getURL() + "/" + getAlertID()).andReturn();
		System.out.println("Send POST command");
		System.out.println("Status Code \n" + resp.getStatusCode());
	}
	
	
	
	@Given("^I trigger update alert API$")
	public void i_trigger_update_alert_API() {		
		JsonObject jobj = getJsonData();
		jobj.add("alertDetails", getalertDetailJson());
		System.out.println(jobj.toString());
		
		Response resp = RestAssured.given().contentType("application/json").body(jobj.toString()).put(getURL() + "/" + getAlertID()).andReturn();
		
		Response resps = RestAssured.given().contentType("application/json").body(jobj.toString()).get("").andReturn();
		
		System.out.println("Send POST command");
		System.out.println("Status Code \n" + resp.getStatusCode());
		System.out.println("Status Body \n" + resp.getBody().asString());
		System.out.println("Time taken to get response is \n" + resp.getTime()+" milli second");
		
		//JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
	    //JsonObject  jobject = jelement.getAsJsonObject();
		int apiStatusID = resp.getStatusCode();
	    System.out.println("Status =====================================================" + resp.getStatusCode());
	    Assert.assertEquals(apiStatusID, 204, "Update alert API execution failed, the api status ID is " + apiStatusID + ", Expected status ID is 204");
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
	
	/**
	 * 
	 * @param currentRow
	 * @param dbValues
	 */
	private void assertReqConTables(HashMap<String, String> currentRow, HashMap<String, String> dbValues) {
		
		Assert.assertTrue(currentRow.get("partners").trim().equalsIgnoreCase(dbValues.get("MemberId")), 
				"Member ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("partners") + ", Actual :" + dbValues.get("MemberId"));
		
		Assert.assertTrue(currentRow.get("sites").trim().equalsIgnoreCase(dbValues.get("SiteId")), 
				"Site ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("sites") + ", Actual :" + dbValues.get("SiteId"));
		
		Assert.assertTrue(currentRow.get("resourceId").trim().equalsIgnoreCase(dbValues.get("ParentUniqueId")), 
				"Resource ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("resourceId") + ", Actual :" + dbValues.get("ParentUniqueId"));
		
		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("ChildUniqueId")), 
				"ChildUniqueId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("ChildUniqueId"));
		
		Assert.assertTrue(currentRow.get("resourceId").trim().equalsIgnoreCase(dbValues.get("ParentRegId")), 
				"ParentRegID does not match in PAS_ReqCons_table, Expected " + currentRow.get("resourceId") + ", Actual :" + dbValues.get("ParentRegId"));
		
		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("ChildRegid")), 
				"ChildRegid does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("ChildRegid"));
		
		Assert.assertTrue(getAlertID().trim().equalsIgnoreCase(dbValues.get("LastStatus")), 
				"Alert ID does not match in PAS_ReqCons_table, Expected " + getAlertID() + ", Actual :" + dbValues.get("LastStatus"));
		
		Assert.assertTrue("DPMA".trim().equalsIgnoreCase(dbValues.get("RegType")), 
				"RegType does not match in PAS_ReqCons_table, Expected " + "DPMA" + ", Actual :" + dbValues.get("RegType"));
		
		Assert.assertTrue("1".trim().equalsIgnoreCase(dbValues.get("ThreshValue")), 
				"ThreshValue does not match in PAS_ReqCons_table, Expected " + "1" + ", Actual :" + dbValues.get("ThreshValue"));
		
		Assert.assertTrue(currentRow.get("conditionId").trim().equalsIgnoreCase(dbValues.get("ConditionId")), 
				"Condition ID does not match in PAS_ReqCons_table, Expected " + currentRow.get("conditionId") + ", Actual :" + dbValues.get("ConditionId"));
		
		Assert.assertTrue("Resource".trim().equalsIgnoreCase(dbValues.get("ConsLevel")), 
				"ConsLevel does not match in PAS_ReqCons_table, Expected " + "Resource" + ", Actual :" + dbValues.get("ConsLevel"));
		
		if(!dbValues.get("AlertId").trim().equalsIgnoreCase("0")){
			
			Assert.assertTrue("0".equalsIgnoreCase(dbValues.get("TicketId")),
					"Alert ID : " + dbValues.get("AlertId") + " and TicketID:  " + dbValues.get("TicketId")+ " both have non zero entry." );
		}else{
			Assert.assertTrue("0".equalsIgnoreCase(dbValues.get("AlertId")),
					"Alert ID : " + dbValues.get("AlertId") + " and TicketID:  " + dbValues.get("TicketId")+ " both have non zero entry." );			
		}
		
		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("CallQId")), 
				"CallQId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("CallQId"));
		
		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("CallQRefId")), 
				"CallQRefId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("CallQRefId"));
		
		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("MsgbId")), 
				"MsgbId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("MsgbId"));		
	}
	
}

