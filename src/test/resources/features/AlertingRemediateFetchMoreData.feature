Feature: Juno Alerting Remediation/FetchMoreData Test

@Functional @BVT
Scenario Outline: Alerting API Test for Remeditaion/FetchMoreData Functionality - "<TestCaseRow>"

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