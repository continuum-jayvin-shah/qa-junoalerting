Feature: Auto Process Functionalities

@Publisher @Prometheus
Scenario: Verify that publisher service is able to send data to Prometheus for Counter

Given Any Service is Sending Data to Publisher Service for Prometheus Counter
Then I verify Get API for Prometheus Counter Monitoring

@Publisher @Prometheus
Scenario: Verify that publisher service is able to send data to Prometheus for Gauge

Given Any Service is Sending Data to Publisher Service for Prometheus Gauge
Then I verify Get API for Prometheus Gauge Monitoring

@Publisher @Dynatrace
Scenario: Verify that publisher service is able to send data to Dynatrace for Counter

Given Any Service is Sending Data to Publisher Service for Dynatrace Counter
Then I verify Get API for Dynatrace Counter Monitoring

@Publisher @Dynatrace
Scenario: Verify that publisher service is able to send data to Dynatrace for Gauge

Given Any Service is Sending Data to Publisher Service for Dynatrace Gauge
Then I verify Get API for Dynatrace Gauge Monitoring