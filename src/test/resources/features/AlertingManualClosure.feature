Feature: Juno Alerting Manual Closure Test

@Functional @BVT
Scenario Outline: Alerting Manual Closure Test from ITSM Kafka Topic - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
#When I trigger CREATE Incident API request on ITSM MS
#Then I verify Duplicate Alert in API response from ITSM MS
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response from ITSM MS for UPDATE Request
Then I verify If alert is Created in AlertingMS
Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM
Then I should verify ITSM payload data as expected
Then I verify If alert is Deleted in AlertingMS
#Then I trigger DELETE API request on ITSM MS
#Then I verify API response "404" from Alert MS for "DELETE" Request
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response "404" from Alert MS for "UPDATE" Request
Examples:
|TestCaseRow|KafkaMessageType|
|UnderResearchWindowsManualClosure|AlertID|
|FilterValueManualClosure|AlertID|
|Alerting 1.0 ManualClosure|AlertID|
|SiteLevelManualClosure|AlertID|

@Functional @BVT
Scenario Outline: Alerting Manual Closure Test for Partner/Client Level Alerts from ITSM Kafka Topic - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
#When I trigger CREATE Incident API request on ITSM MS
#Then I verify Duplicate Alert in API response from ITSM MS
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response from ITSM MS for UPDATE Request
Then I verify If alert is Created in AlertingMS
Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM
Then I should verify ITSM payload data as expected
#Then I trigger DELETE API request on ITSM MS
#Then I verify API response "404" from Alert MS for "DELETE" Request
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response "404" from Alert MS for "UPDATE" Request
Examples:
|TestCaseRow|KafkaMessageType|
|ClientLevelManualClosure|AlertID|
|PartnerLevelManualClosure|AlertID|