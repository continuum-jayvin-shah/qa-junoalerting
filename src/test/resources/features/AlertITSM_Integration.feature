@Alert_ITSM_integration
Feature: Alert ITSM Integration

  @NewApi
  Scenario Outline: Create/Update/Delete ITSM incident

    Given I trigger CREATE Incident API request on ITSM MS for "<TestCaseRow>"
    Then I verify API response from ITSM MS
    Then I trigger UPDATE ITSM API request on ITSM MS
    Then I verify API response from ITSM MS for UPDATE Request
  #  Then I trigger DELETE API request on ITSM MS
  #  Then I verify API response from ITSM MS for DELETE Request

    Examples:
      | TestCaseRow       |
      | PatnerLevel_ITSM1 |
#      | ClientLevel_ITSM1 |
#      | SiteLevel_ITSM1   |
#      | Endpoint_ITSM1    |