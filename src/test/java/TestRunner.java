import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (monochrome = true, 
features = "src/test/resources/features/AlertingCRUDApi.feature",
glue = {"continuum.cucumber.stepDefinations"},
plugin = {"pretty", "json:target/cucumber-reports/cucumber.json",
        "timeline:target/timeline-reports/"},
tags={"@BVT"})
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}