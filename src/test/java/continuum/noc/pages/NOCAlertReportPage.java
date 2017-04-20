package continuum.noc.pages;

import java.util.Set;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCAlertReportPage {
	WebdriverWrapper wd = new WebdriverWrapper();
	public Locator alertID = new Locator("Alert ID", "input#txtJobId", "css");
	public Locator viewAlButton = new Locator("View Button for Alert Reoprt", "input#cmdSubmit", "css");
	public Locator lnkAlertStatus = new Locator("Alert Status", "a[title='Open Alert']", "css");
	public Locator iframeAlertupdate = new Locator("iframe Alert update", "#iframeTaskComments", "css");

	public NOCAlertReportPage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			if (wd.getWebdriver().getCurrentUrl().contains("frmTaskReport")) {
				break;
			}
		}
	}

	/**
	 * View ticket status
	 *
	 * @param ticketInput,
	 *            ticket for which status to be viewed
	 */
	public void viewTicketStatus(String ticketInput) {
		wd.sendKeys(ticketInput, alertID);
		wd.clickElement(viewAlButton);
	}

	/**
	 * View ticket details from Ticket status window
	 * 
	 * @return object of NOCTicketDEtailsPage
	 *//*
	public NOCTicketUpdatePage viewTicketDetails() {
		wd.switchToFrame(iframeAlertupdate);
		wd.waitForElementToBeClickable(lnkAlertStatus, 60000);
		wd.clickElement(lnkAlertStatus);
		// To be removed
		wd.waitFor(10000);
		return new NOCTicketUpdatePage();
	}*/

}
