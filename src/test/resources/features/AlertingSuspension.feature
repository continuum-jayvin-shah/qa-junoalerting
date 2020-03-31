Feature: Juno Alerting Suspension Tests

  @BVT
  Scenario Outline: Alerting API Test for Suspension Functionality - "<TestCaseRow>"

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify No data in Alert State API

    Examples:
      | TestCaseRow               |
      | Endpoint Level Suspension |
      | Site Level Suspension     |
