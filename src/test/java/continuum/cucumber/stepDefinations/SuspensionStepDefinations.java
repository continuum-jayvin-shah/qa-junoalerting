package continuum.cucumber.stepDefinations;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;

import com.continuum.utils.DataUtils;

import continuum.noc.pages.AuvikPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SuspensionStepDefinations {
	
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
		junoAlertingStepDef.iTSLoginPage.navigateToTicketPortal();
		junoAlertingStepDef.iTSLoginPage.loginToTicketPortal();
		junoAlertingStepDef.iTSHomePage.openIntellimonSuspensionSection();
		junoAlertingStepDef.iTSHomePage.setMemberRule();
		junoAlertingStepDef.iTSHomePage.setAlertFamilies();
		junoAlertingStepDef.iTSHomePage.setSuspensionScheduleOneTime();
	}

	@Given("^I wait for x minutes for suspesnion to get applied$")
	public void i_wait_for_x_minutes_for_suspesnion_to_get_applied() throws InterruptedException{
	    long waitTime= 420000;
	    
	    while(waitTime>=0){
	    	System.out.println("Waiting for suspension rule to be synced.. Time remaining : " + (waitTime/1000)+ " Seconds");
	    	waitTime = waitTime - 10000;
	    	Thread.sleep(10000);
	    }
		System.out.println("Suspension rule synced..");
	}

	@When("^\"([^\"]*)\" : \"([^\"]*)\" I trigger create alert API$")
	public void i_trigger_create_alert_API(String arg1, String arg2) throws Throwable {
	    junoAlertingStepDef.triggerCreateAlertAPI(arg1, arg2);
	}
	
	@Then("^I verify create api response code is (\\d+) for suspended partner$")
	public void i_verify_create_api_response_code_is_for_suspended_partner(int arg1) throws Throwable {
		 String statusCode = junoAlertingStepDef.getApiStatusID();
		 Assert.assertTrue(statusCode.equals(String.valueOf(arg1)),"API Status code expected " + arg1 + "but actual is " + statusCode );
		 junoAlertingStepDef.iTSHomePage.deleteSuspensionRule();
	}

}