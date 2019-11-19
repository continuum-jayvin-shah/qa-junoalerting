Feature: Juno Alerting Bridge Test

@Functional @BVT
Scenario Outline: Alerting API Test for Alerting 1.0 Condition - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If all requests were sent to ITSM
Then I should verify ITSM payload data as expected

Examples:
|TestCaseRow|
|Alerting 1.0|
|Alerting 1.0 with ResourceID as EndPointID|

@Functional @BVT
Scenario Outline: Alerting API Test for Alerting 1.0 Condition with LegacyAlertID

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
Then I should verify ITSM payload data as expected
Examples:
|TestCaseRow1|TestCaseRow2|
|Alerting 1.0 with LegacyAlertID|Alerting 1.0 with New LegacyAlertID|

