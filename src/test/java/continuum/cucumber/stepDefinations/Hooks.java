package continuum.cucumber.stepDefinations;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Hooks {

    private Logger logger = Logger.getLogger(this.getClass());
    static AlertingAPITest apiTest;

    @Before
    public void readScenario(Scenario scenario) {
        logger.info("Scenario Name :" + scenario.getName());
        Reporter.log("<b><i><font color='Blue'>====== Scenario Name: =====" + scenario.getName() + "</font></i></b>");
        String environment = Utilities.getMavenProperties("Environment").trim();
        DataUtils.setFileName("TestData_" + environment + ".xls");
        if (environment.equals("QA")) {
            AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("QAAlertingHostUrlV2"));
            AlertingAPITest.setKafkaServer("QAKafkaProducerIP");
        } else if (environment.equals("DT")) {
            AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("DTAlertingHostUrlV2"));
            AlertingAPITest.setKafkaServer("DTKafkaProducerIP");
            AlertingAPITest.setItsmIntegrationUrl(Utilities.getMavenProperties("DTITSMHostUrlV2"));
        } else if (environment.equals("PROD")) {
            AlertingAPITest.setalertingUrl(Utilities.getMavenProperties("PRODHostUrl"));
            AlertingAPITest.setKafkaServer("");
        }
        apiTest = new AlertingAPITest();
    }

    @After
    public void completeScenario(Scenario scenario) {
        logger.info("Scenario Name :" + scenario.getName() + "Status :" + scenario.getStatus());
        Reporter.log("<b><i><font color='Blue'>====== Scenario Name: =====" + scenario.getName() + "</font></i></b>");
        apiTest.closeTest();
    }
}