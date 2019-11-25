package publisherservice;

import continuum.cucumber.Utilities;
import continuum.cucumber.webservices.RestAssuredUtility;
import io.restassured.response.Response;
import metric.CounterOuterClass.Counter;
import metric.MessageOuterClass.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PrometheusCounter {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public boolean sendPrometheusCount() throws IOException {

		InetAddress localhost = InetAddress.getLocalHost();

		Counter counter = Counter.newBuilder()
				.setName("Prometheus Test for Counter")
				.setDescription("Counter for Prometheus Test")
				.setValue(100)
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
		
		System.out.println("isConnected : " + ds.isConnected());
		System.out.println("IsBound : " + ds.isBound());

		ds.send(dp);
		ds.disconnect();
		ds.close();
		
		return true;

	}
	
	public boolean verifyPromrtheusCount() {
		
		Response prometheusResp = RestAssuredUtility.getWithNoParameters(Utilities.getMavenProperties("PrometheusGet"));
		
		if(prometheusResp.getBody().asString().contains("Count")) {
			logger.info("Meassage Reached till Prometheus");
			return true;
		}else {
			logger.info("Meassage Not Reached till Prometheus");
			return false;
		}
		
	}

}
