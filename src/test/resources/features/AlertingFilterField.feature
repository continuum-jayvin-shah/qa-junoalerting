Feature: Juno Alerting Filter Field Test

@Functional @BVT
Scenario Outline: Alerting API Test for Filter Value Functionality - "<TestCaseRow1>" And "<TestCaseRow2>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify New Alert created for New Request
#When I trigger CREATE Incident API request on ITSM MS
#Then I verify Duplicate Alert in API response from ITSM MS
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response from ITSM MS for UPDATE Request
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator
#Then I trigger DELETE API request on ITSM MS
#Then I verify API response "404" from Alert MS for "DELETE" Request
#Then I trigger UPDATE ITSM API request on ITSM MS
#Then I verify API response "404" from Alert MS for "UPDATE" Request
Examples:
|TestCaseRow1|TestCaseRow2|
|WithoutFilter1|FilterValue1|
|FilterValue2|FilterValue3|
|FilterValue4|WithoutFilter2|