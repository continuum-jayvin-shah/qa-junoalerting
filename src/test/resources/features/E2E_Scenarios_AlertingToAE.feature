@E2E
Feature: Verify end to end functionality from Alerting to Automation engine


  Scenario Outline: Verify End to End flow Alert creation, policy file execution for tasking and validate in alerting
    When I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    And I get automation response for current alert
    Then Wait for "10" Secs
    And I verify API response from Alert MS for remediate


    Examples:
      | TestCaseRow |
#      | E2E_test1   |
      | E2E_test2   |