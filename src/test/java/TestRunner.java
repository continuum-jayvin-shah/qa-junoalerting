import com.continuum.platform.alerting.api.AlertingAPITest;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions (monochrome = true, 
features = "src/test/resources/features",
glue = {"continuum.cucumber.stepDefinations"},
plugin = {"pretty", "json:target/cucumber-reports/cucumber.json",
        "timeline:target/timeline-reports/"},
tags={"@Functional"})
public class TestRunner extends AbstractTestNGCucumberTests {
    private Logger logger = Logger.getLogger(this.getClass());
    AlertingAPITest apiTest = null ;
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass
    public void loadPageObjects() throws Throwable {
        logger.info("Before All");
    }

    @AfterClass
    public void end(){
        logger.info("After all scenario : Check app_checkpoint database --");
        logger.info("============================================================================================================================================");
        apiTest = new AlertingAPITest();

    }
}