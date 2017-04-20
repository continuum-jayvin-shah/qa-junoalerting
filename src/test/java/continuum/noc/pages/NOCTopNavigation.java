package continuum.noc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCTopNavigation {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator mnuQuickReports = new Locator("Quick Reports", "table.dmSubmenuTop #dm0m0i5td", "css");

	public Locator subMenuTicketReport = new Locator("Ticket Report", "table#dm0m26tbl #dm0m26i0R", "css");
	public Locator mnuDashboardInbox = new Locator("Dashboard Inbox", "//div[contains(text(),'Dashboard/Inbox')]");
	public Locator subCallingQueue = new Locator("Calling Queue", ".//*[@id='dm0m18i12tdT' AND contains(text(),'Calling Queue')]");
	public Locator manualPickAndCall = new Locator("Manual Pick And Call", "//td[contains(text(),'Manual Pickup and Call')]");

	// public Locator dImnu1 = new Locator("DI", "table#dm0m0tbl td#dm0m0i4td
	// td#dm0m0i4td", "css");
	public Locator dImnu1 = new Locator("DI", "table#dm0m0tbl tr > td#dm0m0i4td", "css");

	public Locator cQmnu2 = new Locator("CQ", "table#dm0m15i11it > tbody > tr > td#dm0m15i11tdT", "css");
	public Locator mPCnu3 = new Locator("MP", "table#dm0m21i0it tr > td#dm0m21i0tdIc", "css");

	public Locator mnu1 = new Locator("DI", "table.dmSubmenuTop #dm0m0i5td", "css");
	public Locator mnu2 = new Locator("DI", "table#dm0m26tbl #dm0m26i0R", "css");

	/**
	 * Navigate to TicketReport page
	 *
	 * @return Object of Ticket Report page
	 * @throws InterruptedException
	 */
	public NOCTicketReportPage navigateToTicketReport() throws InterruptedException {
		/*
		 * wd.mouseHoverAndClick(mnu1); Thread.sleep(5000);
		 * wd.mouseHoverAndClick(mnu2);
		 */
		// WebDriver driver = wd.getWebdriver();
		//Actions actions = new Actions(wd.getWebdriver());
		WebElement mainMenu = wd.getWebdriver().findElement(By.cssSelector("table.dmSubmenuTop #dm0m0i5td"));
		new Actions(wd.getWebdriver()).moveToElement(mainMenu).clickAndHold().build().perform();
		wd.waitFor(8000);
		//Thread.sleep(10000);
		//wd.waitForPageLoad(60);
		wd.waitForElementToBeClickable(mnu2, 60000);
		wd.getWebdriver().findElement(By.cssSelector("table#dm0m26tbl #dm0m26i0R")).click();

		// WebElement subMenu = //
		/*
		 * driver.findElement(By.cssSelector("table#dm0m26tbl #dm0m26i0R")); //
		 * actions.moveToElement(subMenu); // actions.click().build().perform();
		 * Thread.sleep(10000);
		 */
		wd.waitForPageLoad(60);
		//Thread.sleep(10000);
		return new NOCTicketReportPage();
	}

	public NOCCallingQueueUserManualAllocationPage navigateToCallingQueue() {
		// new Actions(driver).moveToElement(el).perform();
		wd.mouseHover(dImnu1);
		// wd.clickElement(dImnu1);
		wd.waitFor(2000);
		// wd.clickElement(cQmnu2);
		wd.mouseHoverAndClick(cQmnu2);
		wd.waitFor(2000);
		// wd.mouseHoverAndClick(mPCnu3);
		wd.clickElement(mPCnu3);
		wd.waitFor(10000);
		// selectTopMenu("Dashboard/Inbox");
		// hoverOverDashBoardInbox("Calling Queue");
		return new NOCCallingQueueUserManualAllocationPage();
	}


	public void selectTopMenu(String menuTyep) {
		WebDriver driver = wd.getWebdriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		for (WebElement el : driver
				.findElements(By.cssSelector("table.dmSubmenuTop > tbody > tr > td tr > td:nth-child(2)"))) {
			if (el.getText().trim().equalsIgnoreCase(menuTyep)) {
				// Actions actions = new Actions(driver);
				// new Actions(driver).moveToElement(el).perform();
				// wd.mouseHover(el);
				wd.waitFor(2000);
				/*WebElement el2 = driver.findElement(
						By.cssSelector("table#dm0m15i11it > tbody > tr > td:nth-child(2)"));

				el2.click();

				wd.waitFor(2000);

				WebElement el3 = driver.findElement(By.cssSelector("table#dm0m21i0it > tbody > tr > td:nth-child(2)"));

				el3.click();
				 */
				// driver.findElement(By.cssSelector("#dm0m21 tr >
				// td:nth-child(2)#dm0m21i0tdT")).click();

				// wd.waitFor(10000);
				/*
				 * for (WebElement el2 : driver.findElements(By
				 * .cssSelector("table#dm0m15tbl.dmSubmenu > tbody > tr table > tbody > tr > td:nth-child(2)"
				 * ))) { System.out.println("------------------------------" +
				 * el2.getText()); if
				 * (el2.getText().trim().equalsIgnoreCase("Calling Queue")) {
				 * el2.click(); // Actions actions = new Actions(driver); //
				 * actions.moveToElement(el).build().perform();
				 * wd.waitFor(3000); break; } }
				 */
				/*
				 * wait.until(ExpectedConditions.visibilityOfAllElements(driver.
				 * findElements( By.cssSelector(
				 * "table#dm0m15tbl.dmSubmenu > tbody > tr table > tbody > tr > td:nth-child(2)"
				 * ))));
				 */// wd.waitFor(500);

				// for (WebElement el2 : driver.findElements(By
				// .cssSelector("table#dm0m15tbl.dmSubmenu > tbody > tr table >
				// tbody > tr > td:nth-child(2)"))) {
				// System.out.println("------------------------------" +
				// el2.getText());
				// if (el2.getText().trim().equalsIgnoreCase("Calling Queue")) {
				// actions = new Actions(driver);
				// actions.moveToElement(el).build().perform();
				// wd.waitFor(3000);
				// break;
				// }
				// }
				break;
			}
		}
	}

	public void hoverOverDashBoardInbox(String subMenu) {
		WebDriver driver = wd.getWebdriver();
		for (WebElement el : driver.findElements(
				By.cssSelector("table#dm0m15tbl.dmSubmenu > tbody > tr table > tbody > tr > td:nth-child(2)"))) {
			System.out.println("------------------------------" + el.getText());
			if (el.getText().trim().equalsIgnoreCase(subMenu)) {
				el.click();
				// Actions actions = new Actions(driver);
				// actions.moveToElement(el).build().perform();
				wd.waitFor(3000);
				break;
			}
		}
	}
}
