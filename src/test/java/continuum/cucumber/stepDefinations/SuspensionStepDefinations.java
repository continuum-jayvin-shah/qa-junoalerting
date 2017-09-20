package continuum.cucumber.stepDefinations;

import java.io.File;
import java.io.IOException;

import com.continuum.utils.DataUtils;

import continuum.noc.pages.AuvikPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class SuspensionStepDefinations extends AuvikPageFactory{
	
	JunoAlertingStepsDefinations junoAlertingStepDef = new JunoAlertingStepsDefinations();
	Scenario scenario = null;
	
	@Before
	 public void readScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" I apply member level family suspension for (\\d+) minutes$")
	public void i_apply_member_level_family_suspension_for_minutes(String arg1, String arg2, int arg3) throws IOException{
		junoAlertingStepDef.readScenario(scenario);
		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + junoAlertingStepDef.getFileName();
		DataUtils.setTestRow(excelFilePath, arg1, arg2);
		junoAlertingStepDef.setEmailTestData();
		iTSLoginPage.navigateToTicketPortal();
		iTSLoginPage.loginToTicketPortal();
		iTSHomePage.openIntellimonSuspensionSection();
		iTSHomePage.setMemberRule();
		iTSHomePage.setAlertFamilies();
		iTSHomePage.setSuspensionScheduleOneTime();
	}

	@Given("^I wait for x minutes for suspesnion to get applied$")
	public void i_wait_for_x_minutes_for_suspesnion_to_get_applied(){
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		System.out.println("Step1");
	}

	@When("^I trigger create alert API$")
	public void i_trigger_create_alert_API() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		System.out.println("Step1");
	}

}