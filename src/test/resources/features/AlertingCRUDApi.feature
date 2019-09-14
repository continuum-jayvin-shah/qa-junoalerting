Feature: Auto Process Functionalities

@Functional
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

@Functional
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
|DM 2.0|
#|BDR|
|Security|

@Functional
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

@Functional
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

@Functional
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

@Functional
Scenario Outline: Alerting CREATE API Test for Duplicate Alert

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response as Duplicate Alert Request from Alert MS
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request


Examples:
|TestCaseRow|
|UnderResearchWindows|

@Functional
Scenario Outline: Alerting DELETE/UPDATE API Test for Non-Existing Alert

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for Non-Existing Alert
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for Non-Existing Alert


Examples:
|TestCaseRow|
|UnderResearchWindows|