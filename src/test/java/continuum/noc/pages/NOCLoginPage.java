package continuum.noc.pages;

import java.util.Set;

import org.testng.Reporter;


import continuum.cucumber.Locator;
import continuum.cucumber.Utilities;
import continuum.cucumber.WebdriverWrapper;
import continuum.ticket.pages.ITSLoginPage;

public class NOCLoginPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator emailId = new Locator("Login email id textbox", ".//*[@id='txtUser']");
	public Locator password = new Locator("Login password text box", ".//*[@id='txtPassword']");
	public Locator loginBtn = new Locator("Login button", ".//*[@id='Submit']");
	public Locator wpopup = new Locator("close pop up", "//li[@class='highslide-close']");

	/**
	 * Login to NOC portal
	 *
	 * @return object of NOCHomePage
	 */
	public NOCHomePage loginToNOC() {
		//Log.message("Enter login credentials  to NOC portal");
		wd.waitForElementToBeClickable(emailId, 70000);
		wd.clearandSendKeys(Utilities.getMavenProperties("NOCPortalUserName"), emailId);
		wd.clearandSendKeys(Utilities.getMavenProperties("NOCPortalPassword"), password);
		wd.clickElement(loginBtn);
		wd.waitForElementToBeClickable(wpopup, 80000);
		wd.mouseHover(wpopup);
		wd.clickElement(wpopup);
		wd.waitForPageLoad(60);
		return new NOCHomePage();
	}

	/**
	 * Navigate to NOC portal
	 */
	public void navigateToNOC() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		if (windowHandles.size() > 1) {
			// Do something to open new tabs
			for (String handle : wd.getWebdriver().getWindowHandles()) {
				if (!handle.equals(ITSLoginPage.parentWIndow)) {
					wd.getWebdriver().switchTo().window(handle);
					wd.getWebdriver().close();
				}
			}
			wd.getWebdriver().switchTo().window(ITSLoginPage.parentWIndow);
		}
		wd.openApplication(Utilities.getMavenProperties("NOCPortalUrl"));
		//Log.message("I naviagte to NOC portal");
	}
}
