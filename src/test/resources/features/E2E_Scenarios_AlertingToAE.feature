@E2E
Feature: Verify end to end functionality from Alerting to Automation engine


  Scenario Outline: Verify End to End flow Alert creation, policy file execution for tasking and validate in alerting
    When I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS

    Examples:
      | TestCaseRow |
      | E2E_test2   |