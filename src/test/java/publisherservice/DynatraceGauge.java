package publisherservice;

import metric.GaugeOuterClass.Gauge;
import metric.MessageOuterClass.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.continuum.utils.JunoAlertingAPIUtil;

import continuum.cucumber.Utilities;
import io.restassured.response.Response;

public class DynatraceGauge {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean sendDynatraceGauge(){

		try {

		InetAddress localhost = InetAddress.getLocalHost();
		InetAddress address = InetAddress.getByName("127.0.0.1");
		DatagramSocket ds;
		DatagramPacket dp;
		Gauge gauge;
		Message.Metric metric;
		Message message;

		for(int i=121; i<=123; i++) {

		gauge = Gauge.newBuilder()
				.setName("Dynatrace Test for Gauge")
				.setDescription("Gauge for Dynatrace Test")
				.setValue(i)
				.setUnit("Alerts")
				.putProperties("HostIpAddress", localhost.getHostAddress().trim())
				.build();

		metric = Message.Metric.newBuilder()
				.setType("Gauge")
				.setValue(gauge.toByteString())
				.build();

		message = Message.newBuilder()
				.setNamespace("Dynatrace Test for Gauge")
				.setProcessName("Dynatrace Test for Gauge")
				.setHostName("Test Machine")
				.setTimestampUnix(System.currentTimeMillis() * 1000000 + 2000000)
				.addAddress("172.28.16.237")
				.putProperties("HostIpAddress", "172.28.16.237")
				.addMetric(metric)
				.build();

		ds = new DatagramSocket();
		dp = new DatagramPacket(message.toByteArray(), message.toByteArray().length);


		address = InetAddress.getByName("127.0.0.1");

		ds.connect(address, 7000);
		
		logger.info("isConnected : " + ds.isConnected());
		logger.info("IsBound : " + ds.isBound());
		
		ds.send(dp);

		try {
			Thread.sleep(65000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ds.disconnect();
		ds.close();
		
		}
		
		return true;
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
public boolean verifyDynatraceGauge() {
		
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Response dynatraceResp = JunoAlertingAPIUtil.getWithAuthorizationNoParameters(Utilities.getMavenProperties("DynatraceGetGauge"));
		
		logger.info(dynatraceResp.getBody().asString());
		
		if(dynatraceResp.getBody().asString().contains("121")
				&& dynatraceResp.getBody().asString().contains("122")  
				&& dynatraceResp.getBody().asString().contains("123")
				&& dynatraceResp.getBody().asString().contains("Gauge")) {
			logger.info("Meassage Reached till Dynatrace");
			return true;
		}else {
			logger.info("Meassage Not Reached till Dynatrace");
			return false;
		}
		
	}

}
