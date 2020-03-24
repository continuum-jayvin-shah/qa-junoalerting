Feature: Juno Alerting Condition ID send to downstream

  @Functional @BVT
  Scenario Outline: Condition ID with send to downstream with "<TestCaseRow1>" and source system is "<TestCaseRow2>"

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger DELETE API request on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Then I should verify ITSM payload data as expected
    Then I should verify ITSM payload source system "<TestCaseRow2>" data as expected
    Examples:
      | TestCaseRow | TestCaseRow1 | TestCaseRow2 |
      | Case3       | false        | 1.0          |
      | Case4       | false        | 2.0          |


  @Functional @BVT
  Scenario Outline: Condition ID with send to downstream with "<TestCaseRow1>" and source system is "<TestCaseRow2>"

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify only POST will reach to ITSM Simulator
    Then I verify alert should present in Alert Failure table
    Examples:
      | TestCaseRow | TestCaseRow1 | TestCaseRow2 |
      | Case1       | true         | 1.0          |
      | Case2       | true         | 2.0          |


  @Functional @BVT
  Scenario Outline: Condition ID with send to downstream with "<TestCaseRow1>" and source system is "<TestCaseRow2>" but payload is of "<TestCaseRow3>"

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger DELETE API request on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Then I should verify ITSM payload data as expected
    Then I should verify ITSM payload source system "<TestCaseRow3>" data as expected
    Examples:
      | TestCaseRow | TestCaseRow1 | TestCaseRow2 |TestCaseRow3|
      | Case5       | true         | 1.0          | 2.0        |
      | Case6       | true         | 2.0          | 1.0        |
