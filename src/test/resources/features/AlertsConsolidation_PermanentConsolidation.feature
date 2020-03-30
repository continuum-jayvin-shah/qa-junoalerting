Feature: Juno Alerting Consolidation Test - Permanent Consolidation

  @Functional @BVT
  Scenario Outline: Permanent Consolidation with 2 Child Alert with 1 Child in Update

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
    Then I verify API response of Parent from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify "child list" in Alert State API response
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow4>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API on Parent Alert "<TestCaseRow3>" 3 with Newly created Child Alert 4
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger GET Alert State API for Parent alert 3
    Then I verify "child list" in Alert State API response
    Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>" for Alert ID "Parent" 3
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |TestCaseRow4|KafkaMessageType|
      | Child34      | Child35      | Parent36     |Child37     |AlertID         |


  @Functional @BVT
  Scenario Outline: Permanent Consolidation with 1 Child Alert with 1 Child in Update

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
    Then I verify API response of Parent from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify "child list" in Alert State API response
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow4>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API on Parent Alert "<TestCaseRow3>" 2 with Newly created Child Alert 3
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger GET Alert State API for Parent alert 2
    Then I verify "child list" in Alert State API response
    Examples:
      | TestCaseRow1 | TestCaseRow3 |TestCaseRow4|
      | Child38      | Parent39     |Child40     |


  @Functional @BVT
  Scenario Outline: Permanent Consolidation with 3 Child Alert and delete 2 then 2 Child in Update

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow4>"
    Then I verify API response of Parent from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify "child list" in Alert State API response
    Then I trigger DELETE API request for Child Alert 1 on Alert MS
    Then I trigger DELETE API request for Child Alert 2 on Alert MS
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow5>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API on Parent Alert "<TestCaseRow4>" 2 with Newly created Child Alert 3
    Then I verify API response from Alert MS for UPDATE Request
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow6>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API on Parent Alert "<TestCaseRow4>" 2 with Newly created Child Alert 4
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger GET Alert State API for Parent alert 2
    Then I verify "child list" in Alert State API response
    Then I trigger Manual Closure By Posting on KafkaTopic with MessageType "<KafkaMessageType>" for Alert ID "Parent" 2
  #  Then I get ITSM Simulator Response for Current Alert
  #  Then I verify If all requests were sent to ITSM
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow4 |TestCaseRow3|TestCaseRow5|TestCaseRow6|KafkaMessageType|
      | Child41      | Child42      | Parent43     |Child44     |Child45     |Child46     |AlertID         |