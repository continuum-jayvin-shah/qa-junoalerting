package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//import com.continuum.framework.utils.Log;
import com.continuum.utils.DataUtils;

import continuum.cucumber.DriverFactory;
import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCTicketReportPage {
	WebdriverWrapper wd = new WebdriverWrapper();
	public Locator ticketID = new Locator("Ticket ID", "input#txtTaskId", "css");
	public Locator viewButton = new Locator("View Button", "input#Button1", "css");
	public Locator lnkTicketStatus = new Locator("Ticket Status", "a[title='View Ticket']", "css");
	public Locator iframe = new Locator("iframe", "#frameTaskComments", "css");

	public Locator ticketStatus = new Locator("Ticket Status", ".HeaderBlackNormal>a", "css");

	public Locator managementNode = new Locator("Ticket Management Node","//a[contains(text(),'Management Node')]/../../../tr[2]/td[3]");

	public Locator subject = new Locator("Ticket Subject", "//a[contains(text(),'Ticket Subject')]/../../../tr[2]/td[4]");
	public Locator resource = new Locator("Ticket Resource", "//a[contains(text(),'Resource')]/../../../tr[2]/td[5]]");
	public Locator priority = new Locator("Ticket Priority", "//a[contains(text(),'Priority ')]/../../../tr[2]/td[6]");
	public Locator assignTo = new Locator("Ticket Assign To", "//a[contains(text(),'Assign to')]/../../../tr[2]/td[7]");

	/**
	 * Verify Ticket details Report Page
	 *
	 * @throws IOException
	 */

	public void verifyTicketDetailsReportPage()throws IOException {

		HashMap<String, String> testData = new HashMap();
		testData.putAll(DataUtils.getTestRow());
		String siteName = testData.get("SiteCode (siteCode)")+ "("+testData.get("Member")+")";
		String nocTicketSubject = testData.get("TCKTSubject").trim()
				.replace("<<Site_Name>>", testData.get("SiteName").trim())
				.replace("<<Resource_Name>>", testData.get("DeviceName (entity)").trim())
				.replace("<<Friendly_Name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim())
				.replace("<<Escalation_category>>",testData.get("ITSPriority").trim())
				.replace("<<Friendly_name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim()); 
		
	
		System.out.println("Ticket Gen Statas =================" + getTicketStatus().trim());
		System.out.println("Ticket Gen Statas in Excel=================" + testData.get("GenStatus").trim());
		System.out.println("Ticket Site Name =================" + getTicketSite().trim());
		System.out.println("Ticket Site Name in Excel=================" + siteName);
		System.out.println("Ticket subject=================" + getTicketSubject().trim());
		
		System.out.println("Ticket subject in Excel=================" + nocTicketSubject );
		
		System.out.println("Ticket resource=================" + getTicketResource().trim());
		System.out.println("Ticket resource in Excel=================" + testData.get("DeviceName (entity)").trim());
		
		System.out.println("Ticket priority=================" + getTicketPriority().trim());
		System.out.println("Ticket priority in Excel=================" + testData.get("Priority").trim());
		System.out.println("Ticket assignto=================" + getTicketAssignTo().trim());
		System.out.println("Ticket assignto in Excel=================" + testData.get("TCKTAction").trim());
		
		
		
		
		String getTicketSite_res=getTicketSite();
	
	
	
		/*Log.softAssertThat(testData.get("GenStatus").equalsIgnoreCase(getTicketStatus()), "Genaration Status successfully verified on Noc ticket report page", "Genaration Status not verified on Noc ticket report page,  Expected : " + testData.get("GenStatus")+ ",  Actual : " + getTicketStatus(), DriverFactory.getDriver());

		Log.softAssertThat(siteName.equalsIgnoreCase(getTicketSite()), "SiteName successfully verified on Noc ticket report page", "SiteName is not correct on Noc ticket report page, Expected : " + siteName + ", Actual : "+ getTicketSite());
		Log.softAssertThat(nocTicketSubject.equalsIgnoreCase(getTicketSubject()), "Ticket Subject successfully verified on Noc Ticket report page", "Ticket Subject is not correct on Noc ticket report page, Expected :" + nocTicketSubject + ", Actual : " + getTicketSubject());
		
		Log.softAssertThat(testData.get("DeviceName (entity)").equalsIgnoreCase(getTicketResource()), "Resource name successfully verified on Noc ticket report page", "Resource name is not correct on Noc ticket report page, Expected :" + testData.get("DeviceName (entity)")+ ",Actual : " + getTicketResource());
		Log.softAssertThat(testData.get("Priority").equalsIgnoreCase(getTicketPriority()), "Priority successfully verified on Noc ticket report page", "Priority is not correct on Noc ticket report page, Expected :" + testData.get("priority")+ ",Actual : " + getTicketPriority());
		Log.softAssertThat(testData.get("TCKTAction").equalsIgnoreCase(getTicketAssignTo()), "AssignTo field successfully verified on Noc ticket report page", "AssignTo field is not correct on Noc ticket report page, Expected :" + testData.get("assignTo")+ ",Actual : " + getTicketAssignTo());
*/		
	}
	public NOCTicketReportPage() {
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
	 *
	 */
	public void viewTicketStatus(String ticketInput) {
		wd.sendKeys(ticketInput, ticketID);
		wd.clickElement(viewButton);
		wd.waitForPageLoad(5);
	}

	public String getTicketStatus() {
		return getTicketDetailsTaskReport("Status");
	}

	public String getTicketSite() {
		return getTicketDetailsTaskReport("Site");
	}

	public String getTicketNocPriority() {
		return getTicketDetailsTaskReport("NOC Priority");
	}

	public String getTicketSubject() {
		return getTicketDetailsTaskReport("Ticket Subject");
	}

	public String getTicketResource() {
		System.out.println("getTicketResource");
		return getTicketDetailsTaskReport("Resource");
	}

	public String getTicketPriority() {
		return getTicketDetailsTaskReport("Priority");
	}

	public String getTicketAssignTo() {
		return getTicketDetailsTaskReport("Assign to");
	}

	public String getTicketTicketDate() {
		return getTicketDetailsTaskReport("Ticket Date");
	}

	public String getTicketDetailsTaskReport(String key) {
		String value ="" ;
		int i = 1;
		System.out.println("getTicketDetailsTaskReport");
		wd.switchToFrame(iframe);
		for (WebElement colHead : wd.getWebdriver()
				.findElements(By.cssSelector("table#derivtbl2 tr.SUBHEADER > td.HEADERBLACKBOLD"))) {
			System.out.println(colHead.getText().trim());
			System.out.println(key);
			if (colHead.getText().trim().equalsIgnoreCase(key)) {
				System.out.println("success");
				value = wd.getWebdriver()
						.findElement(By
								.cssSelector("table#derivtbl2 tr> td.HeaderBlackNormal:nth-child(" + i + ")"))
						.getText();
				break;
			}
			i++;
		}
		wd.getWebdriver().switchTo().defaultContent();
		return value.trim();
	}


	public NOCTicketMsgBoardPage viewTicketDetails1234() {
		wd.switchToFrame(iframe);
		wd.waitForElementToBeClickable(lnkTicketStatus, 80000);
		wd.clickElement(lnkTicketStatus);
		System.out.println("===========================Entered the issue 2===============");
		wd.waitFor(2000);
		// To be removed
		//wd.waitFor(10000);
		//wd.waitForPageLoad(60);
		wd.getWebdriver().switchTo().defaultContent();
		System.out.println("===========================Entered the issue 3===============");
		//new NOCTicketMsgBoardPage();
		System.out.println("===========================Entered the issue 3.1===============");
		return new NOCTicketMsgBoardPage();
	}
}
