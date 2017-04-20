package continuum.ticket.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class ITSAlertDetailsPage {
	WebdriverWrapper wd = new WebdriverWrapper();




	//public Locator itsAlertSubject= new Locator("Alert Subject on ITS Portal","");

	//public Locator itsAlertDescription= new Locator("Ticket Descriptio on ITS Portal","//label[text()='Description']/../../td[2]");

	public Locator itsAlertSiteName= new Locator("Ticket SiteName on ITS Portal","//b[text()='  Site : ']/../../td[2]");

	public Locator itsAlertResourseName= new Locator("Ticket ResourseName on ITS Portal","//b[text()=' Machine Name : ']/../../td[2]");

	public Locator itsAlertEscalationCategory= new Locator("Alert EscalationCategory on ITS Portal","//b[text()='Escalation Category : ']/../../td[2]");

	public Locator itsAlertConditionName= new Locator("Alert Condition Name on ITS Portal","//b[text()='Condition Name : ']/../../td[2]");


	//public Locator itsAlertCreatedBy= new Locator("Ticket Created By on ITS Portal","//label[text()='Created By']/../../td[2]");

	public Locator itsAlertStatus= new Locator("Ticket Status on ITS Portal","//td[contains(text(),'Status')]/../td[2]/select/option[@selected='']]");








	public ITSAlertDetailsPage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			System.out.println("Window Title : " + wd.getDriverTitle());
			System.out.println("Window URL : " + wd.getWebdriver().getCurrentUrl());
			if (wd.getWebdriver().getCurrentUrl().contains("Noc_TaskUpdate_ui")) {
				break;
			}
		}
	}

	/**
	 * Verify Ticket details
	 *
	 * @throws IOException
	 */
	public void verifyTicketDetails() throws IOException {
		HashMap<String, String> testData = new HashMap();
		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");
		System.out.println(testData.get("Condition name"));
		System.out.println(testData.get("Condition Id"));
		System.out.println(testData.get("RMM Service Plan"));
	}
}
