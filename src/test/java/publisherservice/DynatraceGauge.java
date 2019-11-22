package publisherservice;

import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import metric.CounterOuterClass.Counter;

import metric.EventOuterClass.Event;

import metric.GaugeOuterClass.Gauge;

import metric.MessageOuterClass.Message;

public class DynatraceGauge {

	public static void main(String[] args) throws IOException {

		InetAddress localhost = InetAddress.getLocalHost();

		Gauge gauge = Gauge.newBuilder()

				.setName("Dynatrace Test for Gauge")

				.setDescription("Gauge for Dynatrace Test")

				.setValue(100)

				.setUnit("Alerts")

				.putProperties("HostIpAddress", localhost.getHostAddress().trim())

				.build();

		Message.Metric metric = Message.Metric.newBuilder()

				.setType("Gauge")

				.setValue(gauge.toByteString())

				.build();

		Message message = Message.newBuilder()

				.setNamespace("Dynatrace Test for Gauge")

				.setProcessName("Dynatrace Test for Gauge")

				.setHostName("Test Machine")

				.setTimestampUnix(System.currentTimeMillis() * 1000000)

				.addAddress("192.168.56.101")

				.putProperties("HostIpAddress", "192.168.56.101")

				.addMetric(metric)

				.build();

		DatagramSocket ds = new DatagramSocket();

		DatagramPacket dp = new DatagramPacket(message.toByteArray(), message.toByteArray().length);

		// connect() method

		InetAddress address = InetAddress.getByName("127.0.0.1");

		ds.connect(address, 7000);

		System.out.println("isConnected : " + ds.isConnected());

		System.out.println("IsBound : " + ds.isBound());

		ds.send(dp);

		ds.disconnect();

		ds.close();

	}

}
