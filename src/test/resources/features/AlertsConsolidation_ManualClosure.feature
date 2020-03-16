Feature: Juno Alerting Consolidation Test - Manual Closure

  @Functional @BVT
  Scenario Outline: Manual Closure of Parent Alert with 2 child

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify "child list" in Alert State API response
    Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If POST and DELETE reached till ITSM Simulator
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |KafkaMessageType|
      | Child19      | Child20      | Parent21     |AlertID         |


  @Functional @BVT
  Scenario Outline: Manual Closure of Parent Alert with 1 child closed in Alerting MS

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger DELETE API for One Child Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then Wait for "65" Secs
    Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response as Duplicate Alert Request from Alert MS
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then Wait for "40" Secs
    Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>" for Alert ID Child 1
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert reached till ITSM Simulator
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |KafkaMessageType|
      | Child22      | Child23      | Parent24     |AlertID         |


  @Functional @BVT
  Scenario Outline: Alerting API Test for Consolidation Functionality with 1 Child and No Parent with Manual Closure

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then Wait for "65" Secs
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>"
    #Then I trigger DELETE API request on Alert MS
    #Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Then I should verify ITSM payload data as expected
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |KafkaMessageType|
      | Child33      | Child23      | Parent24     |AlertID         |