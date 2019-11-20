Feature: Juno Alerting Negative Test

  @Functional
  Scenario Outline: Alert goes to Failure table for 500 Server Error

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API request after "<duration>" on Alert MS
    Then I verify API response "<UpdateResponse>" from Alert MS for UPDATE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify status code "<StatusCode>" in ITSM Simulator Response

    Examples:
      | TestCaseRow     | UpdateResponse | StatusCode | duration |
      | 500_ServerError | 404            | 500        | 25000    |