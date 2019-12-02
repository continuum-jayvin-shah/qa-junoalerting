package continuum.cucumber.stepDefinations;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.continuum.platform.alerting.api.AlertingAPITest;
import com.continuum.utils.DataUtils;

import continuum.cucumber.Utilities;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class JunoAlertingSteps {

    private Logger logger = Logger.getLogger(this.getClass());
    AlertingAPITest apiTest = Hooks.apiTest ;

    @Given("^I trigger CREATE Alert API request on Alert MS for \"([^\"]*)\"$")
    public void i_trigger_CREATE_Alert_API_request_on_Alert_MS(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerCreateAPI(testCaseRow));
        String msg = apiTest.triggerCreateAPI(testCaseRow);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response from Alert MS$")
    public void i_verify_API_response_from_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyCreateAPIResponse());
        String msg = apiTest.verifyCreateAPIResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger UPDATE Alert API request on Alert MS$")
    public void i_trigger_UPDATE_Alert_API_request_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerUpdateAPI());
        String msg = apiTest.triggerUpdateAPI();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify API response {string} from Alert MS for UPDATE Request")
    public void i_verify_API_response_from_Alert_MS_for_UPDATE_Request(String responseCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyUpdateAPIResponse(responseCode));
        String msg = apiTest.verifyUpdateAPIResponse(responseCode);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response from Alert MS for UPDATE Request$")
    public void i_verify_API_response_from_Alert_MS_for_UPDATE_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // assertTrue(apiTest.verifyUpdateAPIResponse());
        String msg = apiTest.verifyUpdateAPIResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger DELETE API request on Alert MS$")
    public void i_trigger_DELETE_API_request_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerDeleteAPIWithBody());
        String msg = apiTest.triggerDeleteAPIWithBody();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I trigger DELETE API request for second on Alert MS")
    public void i_trigger_DELETE_API_request_for_second_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerLastAlertDeleteAPIWithBody());
        String msg = apiTest.triggerLastAlertDeleteAPIWithBody();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response from Alert MS for DELETE Request$")
    public void i_verify_API_response_from_Alert_MS_for_DELETE_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyDeleteAPIResponse());
        String msg = apiTest.verifyDeleteAPIResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert reached till ITSM Simulator$")
    public void i_verify_If_alert_reached_till_ITSM_Simulator() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyITSMSimulatorResponse());
        String msg = apiTest.verifyITSMSimulatorResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify Child alert list in parent Alert response of ITSM Simulator$")
    public void i_verify_child_alert_list_in_parent_alert_response_of_ITSM_simulator() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyChildListITSMSimulatorResponse());
        String msg = apiTest.verifyChildListITSMSimulatorResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify New Alert created for New Request$")
    public void i_verify_New_Alert_created_for_New_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyNewAlertCreation());
        String msg = apiTest.verifyNewAlertCreation();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response from Alert MS for UPDATE Request with Snooze Enabled$")
    public void i_verify_API_response_from_Alert_MS_for_UPDATE_Request_with_Snooze_Enabled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyUpdateAPIResponseWithSnooze());
        String msg = apiTest.verifyUpdateAPIResponseWithSnooze();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^Wait for \"([^\"]*)\" Secs$")
    public void wait_for_Secs(int duration) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.waitForSnooze(duration));
    }

    @Then("^I verify FetchMore and Remediate URL in ITSM Request$")
    public void i_verify_FetchMore_and_Remediate_URL_in_ITSM_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyRemediateURLITSMRequest());
        String msg = apiTest.verifyRemediateURLITSMRequest();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I get ITSM Simulator Response for Current Alert$")
    public void i_get_ITSM_Simulator_Response_for_Current_Alert() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        apiTest.clearFilterArray();
        //assertTrue(apiTest.getITSMSimulatorResponse());
        String msg = apiTest.getITSMSimulatorResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If all requests were sent to ITSM$")
    public void i_verify_If_all_requests_were_sent_to_ITSM() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyITSMSimulatorResponse());
        String msg = apiTest.verifyITSMSimulatorResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I should verify ITSM payload data as expected")
    public void i_should_verify_ITSM_payload_data_as_expected() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.validateActualDataInITSM());
        String msg = apiTest.validateActualDataInITSM();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify status code {string} in ITSM Simulator Response")
    public void i_verify_status_code_in_ITSM_Simulator_Response(String statusCode) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.validateITSMData("statuscode", statusCode));
        String msg = apiTest.validateITSMData("statuscode", statusCode);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert not reached till ITSM Simulator$")
    public void i_verify_If_alert_not_reached_till_ITSM_Simulator() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyITSMResponseForChildAlert());
        String msg = apiTest.verifyITSMResponseForChildAlert();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger CREATE Alert API request for Parent Alert on Alert MS for \"([^\"]*)\"$")
    public void i_trigger_CREATE_Alert_API_request_for_Parent_Alert_on_Alert_MS_for(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerParentCreateAPI(testCaseRow));
        String msg = apiTest.triggerParentCreateAPI(testCaseRow);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger DELETE API request for Child Alert on Alert MS$")
    public void i_trigger_DELETE_API_request_for_Child_Alert_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerChildDeleteAPI());
    }

    @Then("I trigger GET Alert State API for current alert")
    public void i_trigger_GET_Alert_State_API_for_current_alert() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.getAlertStateResponse());
    }

    @Then("I verify {string} in Alert State API response")
    public void i_verify_in_Alert_State_API_response(String factor) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
		List<String> listFactor = new ArrayList<String>();
		listFactor.add(factor);
        String msg = apiTest.validateAlertState(listFactor);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger DELETE API for One Child Alert on Alert MS$")
    public void i_trigger_DELETE_API_for_One_Child_Alert_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerDeleteAPI(apiTest.getCurrentAlert()));
    }

    @Then("^I trigger CREATE Alert API request for Two Parent Alert with Same ConditionID on Alert MS for \"([^\"]*)\"$")
    public void i_trigger_CREATE_Alert_API_request_for_Two_Parent_Alert_with_Same_ConditionID_on_Alert_MS_for(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerParentWithSameConditionCreateAPI(testCaseRow));
    }

    @Then("^I trigger DELETE API request for Child Alert for Both Parent on Alert MS$")
    public void i_trigger_DELETE_API_request_for_Child_Alert_for_Both_Parent_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerChildDeleteAPIForBothParent());
    }

    @Then("^I verify If alert is Created in JAS$")
    public void i_verify_If_alert_is_Created_in_JAS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyAlertCreationInJAS());
    }

    @Then("^I verify If alert is Deleted in JAS$")
    public void i_verify_If_alert_is_Deleted_in_JAS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyAlertDeletionInJAS());
    }

    @Then("^I verify If alert is Created in AlertingMS$")
    public void i_verify_If_alert_is_Created_in_AlertingMS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyAlertCreationInAlertingMS());
    }

    @Then("^I verify If alert is Deleted in AlertingMS$")
    public void i_verify_If_alert_is_Deleted_in_AlertingMS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyAlertDeletionInAlertingMS());
    }

    @Then("^I verify API response for Suspended Condition from Alert MS$")
    public void i_verify_API_response_for_Suspended_Condition_from_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyAlertSuspension());
    }

    @Then("^I trigger Manual Closure By Posting on KafkaTopic with MessageType \"([^\"]*)\"$")
    public void i_trigger_Manual_Closure_By_Posting_on_KafkaTopic_with_MessageType(String kafkaMessageType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerManualClosure(kafkaMessageType));
    }

    @Then("I verify alert should present in Alert Failure table")
    public void i_verify_alert_should_present_in_Alert_Failure_table() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.getAlertFailureResponse());
    }

    @Then("I reprocess the {string} alert")
    public void i_reprocess_the_alert(String count) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.reprocessAlert(count));
    }

    @Then("I verify Duplicate Alert Id message in response")
    public void i_verify_Duplicate_Alert_Id_message_in_response() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyDuplicateAlertMsgInResponse());
    }

    @Then("I verify alert should not present in Alert Failure table")
    public void i_verify_alert_should_not_present_in_Alert_Failure_table() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.getAlertFailureResponseNotPresent());
    }


    @Then("^I verify API response as Duplicate Alert Request from Alert MS$")
    public void i_verify_API_response_as_Duplicate_Alert_Request_from_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyDuplicateAlertCreation());
    }

    @Then("^I verify API response from Alert MS for Non-Existing Alert$")
    public void i_verify_API_response_from_Alert_MS_for_Non_Existing_Alert() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyNonExistingAlertAPIResponse());
    }

    @Given("I trigger CREATE Incident API request on ITSM MS for {string}")
    public void i_trigger_CREATE_Incident_API_request_on_ITSM_MS_for(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerCreateITSM_API(testCaseRow));
    }

    @Then("I verify API response from ITSM MS")
    public void i_verify_API_response_from_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyCreateAPIResponseITSM());
    }

    @Then("I trigger UPDATE ITSM API request on ITSM MS")
    public void i_trigger_UPDATE_ITSM_API_request_on_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.triggerUpdateAPI_ITSM());
    }

    @Then("I verify API response from ITSM MS for UPDATE Request")
    public void i_verify_API_response_from_ITSM_MS_for_UPDATE_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(apiTest.verifyUpdateAPIResponseITSM());
    }

    @Then("I trigger DELETE API request on ITSM MS")
    public void i_trigger_DELETE_API_request_on_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("I verify API response from ITSM MS for DELETE Request")
    public void i_verify_API_response_from_ITSM_MS_for_DELETE_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

}