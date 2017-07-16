package continuum.noc.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.continuum.framework.utils.Log;
import com.continuum.utils.DataUtils;

import continuum.cucumber.DriverFactory;
import continuum.cucumber.Locator;
import continuum.cucumber.WebdriverWrapper;

public class NOCTicketUpdatePage {
	WebdriverWrapper wd = new WebdriverWrapper();


	public Locator nocTktFamily= new Locator("Ticket Family on Noc Portal","[bgcolor='#fafafa'].userRighrAss_ver_bor>tbody>tr:nth-child(1)>td:nth-child(2)","css");
	public Locator nocTktStatus=new Locator("Ticket Status on Noc Portal",".labelBOLD>b","css");
	public Locator nocTktPriority=new Locator("Ticket Priority on Noc Portal","[bgcolor='#fafafa'].userRighrAss_ver_bor>tbody>tr:nth-child(5)>td:nth-child(4)","css");
	public Locator nocTktAssignto=new Locator("Ticket Assignto on Noc Portal",".userRighrAss_ver_bor>tbody>tr:nth-child(4)>td:nth-child(2)","css");
	public Locator nocTktResourse=new Locator("Ticket Resourse on Noc Portal","//b[contains(text(),'Resource')]/..");
	public Locator nocTktDescription=new Locator("Ticket Description on Noc Portal","//td[contains(text(),'Description Detail')]/../../../../../../tr[2]/td");
	public Locator nocTktSubject=new Locator("Ticket Subject on Noc Portal",".//*[@id='tblMain']/tbody/tr[1]/td/table/tbody/tr/td[3]");
	public Locator nocTktMember=new Locator("Ticket Member on Noc Portal","//img[@title='Member Name']/../../td[2]");
	public Locator nocTktSiteName=new Locator("Ticket Site on Noc Portal","//img[@title='Site Name']/../../td[2]");
	public Locator nocTktId=new Locator("Ticket Id on Noc Portal","//td[contains(text(),'Description Detail - Ticket Id')]/span");
	public Locator nocTktNocCompletedDescriptionNote=new Locator("Ticket Description for Noc Completed on Noc Portal","//tr[@id='Task0']/td[2]/textarea");
	public Locator ifreameOnNocUpdatePage=new Locator("ifreame On Noc Update Page","TaskMain","id");
	public Locator ifreameOnNocUpdatePageForNocCompleted=new Locator("ifreame On Noc Update Page For NocCompleted","tasktktNotes","id");
	

