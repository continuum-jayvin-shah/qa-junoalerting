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
|FilterValue2|