Feature: Juno Alerting Snooze Tests

@Functional
Scenario Outline: Alerting API Test for Snooze Functionality on Update Request - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow|
|SnoozeUpdate|

@Functional
Scenario Outline: Alerting API Test for Snooze Functionality Disabled on Delete Request and Enabled on Multiple Update Request - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow|
|SnoozeMultipleUpdateNDelete|

@Functional
Scenario Outline: Alerting API Test for Snooze Functionality Disabled on Duplicate Create Request - "<TestCaseRow>"

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response as Duplicate Alert Request from Alert MS
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow|
|SnoozeDisabledOnDuplicate|

@Functional
Scenario Outline: Alerting API Test for Snooze Functionality Disabled If New Alert Req with Different Filter Value Received

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger DELETE API request on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|
|SnoozeFilter1|SnoozeFilter2|

@Functional
Scenario Outline: Alerting API Test for Snooze Functionality Enabled during Consolidation

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then Wait for "10" Secs
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
Then I verify API response from Alert MS
Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
Then I verify API response as Duplicate Alert Request from Alert MS
Then I trigger UPDATE Alert API request on Alert MS
Then I verify API response from Alert MS for UPDATE Request with Snooze Enabled
Then I trigger DELETE API request for Child Alert on Alert MS
Then I verify API response from Alert MS for DELETE Request
Then I get ITSM Simulator Response for Current Alert
Then I verify If alert reached till ITSM Simulator

Examples:
|TestCaseRow1|TestCaseRow2|TestCaseRow3|
|SnoozeConsolidationChild1|SnoozeConsolidationChild2|SnoozeConsolidationParent|