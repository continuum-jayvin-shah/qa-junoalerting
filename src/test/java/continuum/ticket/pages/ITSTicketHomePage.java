package continuum.ticket.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//import com.continuum.framework.utils.Log;
import com.continuum.utils.JunoAlertingUtils;
import com.continuum.utils.DataUtils;

import continuum.cucumber.DriverFactory;
import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class ITSTicketHomePage {
	ITSLoginPage login = new ITSLoginPage();
	WebdriverWrapper wd = new WebdriverWrapper();
	
	public Locator frame1 = new Locator("frame1", "#iframe", "css");
	public Locator frame2 = new Locator("frame2", "#Main1", "css");
	public Locator frame3 = new Locator("frame3", "#rightBottom", "css");
	
	public Locator lnkMyTeamQueueNew = new Locator("My Team Queue New Ticket link",
			"#toggleNow11 > tbody > tr:nth-child(2) > td.MainTxt > a", "css");
	public Locator txtTicketID = new Locator("TicketID text box", "#txtTicketId", "css");
	public Locator btnGo = new Locator("TicketID search Go button",
			"#main_container > tbody > tr:nth-child(1) > td > table > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(4) > img",
			"css");
	public Locator lnkSearchedTicketID = new Locator("Seached TicketID", "#id0 > a", "css");
	public Locator friendlyNameTicketID = new Locator("Seached Ticket Friendly Name", "td:nth-child(5)[id=id0]", "css");


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
}
