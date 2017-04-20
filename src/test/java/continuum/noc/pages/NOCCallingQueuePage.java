package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCCallingQueuePage {
	WebdriverWrapper wd = new WebdriverWrapper();


	public Locator nocDashboardInbox= new Locator("Noc dashboard/Inbox option on Noc Portal",".dmTextTop1[id*=dm0m0i4]","css");
	public Locator nocCallingQueue= new Locator("Noc Calling Queue option on Noc Portal",".dmText1[id*=dm0m18i12t]","css");
	public Locator nocMannualPickup= new Locator("Noc Manuual pick up option on Noc Portal",".dmText1[id*=dm0m25i0t]","css");





	public NOCCallingQueuePage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			System.out.println("Window Title : " + wd.getDriverTitle());
			System.out.println("Window URL : " + wd.getWebdriver().getCurrentUrl());
			if (wd.getWebdriver().getCurrentUrl().contains("MessageBoard")) {
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
