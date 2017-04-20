package continuum.ticket.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.continuum.framework.utils.Log;
import com.continuum.utils.DataUtils;

import continuum.cucumber.DriverFactory;
import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class ITSTicketDetailsPage {
	WebdriverWrapper wd = new WebdriverWrapper();

	
	
	

	public Locator itsTktSubject= new Locator("Ticket Subject on ITS Portal","//label[text()='Subject']/../../td[2]");

	public Locator itsTktDescription= new Locator("Ticket Descriptio on ITS Portal","//label[text()='Description']/../../td[2]");

	public Locator itsTktSiteName= new Locator("Ticket SiteName on ITS Portal","//label[text()='Site']/../../td[2]/span");

	public Locator itsTktResourseName= new Locator("Ticket ResourseName on ITS Portal","//label[text()='Machine Name']/../../td[2]/span");

	public Locator itsTktEscalationCategory= new Locator("Ticket EscalationCategory on ITS Portal","//label[contains(text(),'Priority')]/../../td[2]/select/option[@selected='']");

	public Locator itsTktCreatedBy= new Locator("Ticket Created By on ITS Portal","//label[text()='Created By']/../../td[2]");

	public Locator itsTktStatus= new Locator("Ticket Status on ITS Portal","//label[text()='Status']/../../td[2]");












	public ITSTicketDetailsPage() {
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			System.out.println("Window Title : " + wd.getDriverTitle());
			System.out.println("Window URL : " + wd.getWebdriver().getCurrentUrl());
			if (wd.getWebdriver().getCurrentUrl().contains("Noc_TaskUpdate_ui")) {
				break;
			}
		}
		wd.waitForPageLoad(60);
	}
	
	


	/**
	 * Verify Ticket details
	 *
	 * @throws IOException
	 */
	public void verifyTicketDetails()throws IOException {


		HashMap<String, String> testData = new HashMap();
		testData.putAll(DataUtils.getTestRow());
		
		System.out.println(" ITC ============================="+ getTicketDetails("Site"));
		System.out.println(" ITC ============================="+ getTicketDetails("Machine Name"));
		System.out.println(" ITC ============================="+ getTicketDetails("Ticket Type"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Subject"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Description"));
		System.out.println(" ITC ============================="+ getTicketDetails("Created By"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Priority"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Status"));
		
	
		//Log.softAssertThat(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").equalsIgnoreCase(getTicketDetails("Machine Name")),"Friendly name scccefully verified on ticket report page","Friendly name not verified on ticket report page,  Expected : " + testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")+ ",  Actual : " + getTicketDetails("Machine Name"));
		//Log.softAssertThat(getTicketDetails("Machine Name").contains(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")), "Friendly name scccefully verified on ticket report page","Friendly name not verified on ticket report page,  Expected : " + testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")+ ",  Actual : " + getTicketDetails("Machine Name"), DriverFactory.getDriver());
		Log.softAssertThat(testData.get("SiteName").equalsIgnoreCase(getTicketDetails("Site").trim()),"Site name successfully verified on ticket Details page","Site name not verified on ticket details page, Expected : " + testData.get("SiteName") + ", Actual : " + getTicketDetails("Site").trim());
		//Log.softAssertThat(getTicketDetails("Machine Name").contains(testData.get("DeviceName (entity)")),"Machine name successfully verified on ticket Details page","Machine name not verified on ticket details page, Expected : " + testData.get("DeviceName (entity)")+ ", Actual : " + getTicketDetails("Machine Name"));
		
		//Log.softAssertThat(testData.get("TCKTAction").contains(getTicketDetails("Ticket Type")),"Ticket Type scccefully verified on ticket report page","Ticket Type not verified on ticket report page");
		
		
		//Log.softAssertThat(getTicketDetails("Ticket Type").contains(testData.get("TCKTAction")),"Ticket Type scccefully verified on ticket report page","Ticket Type not verified on ticket report page");
		
		
		
		
		//Log.softAssertThat(testData.get("TCKTSubject").equalsIgnoreCase(getTicketDetails("Description")),"Ticket description sccefully verified on ticket report page","Ticket description is not verified on ticket report page");
		
		//Log.softAssertThat(testData.get("GenStatus").equalsIgnoreCase(getTicketDetails("Status")),"Status  scccefully verified on ticket report page","Status not verified on ticket report page,  Expected : " + testData.get("CloseTicketStatus")+ ", Actual : " + getTicketDetails("Status"));
		//Log.softAssertThat(testData.get("Priority").equalsIgnoreCase(getTicketDetails("Priority")),"Priority  scccefully verified on ticket report page","Priority not verified on ticket report page,  Expected : " + testData.get("Priority")+ ", Actual : " + getTicketDetails("Priority"));
		
		/*
		 * HashMap<String, String> testData = new HashMap<>();
		 * DataUtils.setTestRow(
		 * "C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik",
		 * "AUVIKSC001"); testData.putAll(DataUtils.getTestRow());
		 * System.out.println(
		 * "========================================================================================================"
		 * );
		 */

//		HashMap<String, String> testData = new HashMap<>();
//		testData.putAll(DataUtils.getTestRow());
//
//
//		itsTktSiteNameVerification(testData.get("SiteName"));
//
//		itsTktSubjectVerification(testData.get("TCKTSubject"));
//		itsTktDescriptionVerification(testData.get("CreateTCKTDesc"));
//		itsTktResourseNameVerification(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName"));
//		itsTktEscalationCategoryVerification(testData.get("Priority"));
//		itsTktStatusVerification(testData.get("GenStatus"));
		
	
	

	}
	/**
	 * Verify Noc Completed Ticket details
	 *
	 * @throws IOException
	 */
	public void verifyNocCompletedTicketDetails()throws IOException {


		HashMap<String, String> testData = new HashMap();
		testData.putAll(DataUtils.getTestRow());
		
		System.out.println(" ITC ============================="+ getTicketDetails("Site"));
		System.out.println(" ITC ============================="+ getTicketDetails("Machine Name"));
		System.out.println(" ITC ============================="+ getTicketDetails("Ticket Type"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Subject"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Description"));
		System.out.println(" ITC ============================="+ getTicketDetails("Created By"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Priority"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Status"));
		//System.out.println(" ITC ============================="+ getTicketDetails("Comments"));
	
	
		//Log.softAssertThat(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").equalsIgnoreCase(getTicketDetails("Machine Name")),"Friendly name scccefully verified on ticket report page","Friendly name not verified on ticket report page,  Expected : " + testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")+ ",  Actual : " + getTicketDetails("Machine Name"));
		//Log.softAssertThat(getTicketDetails("Machine Name").contains(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")), "Friendly name scccefully verified on ticket report page","Friendly name not verified on ticket report page,  Expected : " + testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)")+ ",  Actual : " + getTicketDetails("Machine Name"), DriverFactory.getDriver());
		Log.softAssertThat(testData.get("SiteName").equalsIgnoreCase(getTicketDetails("Site")),"Site name successfully verified on ticket Details page","Site name not verified on ticket details page, Expected : " + testData.get("SiteName") + ", Actual : " + getTicketDetails("Site"));
		Log.softAssertThat(getTicketDetails("Machine Name").contains(testData.get("DeviceName (entity)")),"Machine name successfully verified on ticket Details page","Machine name not verified on ticket details page, Expected : " + testData.get("DeviceName (entity)")+ ", Actual : " + getTicketDetails("Machine Name"));
		
		//Log.softAssertThat(testData.get("TCKTAction").contains(getTicketDetails("Ticket Type")),"Ticket Type scccefully verified on ticket report page","Ticket Type not verified on ticket report page");
		
		
		Log.softAssertThat(getTicketDetails("Ticket Type").contains(testData.get("TCKTAction")),"Ticket Type scccefully verified on ticket report page","Ticket Type not verified on ticket report page");
		
		
		
		
		//Log.softAssertThat(testData.get("TCKTSubject").equalsIgnoreCase(getTicketDetails("Description")),"Ticket description sccefully verified on ticket report page","Ticket description is not verified on ticket report page");
		
		//Log.softAssertThat(testData.get("CloseTicketStatus").equalsIgnoreCase(getTicketDetails("Status")),"Status  scccefully verified on ticket report page","Status not verified on ticket report page,  Expected : " + testData.get("CloseTicketStatus")+ ", Actual : " + getTicketDetails("Status"), DriverFactory.getDriver());
		//Log.softAssertThat(testData.get("Priority").equalsIgnoreCase(getTicketDetails("Priority")),"Priority  scccessfully verified on ticket report page","Priority not verified on ticket report page,  Expected : " + testData.get("Priority")+ ", Actual : " + getTicketDetails("Priority"), DriverFactory.getDriver());
		//Log.softAssertThat(getTicketDetails("Comments").contains(testData.get("CloseTCKTDesc")), "Noc Completed Ticket comment successfully verified on ticket report page", "Noc Completed Ticket comment not verified on ticket report page, Expected : " + testData.get("CloseTCKTDesc")+ ", Actual : " + getTicketDetails("Comments"));
	
	}
	
	/**
	 * Verify Ticket details for Noc Completed
	 *
	 * @throws IOException
	 */
	public void verifyTicketDetailsNocCompleted()throws IOException {
		
		
		
		




		HashMap<String, String> testData = new HashMap();
		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");


		itsTktSiteNameVerification(testData.get("SiteName"));

		itsTktSubjectVerification(testData.get("TCKTSubject"));
		itsTktDescriptionVerification(testData.get("CreateTCKTDesc"));
		itsTktResourseNameVerification(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName"));
		itsTktEscalationCategoryVerification(testData.get("Priority"));
		itsTktStatusVerification(testData.get("CloseTicketStatus"));
		
	
	}






	public  boolean itsTktSiteNameVerification(String siteName)  {
		String SiteNameValue =wd.getText(itsTktSiteName);
		System.out.println(SiteNameValue);
		return siteName.trim().equals(SiteNameValue);
	}

	public  boolean itsTktSubjectVerification(String subject)  {

		String SubjectValue =wd.getText(itsTktSubject);
		System.out.println(SubjectValue);

		return subject.trim().equals(SubjectValue);


	}

	public  boolean itsTktDescriptionVerification(String description)  {


		String TktDescriptionValue =wd.getText(itsTktDescription);

		System.out.println(TktDescriptionValue);
		return description.trim().equals(TktDescriptionValue);

	}


	public  boolean itsTktResourseNameVerification(String resoursename)  {


		String TktResourseNameValue =wd.getText(itsTktResourseName);
		System.out.println(TktResourseNameValue);

		return resoursename.trim().equals(TktResourseNameValue);


	}

	public  boolean itsTktEscalationCategoryVerification(String escalationcategory)  {


		String TktEscalationCategory =wd.getText(itsTktEscalationCategory);
		System.out.println(TktEscalationCategory);
		return escalationcategory.trim().equals(TktEscalationCategory);

	}

	public  boolean itsTktCreatedByVerification(String createdby)  {


		String TktCreatedBy =wd.getText(itsTktCreatedBy);
		System.out.println(TktCreatedBy);
		return false;


	}

	public  boolean itsTktStatusVerification(String genStatus)  {


		String TktStatus =wd.getText(itsTktStatus);
		System.out.println(TktStatus);
		return genStatus.trim().equals(TktStatus);

		


	}
	


	public static void main(String[] args) throws IOException{



		HashMap<String, String> testData = new HashMap();
		DataUtils.setTestRow("C:\\Sachin\\Auvik\\StackSwitchPortStatusChange.xls", "Auvik", "AUVIKSC001");
		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");


		System.out.println(testData.get("Condition name"));


		System.out.println(testData.get("Condition Id"));
		System.out.println(testData.get("RMM Service Plan"));

		System.out.println(testData.get("SiteName"));
	}


	public String getTicketDetails(String attribute){
		String attVal = null;
		for(WebElement row : wd.getWebdriver().findElements(By.cssSelector("#TaskManagement  > table > tbody > tr > td:nth-child(1):not(.main_header)"))){
			if(attribute.equalsIgnoreCase("Subject")){
			if(row.getText().trim().equalsIgnoreCase("Subject")){
				attVal = row.findElement(By.xpath("..")).findElement(By.cssSelector("textarea")).getAttribute("value");
				break;
			}
			}
			if(row.getText().trim().equalsIgnoreCase(attribute)){
				attVal = row.findElement(By.xpath("..")).findElement(By.cssSelector("td:nth-child(2)")).getText();
				break;
			}
		 if(attribute.equalsIgnoreCase("Status")){
			       attVal = wd.getWebdriver().findElement(By.xpath("//Select[@id='cbostatus']/option[@selected]")).getText();
			      System.out.println(attVal);
			       break;
			       
			    }
			 if(attribute.equalsIgnoreCase("Priority")){
		          attVal = wd.getWebdriver().findElement(By.xpath("//Select[@id='cboPriority']/option[@selected]")).getText();
		          System.out.println(attVal);
		          break;
		       }
		

		
			 if(attribute.equalsIgnoreCase("Subject")){
		          attVal = wd.getWebdriver().findElement(By.xpath("//label[contains(text(),'Subject')]/../../td/textarea")).getText();
		          break;
		       }
			 if(attribute.equalsIgnoreCase("Comments")){
		          attVal = wd.getWebdriver().findElement(By.xpath("//label[text()='"+attribute+"']/../../../tr[2]/td[4]/div[1]/div")).getText();
		          break;
		       }
			 
			 
			 }
		if (!(attVal == null)){
			attVal.trim();
		}
		return attVal;
		
	}
}



