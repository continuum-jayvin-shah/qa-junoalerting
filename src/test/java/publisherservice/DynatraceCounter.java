package publisherservice;

import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import metric.CounterOuterClass.Counter;

import metric.EventOuterClass.Event;

import metric.MessageOuterClass.Message;

public class DynatraceCounter {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public static void main(String[] args) throws IOException {

		InetAddress localhost = InetAddress.getLocalHost();

		Counter counter = Counter.newBuilder()

				.setName("Dynatrace Test for Counter")

				.setDescription("Counter for Dynatrace Test")

				.setValue(100)

				.putProperties("HostIpAddress", localhost.getHostAddress().trim())

				.build();

		Message.Metric metric = Message.Metric.newBuilder()

				.setType("Counter")

				.setValue(counter.toByteString())

				.build();

		Message message = Message.newBuilder()

				.setNamespace("Dynatrace Test for Counter")

				.setProcessName("Dynatrace Test for Counter")

				.setHostName("Test Machine")

				.setTimestampUnix(System.currentTimeMillis() * 1000000)

				.addAddress("172.28.16.237")

				.putProperties("HostIpAddress", "172.28.16.237")

				.addMetric(metric)

				.build();

		DatagramSocket ds = new DatagramSocket();

		DatagramPacket dp = new DatagramPacket(message.toByteArray(), message.toByteArray().length);

		// connect() method
		
		byte[] ip = {(byte) 172,28,16,(byte) 237};
		
		InetAddress address = InetAddress.getByAddress(ip);
		
		System.out.println(address);
		
		ds.connect(address, 7000);

		System.out.println("isConnected : " + ds.isConnected());

		System.out.println("IsBound : " + ds.isBound());

		ds.send(dp);

		ds.disconnect();

		ds.close();

	}

}
