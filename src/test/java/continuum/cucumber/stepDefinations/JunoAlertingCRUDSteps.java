package continuum.cucumber.stepDefinations;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import com.continuum.platform.alerting.api.AlertingAPITest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class JunoAlertingCRUDSteps {

    private Logger logger = Logger.getLogger(this.getClass());
    AlertingAPITest apiTest = Hooks.apiTest;

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
        //assertTrue(apiTest.triggerChildDeleteAPI());
        String msg = apiTest.triggerChildDeleteAPI();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("I trigger GET Alert State API for current alert")
    public void i_trigger_GET_Alert_State_API_for_current_alert() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.getAlertStateResponse());
        String msg = apiTest.getAlertStateResponse();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger DELETE API for One Child Alert on Alert MS$")
    public void i_trigger_DELETE_API_for_One_Child_Alert_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerDeleteAPI(apiTest.getCurrentAlert()));
        String msg = apiTest.triggerDeleteAPI(apiTest.getCurrentAlert());
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger CREATE Alert API request for Two Parent Alert with Same ConditionID on Alert MS for \"([^\"]*)\"$")
    public void i_trigger_CREATE_Alert_API_request_for_Two_Parent_Alert_with_Same_ConditionID_on_Alert_MS_for(String testCaseRow) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerParentWithSameConditionCreateAPI(testCaseRow));
        String msg = apiTest.triggerParentWithSameConditionCreateAPI(testCaseRow);
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I trigger DELETE API request for Child Alert for Both Parent on Alert MS$")
    public void i_trigger_DELETE_API_request_for_Child_Alert_for_Both_Parent_on_Alert_MS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.triggerChildDeleteAPIForBothParent());
        String msg = apiTest.triggerChildDeleteAPIForBothParent();
        assertTrue(msg, msg.length() < 2);
    }

    @Then("^I verify If alert is Deleted in AlertingMS$")
    public void i_verify_If_alert_is_Deleted_in_AlertingMS() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //assertTrue(apiTest.verifyAlertDeletionInAlertingMS());
        String msg = apiTest.verifyAlertDeletionInAlertingMS();
        assertTrue(msg, msg.length() < 2);
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