package continuum.noc.pages;

import java.util.Set;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCCallingQueueUserManualAllocationPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator goButton = new Locator("Go Button", "input[name='btnSubmit']", "css");
	public Locator partner = new Locator("Partner", "input#memberlike", "css");
	public Locator alertid = new Locator("Partner","//td[contains(text(),'Partner')]/../../tr[last()]/td[6]");





	public Locator iframeAlertupdate = new Locator("iframe Alert update", "#iframeTaskComments", "css");

	public NOCCallingQueueUserManualAllocationPage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			if (wd.getWebdriver().getCurrentUrl().contains("/frmCallingQueueMain")) {
				break;
			}
		}
	}

	public void searchPartner(String partnerName) {
		wd.sendKeys(partnerName, partner);
		wd.clickElement(goButton);
		wd.waitFor(1000000);
	}



}
