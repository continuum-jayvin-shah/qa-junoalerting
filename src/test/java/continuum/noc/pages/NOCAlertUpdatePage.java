package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCAlertUpdatePage {
	WebdriverWrapper wd = new WebdriverWrapper();


	public Locator nocAlFamily= new Locator("Alert Family on Noc Portal","//td[contains(text(),' Family Name :')]/../td[2]");
	public Locator nocAlStatus=new Locator("Alert Status on Noc Portal","//td[contains(text(),'Current Status : ')]/font[1]");
	//public Locator nocTktPriority=new Locator("Ticket Priority on Noc Portal",".labelBOLD.ColRed","css");
	public Locator nocAlAssignto=new Locator("Alert Assignto on Noc Portal","//td[contains(text(),'This Alert is assigned to group  :')]/../td[2]/font[1]","css");
	//public Locator nocTktResourse=new Locator("Ticket Resourse on Noc Portal",".label.firefinder-match");
	public Locator nocAlDescription=new Locator("Alert Description on Noc Portal","//td[contains(text(),'Description :')]/../td[2]");
	//public Locator nocTktSubject=new Locator("Ticket Subject on Noc Portal",".labelBOLD.ColBrown.firefinder-match","css");
	public Locator nocAlMember=new Locator("Alert Member on Noc Portal","//td[contains(text(),'Member Name :')]/../td[2]");
	public Locator nocAlSiteName=new Locator("Alert Site on Noc Portal","//td[contains(text(),'Site Name :')]/../td[2]");

	public Locator nocAlEscalationCategory=new Locator("Alert Escalation category on Noc Portal","//td[contains(text(),'Escalation Category :')]/../td[2]/table/tbody/tr/td[1]");


	public Locator nocAlName= new Locator("Alert Name on Noc Portal","//td[contains(text(),'Alert Name :')]/../td[2]/table/tbody/tr/td[1]");


	public NOCAlertUpdatePage() {
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
