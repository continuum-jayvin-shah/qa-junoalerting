Feature: Juno Alerting Error Code Scenario

  @Functional @Negative
  Scenario Outline: Alert goes to Failure table for 500 Server Error

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then Wait for "<duration>" Secs
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response "<UpdateResponse>" from Alert MS for UPDATE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify status code "<StatusCode>" in ITSM Simulator Response
    Then I verify alert should present in Alert Failure table

    Examples:
      | TestCaseRow     | UpdateResponse | StatusCode | duration |
      | 500_ServerError | 404            | 500        | 45       |


  @Functional @Negative
  Scenario Outline: Success in Reprocessing Alert remove alert from Failure table

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then Wait for "<duration>" Secs
    Then I get ITSM Simulator Response for Current Alert
    Then I verify status code "<StatusCode>" in ITSM Simulator Response
    Then I verify alert should present in Alert Failure table
    Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify status code "<StatusCode>" in ITSM Simulator Response
    Then I reprocess the "first" alert
    Then I verify Duplicate Alert Id message in response
    Then I verify alert should not present in Alert Failure table
    Then I trigger DELETE API request for second on Alert MS
    Then I verify API response from Alert MS for DELETE Request

    Examples:
      | TestCaseRow                  | StatusCode | duration | TestCaseRow1                   |
      | 500_ServerError_Reprocession | 500        | 25       | 500_ServerError_Reprocession_1 |


  @Functional @Negative
  Scenario Outline: Alert goes as Create while 404 comes in PUT response

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API request on Alert MS having "<UpdateResponse>" ITSM Simulator response
    Then I verify API response from Alert MS for UPDATE Request
    Then Wait for "<duration>" Secs
    Then I get ITSM Simulator Response for Current Alert
    Then I verify New create POST message in ITSM response

    Examples:
      | TestCaseRow     | UpdateResponse |  duration |
      | 404_PUT_Error   | 404            |  5        |