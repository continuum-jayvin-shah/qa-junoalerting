package continuum.juno.alerting.stepdefination;

import com.continuum.framework.utils.Log;
import continuum.cucumber.DriverFactory;
import continuum.noc.pages.AuvikPageFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class JunoAlertingStepsDefinations extends AuvikPageFactory{
	
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" : I create ticketId using Juno Alerting ticket API and I verify the response$")
	public void i_create_ticketId_using_Juno_Alerting_ticket_API_and_I_verify_the_response(String arg1, String arg2) {
	    // Write code here that turns the phrase above into concrete actions
	}
	
	@Given("^I naviagte to ITS portal$")
	public void user_naviagte_to_Ticket_portal() throws Throwable {		
		Log.assertThat(iTSLoginPage.navigateToTicketPortal(), "Navigated to ITS portal page", "Failed to Navigate to ITS portal page", DriverFactory.getDriver());		
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
		Log.message("I login to Noc portal", DriverFactory.getDriver());
	}
}

