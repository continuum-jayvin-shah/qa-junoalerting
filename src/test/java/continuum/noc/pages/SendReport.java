package continuum.noc.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;

import continuum.cucumber.Utilities;
import continuum.cucumber.reporting.Artifactory;

public class SendReport {
	
	/**
	 * @param userName
	 * @param password
	 * @param reciever
	 * @param subject
	 * @param message
	 * @param report
	 *            sending email
	 */
	public static void sendEmailWithAttachment(final String userName, final String password, String reciever, String subject,
			String message, File report) {
		try {
			// sets SMTP server properties
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", Utilities.getMavenProperties("emailHost"));
			properties.setProperty("mail.smtp.port", Utilities.getMavenProperties("emailPort"));
			properties.setProperty("mail.smtp.auth", "true");

			properties.setProperty("mail.smtp.starttls.enable", "true");

			/*Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});*/
			
			Session session = Session.getDefaultInstance(properties,  
				    new javax.mail.Authenticator() {  
				     protected PasswordAuthentication getPasswordAuthentication() {  
				      return new PasswordAuthentication(userName,password);  
				     }  
				      });

			// creates a new e-mail message
			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(userName));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciever));

			msg.setSubject(subject);
			msg.setSentDate(new Date());

			addReportBody(msg, report);
			
			
			//New Code Added in reporting
			String absolutePath = new File("").getAbsolutePath();
			String [] attachFiles={absolutePath+"\\target\\surefire-reports\\Cucumber-Report\\Juno-Alerting-AutomationReport.html"};
			
			MimeBodyPart messageBodyPart =   new MimeBodyPart();

		    Multipart multipart = new MimeMultipart();
		    
		    multipart.addBodyPart(messageBodyPart);

		    // Part two is attachment
		    messageBodyPart = new MimeBodyPart();
		
		    // adds attachments
	        if (attachFiles != null && attachFiles.length > 0) {
	            for (String filePath : attachFiles) {
	                MimeBodyPart attachPart = new MimeBodyPart();
	 
	                try {
	                    attachPart.attachFile(filePath);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	 
	                multipart.addBodyPart(attachPart);
	            }
	        }
	        
	        msg.setContent(multipart);
			
			
			Transport.send(msg);
			System.out.println("********Sending report mail**********");

		} catch (MessagingException e) {
			System.out.println("****************Unable to Send Email : " + e.getMessage());
		}
	}

	public static void addReportBody(Message msg, File report) throws MessagingException {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(new FileInputStream(report), writer);
			msg.setContent(writer.toString(), "text/html");
		} catch (IOException e) {
			System.out.println("Not able to retrive cucumber report file");
			e.printStackTrace();
		}
	}

	public static void sendReportWithMail(String folderName) {
		String absolutePath = new File("").getAbsolutePath();
		if (Utilities.getMavenProperties("reportMail").equalsIgnoreCase("true")) {
			String sender = Utilities.getMavenProperties("reportUser");
			JSONObject buildNumberInfo = Artifactory.getLatestBuildNumberOfRespository();
			String subject;
			if (buildNumberInfo == null || buildNumberInfo.isEmpty() || buildNumberInfo.size() < 0) {
				subject = "Automation Report for " + Utilities.getMavenProperties("ProjectName");
			} else {
				subject = "Automation Report for " + Utilities.getMavenProperties("ProjectName") + " on "
						+ Artifactory.formTheBuildVersionsForReporting(Artifactory.getLatestBuildNumberOfRespository());
			}

			String message = "Automation Report for " + Utilities.getMavenProperties("ProjectName");
			if (Utilities.getMavenProperties("ProjectName").equalsIgnoreCase("Platform-Alerting")) {
				message = message
						+ "\n For more details, Visit:  http://qedashboard.continuum.net/qemetrics/status.php";
			}

			String password = Utilities.getMavenProperties("reportPassword");
			File cucumberReport = new File(
					absolutePath + "\\" + folderName + "\\" + "cucumber-results-feature-overview.html");
			String reciever = Utilities.getMavenProperties("reportReciever");
			sendEmailWithAttachment(sender, password, reciever, subject, message, cucumberReport);
		}
	}

}