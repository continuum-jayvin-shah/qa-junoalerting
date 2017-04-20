package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCTicketUpdateScheduledMessage {
	WebdriverWrapper wd = new WebdriverWrapper();


	public Locator nocSubjecttextMsgBoard = new Locator("Subject Text Msg BoardTk page on Noc Portal","input[name=txtSub]","css");
	public Locator nocMessageDetailMsgBoard = new Locator("Message Detail Text Msg BoardTk page on Noc Portal","textarea.searchtxtBox","css");

	public Locator nocResourceUpdateMsgBoard = new Locator(" Resource Detail Text Msg BoardTk page on Noc Portal","tr:nth-child(3)>td:nth-child(2)","css");
	public Locator nocMSPUpdateMsgBoard = new Locator("MSP Details on Message BoardTk page on Noc Portal","#rankings>select>option","css");
	public Locator nocSiteNameUpdateMsgBoard = new Locator("Site Detail Text Msg BoardTk page on Noc Portal","tr:nth-child(2) >td:nth-child(2)[class='HeaderBlackNormal']","css");

	public NOCTicketUpdateScheduledMessage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			System.out.println("Window Title NOCTicketUpdateScheduledMessage : " + wd.getDriverTitle());
			System.out.println("Window URL NOCTicketUpdateScheduledMessage : " + wd.getWebdriver().getCurrentUrl());
			if (wd.getWebdriver().getCurrentUrl().contains("/UpdateSchMessage")) {
				break;
			}
		}
	}

	/**
	 * Verify Ticket Update Scheduled MessageDetails
	 *
	 * @throws IOException
	 */
	public void verifyTicketUpdateScheduledMessageDetails() throws IOException {
		HashMap<String, String> testData = new HashMap();
		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");
		System.out.println(testData.get("Condition name"));
		System.out.println(testData.get("Condition Id"));
		System.out.println(testData.get("RMM Service Plan"));


		verifyTicketMspDetailForUpdateScheduledMessage(testData.get("Member"));
		verifyTicketClientDetailForUpdateScheduledMessage(testData.get("SiteName"));
		verifyTicketResourceDetailForUpdateScheduledMessage(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"));
		verifyTicketSubjectDetailForUpdateScheduledMessage(testData.get("MSGBSubject"));
		verifyTicketMessageDetailForUpdateScheduledMessage(testData.get("MSGBDesc"));



	}



	/**
	 * verify Ticket Msp Detail For Update Scheduled Message page
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketMspDetailForUpdateScheduledMessage (String mspOnUpdateScheduleMessage) {
		String mspDetails =wd.getText(nocMSPUpdateMsgBoard);
		System.out.println(mspDetails);
		return mspOnUpdateScheduleMessage.trim().equals(mspDetails);

	}

	/**
	 * verify Ticket Client Detail For Update Scheduled Message page
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketClientDetailForUpdateScheduledMessage (String clientOnUpdateScheduleMessage) {
		String clientDetails =wd.getText(nocSiteNameUpdateMsgBoard);
		System.out.println(clientDetails);
		return clientOnUpdateScheduleMessage.trim().equals(clientDetails);

	}

	/**
	 * verify Ticket Resource Detail For Update Scheduled Message page
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketResourceDetailForUpdateScheduledMessage (String resourceOnUpdateScheduleMessage) {
		String resourceDetails =wd.getText(nocResourceUpdateMsgBoard);
		System.out.println(resourceDetails);
		return resourceOnUpdateScheduleMessage.trim().equals(resourceDetails);

	}

	/**
	 * verify Ticket Subject Detail For Update Scheduled Message page
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketSubjectDetailForUpdateScheduledMessage (String subjectOnUpdateScheduleMessage) {
		String subjectDetails =wd.getText(nocSubjecttextMsgBoard);
		System.out.println(subjectDetails);
		return subjectOnUpdateScheduleMessage.trim().equals(subjectDetails);

	}


	/**
	 * verify Ticket Message Detail For Update Scheduled Message page
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketMessageDetailForUpdateScheduledMessage (String messageOnUpdateScheduleMessage) {
		String messageDetails =wd.getText(nocMessageDetailMsgBoard);
		System.out.println(messageDetails);
		return messageOnUpdateScheduleMessage.trim().equals(messageDetails);

	}




}
