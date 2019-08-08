package continuum.cucumber.stepDefinations;

import static org.junit.Assert.assertTrue;

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
import com.continuum.platform.alerting.api.AlertingAPITest;
import com.continuum.utils.DataUtils;
import com.continuum.utils.JunoAlertingUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.Utilities;
import continuum.cucumber.testRunner.SendReport;
import continuum.cucumber.webservices.JSonAssertionUtility;
import continuum.cucumber.webservices.JsonUtility;
import continuum.cucumber.webservices.RestAssuredUtility;
import continuum.noc.pages.AuvikPageFactory;
import continuum.noc.pages.NewAlertingMSPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JunoAlertingSteps{
	
	@Before
	public void readScenario(Scenario scenario) {
		
		System.out.println("Scenario Name :" + scenario.getName());
		Reporter.log("<b><i><font color='Blue'>====== Scenario Name: ====="+ scenario.getName()+"</font></i></b>");
		String environment = Utilities.getMavenProperties("Environment").trim();
		DataUtils.setFileName("TestData_" + environment + ".xls");
		if (environment.equals("QA")) {
			AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("QAHostUrlV1"));
			AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("QAHostUrlV1"));
		} else if (environment.equals("DT")) {
			AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("DTHostUrlV1"));
			AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("DTHostUrlV1"));
		} else if (environment.equals("PROD")) {
			AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("PRODHostUrl"));
			AlertingAPITest.setItsmUrl(Utilities.getMavenProperties("PRODHostUrl"));
		}
	}

	@Given("^I trigger CREATE Alert API request on Alert MS for \"([^\"]*)\"$")
	public void i_trigger_CREATE_Alert_API_request_on_Alert_MS(String testCaseRow) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerCreateAPI(testCaseRow));
	}
	
	@Then("^I verify API response from Alert MS$")
	public void i_verify_API_response_from_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyCreateAPIResponse());
	}
	
	@Then("^I trigger UPDATE Alert API request on Alert MS$")
	public void i_trigger_UPDATE_Alert_API_request_on_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerUpdateAPI());
	}

	@Then("^I verify API response from Alert MS for UPDATE Request$")
	public void i_verify_API_response_from_Alert_MS_for_UPDATE_Request() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyUpdateAPIResponse());
	}
	
	@Then("^I trigger DELETE API request on Alert MS$")
	public void i_trigger_DELETE_API_request_on_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerDeleteAPI());
	}
	
	@Then("^I verify API response from Alert MS for DELETE Request$")
	public void i_verify_API_response_from_Alert_MS_for_DELETE_Request() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyDeleteAPIResponse());
	}
	
	@Then("^I verify If alert reached till ITSM Simulator$")
	public void i_verify_If_alert_reached_till_ITSM_Simulator() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyITSMSimulatorResponse());
	}
	
	@Then("^I verify New Alert created for New Request$")
	public void i_verify_New_Alert_created_for_New_Request() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyNewAlertCreation());
	}
	
	@Then("^I verify API response from Alert MS for UPDATE Request with Snooze Enabled$")
	public void i_verify_API_response_from_Alert_MS_for_UPDATE_Request_with_Snooze_Enabled() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyUpdateAPIResponseWithSnooze());
	}
	
	@Then("^Wait for Snooze Period$")
	public void wait_for_Snooze_Period() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.waitForSnooze());
	}
	
	@Then("^I verify FetchMore and Remediate URL in ITSM Request$")
	public void i_verify_FetchMore_and_Remediate_URL_in_ITSM_Request() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyRemediateURLITSMRequest());
	}
	
	@Then("^I get ITSM Simulator Response for Current Alert$")
	public void i_get_ITSM_Simulator_Response_for_Current_Alert() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.getITSMSimulatorResponse());
	}
	
	@Then("^I verify If all requests were sent to ITSM$")
	public void i_verify_If_all_requests_were_sent_to_ITSM() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyITSMSimulatorResponse());
	}
	
	@Then("^I verify If alert not reached till ITSM Simulator$")
	public void i_verify_If_alert_not_reached_till_ITSM_Simulator() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyITSMResponseForChildAlert());
	}

	@Then("^I trigger CREATE Alert API request for Parent Alert on Alert MS for \"([^\"]*)\"$")
	public void i_trigger_CREATE_Alert_API_request_for_Parent_Alert_on_Alert_MS_for(String testCaseRow) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerParentCreateAPI(testCaseRow));
	}

	@Then("^I trigger DELETE API request for Child Alert on Alert MS$")
	public void i_trigger_DELETE_API_request_for_Child_Alert_on_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerChildDeleteAPI());
	}
	
	@Then("^I trigger DELETE API for One Child Alert on Alert MS$")
	public void i_trigger_DELETE_API_for_One_Child_Alert_on_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerDeleteAPI(AlertingAPITest.getCurrentAlert()));
	}
	
	@Then("^I trigger CREATE Alert API request for Two Parent Alert with Same ConditionID on Alert MS for \"([^\"]*)\"$")
	public void i_trigger_CREATE_Alert_API_request_for_Two_Parent_Alert_with_Same_ConditionID_on_Alert_MS_for(String testCaseRow) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerParentWithSameConditionCreateAPI(testCaseRow));
	}
	
	@Then("^I trigger DELETE API request for Child Alert for Both Parent on Alert MS$")
	public void i_trigger_DELETE_API_request_for_Child_Alert_for_Both_Parent_on_Alert_MS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.triggerChildDeleteAPIForBothParent());
	}
	
	@Then("^I verify If alert is Created in JAS$")
	public void i_verify_If_alert_is_Created_in_JAS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyAlertCreationInJAS());
	}

	@Then("^I verify If alert is Deleted in JAS$")
	public void i_verify_If_alert_is_Deleted_in_JAS() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(AlertingAPITest.verifyAlertDeletionInJAS());
	}
	
	@After
	public void completeScenario(Scenario scenario) {
		
		System.out.println("Scenario Name :" + scenario.getName() + "Status :" + scenario.getStatus());
		Reporter.log("<b><i><font color='Blue'>====== Scenario Name: ====="+ scenario.getName()+"</font></i></b>");
		AlertingAPITest.closeTest();
	}
	
}