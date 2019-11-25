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

import continuum.cucumber.Utilities;
import continuum.cucumber.webservices.RestAssuredUtility;
import io.restassured.response.Response;

public class PrometheusGauge {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean sendPrometheusGauge(){

		try {

		InetAddress localhost = InetAddress.getLocalHost();

		Gauge gauge = Gauge.newBuilder()
				.setName("Prometheus Test for Gauge")
				.setDescription("Gauge for Prometheus Test")
				.setValue(4321)
				.setUnit("Alerts")
				.putProperties("HostIpAddress", localhost.getHostAddress().trim())
				.build();

		Message.Metric metric = Message.Metric.newBuilder()
				.setType("Gauge")
				.setValue(gauge.toByteString())
				.build();

		Message message = Message.newBuilder()
				.setNamespace("Prometheus Test for Gauge")
				.setProcessName("Prometheus Test for Gauge")
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
	
	public boolean verifyPromrtheusGauge() {
		
		Response prometheusResp = RestAssuredUtility.getWithNoParameters(Utilities.getMavenProperties("PrometheusGet"));
		
		logger.info(prometheusResp.getBody().asString());
		
		if(prometheusResp.getBody().asString().contains("4321") && prometheusResp.getBody().asString().contains("Gauge")) {
			logger.info("Meassage Reached till Prometheus");
			return true;
		}else {
			logger.info("Meassage Not Reached till Prometheus");
			return false;
		}
		
	}

}
