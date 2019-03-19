package continuum.cucumber.stepDefinations;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.Reporter;

import com.continuum.platform.alerting.api.AlertDetailAPITest;
import com.continuum.utils.DataUtils;
import com.continuum.utils.JunoAlertingUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.Utilities;
import continuum.cucumber.testRunner.SendReport;
import continuum.noc.pages.AuvikPageFactory;
import continuum.noc.pages.NewAlertingMSPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class JunoAlertingStepsDefinations extends NewAlertingMSPageFactory{

	public static Scenario scenario;
	long wait = 200;

	@Before
	public void readScenario(Scenario scenario) {
		
		junoAlertingUtil.getAlertingMSVersionDetails(Utilities.getMavenProperties("Environment"));
		JunoAlertingStepsDefinations.scenario = scenario;
		Reporter.log("<b><i><font color='Blue'>====== Scenario Name: ====="+ scenario.getName()+"</font></i></b>");
		String environment = Utilities.getMavenProperties("Environment").trim();
		setFileName("TestData_" + environment + ".xls");
		if (environment.equals("QA")) {
			setHostURL(Utilities.getMavenProperties("QAHostUrlV1"));
			setDbHost(Utilities.getMavenProperties("QADBHost"));
			setDbUserName(Utilities.getMavenProperties("QADBUserName"));
			setDbPassword(Utilities.getMavenProperties("QADBPassword"));
		} else if (environment.equals("DT")) {
			setHostURL(Utilities.getMavenProperties("DTHostUrlV1"));
			setDbHost(Utilities.getMavenProperties("DTDBHost"));
			setDbUserName(Utilities.getMavenProperties("DTDBUserName"));
			setDbPassword(Utilities.getMavenProperties("DTDBPassword"));
		} else if (environment.equals("PROD")) {
			setHostURL(Utilities.getMavenProperties("PRODHostUrl"));
			setDbHost(Utilities.getMavenProperties("PRODDBHost"));
			setDbUserName(Utilities.getMavenProperties("PRODDBUserName"));
			setDbPassword(Utilities.getMavenProperties("PRODDBPassword"));
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
	private String RoundRobinURL;
	private String RoundRobinGETURL;
	private String fileName;
	private String hostUrl;
	private String currentTime;
	private String dUpDcDtime1 = null;
	private JsonObject alertDetailJson;
	private String statusCode = null;
	private int httpstatusCode = 0;
	private String errorCodeScenario = "111";

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

	public String getFileName(){
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
	
	public String getRoundRobinURL() {
		return RoundRobinURL;
	}

	public void setRoundRobinURL(String roundRobinURL) {
		RoundRobinURL = roundRobinURL;
	}

	public String getRoundRobinGETURL() {
		return RoundRobinGETURL;
	}

	public void setRoundRobinGETURL(String roundRobinGETURL) {
		RoundRobinGETURL = roundRobinGETURL;
	}

	private void setHostURL(String hosturl){
		hostUrl = hosturl;
	}

	private String getHostURL(){
		return hostUrl;
	}

	public String getDbHost(){
		return databaseHost;
	}

	private void setDbHost(String dbHost){
		databaseHost = dbHost;
	}

	public String getDbUserName(){
		return databaseUserName;
	}

	private void setDbUserName(String userCreds){
		databaseUserName = userCreds;
	}

	public String getDbPassword(){
		return databasePassword;
	}

	private void setDbPassword(String userPassword){
		databasePassword = userPassword;
	}

	private void setApiStatusID(String responseCode) {
		statusCode = responseCode;		
	}
	public String getApiStatusID() {
		return statusCode;
	}

	private void setStatusCode(int httpstatusCode) {

		this.httpstatusCode = httpstatusCode;
	}

	public int getStatusCode() {

		return httpstatusCode;
	}

	private void setErrorCodeScneario(String code) {
		this.errorCodeScenario	= code;
	}

	private String getErrorCodeScenario(){
		return errorCodeScenario;
	}


	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API$")
	public void i_trigger_create_alert_API(String arg1, String arg2) throws Throwable {
		Reporter.log("====================EXECUTING POST REQUEST FOR CREATING ALERTS=================================");
		triggerCreateAlertAPI(arg1, arg2);
		Assert.assertEquals(getApiStatusID(), "201", "Create alert API execution failed, the api status ID is " + getApiStatusID() + " and API message body is ");
		Reporter.log("AlertID is :" + getAlertID());
	}

	// Added by Bilal to validate HashID response
	@Then("^AlertID should get generated$")
	public void alertIDShouldGetGenerated() throws Throwable {
		Assert.assertEquals(getApiStatusID(), "201", "Create alert API execution failed, the api status ID is " + getApiStatusID() + " and API message body is ");
		
	}

	private JsonObject preProcessingCreateAlert(String arg1, String arg2) throws IOException {

		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + getFileName();		
		DataUtils.setTestRow(excelFilePath, arg1, arg2);
		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchemaNode")
					.replace("{partners}", currentRow.get("partners"))
					.replace("{sites}", currentRow.get("sites"))
					.replace("{endpoints}", currentRow.get("endpoints"))
					.replace("{Node1}", Utilities.getMavenProperties("Node1"));
			RoundRobinURL = createAlertUrl;
			setRoundRobinURL(RoundRobinURL);
			scenario.write(RoundRobinURL);
		}
		else {
		
		String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchema")
				.replace("{partners}", currentRow.get("partners"))
				.replace("{sites}", currentRow.get("sites"))
				.replace("{endpoints}", currentRow.get("endpoints"))
				.replace("{HostUrlV1}", getHostURL());
		URL = createAlertUrl;
		setURL(URL);		
		scenario.write(URL);
		}
		
		JsonObject albums = new JsonObject();
		albums.addProperty("resourceId", Integer.parseInt(currentRow.get("resourceId")));
		albums.addProperty("conditionId", Integer.parseInt(currentRow.get("conditionId")));

		if (Integer.parseInt(currentRow.get("conditionId"))==17054) {
			albums.addProperty("filterField", currentRow.get("filterField"));
		}


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
		//URL = createAlertUrl;
						
		System.out.println(albums);		
		scenario.write("Payload =====================================================" + albums);		

		return albums;
	}

	@Given("^I naviagte to ITS portal$")
	public void user_naviagte_to_Ticket_portal() throws Throwable {		
		//Log.assertThat(iTSLoginPage.navigateToTicketPortal(), "Navigated to ITS portal page", "Failed to Navigate to ITS portal page", DriverFactory.getDriver());		
	}

	//Commeting Code for New Alerting MS
	/*@When("^I login to ITS portal$")
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
*/
	@Given("^I verify create alert api request in PAS_ReqQueue table$")
	public void i_verify_create_alert_api_request_in_PAS_ReqQueue_table() throws Exception {

		commonReqQueValidation("create");		

	}

	private void commonReqQueValidation(String scenario) throws Exception {

		JsonParser parser = new JsonParser();
		System.out.println(getDbHost());
		System.out.println(getDbUserName());
		System.out.println(getDbPassword());

		String operationID ="2";
		if(scenario.equalsIgnoreCase("create")){
			operationID ="1";
		}else if(scenario.equalsIgnoreCase("delete")){
			operationID ="3";
		}

		String query = "select * from PAS_ReqQueue where Operation = " + operationID + " and CorrelationID like '" + getAlertID() + "'";

		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost(), getDbUserName(), getDbPassword(),query);
		HashMap<String, String> currentRow = new HashMap<>();
		currentRow.putAll(DataUtils.getTestRow());
		String dRegId = null, dConditionId = null, dSiteId = null, dMemberId = null, dInputReq = null,
				dOperation = null, dUpDcDtime = null;

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
				"Resource ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("resourceId")+ ", Actual :" + dRegId);
		Assert.assertTrue(currentRow.get("conditionId").trim().equalsIgnoreCase(dConditionId),
				"Condition ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("conditionId")+ ", Actual :" + dConditionId);
		Assert.assertTrue(currentRow.get("sites").trim().equalsIgnoreCase(dSiteId),
				"Site ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("sites") + ", Actual :"+ dSiteId);
		Assert.assertTrue(currentRow.get("partners").trim().equalsIgnoreCase(dMemberId),
				"Partner ID does not match in PAS_ReqQueue_table, Expected " + currentRow.get("partners") + ", Actual :"+ dMemberId);

		if(Integer.parseInt(operationID)!=3)
			Assert.assertEquals(parser.parse(dInputReq).getAsJsonObject(), getalertDetailJson(),
					"InputReq does not match in PAS_ReqQueue_table, Expected " + getalertDetailJson() + " ,Actual "+ dInputReq);

		Assert.assertTrue(dOperation.equals(operationID),
				"Operation ID does not match in PAS_ReqQueue_table, Expected"+ operationID+", Actual :" + dOperation);

		System.out.println("dUpDcDtime ReqQue : " + dUpDcDtime1);

		dUpDcDtime1 = dUpDcDtime1.substring(0, 19);

		long timeElapsed = JunoAlertingUtils.getDateDifference(dUpDcDtime1,currentTime);

		if (timeElapsed > 30000) {
			Assert.fail("Alert processing taken time more than expected value of 30 seconds.");
		} else

			System.out.println("***Processing done within time limits for" +scenario+" - " + timeElapsed + " milliseconds");

	}

	@Given("^I verify create alert api request is deleted from pas_reqcons table$")
	public void i_verify_create_alert_api_request_is_deleted_from_pas_reqcons_table() throws Exception {

		int count = 1 ;
		while(count >=1){
			String query = "select * from PAS_ReqQueue where CorrelationID like '" + getAlertID() + "'";

			ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://10.2.40.45:1433", "DB_Architect", "DBabc@1234", query);	

			if (!rs.next()) {
				count--;
			}
			System.out.println("Number of cons entry_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+  " + count);
			Thread.sleep(5000);
		}
		if(count ==0)
			System.out.println("Ticket entry has been removed from the ReqCons table");
		else
			Assert.fail("Ticket entry is not deleted from ReqCons table after Autoclose");

	}

	@Given("^I verify create alert request is archived in PAS_ReqQueueArchive table$")
	public void i_verify_create_alert_request_is_archived_in_PAS_ReqQueueArchive_table() throws Exception {		

		commonReqQueArchiveValidation("create");
	}

	private void commonReqQueArchiveValidation(String scenario) throws Exception {

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
		String dRegId = null, dConditionId = null, dSiteId = null, dMemberId = null, dInputReq, dOperation=null, dDcDtime = null, dUpDcDtime=null, dInsertedOn=null;
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

		if(timeElapsed > 45000){
			Assert.fail("Alert processing taken time more than expected value of 45 seconds.");
		}else{
			System.out.println("***Processing done within time limits for "+scenario+" - " + timeElapsed + " milliseconds" );
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
		}

		assertReqConTables(currentRow,dbValues); //asserting DB column values

		System.out.println("ReqCons Table validation successfull.");
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

	//	public static void main(String[] args) throws Exception {
	//		JunoAlertingStepsDefinations obj = new JunoAlertingStepsDefinations();
	//		obj.setURL("http://10.2.40.136:8080/alerting/v1/partners/50016358/sites/50110019/alerts");
	//		obj.setAlertID("a5f6185c-2204-4de3-aced-af0b76ff7f57");
	//		obj.i_trigger_auto_close_alert_API();
	//	}

	@Given("^I trigger auto close alert API$")
	public void i_trigger_auto_close_alert_API() throws Exception {
		//HashMap<String, String> currentRow = new HashMap<>();		
		//currentRow.putAll(DataUtils.getTestRow());
		/*String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchema")
				.replace("{partners}", currentRow.get("partners"))
				.replace("{sites}", currentRow.get("sites"))
				.replace("{HostUrl}", getHostURL());*/
		//Response resp = RestAssured.given().delete(getURL() + "/" + getAlertID()).andReturn();
		Reporter.log("\n ====================EXECUTING DELETE REQUEST FOR DELETING ALERTS================================= \n");
		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).delete(getRoundRobinGETURL() + "/" + getAlertID()).andReturn();
			scenario.write("DELETE URL : ===================================================== : " + getRoundRobinGETURL());
		}else {
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).delete(getURL() + "/" + getAlertID()).andReturn();
			scenario.write("DELETE URL : ===================================================== : " + getURL());
		}
		scenario.write("Response Body For DELETE : ===================================================== : " + resp.getBody().asString());
		scenario.write("StatusCode : ===================================================== : " + resp.getStatusCode());
		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");
		Reporter.log("Send DELETE command");
		Reporter.log("Status Code \n" + resp.getStatusCode());
		Reporter.log("Status Body \n" + resp.getBody().asString());
		int apiStatusID = resp.getStatusCode();
		Assert.assertEquals(apiStatusID, 204, "Delete alert API execution failed, the api status ID is " + apiStatusID + ", Expected status ID is 204");
	}

	@Given("^I trigger update alert API$")
	public void i_trigger_update_alert_API() throws Exception {
		Reporter.log("\n ====================EXECUTING PUT REQUEST FOR UPDATING ALERTS================================= \n");
		JsonObject jobj = getJsonData();
		jobj.add("alertDetails", getalertDetailJson());
		System.out.println(jobj.toString());

		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(jobj.toString()).put(getRoundRobinGETURL() + "/" + getAlertID()).andReturn();
			scenario.write("PUT URL : ===================================================== : " + getRoundRobinGETURL());
		}
		else {
		
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(jobj.toString()).put(getURL() + "/" + getAlertID()).andReturn();
			scenario.write("PUT URL : ===================================================== : " + getURL());
		}
		
		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");		//Response resps = RestAssured.given().contentType("application/json").body(jobj.toString()).get("").andReturn();
		scenario.write("Response Body For PUT : ===================================================== : " + resp.getBody().asString());
		scenario.write("StatusCode : ===================================================== : " + resp.getStatusCode());
		Reporter.log("Send PUT command");
		Reporter.log("Status Code \n" + resp.getStatusCode());
		Reporter.log("Status Body \n" + resp.getBody().asString());
		Reporter.log("Time taken to get response is \n" + resp.getTime()+" milli second");

		//JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		//JsonObject  jobject = jelement.getAsJsonObject();
		int apiStatusID = resp.getStatusCode();
		Reporter.log("Status Code is " + resp.getStatusCode());
		Assert.assertEquals(apiStatusID, 204, "Update alert API execution failed, the api status ID is " + apiStatusID + ", Expected status ID is 204");
	}

	@Given("^I verify update alert api request in PAS_ReqQueue table$")
	public void i_verify_update_alert_api_request_in_PAS_ReqQueue_table() throws Throwable {
		commonReqQueValidation("update");
	}

	@Given("^I verify update alert request is archived in PAS_ReqQueueArchive table$")
	public void i_verify_update_alert_request_is_archived_in_PAS_ReqQueueArchive_table() throws Throwable {
		commonReqQueArchiveValidation("update");
	}

	@Given("^I verify an alert entry is created in pas_reqcons table on successful processing of an update Alert request$")
	public void i_verify_an_alert_entry_is_created_in_pas_reqcons_table_on_successful_processing_of_an_update_Alert_request() throws Throwable {
		HashMap<String, String> currentRow = new HashMap<>();		
		currentRow.putAll(DataUtils.getTestRow());				
		System.out.println("RegId " + currentRow.get("resourceId"));
		System.out.println("ConditionId " + currentRow.get("conditionId"));
		System.out.println("SiteId " + currentRow.get("sites"));
		System.out.println("MemberId " + currentRow.get("partners"));


		String query = "select * from PAS_ReqCons with(NOLOCK) where LastStatus = '" + getAlertID() + "'";
		ResultSet rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost(), getDbUserName(), getDbPassword(), query);

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
		}

		assertReqConTables(currentRow,dbValues); //asserting DB column values
		System.out.println("ReqCons Table validated for the Update Ticket...");

	}

	@Given("^I verify delete alert api request in PAS_ReqQueue table$")
	public void i_verify_delete_alert_api_request_in_PAS_ReqQueue_table() throws Throwable {
		commonReqQueValidation("delete");
	}

	@Given("^I verify delete alert request is archived in PAS_ReqQueueArchive table$")
	public void i_verify_delete_alert_request_is_archived_in_PAS_ReqQueueArchive_table() throws Throwable {
		commonReqQueArchiveValidation("delete");
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

		/*	Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("CallQId")), 
				"CallQId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("CallQId"));
		 */	
		/*Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("CallQRefId")), 
				"CallQRefId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("CallQRefId"));*/

		//		Assert.assertTrue("0".trim().equalsIgnoreCase(dbValues.get("MsgbId")), 
		//				"MsgbId does not match in PAS_ReqCons_table, Expected " + "0" + ", Actual :" + dbValues.get("MsgbId"));		
	}

	public void triggerCreateAlertAPI(String arg1, String arg2) throws Exception {

		JsonObject albums = preProcessingCreateAlert(arg1,arg2);		
		//Response resp = RestAssured.given().header("txKey","Automation").contentType("application/json").body(albums.toString()).post(getURL()).andReturn();
		//Response resp = RestAssured.given().contentType("application/json").body(albums.toString()).post(getURL()).andReturn();
		
		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true"))
		{
			resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(albums.toString()).post(getRoundRobinURL()).andReturn();
		}
		else {
			resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(albums.toString()).post(getURL()).andReturn();
		}

		Reporter.log("Status Code is : " + resp.getStatusCode());
		Reporter.log("Response Body  is : " + resp.getBody().asString());
		//scenario.write("StatusCode =====================================================" + resp.getStatusCode());
		scenario.write("Response Body For POST : ===================================================== : " + resp.getBody().asString());

		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());

		//JsonElement jelement2 = new JsonParser().parse.Integer.parseInt(resp.getStatusCode());
		JsonObject  jobject = jelement.getAsJsonObject();
		//scenario.write("AlertID =====================================================" + jobject.get("alertId").getAsString());
		
		if(!jobject.get("status").toString().equalsIgnoreCase("205") || jobject.get("status").toString().equalsIgnoreCase("400")){
			Reporter.log("AlertID is : " + jobject.get("alertId").getAsString());
		}
		//Reporter.log("AlertID is : " + jobject.get("alertId").getAsString());
		
		//System.out.println("Status =====================================================" + resp.getStatusCode());

		//System.out.println("Status =====================================================" + jobject.get("status"));
		setApiStatusID(jobject.get("status").toString());
		
		setStatusCode(resp.getStatusCode());

		if(jobject.get("status").toString().equalsIgnoreCase("201") || jobject.get("status").toString().equalsIgnoreCase("202")){
			setAlertID(jobject.get("alertId").getAsString());
			setApiStatusID(jobject.get("status").toString());
			System.out.println("Alert ID ::  " + getAlertID());
		}

		if(getApiStatusID().equals("100"))
			triggerCreateAlertAPI(arg1, arg2);

		if(!getErrorCodeScenario().equalsIgnoreCase("202")){
			if(getApiStatusID().equals("202")){
				triggerDeleteAlertAPI();
				//i_verify_create_alert_api_request_is_deleted_from_pas_reqcons_table();
				Thread.sleep(6000);
				triggerCreateAlertAPI(arg1, arg2);
			}
		}
	}

	public void triggerUpdateAlertAPI(String arg1, String arg2) throws Exception{
		JsonObject albums = preProcessingCreateAlert(arg1, arg2);	
		//Response resp = RestAssured.given().header("txKey","Automation").contentType("application/json").body(albums.toString()).put(getURL() + "/" + getAlertID()).andReturn();
		System.out.println("\n ====================EXECUTING PUT REQUEST FOR UPDATING ALERTS================================= \n");
		Response resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(albums.toString()).put(getURL() + "/" + getAlertID()).andReturn();
		scenario.write("Response Body For PUT : ===================================================== : " + resp.getBody().asString());
		
		Reporter.log("Status Code is : " + resp.getStatusCode());
		Reporter.log("Response Body  is : " + resp.getBody().asString());
		
		setStatusCode(resp.getStatusCode());
		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();

		System.out.println("Status =====================================================" + jobject.get("status") );
		setApiStatusID(jobject.get("status").toString());
	}

	public void triggerDeleteAlertAPI(){
		//Response resp = RestAssured.given().header("txKey","Automation").delete(getURL() + "/" + getAlertID()).andReturn();
		System.out.println("\n ====================EXECUTING DELETE REQUEST FOR DELETING ALERTS================================= \n");
		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).delete(getRoundRobinURL() + "/" + getAlertID()).andReturn();
		}
		else {
			resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).delete(getURL() + "/" + getAlertID()).andReturn();
		}
		
		scenario.write("Response Body For DELETE : ===================================================== : " + resp.getBody().asString());
		
		Reporter.log("Status Code is : " + resp.getStatusCode());
		Reporter.log("Response Body  is : " + resp.getBody().asString());
		
		System.out.println("Send DELETE command");
		System.out.println("Status Code \n" + resp.getStatusCode());
		setStatusCode(resp.getStatusCode());
		System.out.println("Alert - " + getAlertID() + " deleted.");
		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		if(getStatusCode()!=204){
			JsonObject  jobject = jelement.getAsJsonObject();
			System.out.println("Status =====================================================" + jobject.get("status") );
			setApiStatusID(jobject.get("status").toString());
		}
	}



	/* ************************ Error Code 102**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with datatype for partner invalid$")
	public void i_trigger_create_alert_API_request_with_datatype_for_partner_invalid(String arg1, String arg2) throws Exception  {
		triggerCreateAlertAPI(arg1,arg2);
	}

	@Then("^I verify create api response code is (\\d+) for invalid partener datatype$")
	public void i_verify_create_api_response_code_is_for_invalid_partener_datatype(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with datatype for site invalid$")
	public void i_trigger_create_alert_API_request_with_datatype_for_site_invalid(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1,arg2);	   
	}

	@Then("^I verify api response code is (\\d+) for invalid site datatype$")
	public void i_verify_api_response_code_is_for_invalid_site_datatype(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}	


	/* ************************ Error Code 103**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with invalid request body$")
	public void i_trigger_create_alert_API_request_with_invalid_request_body(String arg1, String arg2) throws Throwable {
		JsonObject albums = preProcessingCreateAlert(arg1,arg2);
		String invalidBody = albums.toString().replace("{", "");
		//Response resp = RestAssured.given().header("txKey","Automation").contentType("application/json").body(invalidBody).post(getURL()).andReturn();

		Response resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(invalidBody).post(getURL()).andReturn();

		System.out.println("Status =====================================================" + resp.getStatusCode());

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();
		System.out.println("Status =====================================================" + jobject.get("status"));
		setApiStatusID(jobject.get("status").toString());

		System.out.println("BODY =====================================================" + invalidBody);

	}

	@Then("^I verify api response code is (\\d+) for invalid request body$")
	public void i_verify_api_response_code_is_for_invalid_request_body(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	/* ************************ Error Code 104**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with resource ID missing$")
	public void i_trigger_create_alert_API_request_with_resource_ID_missing(String arg1, String arg2) throws Exception {
		triggerCreateAlertAPI(arg1,arg2);
	}

	@Then("^I verify create api response code is (\\d+) for missing resource ID$")
	public void i_verify_create_api_response_code_is_for_missing_resource_ID(int arg1) throws Exception {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );

	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with condition ID missing$")
	public void i_trigger_create_alert_API_request_with_condition_ID_missing(String arg1, String arg2) throws Exception {
		triggerCreateAlertAPI(arg1,arg2);
	}

	@Then("^I verify api response code is (\\d+) for missing condition ID$")
	public void i_verify_api_response_code_is_for_missing_condition_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );

	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with request body missing$")
	public void i_trigger_create_alert_API_request_with_request_body_missing(String arg1, String arg2) throws Throwable {
		JsonObject albums = preProcessingCreateAlert(arg1,arg2);
		albums.remove("conditionId");
		System.out.println(albums);
		//Response resp = RestAssured.given().header("txKey","Automation").contentType("application/json").body(albums.toString()).post(getURL()).andReturn();

		Response resp = RestAssured.given().log().all().header("txKey","Automation").contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(albums.toString()).post(getURL()).andReturn();

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();
		System.out.println("Status =====================================================" + jobject.get("status"));
		setApiStatusID(jobject.get("status").toString());
	}

	@Then("^I verify api response code is (\\d+) for missing request body$")
	public void i_verify_api_response_code_is_for_missing_request_body(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );

	}

	/* ************************ Error Code 105**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect partner ID$")
	public void i_trigger_create_alert_API_request_with_incorrect_partner_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}

	@Then("^I verify create api response code is (\\d+) for incorrect partner ID$")
	public void i_verify_create_api_response_code_is_for_incorrect_partner_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect site ID$")
	public void i_trigger_create_alert_API_request_with_incorrect_site_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}

	@Then("^I verify create api response code is (\\d+) for incorrect site ID$")
	public void i_verify_create_api_response_code_is_for_incorrect_site_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect resource ID$")
	public void i_trigger_create_alert_API_request_with_incorrect_resource_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}

	@Then("^I verify create api response code is (\\d+) for incorrect resource ID$")
	public void i_verify_create_api_response_code_is_for_incorrect_resource_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}
	
		
	//Added by Bilal for 400 Status Code
	
	@Given("^\\\"([^\\\"]*)\\\" : \\\"([^\\\"]*)\\\" : I trigger create alert API request with SUSPENDED resource ID$")
	public void ITriggerCreateAlertAPIRequestWithSUSPENDEDResourceID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}
	
	@Then("^I verify create api status code is (\\d+) for SUSPENDED resources$")
    public void iVerifyCreateApiStatusCodeIs400ForSUSPENDEDResources(int arg1) throws Throwable {
		//String statusCode = getApiStatusID();		
		Assert.assertTrue(String.valueOf(getStatusCode()).equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + getStatusCode() );
	}
	
	@Then("^I verify create api response code is (\\d+) for SUSPENDED resource ID$")
	public void iVerifyCreateApiResponseCodeIs205ForSUSPENDEDResourceID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	/* ************************ Update -  Error Code 102**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with datatype for partner invalid$")
	public void i_trigger_update_alert_API_request_with_datatype_for_partner_invalid(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for invalid partener datatype$")
	public void i_verify_update_api_response_code_is_for_invalid_partener_datatype(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}
	/* ************************ Update -  Error Code 103**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with invalid request body$")
	public void i_trigger_update_alert_API_request_with_invalid_request_body(String arg1, String arg2) throws Throwable {

		triggerCreateAlertAPI(arg1, arg2);

		JsonObject albums = preProcessingCreateAlert(arg1, arg2);
		String invalidBody = albums.toString().replace("{", "");
		//Response resp = RestAssured.given().contentType("application/json").body(invalidBody).put(getURL() + "/" + getAlertID()).andReturn();

		Response resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(invalidBody).put(getURL() + "/" + getAlertID()).andReturn();

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();

		System.out.println("Status =====================================================" + jobject.get("status") );
		setApiStatusID(jobject.get("status").toString());

	}

	@Then("^I verify update api response code is (\\d+) for invalid request body$")
	public void i_verify_update_api_response_code_is_for_invalid_request_body(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	/* ************************ Update -  Error Code 104**************************** */

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with resource ID missing$")
	public void i_trigger_update_alert_API_request_with_resource_ID_missing(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for resource condition ID$")
	public void i_verify_update_api_response_code_is_for_resource_condition_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();	
	}
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with condition ID missing$")
	public void i_trigger_update_alert_API_request_with_condition_ID_missing(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for missing condition ID$")
	public void i_verify_update_api_response_code_is_for_missing_condition_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with request body missing$")
	public void i_trigger_update_alert_API_request_with_request_body_missing(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
		Thread.sleep(wait);
		JsonObject albums = preProcessingCreateAlert(arg1,arg2);
		albums.remove("conditionId");
		System.out.println(albums);
		//Response resp = RestAssured.given().contentType("application/json").body(albums.toString()).put(getURL() + "/" + getAlertID()).andReturn();

		Response resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(albums.toString()).put(getURL() + "/" + getAlertID()).andReturn();

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();

		System.out.println("Status =====================================================" + jobject.get("status") );
		setApiStatusID(jobject.get("status").toString());
	}

	@Then("^I verify update api response code is (\\d+) for missing request body$")
	public void i_verify_update_api_response_code_is_for_missing_request_body(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	/* ************************ Update -  Error Code 105**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with incorrect partner ID$")
	public void i_trigger_update_alert_API_request_with_incorrect_partner_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for incorrect partner ID$")
	public void i_verify_update_api_response_code_is_for_incorrect_partner_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with incorrect site ID$")
	public void i_trigger_update_alert_API_request_with_incorrect_site_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for incorrect site ID$")
	public void i_verify_update_api_response_code_is_for_incorrect_site_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with incorrect resource ID$")
	public void i_trigger_update_alert_API_request_with_incorrect_resource_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, "ErrorCode103");
		Thread.sleep(wait);
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for incorrect resource ID$")
	public void i_verify_update_api_response_code_is_for_incorrect_resource_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	/* ************************ Delete -  Error Code 102**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger delete alert API request with datatype for partner invalid$")
	public void i_trigger_delete_alert_API_request_with_datatype_for_partner_invalid(String arg1, String arg2) throws Throwable {
		preProcessingCreateAlert(arg1, arg2);
		triggerDeleteAlertAPI();
	}

	@Then("^I verify delete api response code is (\\d+) for invalid partener datatype$")
	public void i_verify_delete_api_response_code_is_for_invalid_partener_datatype(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	/* ************************ Error Code 404**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger delete alert API request with invalid alertID$")
	public void i_trigger_delete_alert_API_request_with_invalid_alertID(String arg1, String arg2) throws Throwable {
		preProcessingCreateAlert(arg1, arg2);
		setAlertID("abc-abc-abc");
		triggerDeleteAlertAPI();
	}

	@Then("^I verify delete api response code is (\\d+) for invalid alertID$")
	public void i_verify_delete_api_response_code_is_for_invalid_alertID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		
	}

	@Then("^I verify delete api status code is (\\d+) for invalid alertID$")
	public void i_verify_delete_api_status_code_is_for_invalid_alertID(int arg1) throws Throwable {
		Assert.assertTrue(String.valueOf(getStatusCode()).equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + getStatusCode() ); 
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with invalid alertID$")
	public void i_trigger_update_alert_API_request_with_invalid_alertID(String arg1, String arg2) throws Throwable {
		setAlertID("abc-abc-abc");
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) for invalid alertID$")
	public void i_verify_update_api_response_code_is_for_invalid_alertID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	@Then("^I verify update api status code is (\\d+) for invalid alertID$")
	public void i_verify_update_api_status_code_is_for_invalid_alertID(int arg1) throws Throwable {
		Assert.assertTrue(String.valueOf(getStatusCode()).equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + getStatusCode() );
	}

	/* ************************ Error Code 202**************************** */
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request when alert already exist$")
	public void i_trigger_create_alert_API_request_when_alert_already_exist(String arg1, String arg2) throws Throwable {
		setErrorCodeScneario("202");
		triggerCreateAlertAPI(arg1, arg2);
		if (!getApiStatusID().equalsIgnoreCase("202")) {
			Thread.sleep(wait);
			triggerCreateAlertAPI(arg1, arg2);			
		}	    
	}

	@Then("^I verify create api response code is (\\d+) when alert already exist$")
	public void i_verify_create_api_response_code_is_when_alert_already_exist(int arg1) throws Throwable {	
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		Thread.sleep(wait);
		triggerDeleteAlertAPI();
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect condition ID$")
	public void i_trigger_create_alert_API_request_with_incorrect_condition_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}

	@Then("^I verify create api response code is (\\d+) triggered with incorrect condition ID$")
	public void i_verify_create_api_response_code_is_triggered_with_incorrect_condition_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	//@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect post  ID$")
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger create alert API request with incorrect post  ID$")
	public void i_trigger_create_alert_API_request_with_incorrect_post_ID(String arg1, String arg2) throws Throwable {
		triggerCreateAlertAPI(arg1, arg2);
	}

	@Then("^I verify create api response code is (\\d+) triggered with incorrect post ID$")
	public void i_verify_create_api_response_code_is_triggered_with_incorrect_post_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger update alert API request with incorrect post  ID$")
	public void i_trigger_update_alert_API_request_with_incorrect_post_ID(String arg1, String arg2) throws Throwable {
		triggerUpdateAlertAPI(arg1, arg2);
	}

	@Then("^I verify update api response code is (\\d+) triggered with incorrect post ID$")
	public void i_verify_update_api_response_code_is_triggered_with_incorrect_post_ID(int arg1) throws Throwable {
		String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
	}

	//Commenting Code for New Alerting MS
	/*public void setEmailTestData(){
		HashMap <String, String> testData = DataUtils.getTestRow();
		System.out.println(testData);

		iTSHomePage.setAlertFamilyName(testData.get("AlertFamilies"));
		iTSHomePage.setSiteValue(testData.get("sites"));
		iTSHomePage.setResourceValue(testData.get("resourceId"));
		iTSHomePage.setNotificationName(testData.get("NotificationName"));
	}

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" I am able to login to ITS Portal$")
	public void i_am_able_to_login_to_ITS_Portal(String arg1, String arg2) throws Throwable {

		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + getFileName();		
		DataUtils.setTestRow(excelFilePath, arg1, arg2);
		iTSLoginPage.navigateToTicketPortal();
		iTSLoginPage.loginToTicketPortal();
	}

	@Then("^I am able to Navigate to Intellimon Email Extension Section$")
	public void i_am_able_to_Navigate_to_Intellimon_Email_Extension_Section() throws Throwable {
		iTSHomePage.openIntellimonEmailSection();
	}

	@Then("^I am able to Navigate to Intellimon Alert Suspension Section$")
	public void i_am_able_to_Navigate_to_Intellimon_Alert_Suspension_Section() throws Throwable {
		iTSHomePage.openIntellimonSuspensionSection();
	}

	@Then("^I should be able to set a resource level rule$")
	public void i_should_be_able_to_set_a_resource_level_rule() throws Throwable {
		setEmailTestData();
		iTSHomePage.setResourceRule();
		iTSHomePage.setAlertFamilies();		
		Thread.sleep(4000);
		iTSHomePage.deleteNotificationRule();		
	}

	@Then("^I should be able to set a site level rule$")
	public void i_should_be_able_to_set_a_site_level_rule() throws Throwable {
		setEmailTestData();
		iTSHomePage.setSiteRule();
		iTSHomePage.setAlertFamilies();
		Thread.sleep(4000);
		iTSHomePage.deleteNotificationRule();
	}

	@Then("^I should be able to set a Member level rule$")
	public void i_should_be_able_to_set_a_Member_level_rule() throws Throwable {
		setEmailTestData();
		iTSHomePage.setMemberRule();
		iTSHomePage.setAlertFamilies();
		Thread.sleep(4000);
		iTSHomePage.deleteNotificationRule();
	}
*/
	@Then("^\"([^\"]*)\" : \"([^\"]*)\" I verify the email params in SaazOnline Live table$")
	public void i_verify_the_email_params_in_SaazOnline_Live_table(String arg1, String arg2) throws Throwable {
		ResultSet rs 		= null;
		String ticketID 	= null;
		String emailFrom 	= null;
		String emailTo 		= null;
		String emailSubject = null;
		String timeStamp 	= null;
		int count 			= 0;

		triggerCreateAlertAPI(arg1, arg2);

		String query = "Select TicketId from PAS_ReqCons with(NOLOCK) where LastStatus = '" + getAlertID() + "'";
		while (count < 1) {
			rs = executeQuery("ITSAlertDB", "jdbc:sqlserver://" + getDbHost(), getDbUserName(), getDbPassword(), query);
			while (rs.next()) {
				count++;
				ticketID = rs.getString("TicketId");
			}
			System.out.println("No of rows in reqCOns = " + count);
			Thread.sleep(2000);
		}
		count = 0;

		query = "Select * from MailDump with(NOLOCK) where StrTo like '%aditya%' and StrSubject like '%" + ticketID	+ "%'";

		while (count < 1) {
			rs = executeQuery("ITSUPPORT247DB", "10.2.40.45:1433", "QA Automation", "aUt0T3$t#958", query);
			while (rs.next()) {
				count++;
				emailFrom = rs.getString("StrFrom");
				emailTo = rs.getString("StrTo");
				emailSubject = rs.getString("StrSubject");
				timeStamp = rs.getString("CreatedOn");

				System.out.println(emailFrom + "\n" + emailTo + "\n" + emailSubject + "\n" + timeStamp);
			}
			System.out.println("Ticket entry found  +++++++++++ " + count);
			Thread.sleep(2000);
		}		
		Assert.assertTrue(emailFrom.equalsIgnoreCase("tickets@itsupport247.net"),"Email from expected tickets@itsupport247.net but found - " + emailFrom);
		Assert.assertTrue(emailTo.contains("sachin.thakur@continuum.net"),"Email To expected to contain email address - sachin.thakur@continuum.net but found " + emailTo);
		Assert.assertTrue(emailTo.contains("aditya.gaur@continuum.net"),"Email To expected to contain email address - aditya.gaur@continuum.net but found " + emailTo);
		Assert.assertTrue(emailSubject.contains(ticketID), "Email subject expected to contains ticket ID as " + ticketID + "but subject is - " + emailSubject);
		count=0;


		query = "Select * from MailDump with(NOLOCK) where StrTo like '%aditya%' and StrBody like '%" + ticketID
				+ "%' and StrFrom='notify@dtitsupport247.net'";
		while (count < 1) {
			rs = executeQuery("ITSUPPORT247DB", "10.2.40.45:1433", "QA Automation", "aUt0T3$t#958", query);

			while (rs.next()) {
				count++;
				emailFrom = rs.getString("StrFrom");
				emailTo = rs.getString("StrTo");
				emailSubject = rs.getString("StrSubject");
				timeStamp = rs.getString("CreatedOn");

				System.out.println(emailFrom + "\n" + emailTo + "\n" + emailSubject + "\n" + timeStamp);
			}
			System.out.println("Notify entry found  +++++++++++ " + count);
			Thread.sleep(2000);
		}

		Assert.assertTrue(emailFrom.equalsIgnoreCase("notify@dtitsupport247.net"),"Email from expected notify@dtitsupport247.net but found - " + emailFrom);
		Assert.assertTrue(emailTo.contains("sachin.thakur@continuum.net"),"EMail To expected to contain email address - sachin.thakur@continuum.net but found " + emailTo);
		Assert.assertTrue(emailTo.contains("aditya.gaur@continuum.net"),"EMail To expected to contain email address - aditya.gaur@continuum.net but found " + emailTo);

		triggerDeleteAlertAPI();
	}


	///////*Alert Details API*////////

	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I trigger GET request on alert detail API$")
	public void i_trigger_GET_request_on_alert_detail_API(String arg1, String arg2) throws Throwable {

		AlertDetailAPITest alertDetailAPI = new AlertDetailAPITest();
		alertDetailAPI.triggerAlertDetailsGETrequest(getFileName(),arg1,arg2);
	}

	@Then("^I verify api response from the jmgtjobmanagement table$")
	public void i_verify_api_response_from_the_jmgtjobmanagement_table() throws Throwable {

		try{
			AlertDetailAPITest alertDetailAPI = new AlertDetailAPITest();
			alertDetailAPI.verifyGETApiResponse();
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Given("^I trigger GetAPI to verify alerts exists$")
	public void iTriggerGetAPIToVerifyAlertsExists() throws Exception {		
		//JsonObject jobj = getJsonData();
		//jobj.add("alertDetails", getalertDetailJson());
		//System.out.println(jobj.toString());
		//System.out.println("\n ====================EXECUTING GET REQUEST FOR GETTING ALERTS================================= \n");
		
		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			String createAlertUrl = Utilities.getMavenProperties("PlatformAlertUrlSchemaNode")
					.replace("{partners}", currentRow.get("partners"))
					.replace("{sites}", currentRow.get("sites"))
					.replace("{endpoints}", currentRow.get("endpoints"))
					.replace("{Node1}", Utilities.getMavenProperties("Node2"));
			
			RoundRobinGETURL = createAlertUrl;
			setRoundRobinGETURL(RoundRobinGETURL);
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).get(getRoundRobinGETURL() + "/" + getAlertID()).andReturn();
			scenario.write("GET URL : ===================================================== : " + getRoundRobinGETURL());
		}
		else {
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).get(getURL() + "/" + getAlertID()).andReturn();
			scenario.write("GET URL : ===================================================== : " + getURL());
		}
		Reporter.log("\n ====================EXECUTING GET REQUEST FOR GETTING ALERTS================================= \n");
		
		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");		//Response resps = RestAssured.given().contentType("application/json").body(jobj.toString()).get("").andReturn();
		
		scenario.write("Response Body For GET : ===================================================== : " + resp.getBody().asString());
		Reporter.log("Send GET command");
		Reporter.log("Status Code \n" + resp.getStatusCode());
		Reporter.log("Status Body \n" + resp.getBody().asString());
		Reporter.log("Time taken to get response is \n" + resp.getTime()+" milli second");

		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();

		int apiStatusID = resp.getStatusCode();		
		//("AlertID =====================================================" + jobject.get("alertId").getAsString());
		String AlertID = jobject.get("alertId").getAsString();
		Reporter.log("Status Code is : " + resp.getStatusCode());
		Assert.assertEquals(apiStatusID, 200, "GET alert API execution failed, the api status ID is " + apiStatusID + ", Expected status ID is 200");
		Reporter.log("AlertId in GET API ======= " + jobject.get("alertId").getAsString());
		Assert.assertEquals(AlertID, getAlertID(), "GET alert API execution failed, the api AlertID is " + AlertID + ", Expected AertID is :"+getAlertID());

	}
	
	
	//SNOOZE VALIDATION
	
    @Then("^^I verify update api response code is (\\d+) for snooze$")
    public void i_verify_update_api_response_code_is_207_for_snooze(int arg1) throws Throwable {
    	String statusCode = getApiStatusID();		
		Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
    }

    @Then("^I verify update api status code is (\\d+) for snooze$")
    public void i_verify_update_api_status_code_is_202_for_snooze(int arg1) throws Throwable {
    	//String statusCode = getApiStatusID();		
    	Assert.assertTrue(String.valueOf(getStatusCode()).equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + getStatusCode() );
    }

    @Given("^I trigger update alert API for validating snooze$")
    public void i_trigger_update_alert_api_for_validating_snooze() throws Throwable {
    	Reporter.log("\n ====================EXECUTING PUT REQUEST FOR UPDATING ALERTS TO VALIDATE SNOOZE================================= \n");
		JsonObject jobj = getJsonData();
		jobj.add("alertDetails", getalertDetailJson());
		System.out.println(jobj.toString());

		HashMap<String, String> currentRow = new HashMap<>();

		currentRow.putAll(DataUtils.getTestRow());
		Response resp;
		if (currentRow.get("RoundRobin").equalsIgnoreCase("true")) {
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(jobj.toString()).put(getRoundRobinGETURL() + "/" + getAlertID()).andReturn();
			scenario.write("PUT URL : ===================================================== : " + getRoundRobinGETURL());
		}
		else {
		
			resp = RestAssured.given().log().all().contentType("application/json").config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).body(jobj.toString()).put(getURL() + "/" + getAlertID()).andReturn();
			scenario.write("PUT URL : ===================================================== : " + getURL());
		}
		
		currentTime = JunoAlertingUtils.getCurrentTime("America/Los_Angeles");		//Response resps = RestAssured.given().contentType("application/json").body(jobj.toString()).get("").andReturn();
		scenario.write("Response Body For PUT : ===================================================== : " + resp.getBody().asString());
		scenario.write("StatusCode : ===================================================== : " + resp.getStatusCode());
		Reporter.log("Send PUT command");
		Reporter.log("Status Code \n" + resp.getStatusCode());
		Reporter.log("Status Body \n" + resp.getBody().asString());
		Reporter.log("Time taken to get response is \n" + resp.getTime()+" milli second");
			
		setStatusCode(resp.getStatusCode());
		JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		JsonObject  jobject = jelement.getAsJsonObject();

		System.out.println("Status =====================================================" + jobject.get("status") );
		setApiStatusID(jobject.get("status").toString());
		
		
		//JsonElement jelement = new JsonParser().parse(resp.getBody().asString());
		//JsonObject  jobject = jelement.getAsJsonObject();
		int apiStatusID = resp.getStatusCode();
		Reporter.log("Status Code is " + resp.getStatusCode());
		Assert.assertEquals(apiStatusID, 202, "Update alert API execution failed, the api status ID is " + apiStatusID + ", Expected status ID is 202");

    }

    @Given("^I wait for snooze to expire$")
    public void i_wait_for_snooze_to_expire() throws Throwable {
       Thread.sleep(30000);
    }
	
}