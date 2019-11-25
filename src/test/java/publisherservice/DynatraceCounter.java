package publisherservice;

import metric.CounterOuterClass.Counter;
import metric.MessageOuterClass.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.continuum.utils.JunoAlertingAPIUtil;

import continuum.cucumber.Utilities;
import io.restassured.response.Response;


public class DynatraceCounter {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean sendDynatraceCounter(){

		try {

		InetAddress localhost = InetAddress.getLocalHost();
		InetAddress address = InetAddress.getByName("127.0.0.1");
		DatagramSocket ds;
		DatagramPacket dp;
		Counter counter;
		Message.Metric metric;
		Message message;

		for(int i=1234; i<=1236; i++) {
		
		counter = Counter.newBuilder()
				.setName("Dynatrace Test for Counter")
				.setDescription("Counter for Dynatrace Test")
				.setValue(i)
				.putProperties("HostIpAddress", localhost.getHostAddress().trim())
				.build();

		metric = Message.Metric.newBuilder()
				.setType("Counter")
				.setValue(counter.toByteString())
				.build();

		message = Message.newBuilder()
				.setNamespace("Dynatrace Test for Counter")
				.setProcessName("Dynatrace Test for Counter")
				.setHostName("Test Machine")
				.setTimestampUnix(System.currentTimeMillis() * 1000000 + 2000000)
				.addAddress("172.28.16.237")
				.putProperties("HostIpAddress", "172.28.16.237")
				.addMetric(metric)
				.build();

		ds = new DatagramSocket();
		dp = new DatagramPacket(message.toByteArray(), message.toByteArray().length);	
		
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
	
	public boolean verifyDynatraceCount() {
		
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Response dynatraceResp = JunoAlertingAPIUtil.getWithAuthorizationNoParameters(Utilities.getMavenProperties("DynatraceGetCounter"));
		
		logger.info(dynatraceResp.getBody().asString());
		
		if(dynatraceResp.getBody().asString().contains("1234")  
				&& dynatraceResp.getBody().asString().contains("1235")  
				&& dynatraceResp.getBody().asString().contains("1236")    
				&& dynatraceResp.getBody().asString().contains("Counter")) {
			logger.info("Meassage Reached till Dynatrace");
			return true;
		}else {
			logger.info("Meassage Not Reached till Dynatrace");
			return false;
		}
		
	}
}
