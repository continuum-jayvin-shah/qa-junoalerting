import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions (monochrome = true, 
features = "src/test/resources/features/PublisherService.feature",
glue = {"continuum.cucumber.stepDefinations"},
plugin = {"pretty", "json:target/cucumber-reports/cucumber.json",
        "timeline:target/timeline-reports/"},
tags={"@Publisher"})
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
