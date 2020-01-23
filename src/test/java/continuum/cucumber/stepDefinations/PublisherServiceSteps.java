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
import publisherservice.DynatraceCounter;
import publisherservice.DynatraceGauge;
import publisherservice.PrometheusCounter;
import publisherservice.PrometheusGauge;

public class PublisherServiceSteps{
	
	private Logger logger = Logger.getLogger(this.getClass());
	PrometheusCounter prometheusCounterTest;
	PrometheusGauge prometheusGaugeTest;
	DynatraceCounter dynatraceCounterTest;
	DynatraceGauge dynatraceGaugeTest;
	
	@Before
	public void readScenario(Scenario scenario) {
		
		logger.debug("Scenario Name :" + scenario.getName());
		Reporter.log("<b><i><font color='Blue'>====== Scenario Name: ====="+ scenario.getName()+"</font></i></b>");		
		prometheusCounterTest = new PrometheusCounter();
		prometheusGaugeTest = new PrometheusGauge();
		dynatraceCounterTest = new DynatraceCounter();
		dynatraceGaugeTest = new DynatraceGauge();
	}

	@Given("Any Service is Sending Data to Publisher Service for Prometheus Counter")
	public void any_Service_is_Sending_Data_to_Publisher_Service_for_Prometheus_Counter() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(prometheusCounterTest.sendPrometheusCount());
	}

	@Then("I verify Get API for Prometheus Counter Monitoring")
	public void i_verify_Get_API_for_Prometheus_Counter_Monitoring() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(prometheusCounterTest.verifyPromrtheusCount());
	}
	
	@Given("Any Service is Sending Data to Publisher Service for Prometheus Gauge")
	public void any_Service_is_Sending_Data_to_Publisher_Service_for_Prometheus_Gauge() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(prometheusGaugeTest.sendPrometheusGauge());
	}

	@Then("I verify Get API for Prometheus Gauge Monitoring")
	public void i_verify_Get_API_for_Prometheus_Gauge_Monitoring() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(prometheusGaugeTest.verifyPromrtheusGauge());
	}
	
	@Given("Any Service is Sending Data to Publisher Service for Dynatrace Counter")
	public void any_Service_is_Sending_Data_to_Publisher_Service_for_Dynatrace_Counter() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(dynatraceCounterTest.sendDynatraceCounter());
	}

	@Then("I verify Get API for Dynatrace Gauge Monitoring")
	public void i_verify_Get_API_for_Dynatrace_Gauge_Monitoring() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(dynatraceGaugeTest.verifyDynatraceGauge());
	}
	
	@Given("Any Service is Sending Data to Publisher Service for Dynatrace Gauge")
	public void any_Service_is_Sending_Data_to_Publisher_Service_for_Dynatrace_Gauge() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(dynatraceGaugeTest.sendDynatraceGauge());
	}

	@Then("I verify Get API for Dynatrace Counter Monitoring")
	public void i_verify_Get_API_for_Dynatrace_Counter_Monitoring() {
	    // Write code here that turns the phrase above into concrete actions
		assertTrue(dynatraceCounterTest.verifyDynatraceCount());
	}
	
	@After
	public void completeScenario(Scenario scenario) {
		
		logger.debug("Scenario Name :" + scenario.getName() + "Status :" + scenario.getStatus());
		Reporter.log("<b><i><font color='Blue'>====== Scenario Name: ====="+ scenario.getName()+"</font></i></b>");
	}
}