@Alert_ITSM_integration @ignore
Feature: Alert ITSM Integration


  Scenario Outline: Create/Update/Delete ITSM incident
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    When I trigger CREATE Incident API request on ITSM MS
    Then I verify Duplicate Alert in API response from ITSM MS

    Examples:
      | TestCaseRow       |
      | PatnerLevel_ITSM1 |


     # Scenario Outline: Create/Update/Delete ITSM incident
  #  Given I trigger CREATE Incident API request on ITSM MS for "<TestCaseRow>"
  #  Then I verify API response from ITSM MS
  #  Then I trigger UPDATE ITSM API request on ITSM MS
  #  Then I verify API response from ITSM MS for UPDATE Request
  #  Then I trigger DELETE API request on ITSM MS
  #  Then I verify API response from ITSM MS for DELETE Request
  #  Examples:
  #    | TestCaseRow       |
  #    | PatnerLevel_ITSM1 |


