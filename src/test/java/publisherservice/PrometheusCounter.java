package publisherservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import continuum.cucumber.Utilities;
import continuum.cucumber.webservices.RestAssuredUtility;
import io.restassured.response.Response;
import metric.CounterOuterClass.Counter;
import metric.MessageOuterClass.Message;

public class PrometheusCounter {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean sendPrometheusCount(){

		try {
			InetAddress localhost = InetAddress.getLocalHost();

			Counter counter = Counter.newBuilder()
					.setName("Prometheus Test for Counter")
					.setDescription("Counter for Prometheus Test")
					.setValue(1234)
					.putProperties("HostIpAddress", localhost.getHostAddress().trim())
					.build();

			Message.Metric metric = Message.Metric.newBuilder()
					.setType("Counter")
					.setValue(counter.toByteString())
					.build();

			Message message = Message.newBuilder()
					.setNamespace("Prometheus Test for Counter")
					.setProcessName("Prometheus Test for Counter")
					.setHostName("Test Machine")
					.setTimestampUnix(System.currentTimeMillis() * 1000000)
					.addAddress(localhost.getHostAddress().trim())
					.putProperties("HostIpAddress", localhost.getHostAddress().trim())
					.addMetric(metric)
					.build();

			DatagramSocket ds = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(message.toByteArray(), message.toByteArray().length);

			InetAddress address = InetAddress.getByName("127.0.0.1");
			ds.connect(address, 7000);
			
			logger.info("isConnected : " + ds.isConnected());
			logger.info("IsBound : " + ds.isBound());

			ds.send(dp);
			
			ds.disconnect();
			ds.close();
			
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
	
	public boolean verifyPromrtheusCount() {
		
		Response prometheusResp = RestAssuredUtility.getWithNoParameters(Utilities.getMavenProperties("PrometheusGet"));
		
		logger.info(prometheusResp.getBody().asString());
		
		if(prometheusResp.getBody().asString().contains("1234")  && prometheusResp.getBody().asString().contains("Counter")) {

			logger.info("Meassage Reached till Prometheus");
			return true;
		}else {
			logger.info("Meassage Not Reached till Prometheus");
			return false;
		}
		
	}

}
