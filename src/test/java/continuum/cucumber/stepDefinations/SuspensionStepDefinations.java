package continuum.cucumber.stepDefinations;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.Assert;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JunoAlertingUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.Utilities;
import continuum.noc.pages.AuvikPageFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SuspensionStepDefinations{
	
	@Given("^\"([^\"]*)\" : \"([^\"]*)\" I apply member level family suspension for (\\d+) minutes$")
	public void i_apply_member_level_family_suspension_for_minutes(String arg1, String arg2, int arg3){
	    // Write code here that turns the phrase above into concrete actions
	   // throw new PendingException();
		System.out.println("Step1");
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