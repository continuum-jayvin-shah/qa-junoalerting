package continuum.ticket.pages;

import java.sql.SQLException;
import java.util.Set;

import org.testng.Reporter;

//import com.continuum.framework.utils.Log;
import com.continuum.utils.JunoAlertingUtils;

import continuum.cucumber.DriverFactory;
import continuum.cucumber.Locator;
import continuum.cucumber.Utilities;
import continuum.cucumber.WebdriverWrapper;

public class ITSLoginPage {

	WebdriverWrapper wd = new WebdriverWrapper();
	public Locator emailId = new Locator("Login email id textbox", "#main_str .log-input-text#user_txt", "css");
	public Locator password = new Locator("Login password text box", "#main_str .log-input-text#user_pass", "css");
	public Locator loginBtn = new Locator("Login button", "#main_str #Submit", "css");
	public static String parentWIndow;

	/**
	 * Navigate to ITS portal
	 */
	public boolean navigateToTicketPortal() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		if (windowHandles.size() > 1) {
			// Do something to open new tabs
			for (String handle : wd.getWebdriver().getWindowHandles()) {
				if (!handle.equals(parentWIndow)) {
					wd.getWebdriver().switchTo().window(handle);
					wd.getWebdriver().close();
				}
			}
			wd.getWebdriver().switchTo().window(parentWIndow);
		}
		else {
			parentWIndow = wd.getWebdriver().switchTo().defaultContent().getWindowHandle();
		}

		wd.openApplication(Utilities.getMavenProperties("ITSPortalUrl"));
		wd.waitForPageLoad();
		//wd.waitFor(6000);
		wd.verifyCurrentUrl(Utilities.getMavenProperties("ITSPortalUrl"));
		return wd.getWebdriver().getCurrentUrl().equalsIgnoreCase(Utilities.getMavenProperties("ITSPortalUrl"));
	}

	/**
	 * Login to ITS portal
	 *
	 * @return object of ITSHomePage
	 * @throws SQLException
	 */
	public ITSTicketHomePage loginToTicketPortal() throws SQLException {
		//Reporter.log("Enter login credentials  to NOC portal");
		wd.waitForElementToBeClickable(emailId, 1000);
		wd.clearandSendKeys(Utilities.getMavenProperties("ITSPortalUserName"), emailId);
		wd.clearandSendKeys(Utilities.getMavenProperties("ITSPortalPassword"), password);
		wd.clickElement(loginBtn);
		//wd.waitFor(20000);
		JunoAlertingUtils.waitForPageLoad(70, wd);
		//Log.message("Successfully logged in to ITS portal", DriverFactory.getDriver());
		return new ITSTicketHomePage();
	}
}
