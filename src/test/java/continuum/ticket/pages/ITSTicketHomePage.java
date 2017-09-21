package continuum.ticket.pages;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//import com.continuum.framework.utils.Log;
import com.continuum.utils.JunoAlertingUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class ITSTicketHomePage {
	
	ITSLoginPage login = new ITSLoginPage();
	WebdriverWrapper wd = new WebdriverWrapper();
	
	
	public Locator frame1 = new Locator("frame1", "#iframe", "css");
	public Locator frame2 = new Locator("frame2", "#Main1", "css");
	public Locator frame3 = new Locator("frame3", "#rightBottom", "css");
	
	private String siteValue 		= 	null;
	private String resourceValue 	= 	null;
	private String notificationName	=	null;
	private String alertFamilies 	=	null;
	
	
	//Intellimon Email Section Locators
	public Locator RMMLink = new Locator("RMMLinkTab", ".//*[@id='ctl00_ctl00_mnuSetup']/a");
	public Locator extensionsLink = new Locator("Intellimon Email Notifications Link", ".//*[@id='ctl00_ctl00_leftPanel_mnuExtensions']/a");
	public Locator intellimonEmailLink = new Locator("Intellimon Email Notifications Link", ".//*[@id='ctl00_ctl00_leftPanel_LinkButton8']");
	public Locator intellimonSuspensionLink = new Locator("Intellimon Alert Suspension Link", ".//*[@id='ctl00_ctl00_leftPanel_LinkButton6']");
	public Locator addNewButton = new Locator("Add Rule", ".//*[@id='btnAddRule']");
	public Locator configName = new Locator(" Notification Configuration Name", ".//*[@id='txtPolicyName']");
	public Locator siteRadioButton = new Locator("siteRadioButton", ".//*[@id='rbSites']");
	public Locator resourceRadioButton = new Locator("siteRadioButton", ".//*[@id='rbResources']");
	public Locator siteListBox = new Locator("SiteListBox", ".//*[@id='div_site']/button");
	public Locator siteListBox_resource = new Locator("SiteListBox Resource", "//*[@id='div_siteResource']/button");
	
	public Locator closeSiteListMenu = new Locator("closeSiteListMenu", ".ui-icon.ui-icon-circle-close","css");
	public Locator alertFamiliesRadioButton = new Locator("alertFamiliesRadioButton", "#alertFamilies","css");
	public Locator alertFamiliesSelect = new Locator("alertFamiliesSelect", "#familyList","css");
	public Locator rightButtonToSelect = new Locator("rightButtonToSelect", "#btnright","css");
	public Locator submitButton = new Locator("rightButtonToSelect", "#btnSubmit","css");
	public Locator frameId = new Locator("frame", ".k-content-frame","css");
	public Locator deleteNotificationButton = new Locator("deleteNotificationButton", "btnDelete","id");
	public Locator confirmDeleteBox = new Locator("confirmDeleteBox", "confirmItemDeletion","id");
	public Locator confirmDeleteBtn = new Locator("confirmDeleteBtn", "btnDeleteConfirmYes","id");
	public Locator resourceType 	=	new Locator("resource type drop down", "//*[@id='ddlResourcesType']");
	public Locator resourceNameDropDown = new Locator("Resource Name Drop down", ".//*[@id='div_Resource']/button");
	public Locator selectAllResourcesLink 	=	new Locator("selectAllResourcesLink", "//a/span[text()='Select all']");
	public Locator applyToNewSites 	=	new Locator("applyToNewSites", ".//*[@id='chkApplyTo']");
	
	public Locator suspensionScheduleOnetimeRadioButton 	=	new Locator("suspensionScheduleRadioButton", ".//*[@id='rdbonetime']");
	public Locator timeZoneDropDown 	=	new Locator("timeZoneDropDown", ".//*[@id='ddlTimeZone']");
	public Locator fromDateCalendar = new Locator("fromDateCalendar",".//*[@id='divonetime']/div[1]/div[2]/span/span/span/span");
	public Locator fromDate = new Locator("fromDate",".//div[@id='txtCustomFdt_dateview']//td/a[@title='"+getformattedDate()+"']");
	public Locator EndDateCalendar = new Locator("EndDateCalendar",".//*[@id='divonetime']/div[2]/div[1]/div[2]/span/span/span/span");
	public Locator endDate = new Locator("endDate",".//div[@id='txtCustomTodt_dateview']//td/a[@title='"+getformattedDate()+"']");
	public Locator suspensionStartTime = new Locator("suspensionStartTime",".//*[@id='tbInstanceTimeStart']");
	public Locator suspensionEndTime = new Locator("suspensionEndTime",".//*[@id='tbInstanceTimeEnd']");
	public Locator lnkMyTeamQueueNew = new Locator("My Team Queue New Ticket link",
			"#toggleNow11 > tbody > tr:nth-child(2) > td.MainTxt > a", "css");
	public Locator txtTicketID = new Locator("TicketID text box", "#txtTicketId", "css");
	public Locator btnGo = new Locator("TicketID search Go button",
			"#main_container > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(4) > img",
			"css");
	public Locator lnkSearchedTicketID = new Locator("Seached TicketID", "#id0 > a", "css");
	public Locator friendlyNameTicketID = new Locator("Seached Ticket Friendly Name", "td:nth-child(5)[id=id0]", "css");
	
	
	
	
	public void setSiteValue(String siteValue) {
		this.siteValue = siteValue;
	}

	public String getSiteValue() {
		return siteValue;
	}
	
	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}
	
	public String getResourceValue(){
		return resourceValue;
	}
	
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	
	public String getNotificationName() {
		return notificationName;
	}
	
	public void setAlertFamilyName(String alertFamilies) {
		this.alertFamilies = alertFamilies;
	}
	
	public String getAlertFamilyName() {
		return alertFamilies;
	}
	
	
	public void clickTab(String WebElement) throws InterruptedException{
		List<WebElement> tabs = wd.getWebdriver().findElements(By.cssSelector("#divMainTopNav > #ulNav > li"));
		for(WebElement tab : tabs){
			if(tab.findElement(By.cssSelector("a")).getText().trim().equalsIgnoreCase(WebElement)){
				tab.click();
				break;
			}
		}
		JunoAlertingUtils.waitForPageLoad(60, wd);
		//Log.message("I clicked on Tab " + WebElement, DriverFactory.getDriver());
	}
	
	/**
	 * Navigate to Team Queue New Ticket window
	 */
	public void navigateToTeamQueueNewTicket() {
		//wd.waitFor(10000);		
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#iframe")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#Main1")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#rightBottom1")));
		wd.clickElement(lnkMyTeamQueueNew);
		wd.waitForPageLoad();
		wd.getWebdriver().switchTo().defaultContent();
		//Log.message("I clicked on new Team Queue New Ticket by clicking New", DriverFactory.getDriver());
	}
	
	/**
	 * Search the given ticket ID
	 *
	 * @throws IOException
	 */
	public void searchTicketID(String ticketID) throws IOException {
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#iframe")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#Main1")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#rightTop")));
		wd.selectByTextFromDropDown(new Locator("SearcchBy", "#rdSearchType", "css"), "Alert ID");
		wd.sendKeys(ticketID, txtTicketID);
		wd.clickElement(btnGo);
		wd.getWebdriver().switchTo().defaultContent();
		wd.waitForPageLoad();		
		//Log.message("I searched for Ticket " + ticketID, DriverFactory.getDriver());
		
	}

	/**
	 * Navigate to ticket details page
	 *
	 * @return object of ITSTicketDetailsPage
	 */
	public ITSTicketDetailsPage navigateToTicketDetailsPage() {
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#iframe")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#Main1")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#rightBottom")));
		wd.clickElement(lnkSearchedTicketID);
		wd.getWebdriver().switchTo().defaultContent();
		wd.waitFor(3000);
		return new ITSTicketDetailsPage();
	}
	
	public String getSearchedTicketDetails(String key) {
		String value = null;
		int i = 2;
		wd.switchToFrame(frame1);
		wd.switchToFrame(frame2);
		wd.switchToFrame(frame3);
		for (WebElement colHead : wd.getWebdriver()
				.findElements(By.cssSelector("table.dataDisTable > tbody > tr.dispCont_TR > td tr > td.labelBOLD"))) {
			if (colHead.getText().trim().equalsIgnoreCase(key)) {
				value = wd.getWebdriver()
						.findElement(By
								.cssSelector("table.dataDisTable > tbody > tr:not(.dispCont_TR) >  td:nth-child(" + i + ")"))
						.getText();
				if(key.equalsIgnoreCase("Subject")){
					value = wd.getWebdriver()
							.findElement(By
									.cssSelector("table.dataDisTable > tbody > tr:not(.dispCont_TR) >  td:nth-child(" + i + ")")).getAttribute("title");
				}
				break;
			}
			i++;
		}
		wd.getWebdriver().switchTo().defaultContent();
		return value.trim();
	}
	
	public boolean verifySearchTicketDetails(){
		/*HashMap<String, String> testData = new HashMap();
		testData.putAll(DataUtils.getTestRow());
*/		
		System.out.println("Alert Id Actual =================" + getSearchedTicketDetails("Alert Id"));
		System.out.println("Site Actual =================" + getSearchedTicketDetails("Site"));
		System.out.println("Machine Name Actual =================" + getSearchedTicketDetails("Machine Name").trim());
		System.out.println("Friendly Name Actual ===================" + getSearchedTicketDetails("Friendly Name").trim());
		System.out.println("Alert Description Actual ===================" + getSearchedTicketDetails("Alert Description").trim());
		System.out.println("Status Actual ===================" + getSearchedTicketDetails("Status").trim());
		System.out.println("Escalation Category Actual ===================" + getSearchedTicketDetails("Escalation Category").trim());
		return false;
	}
	
	public void openIntellimonEmailSection(){
		wd.waitForElementToBeClickable(RMMLink,5000);
		wd.clickElement(RMMLink);
		wd.waitForElementToBeClickable(extensionsLink,5000);
		wd.clickElement(extensionsLink);
		wd.waitForElementToBeClickable(intellimonEmailLink,2000);
		wd.clickElement(intellimonEmailLink);
	}
	
	public void openIntellimonSuspensionSection(){
		wd.waitForElementToBeClickable(RMMLink,5000);
		wd.clickElement(RMMLink);
		wd.waitForElementToBeClickable(extensionsLink,5000);
		wd.clickElement(extensionsLink);
		wd.waitForElementToBeClickable(intellimonSuspensionLink,2000);
		wd.clickElement(intellimonSuspensionLink);
	}
	
	public void setMemberRule(){
		wd.waitForElementToBeClickable(addNewButton,3000);
		wd.clickElement(addNewButton);
		wd.switchToFrame(frameId);
		wd.waitForTextToAppearInTextField(configName,3000);
		wd.clearandSendKeys(getNotificationName(), configName);
		wd.waitForElementToBeClickable(siteRadioButton,3000);
		wd.clickElement(siteRadioButton);
		wd.clickElement(siteListBox);
		wd.waitForElementToBeClickable(selectAllResourcesLink, 2000);
		wd.clickElement(selectAllResourcesLink);
		wd.clickElement(closeSiteListMenu);
		wd.clickElement(applyToNewSites);
	}
	
	public void setSiteRule(){	
		wd.waitForElementToBeClickable(addNewButton,3000);
		wd.clickElement(addNewButton);
		wd.switchToFrame(frameId);
		wd.waitForElementToBeClickable(configName,3000);
		wd.clearandSendKeys(getNotificationName(), configName);
		wd.waitForElementToBeClickable(siteRadioButton,3000);
		wd.clickElement(siteRadioButton);
		wd.clickElement(siteListBox);
		wd.waitForElementToBeClickable(closeSiteListMenu,3000);
		WebElement siteName = wd.getWebdriver().findElement(By.xpath(".//input[@value='"+getSiteValue()
		+"' and @name = 'multiselect_ddlSites']"));
		siteName.click();;
		wd.clickElement(closeSiteListMenu);		
	}
	
	public void setAlertFamilies(){
		wd.clickElement(alertFamiliesRadioButton);
		wd.waitForElementToBeClickable(alertFamiliesSelect, 3000);
		wd.waitFor(5000);
		String text = getAlertFamilyName();
		System.out.println(text.trim());
		wd.selectByTextFromDropDown(alertFamiliesSelect,"Desktop Health (4 alerts)");
		wd.clickElement(rightButtonToSelect);
		wd.clickElement(submitButton);
	}
	
	public void setResourceRule(){
		
		
		wd.waitForElementToBeClickable(addNewButton,3000);
		wd.clickElement(addNewButton);
		wd.switchToFrame(frameId);
		wd.waitForElementToBeClickable(configName,3000);
		wd.clearandSendKeys(getNotificationName(), configName);
		
		wd.waitForElementToBeClickable(resourceRadioButton,3000);
		wd.clickElement(resourceRadioButton);
		wd.clickElement(siteListBox_resource);
		WebElement siteName_resource = wd.getWebdriver().findElement(By.xpath(".//input[@value='"+getSiteValue()
		+"' and @name = 'multiselect_ddlSiteResources']"));
		siteName_resource.click();
		wd.clickElement(closeSiteListMenu);	
		
		
		wd.selectByTextFromDropDown(resourceType, "Windows Desktop");
		wd.clickElement(resourceNameDropDown);
		WebElement resourceName = wd.getWebdriver().findElement(By.xpath(".//*[@value= '"+getSiteValue()+"|"+getResourceValue()
		+ "' and @name='multiselect_ddlResources']"));
		resourceName.click();
		wd.clickElement(closeSiteListMenu);	
		
	}
	
	public void deleteNotificationRule(){
		
		wd.switchtoParentWindow();
		WebElement notificatonCheckBox = wd.getWebdriver().findElement(By.xpath("//td/div/span[text()='"+	 
				getNotificationName() + "']/parent :: div/parent :: td/preceding-sibling:: td/input"));		
		notificatonCheckBox.click();
		wd.clickElement(deleteNotificationButton);
		wd.waitForElementToBeClickable(confirmDeleteBox, 2000);
		wd.clickElement(confirmDeleteBtn);
		
	}
	
	public void setSuspensionScheduleOneTime(){
		wd.clickElement(suspensionScheduleOnetimeRadioButton);
		wd.selectByTextFromDropDown(timeZoneDropDown, "UTC+05:30 (India)");
		wd.clickElement(fromDateCalendar);
		wd.waitForElementToBeClickable(fromDate, 2000);
		wd.clickElement(fromDate);
		String [] suspentionTime = getSusupensionTime();
		wd.clearandSendKeys(suspentionTime[0],suspensionStartTime);
		wd.clickElement(EndDateCalendar);
		wd.waitForElementToBeClickable(endDate, 3000);
		wd.clickElement(endDate);
		wd.clearandSendKeys(suspentionTime[1], suspensionEndTime);
		wd.clickElement(submitButton);
		
	}
	
	public void deleteSuspensionRule(){
		wd.switchtoParentWindow();
		WebElement notificatonCheckBox = wd.getWebdriver().findElement(By.xpath("//td/div/span[text()='"+	 
				getNotificationName() + "']/parent :: div/parent :: td/preceding-sibling:: td/input"));		
		notificatonCheckBox.click();
		wd.clickElement(deleteNotificationButton);
		wd.waitForElementToBeClickable(confirmDeleteBox, 2000);
		wd.clickElement(confirmDeleteBtn);		
	}
	
		
	public String[] getSusupensionTime(){
		String [] timevalues = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		timevalues[0] = sdf.format(new Date(System.currentTimeMillis()+5*60*1000));
		timevalues[1] = sdf.format(new Date(System.currentTimeMillis()+10*60*1000));
		return timevalues;
	}
	
	
	private String getformattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, YYYY");
		System.out.println(sdf.format(new Date()));
		return sdf.format(new Date());
	}
}