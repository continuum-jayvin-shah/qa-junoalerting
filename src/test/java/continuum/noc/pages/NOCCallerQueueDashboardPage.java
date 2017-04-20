package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.continuum.utils.DataUtils;

import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCCallerQueueDashboardPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	public Locator expand = new Locator("Expand","//div[contains(text(),'Expand')]");

	public Locator siteName = new Locator("Site","//th[contains(text(),'Site')]/../../tr[2]/td[1]/a");

	public Locator deviceName = new Locator("Device","//th[contains(text(),'Site')]/../../tr[2]/td[2]/a");

	public Locator categoryName = new Locator("Category","//th[contains(text(),'Site')]/../../tr[2]/td[4]/a");

	public Locator callDescription = new Locator("Call Description","//th[contains(text(),'Site')]/../../tr[3]/td/table/tbody/tr[2]/td");




	public NOCCallerQueueDashboardPage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			if (wd.getWebdriver().getCurrentUrl().contains("frmTaskReport")) {
				break;
			}
		}
	}

	/**
	 * Verify Ticket details Report Page
	 *
	 * @throws IOException
	 */

	public void verifyCallingQueueDetailsOnCallerQueueDashboardPage()throws IOException {




		HashMap<String, String> testData = new HashMap();
		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");


		verifySiteNameCallingQueue(testData.get("SiteName"));

		verifyDeviceCallingQueue(testData.get("DeviceName (entity)"));

		verifyCategoryCallingQueue(testData.get("Category"));

		verifycallDescriptionCallingQueue(testData.get("CALLDesc"));





		System.out.println(testData.get("Condition name"));

		System.out.println(testData.get("SiteName"));


	}


	/**
	 * Verify site name for calling Queue
	 *
	 * @param sitename
	 *
	 */


	public boolean verifySiteNameCallingQueue(String site) {


		String siteForCallingQueue =wd.getText(siteName);
		System.out.println(siteForCallingQueue);
		return site.trim().equals(siteForCallingQueue);

	}


	/**
	 * Verify device for calling Queue
	 *
	 * @param device
	 *
	 */


	public boolean verifyDeviceCallingQueue(String device) {


		String deviceForCallingQueue =wd.getText(deviceName);
		System.out.println(deviceForCallingQueue);
		return device.trim().equals(deviceForCallingQueue);

	}


	/**
	 * Verify category for calling Queue
	 *
	 * @param category
	 *
	 */


	public boolean verifyCategoryCallingQueue(String category) {


		String verifyCategoryCallingQueue =wd.getText(categoryName);
		System.out.println(verifyCategoryCallingQueue);
		return category.trim().equals(verifyCategoryCallingQueue);

	}


	/**
	 * Verify call description for calling Queue
	 *
	 * @param call description
	 *
	 */


	public boolean verifycallDescriptionCallingQueue(String calldes) {


		String verifycallDescriptionCallingQueue =wd.getText(callDescription);
		System.out.println(verifycallDescriptionCallingQueue);
		return calldes.trim().equals(verifycallDescriptionCallingQueue);

	}





}
