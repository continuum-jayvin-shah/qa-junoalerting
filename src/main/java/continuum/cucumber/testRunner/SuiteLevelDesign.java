package continuum.cucumber.testRunner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.continuum.utils.SendReport;

import cucumber.api.testng.AbstractTestNGCucumberTests;

public class SuiteLevelDesign extends AbstractTestNGCucumberTests{
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteExecution() throws Throwable{
		
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuiteExecution() throws Throwable{
		
		SendReport.sendReport("test-report");
	}

}
