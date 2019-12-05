package continuum.cucumber.stepDefinations;

import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import com.continuum.platform.alerting.api.AlertingAPITest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.util.ArrayList;
import java.util.List;

public class JunoAlertingSteps {

    private Logger logger = Logger.getLogger(this.getClass());
    Hooks hooks = new Hooks();
    AlertingAPITest apiTest = hooks.getApiTest();

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

    @Then("I verify {string} in Alert State API response")
    public void i_verify_in_Alert_State_API_response(String factor) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        List<String> listFactor = new ArrayList<String>();
        listFactor.add(factor);
        String msg = apiTest.validateAlertState(listFactor);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert is Created in JAS$")
    public void i_verify_If_alert_is_Created_in_JAS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyAlertCreationInJAS());
        String msg = apiTest.verifyAlertCreationInJAS();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert is Deleted in JAS$")
    public void i_verify_If_alert_is_Deleted_in_JAS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyAlertDeletionInJAS());
        String msg = apiTest.verifyAlertDeletionInJAS();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert is Created in AlertingMS$")
    public void i_verify_If_alert_is_Created_in_AlertingMS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyAlertCreationInAlertingMS());
        String msg = apiTest.verifyAlertCreationInAlertingMS();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response for Suspended Condition from Alert MS$")
    public void i_verify_API_response_for_Suspended_Condition_from_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyAlertSuspension());
        String msg = apiTest.verifyAlertSuspension();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger Manual Closure By Posting on KafkaTopic with MessageType \"([^\"]*)\"$")
    public void i_trigger_Manual_Closure_By_Posting_on_KafkaTopic_with_MessageType(String kafkaMessageType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerManualClosure(kafkaMessageType));
        String msg = apiTest.triggerManualClosure(kafkaMessageType);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify alert should present in Alert Failure table")
    public void i_verify_alert_should_present_in_Alert_Failure_table() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.getAlertFailureResponse());
        String msg = apiTest.getAlertFailureResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I reprocess the {string} alert")
    public void i_reprocess_the_alert(String count) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.reprocessAlert(count));
        String msg = apiTest.reprocessAlert(count);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify Duplicate Alert Id message in response")
    public void i_verify_Duplicate_Alert_Id_message_in_response() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyDuplicateAlertMsgInResponse());
        String msg = apiTest.verifyDuplicateAlertMsgInResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify alert should not present in Alert Failure table")
    public void i_verify_alert_should_not_present_in_Alert_Failure_table() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.getAlertFailureResponseNotPresent());
        String msg = apiTest.getAlertFailureResponseNotPresent();
        assertTrue(msg, msg.length() < 2);
    }


    @Then("^I verify API response as Duplicate Alert Request from Alert MS$")
    public void i_verify_API_response_as_Duplicate_Alert_Request_from_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyDuplicateAlertCreation());
        String msg = apiTest.verifyDuplicateAlertCreation();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify API response from Alert MS for Non-Existing Alert$")
    public void i_verify_API_response_from_Alert_MS_for_Non_Existing_Alert() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyNonExistingAlertAPIResponse());
        String msg = apiTest.verifyNonExistingAlertAPIResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Given("I trigger CREATE Incident API request on ITSM MS for {string}")
    public void i_trigger_CREATE_Incident_API_request_on_ITSM_MS_for(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerCreateITSM_API(testCaseRow));
        String msg = apiTest.triggerCreateITSM_API(testCaseRow);
        assertTrue(msg, msg.length() < 2);
    }

    @When("I trigger CREATE Incident API request on ITSM MS")
    public void i_trigger_CREATE_Incident_API_request_on_ITSM_MS() throws Throwable {
        String msg = apiTest.triggerCreateITSM_API_AlertMS();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify API response from ITSM MS")
    public void i_verify_API_response_from_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyCreateAPIResponseITSM());
        String msg = apiTest.verifyCreateAPIResponseITSM();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify Duplicate Alert in API response from ITSM MS")
    public void i_verify_duplicate_alert_in_API_response_from_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyCreateAPIResponseITSM());
        String msg = apiTest.verifyDuplicateCreateAPIResponseITSM();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I trigger UPDATE ITSM API request on ITSM MS")
    public void i_trigger_UPDATE_ITSM_API_request_on_ITSM_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerUpdateAPI_ITSM());
        String msg = apiTest.triggerUpdateAPI_ITSM();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I verify API response from ITSM MS for UPDATE Request")
    public void i_verify_API_response_from_ITSM_MS_for_UPDATE_Request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // assertTrue(apiTest.verifyUpdateAPIResponseITSM());
        String msg = apiTest.verifyUpdateAPIResponseITSM();
        assertTrue(msg, msg.length() < 2);
    }

}