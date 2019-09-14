Feature: Auto Process Functionalities

@Functional
Scenario Outline: Alerting Manual Closure Test from ITSM Kafka Topic

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I verify If alert is Created in AlertingMS
Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM
Then I verify If alert is Deleted in AlertingMS


Examples:
|TestCaseRow|KafkaMessageType|
|UnderResearchWindows|AlertID|
|FilterValue2|AlertID|
|ALerting 1.0|AlertID|
#|SiteLevel|AlertID|
#|ClientLevel|AlertID|
#|PartnerLevel|AlertID|