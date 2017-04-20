package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.continuum.utils.JunoAlertingUtils;
import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;
import edu.emory.mathcs.backport.java.util.Collections;

public class NOCTicketMsgBoardPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator frame = new Locator("MessageFrame", "iframe#iFrmMessage", "css");

	public Locator nocCheckBoxMsgBoard = new Locator("Check box on Msg Board page on Noc Portal", "input#chkReadNotes",
			"css");

	public Locator nocSubmitButtonMsgBoard = new Locator("Submit Button Msg BoardTk page on Noc Portal",
			"input#buttReadNotes", "css");
	public Locator nocResoursetextMsgBoard = new Locator("Resourse Text Msg BoardTk page on Noc Portal","//td[contains(text(),'Resource')]/../../tr[last()]/td[1]");
	public Locator nocSubjecttextMsgBoard = new Locator("Subject Text Msg BoardTk page on Noc Portal","//td[contains(text(),'Subject ')]/../../tr[last()]/td[2]");
	public Locator nocMessageDetailMsgBoard = new Locator("Message Detail Text Msg BoardTk page on Noc Portal","//td[contains(text(),'Subject ')]/../../tr[last()]/td[3]");
	public Locator nocMSPBoard = new Locator("MSP Details on Message BoardTk page on Noc Portal",".dataNormal.firefinder-match","css");
	public Locator nocSiteNameMsgBoard = new Locator("Site Detail Text Msg BoardTk page on Noc Portal","td:nth-child(2)[class=dataNormal]","css");

	public Locator nocMsgBoardicon = new Locator("Msg BoardTk icon on Noc Portal","//td[contains(text(),'MSG Posted')]/../../tr[last()]/td[5]/div/img","css");






	public NOCTicketMsgBoardPage() {
		System.out.println("===========================Entered the issue 4===============");
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		String nocWinHandle = null;
		for (String windowHandle : windowHandles) {
			System.out.println("===========================Entered the issue 5===============");
			wd.switchToWindow(windowHandle);
			System.out.println("Window URL NOCTicketMsgBoardPage" + wd.getDriverTitle());
			if (wd.getWebdriver().getCurrentUrl().contains("/frmNotes_MessageBoard_New_HighSlide")) {
				nocWinHandle = wd.getWebdriver().getWindowHandle();
				break;
			}
		}

		// Do something to open new tabs
		for (String handle : wd.getWebdriver().getWindowHandles()) {
			wd.switchToWindow(handle);
			if (wd.getWebdriver().getCurrentUrl().contains("/UpdateSchMessage")) {
				wd.getWebdriver().close();
			}
		}

		wd.getWebdriver().switchTo().window(nocWinHandle);
		wd.waitForPageLoad(90);

	}

	public NOCTicketMsgBoardPage navigateBackToTicketMsgBoardPage() {
		return new NOCTicketMsgBoardPage();

	}

	/**
	 * Verify Ticket Message Board Details
	 *
	 * @throws IOException
	 */
	public void verifyMessageBoardDetails() throws IOException {

		HashMap<String, String> testData = new HashMap();
		testData.putAll(DataUtils.getTestRow());

		wd.switchToFrame(frame);
		List<WebElement> msgBoardLst = wd.getWebdriver()
				.findElements(By.cssSelector(".borderedTable.buttonTopMargin > tbody > tr"));
		Collections.reverse(msgBoardLst);

		for (WebElement el : msgBoardLst) {
			JunoAlertingUtils.scrollIntoView(wd.getWebdriver(), el);
			System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(1)")).getText());
			System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(2)")).getText());
			System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(3)")).getText());
			System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(4)")).getText());
			System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(5)")).getText());
			break;

		}
		wd.getWebdriver().switchTo().defaultContent();

		//		HashMap<String, String> testData = new HashMap<>();
		//		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		//		testData.putAll(DataUtils.getTestRow());
		//		System.out.println(
		//				"========================================================================================================");
		//		System.out.println(testData.get("Condition name"));
		//		System.out.println(testData.get("Condition Id"));
		//		System.out.println(testData.get("RMM Service Plan"));
		//
		//
		//		verifyTicketMspMessageBoard(testData.get("Member"));
		//		verifyTicketSiteNameMessageBoard(testData.get("SiteName"));
		//		verifyTicketResourceMessageBoard(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"));
		//		verifyTicketSubjectMessageBoard(testData.get("MSGBSubject"));
		//
		//		verifyTicketMessageDetailsMessageBoard(testData.get("MSGBDesc"));
	}

	/**
	 * Verify ticket message MSP
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketMspMessageBoard(String msp) {
		String msptextMsgBoard =wd.getText(nocResoursetextMsgBoard);
		System.out.println(msptextMsgBoard);
		return msp.trim().equals(msptextMsgBoard);

	}

	/**
	 * Verify ticket message Site Name
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketSiteNameMessageBoard(String sitename) {
		String sitenametextMsgBoard =wd.getText(nocSiteNameMsgBoard);
		System.out.println(sitenametextMsgBoard);
		return sitename.trim().equals(sitenametextMsgBoard);

	}

	/**
	 * Verify ticket message Board resource
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketResourceMessageBoard(String resource) {
		String resoursetextMsgBoard =wd.getText(nocResoursetextMsgBoard);
		System.out.println(resoursetextMsgBoard);
		return resource.trim().equals(resoursetextMsgBoard);

	}

	/**
	 * Verify ticket message Board subject
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketSubjectMessageBoard(String subject) {
		String subjecttextMsgBoard =wd.getText(nocSubjecttextMsgBoard);
		System.out.println(subjecttextMsgBoard);
		return subject.trim().equals(subjecttextMsgBoard);

	}

	/**
	 * Verify ticket message Board message details
	 *
	 * @param
	 *
	 */


	public boolean verifyTicketMessageDetailsMessageBoard(String messagedetails) {
		String messagetextMsgBoard =wd.getText(nocMessageDetailMsgBoard);
		System.out.println(nocMessageDetailMsgBoard);
		return messagedetails.trim().equals(messagetextMsgBoard);

	}

	public NOCTicketUpdateScheduledMessage clickLatestEditMessageIcon(String resourse)
	{
		wd.switchToFrame(frame);
		List<WebElement> msgBoardLst = wd.getWebdriver()
				.findElements(By.cssSelector(".borderedTable.buttonTopMargin > tbody > tr"));
		Collections.reverse(msgBoardLst);

		for (WebElement el : msgBoardLst) {
			JunoAlertingUtils.scrollIntoView(wd.getWebdriver(), el);
			if (resourse.equalsIgnoreCase(el.findElement(By.cssSelector("td:nth-child(1)")).getText())) {
				el.findElement(By.cssSelector("td:nth-child(5) div img[title='edit message']")).click();
				System.out.println("Resource " + el.findElement(By.cssSelector("td:nth-child(1)")).getText());
				break;
			}
		}
		wd.waitFor(10000);
		wd.getWebdriver().switchTo().defaultContent();
		return new NOCTicketUpdateScheduledMessage();
	}


	/**
	 * Navigating to Ticket Update Page
	 *
	 * @param
	 *
	 */
	public NOCTicketUpdatePage navigateTicketUpdatePage()

	{
		wd.waitFor(5000);
		wd.clickElement(nocCheckBoxMsgBoard);
		wd.clickElement(nocSubmitButtonMsgBoard);
		wd.waitFor(10000);
		return new NOCTicketUpdatePage();
		// wd.waitForElementToBeDisplayed(nocTktAssignto, 1000);
	}

}
