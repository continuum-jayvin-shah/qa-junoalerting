package continuum.noc.pages;

import java.util.Set;

import continuum.cucumber.WebdriverWrapper;

public class NOCHomePage {
	WebdriverWrapper wd = new WebdriverWrapper();
	public NOCTopNavigation topnav = new NOCTopNavigation();

	public NOCHomePage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		if (windowHandles.size() > 1) {
			for (String handle : wd.getWebdriver().getWindowHandles()) {
				if (wd.getWebdriver().getCurrentUrl().contains("/NOCIndex")) {
					wd.getWebdriver().switchTo().window(handle);
					wd.getWebdriver().close();
				}
			}

			windowHandles = wd.getWebdriver().getWindowHandles();

			for (String windowHandle : windowHandles) {
				wd.switchToWindow(windowHandle);
				if (wd.getWebdriver().getCurrentUrl().contains("/NOCIndex")) {
					break;
				}
			}
		}
	}

	public NOCHomePage navigateBackToHomePage() {
		return new NOCHomePage();

	}


}
