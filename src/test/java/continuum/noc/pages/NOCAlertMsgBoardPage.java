package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCAlertMsgBoardPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator nocCheckBoxAlertMsgBoard = new Locator("Check box on Msg Board page on Noc Portal","input[name='chkReadNotes']","css");

	public Locator nocSubmitButtonAlertMsgBoard = new Locator("Submit Button Msg BoardAl page on Noc Portal","input[id='buttReadNotes'][name='buttReadNotes']","css");
	public Locator nocResoursetextAlertMsgBoard = new Locator("Resourse Text Msg BoardAl page on Noc Portal","tr:nth-child(7)>td:nth-child(1)","css");
	public Locator nocSubjecttextAlertMsgBoard = new Locator("Subject Text Msg BoardAl page on Noc Portal","tr:nth-child(7)>td:nth-child(2)","css");
	public Locator nocMessageDetailAlertMsgBoard = new Locator("Message Detail Text Msg BoardAl page on Noc Portal","tr:nth-child(7)>td:nth-child(3)","css");
	public Locator nocAlertMSPBoard = new Locator("MSP Details on Message BoardAl page on Noc Portal","tr:nth-child(2)>td","css");
	public Locator nocSiteNameAlertMsgBoard = new Locator("Site Detail Text Msg BoardAl page on Noc Portal","tr:nth-child(3)>td","css");

	public Locator nocAlertMsgBoardicon = new Locator("Msg BoardAl icon on Noc Portal","tr:nth-child(7)>td:nth-child(6)>table>tbody>tr>td:nth-child(2)>img","css");






	public NOCAlertMsgBoardPage() {
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
