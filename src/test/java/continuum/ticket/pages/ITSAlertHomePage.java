package continuum.ticket.pages;

import java.io.IOException;

import org.openqa.selenium.By;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class ITSAlertHomePage {
	ITSLoginPage login = new ITSLoginPage();
	WebdriverWrapper wd = new WebdriverWrapper();
	public Locator lnkCriticalImpactAlert = new Locator("Critical Impact Alert link",
			"//*[@id='toggleNow6']/tbody/tr[2]/td[2]");
	public Locator txtAlertID = new Locator("AlertID Selection", "#txtTicketId", "css");
	public Locator alertbtnGo = new Locator("AlertID search Go button",
			".notes>img",
			"css");
	public Locator searchBy = new Locator("Seach By", "#rdSearchType", "css");	
	public Locator searchByAlertId = new Locator("Seach By AlertId", "#rdSearchType>option:nth-child(2)", "css");	
	public Locator lnkSearchedAlertID = new Locator("Seached AlertID", "#id0 > a", "css");	
	public Locator friendlyNameAlertID = new Locator("Seached Alert Friendly Name", " td:nth-child(5)[id=id0]", "css");

	/**
	 * Navigate to Team Queue New Ticket window
	 */
	public void navigateToTeamQueueNewTicket() {
		wd.waitFor(10000);
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#iframe")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#Main1")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#rightBottom1")));
		wd.clickElement(lnkCriticalImpactAlert);
		wd.waitFor(5000);
		wd.getWebdriver().switchTo().defaultContent();
	}

	/**
	 * Search the given ticket ID
	 *
	 * @throws IOException
	 */
	public void searchTicketID() throws IOException {
		wd.waitFor(3000);
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#iframe")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#Main1")));
		wd.getWebdriver().switchTo().frame(wd.getWebdriver().findElement(By.cssSelector("iframe#rightTop")));
		wd.sendKeys("2016/12/14-0000100", txtAlertID);
		wd.clickElement(alertbtnGo);
		wd.getWebdriver().switchTo().defaultContent();
		wd.waitFor(5000);
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
		wd.clickElement(lnkSearchedAlertID);
		wd.waitFor(5000);
		wd.getWebdriver().switchTo().defaultContent();
		return new ITSTicketDetailsPage();
	}
}
