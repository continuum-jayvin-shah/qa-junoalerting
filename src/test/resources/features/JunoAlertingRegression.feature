Feature: Auto Process Functionalities

#@Regression
Scenario Outline: Alerting API Test for Under Research Windows/SQL Condition 

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM

Examples:
|TestCaseRow|
|UnderResearchWindows|
|UnderResearchSQL|

#@Regression
Scenario Outline: Alerting API Test for JAS Condition 

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I verify If alert is Created in JAS
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I verify If alert is Deleted in JAS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator

Examples:
|TestCaseRow|
#|DM 2.0|
#|BDR|
|Security|

#@Regression
Scenario Outline: Alerting API Test for Alerting 1.0 Condition 

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM

Examples:
|TestCaseRow|
|ALerting 1.0|

#@Regression
Scenario Outline: Alerting API Test for Alerting 1.0 Condition with LegacyRegID

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify New Alert created for New Request
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM

Examples:
|TestCaseRow1|TestCaseRow2|
|Alerting 1.0 with LegacyAlertID|Alerting 1.0 with New LegacyAlertID|

#@Regression
Scenario Outline: Alerting API Test for Site/Partner/Client Level Alert 

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM

Examples:
|TestCaseRow|
|SiteLevel|
|ClientLevel|
|PartnerLevel|

@Regression
Scenario Outline: Alerting API Test for Filter Value Functionality

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify New Alert created for New Request
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|
|FilterValue1|FilterValue2|

#@Regression
Scenario Outline: Alerting API Test for Snooze Functionality

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "60" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow|
|Snooze|

#@Regression
Scenario Outline: Alerting API Test for Remeditaion/FetchMoreData Functionality

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator
Then I verify FetchMore and Remediate URL in ITSM Request

Examples:
|TestCaseRow|
|RemediationFetchMoreTrue|
#|RemediationFetchMoreFalse|

#@Regression
Scenario Outline: Alerting API Test for Suspension Functionality

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response for Suspended Condition from Alert MS

Examples:
|TestCaseRow|
|Suspension|

#@Regression
Scenario Outline: Alerting API Test for Consolidation Functionality with 2 Child and 1 Parent

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
Then I verify API response from Alert MS
Then I trigger DELETE API request for Child Alert on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|TestCaseRow3|
|Child1|Child2|Parent|

#@Regression
Scenario Outline: Alerting API Test for Consolidation Functionality with 2 Parent With Same ConditionID

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow3>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow4>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Then I trigger CREATE Alert API request for Two Parent Alert with Same ConditionID on Alert MS for "<TestCaseRow5>"
Then I trigger DELETE API request for Child Alert for Both Parent on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|TestCaseRow3|TestCaseRow4|TestCaseRow5|
|Child1|Child2|FilterChild1|FilterChild2|Parent|

#@Regression
Scenario Outline: Alerting API Test for Consolidation Functionality with 3 Child 1 Child Delete and 1 Parent

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow3>"
Then I verify API response from Alert MS
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert not reached till ITSM Simulator
Then I trigger DELETE API for One Child Alert on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow4>"
Then I verify API response from Alert MS
Then I trigger DELETE API request for Child Alert for Both Parent on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|TestCaseRow3|TestCaseRow4|
|FilterChild1|FilterChild2|FilterChild3|FilterParent|

#@Regression
Scenario Outline: Alerting Manual Closure Test from ITSM Kafka Topic

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM

Examples:
|TestCaseRow|KafkaMessageType|
|UnderResearchWindows|AlertID|
#|UnderResearchSQL|MetaData|