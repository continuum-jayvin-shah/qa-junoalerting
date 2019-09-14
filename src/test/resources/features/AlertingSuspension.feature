Feature: Auto Process Functionalities

@Functional
Scenario Outline: Alerting API Test for Suspension Functionality

Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
Then I verify API response for Suspended Condition from Alert MS

Examples:
|TestCaseRow|
|Suspension|
#|ConditionLevelSuspension|
#|SiteLevelSuspension|
#|PartnerLevelSuspension|
#|ClientLevelSuspension|