	public NOCTicketUpdatePage() {
		wd.waitForAlert(9000);
		WebDriverWait wait = new WebDriverWait(wd.getWebdriver(), 20);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = wd.getWebdriver().switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			System.out.println("No alert");
		}
		Set<String> windowHandles = wd.getWebdriver().getWindowHandles();
		for (String windowHandle : windowHandles) {
			wd.switchToWindow(windowHandle);
			System.out.println("Window Title NOCTicketUpdatePage : " + wd.getDriverTitle());
			System.out.println("Window URL NOCTicketUpdatePage : " + wd.getWebdriver().getCurrentUrl());
			/*
			 * if (wd.getWebdriver().getCurrentUrl().contains("")) { break; }
			 */
		}
	}

	/**
	 * Verify Ticket details
	 *
	 * @throws IOException
	 */
	public void verifyTicketDetailsOnUpdatePage() throws IOException {
		HashMap<String, String> testData = new HashMap();

		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");
	
		
		String siteName = testData.get("SiteCode (siteCode)")+ "("+testData.get("Member")+")";
		String nocTicketSubject = testData.get("NocTCKTSubject").trim()
				.replace("<<Site_Name>>", testData.get("SiteName").trim())
				.replace("<<Resource_Name>>", testData.get("DeviceName (entity)").trim())
				.replace("<<Friendly_Name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim())
				.replace("<<Escalation_category>>",testData.get("ITSPriority").trim())
				.replace("<<Friendly_name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim());
		
		//System.out.println("siteName-loc"+siteName);
	
		//String ticketId=wd.getText(nocTktId);

		
//String nocTicketDiscription = testData.get("CreateTCKTDesc").trim()
//			.replace("<<Site_Name>>", testData.get("SiteName").trim())
//				.replace("<<Resource_Name>>", testData.get("DeviceName (entity)").trim())
//				.replace("<<Friendly_Name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim())
//				.replace("<<Ticket_ID>>", ticketId );
//		
		
		
	
				
		
		
		
		System.out.println("Ticket Gen Statas in Excel=================" + testData.get("GenStatus").trim());

		
		System.out.println("Ticket resource in Excel=================" + testData.get("DeviceName (entity)").trim());
		
		System.out.println("Ticket priority in Excel=================" + testData.get("Priority").trim());
		System.out.println("Ticket assignto in Excel=================" + testData.get("TCKTAction").trim());
		//System.out.println("Ticket Description in Excel=================" + nocTicketDiscription.trim());
		System.out.println("Ticket Family in Excel=================" + testData.get("Family").trim());
		System.out.println("Ticket MemberName in Excel=================" + testData.get("Member"));
		System.out.println("Ticket SiteName in Excel=================" + testData.get("SiteName").trim());
		wd.switchToNewWindow();
	    wd.waitForFrame(ifreameOnNocUpdatePage, 10000);
		wd.switchToFrame(ifreameOnNocUpdatePage);
		
		
		System.out.println("On the Page Desired");
	//	verifyResourceOnTicketUpdate(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"));
		System.out.println();
//		verifyFamilyOnTicketUpdate(testData.get("Family"));
//		verifyMemberOnTicketUpdate(testData.get("Member"));
//		verifySiteOnTicketUpdate(testData.get("SiteName"));
//		verifyStatusOnTicketUpdate(testData.get("GenStatus"));
//		verifyassignToOnTicketUpdate(testData.get("AssignToGrp"));
//		verifypriorityOnTicketUpdate(testData.get("Priority"));
//
//		verifysubjectOnTicketUpdate(testData.get("TCKTSubject"));
//		verifydesciptionOnTicketUpdate(testData.get("CreateTCKTDesc"));
//		verifypriorityOnTicketUpdate(testData.get("Priority"));
		
		//verification(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"), nocTktResourse);
//		verification(testData.get("Family"), nocTktFamily);
//		verification(testData.get("MemberCode"), nocTktMember);
//		verification(testData.get("SiteName"),nocTktSiteName);
//		verification(testData.get("GenStatus"),nocTktStatus);
//		verification(testData.get("AssignToGrp"),nocTktAssignto);
//		verification(testData.get("Priority"),nocTktPriority);
//		verification(nocTicketSubject,nocTktSubject);
		//verification(testData.get("CreateTCKTDesc"),nocTktDescription);
		//verification(testData.get("CloseTCKTDesc"),nocTktNocCompletedDescriptionNote);
	/*	Log.softAssertThat(testData.get("Family").equalsIgnoreCase(wd.getText(nocTktFamily)), 
				"Family verified succefully on Noc TicketUpdate Page", 
				"Family not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("Family") + ", Actual" + wd.getText(nocTktFamily));
		Log.softAssertThat(testData.get("MemberCode").equalsIgnoreCase(wd.getText(nocTktMember)), 
				"MemberCode verified succefully on Noc TicketUpdate Page", 
				"MemberCode not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("MemberCode") + ", Actual" + wd.getText(nocTktMember));
		Log.softAssertThat(testData.get("SiteName").trim().equalsIgnoreCase(wd.getText(nocTktSiteName).trim()), 
				"SiteName verified succefully on Noc TicketUpdate Page", 
				"SiteName not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("SiteName").trim() + ", Actual" + wd.getText(nocTktSiteName).trim());
		Log.softAssertThat(testData.get("GenStatus").equalsIgnoreCase(wd.getText(nocTktStatus)), 
				"Ticket Generation Status verified succefully on Noc TicketUpdate Page", 
				"Ticket Generation Status not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("GenStatus") + ", Actual" + wd.getText(nocTktStatus));
		
		
		Log.softAssertThat(testData.get("AssignToGrp").equalsIgnoreCase(wd.getText(nocTktAssignto)), 
				"Ticket Generation Status verified succefully on Noc TicketUpdate Page", 
				"Ticket Generation Status not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("AssignToGrp") + ", Actual" + wd.getText(nocTktAssignto));
		Log.softAssertThat(testData.get("Priority").equalsIgnoreCase(wd.getText(nocTktPriority)), 
				"Priority verified succefully on Noc TicketUpdate Page", 
				"Priority verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("Priority") + ", Actual" + wd.getText(nocTktPriority));
		
		 
		
		Log.softAssertThat(nocTicketSubject.trim().equalsIgnoreCase(wd.getText(nocTktSubject).trim()), 
				"Subject verified succefully on Noc TicketUpdate Page", 
				"Subject not verified succefully on Noc TicketUpdate Page,Expected : " + nocTicketSubject + ", Actual :" + wd.getText(nocTktSubject));
	*/	
//		Log.softAssertThat(testData.get("CreateTCKTDesc").equalsIgnoreCase(wd.getText(nocTktDescription)), 
//				"Subject verified succefully on Noc TicketUpdate Page", 
//				"Subject verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("CreateTCKTDesc") + ", Actual" + wd.getText(nocTktDescription));
		wd.getWebdriver().switchTo().defaultContent();
		
	}
	
	
	/**
	 * Verify Ticket details for Noc Completed Condition
	 *
	 * @throws IOException
	 */
	public void verifyTicketDetailsOnUpdatePageNocCompletedCondition() throws IOException {
		HashMap<String, String> testData = new HashMap();

		testData.putAll(DataUtils.getTestRow());
		System.out.println(
				"========================================================================================================");
	
		
		String siteName = testData.get("SiteCode (siteCode)")+ "("+testData.get("Member")+")";
		String nocTicketSubject = testData.get("NocTCKTSubject").trim()
				.replace("<<Site_Name>>", testData.get("SiteName").trim())
				.replace("<<Resource_Name>>", testData.get("DeviceName (entity)").trim())
				.replace("<<Friendly_Name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim())
				.replace("<<Escalation_category>>",testData.get("ITSPriority").trim())
				.replace("<<Friendly_name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim());
		
		String closeTicketDiscription = testData.get("CloseTCKTDesc").trim()
				.replace("<<Site_Name>>", testData.get("SiteName").trim());
		System.out.println(closeTicketDiscription);
		
		
		
		//System.out.println("siteName-loc"+siteName);
	
		//String ticketId=wd.getText(nocTktId);

		
//String nocTicketDiscription = testData.get("CreateTCKTDesc").trim()
//			.replace("<<Site_Name>>", testData.get("SiteName").trim())
//				.replace("<<Resource_Name>>", testData.get("DeviceName (entity)").trim())
//				.replace("<<Friendly_Name>>",testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)").trim())
//				.replace("<<Ticket_ID>>", ticketId );
//		
		
		
	
				
		
		
		
		System.out.println("Ticket Gen Statas in Excel=================" + testData.get("GenStatus").trim());

		
		System.out.println("Ticket resource in Excel=================" + testData.get("DeviceName (entity)").trim());
		
		System.out.println("Ticket priority in Excel=================" + testData.get("Priority").trim());
		System.out.println("Ticket assignto in Excel=================" + testData.get("TCKTAction").trim());
		//System.out.println("Ticket Description in Excel=================" + nocTicketDiscription.trim());
		System.out.println("Ticket Family in Excel=================" + testData.get("Family").trim());
		System.out.println("Ticket MemberName in Excel=================" + testData.get("Member"));
		System.out.println("Ticket SiteName in Excel=================" + testData.get("SiteName").trim());
		//wd.switchToNewWindow();
	    wd.waitForFrame(ifreameOnNocUpdatePage, 6000);
		wd.switchToFrame(ifreameOnNocUpdatePage);
		
		System.out.println("On the Page Desired");
	//	verifyResourceOnTicketUpdate(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"));
		System.out.println();
//		verifyFamilyOnTicketUpdate(testData.get("Family"));
//		verifyMemberOnTicketUpdate(testData.get("Member"));
//		verifySiteOnTicketUpdate(testData.get("SiteName"));
//		verifyStatusOnTicketUpdate(testData.get("GenStatus"));
//		verifyassignToOnTicketUpdate(testData.get("AssignToGrp"));
//		verifypriorityOnTicketUpdate(testData.get("Priority"));
//
//		verifysubjectOnTicketUpdate(testData.get("TCKTSubject"));
//		verifydesciptionOnTicketUpdate(testData.get("CreateTCKTDesc"));
//		verifypriorityOnTicketUpdate(testData.get("Priority"));
		
		//verification(testData.get("DeviceFriendlyName (AuvikDeviceFriendlyName)"), nocTktResourse);
//		verification(testData.get("Family"), nocTktFamily);
//		verification(testData.get("MemberCode"), nocTktMember);
//		verification(testData.get("SiteName"),nocTktSiteName);
//		verification(testData.get("CloseTicketStatus"),nocTktStatus);
//		verification(testData.get("AssignToGrp"),nocTktAssignto);
//		verification(testData.get("Priority"),nocTktPriority);
//		verification(nocTicketSubject,nocTktSubject);
//		//verification(testData.get("CreateTCKTDesc"),nocTktDescription);
//		verification(testData.get("CloseTCKTDesc"),nocTktNocCompletedDescriptionNote);
		
		/*Log.softAssertThat(testData.get("Family").equalsIgnoreCase(wd.getText(nocTktFamily)), 
				"Family verified succefully on Noc TicketUpdate Page", 
				"Family not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("Family") + ", Actual" + wd.getText(nocTktFamily));
		Log.softAssertThat(testData.get("MemberCode").equalsIgnoreCase(wd.getText(nocTktMember)), 
				"MemberCode verified succefully on Noc TicketUpdate Page", 
				"MemberCode not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("MemberCode") + ", Actual" + wd.getText(nocTktMember));
		Log.softAssertThat(testData.get("SiteName").trim().equalsIgnoreCase(wd.getText(nocTktSiteName).trim()), 
				"SiteName verified succefully on Noc TicketUpdate Page", 
				"SiteName not verified succefully on Noc TicketUpdate Page,Expected:" + testData.get("SiteName").trim() + ", Actual:" + wd.getText(nocTktSiteName).trim());
		Log.softAssertThat(testData.get("CloseTicketStatus").equalsIgnoreCase(wd.getText(nocTktStatus)), 
				"Ticket Generation Status verified succefully on Noc TicketUpdate Page", 
				"Ticket Generation Status not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("CloseTicketStatus") + ", Actual" + wd.getText(nocTktStatus));
		
		
		Log.softAssertThat(testData.get("AssignToGrp").equalsIgnoreCase(wd.getText(nocTktAssignto)), 
				"Ticket Generation Status verified succefully on Noc TicketUpdate Page", 
				"Ticket Generation Status not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("AssignToGrp") + ", Actual" + wd.getText(nocTktAssignto));
		Log.softAssertThat(testData.get("Priority").equalsIgnoreCase(wd.getText(nocTktPriority)), 
				"Priority verified succefully on Noc TicketUpdate Page", 
				"Priority verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("Priority") + ", Actual" + wd.getText(nocTktPriority));
		
		Log.softAssertThat(nocTicketSubject.trim().equalsIgnoreCase(wd.getText(nocTktSubject).trim()), 
				"Subject verified succefully on Noc TicketUpdate Page", 
				"Subject not verified succefully on Noc TicketUpdate Page,Expected :" + nocTicketSubject + ", Actual :" + wd.getText(nocTktSubject).trim());
*/		
//		Log.softAssertThat(testData.get("CreateTCKTDesc").equalsIgnoreCase(wd.getText(nocTktDescription)), 
//				"Create ticket discription Create ticket discription succefully on Noc TicketUpdate Page", 
//				"Create ticket discription not verified succefully on Noc TicketUpdate Page,Expected : " + testData.get("CreateTCKTDesc") + ", Actual" + wd.getText(nocTktDescription));
//	wd.waitForFrame(ifreameOnNocUpdatePageForNocCompleted, 4000);
//		Log.softAssertThat(closeTicketDiscription.equalsIgnoreCase(wd.getText(nocTktNocCompletedDescriptionNote)), 
//				"Subject verified succefully on Noc TicketUpdate Page", 
//				"Subject verified succefully on Noc TicketUpdate Page,Expected : " + closeTicketDiscription + ", Actual" + wd.getText(nocTktNocCompletedDescriptionNote));
//		
//		wd.getWebdriver().switchTo().defaultContent();
	}




	/**
	 * Verify ticket resource details on Update page
	 *
	 * @param resource
	 *
	 */


//	public boolean verifyResourceOnTicketUpdate(String resource) {
//		
//	//	wd.switchToFrame(ifreameOnNocUpdatePage);
//		//System.out.println("Switched to Frame NOCUPDATE");
//		String resourceOnTicketIUpdate=wd.getText(nocTktResourse);
//		System.out.println(resourceOnTicketIUpdate);
//		return resource.trim().equals(resourceOnTicketIUpdate);
//
//	}
	public String verifyResourceOnTicketUpdate(String resource) {
		
		//	wd.switchToFrame(ifreameOnNocUpdatePage);
			//System.out.println("Switched to Frame NOCUPDATE");
			String resourceOnTicketIUpdate=wd.getText(nocTktResourse);
			
			System.out.println(resourceOnTicketIUpdate);
			return resourceOnTicketIUpdate;

		}
	
	
	
	
	
	/**
	 * Verify ticket family details on Update page
	 *
	 * @param family
	 *
	 */


	public boolean verifyFamilyOnTicketUpdate(String family) {
		
		String familyOnTicketIUpdate=wd.getText(nocTktFamily);
		System.out.println(familyOnTicketIUpdate);
		return family.trim().equals(familyOnTicketIUpdate);

	}
	
	
	/**
	 * Verify ticket member details on Update page
	 *
	 * @param memberName
	 *
	 */


	public boolean verifyMemberOnTicketUpdate(String member) {
	
		String memberOnTicketIUpdate=wd.getText(nocTktMember);
		System.out.println(memberOnTicketIUpdate);
		return member.trim().equals(memberOnTicketIUpdate);

	}
	/**
	 * Verify ticket sitename details on Update page
	 *
	 * @param sitename
	 *
	 */


	public boolean verifySiteOnTicketUpdate(String site) {
	
		String siteOnTicketIUpdate=wd.getText(nocTktSiteName);
		System.out.println(siteOnTicketIUpdate);
		return site.trim().equals(siteOnTicketIUpdate);

	}
	/**
	 * Verify ticket status details on Update page
	 *
	 * @param status
	 *
	 */


	public boolean verifyStatusOnTicketUpdate(String status) {
	
		String statusOnTicketIUpdate=wd.getText(nocTktStatus);
		System.out.println(statusOnTicketIUpdate);
		return status.trim().equals(statusOnTicketIUpdate);

	}

	/**
	 * Verify ticket assignTo details on Update page
	 *
	 * @param assignTo
	 *
	 */


	public boolean verifyassignToOnTicketUpdate(String assignTo) {

		String assignToOnTicketIUpdate=wd.getText(nocTktAssignto);
		System.out.println(assignToOnTicketIUpdate);
		return assignTo.trim().equals(assignToOnTicketIUpdate);

	}

	/**
	 * Verify ticket priority details on Update page
	 *
	 * @param priority
	 *
	 */


	public boolean verifypriorityOnTicketUpdate(String priority) {
	
		String priorityOnTicketIUpdate=wd.getText(nocTktPriority);
		System.out.println(priorityOnTicketIUpdate);
		return priority.trim().equals(priorityOnTicketIUpdate);

	}

	/**
	 * Verify ticket subject details on Update page
	 *
	 * @param subject
	 *
	 */


	public boolean verifysubjectOnTicketUpdate(String subject) {
		//wd.switchToFrame(ifreameOnNocUpdatePage);
		
		String subjectOnTicketIUpdate=wd.getText(nocTktSubject);
		System.out.println(subjectOnTicketIUpdate);
		return subject.trim().equals(subjectOnTicketIUpdate);

	}

	/**
	 * Verify ticket desciption details on Update page
	 *
	 * @param desciption
	 *
	 */


	public boolean verifydesciptionOnTicketUpdate(String desciption) {
		
		String desciptionOnTicketIUpdate=wd.getText(nocTktDescription);
		System.out.println(desciptionOnTicketIUpdate);
		return desciption.trim().equals(desciptionOnTicketIUpdate);

	}
	
//	public void verification(String request,Locator xpath)
//	{
//		String xpathResponse=wd.getText(xpath);
//		System.out.println("Verification request="+request);
//		System.out.println("Verification xpathResponse="+xpathResponse);
//		System.out.println("Verification Result="+request.equals(xpathResponse.trim()));
//	}
	
	/**
	 * Verify ticket Notes desciption details on Update page for Noc Completed Condition
	 *
	 * @param desciption
	 *
	 */


	public boolean verifydesciptionOnTicketUpdateNocCompletedCondition(String nocCompletedNoteDesciption) {
		String nocCompletedDesciptionOnTicketIUpdate=wd.getText(nocTktNocCompletedDescriptionNote);
		System.out.println(nocCompletedDesciptionOnTicketIUpdate);
		return nocCompletedNoteDesciption.trim().equals(nocCompletedDesciptionOnTicketIUpdate);

	} 
	
	

}
