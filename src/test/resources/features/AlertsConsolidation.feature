Feature: Juno Alerting Consolidation Test

  @Functional @BVT
  Scenario Outline: Alerting API Test for Consolidation Functionality with 2 Child and 1 Parent

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
    #When I trigger CREATE Incident API request on ITSM MS
    #Then I verify Duplicate Alert in API response from ITSM MS
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response from ITSM MS for UPDATE Request
    Then I trigger GET Alert State API for current alert
    Then I verify "child list" in Alert State API response
    Then I trigger DELETE API request for Child Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert reached till ITSM Simulator
    Then I should verify ITSM payload data as expected
    #Then I trigger DELETE API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "DELETE" Request
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "UPDATE" Request
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |
      | Child1       | Child2       | Parent1      |


  @Functional
  Scenario Outline: Alerting API Test for Consolidation Functionality with 2 Parent With Same ConditionID

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
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow4>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request for Two Parent Alert with Same ConditionID on Alert MS for "<TestCaseRow5>"
    #When I trigger CREATE Incident API request on ITSM MS
    #Then I verify Duplicate Alert in API response from ITSM MS
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response from ITSM MS for UPDATE Request
    Then I trigger DELETE API request for Child Alert for Both Parent on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert reached till ITSM Simulator
    #Then I trigger DELETE API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "DELETE" Request
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "UPDATE" Request
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 | TestCaseRow4 | TestCaseRow5 |
      | Child3       | Child4       | FilterChild1 | FilterChild2 | Parent2      |


  @Functional
  Scenario Outline: Alerting API Test for Consolidation Functionality with 3 Child 1 Child Delete and 1 Parent

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger CREATE Alert API request on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger DELETE API for One Child Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow4>"
    Then I verify API response from Alert MS
    #When I trigger CREATE Incident API request on ITSM MS
    #Then I verify Duplicate Alert in API response from ITSM MS
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response from ITSM MS for UPDATE Request
    Then I trigger DELETE API request for Child Alert for Both Parent on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert reached till ITSM Simulator
    Then I should verify ITSM payload data as expected
    #Then I trigger DELETE API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "DELETE" Request
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "UPDATE" Request
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 | TestCaseRow4 |
      | FilterChild3 | FilterChild4 | FilterChild5 | FilterParent |


  @Functional @BVT
  Scenario Outline: Alerting API Test for Consolidation Functionality with 1 Child and No Parent

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then Wait for "65" Secs
    Then I trigger UPDATE Alert API request on Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    #When I trigger CREATE Incident API request on ITSM MS
    #Then I verify Duplicate Alert in API response from ITSM MS
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response from ITSM MS for UPDATE Request
    Then I trigger DELETE API request on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Then I should verify ITSM payload data as expected
    #Then I trigger DELETE API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "DELETE" Request
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "UPDATE" Request
    Examples:
      | TestCaseRow1 |
      | Child5       |


  @Functional
  Scenario Outline: Alerting API Test for Consolidation Functionality with 2 Child 1 Child Delete and 1 Parent

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
    #When I trigger CREATE Incident API request on ITSM MS
    #Then I verify Duplicate Alert in API response from ITSM MS
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response from ITSM MS for UPDATE Request
    Then I trigger DELETE API for One Child Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM
    Then I should verify ITSM payload data as expected
    #Then I trigger DELETE API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "DELETE" Request
    #Then I trigger UPDATE ITSM API request on ITSM MS
    #Then I verify API response "404" from Alert MS for "UPDATE" Request
    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |
      | Child6       | Child7       | Parent3      |


  @Functional @BVT
  Scenario Outline: Parent gets dropped for deleted child in payload

    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator
    Then I trigger DELETE API request on Alert MS
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I trigger GET Alert State API for current alert
    Then I verify No data in Alert State API
    Then I verify alert should not present in Alert Failure table
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If alert not reached till ITSM Simulator

    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |
      | Child11      | Child12      | Parent13     |


  @Functional @54306
  Scenario Outline: Multiple Update Delete process Execution sequence
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow1>"
    Then I verify API response from Alert MS
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow2>"
    Then I verify API response from Alert MS
    Given I trigger CREATE Alert API request on Alert MS for "<TestCaseRow3>"
    Then I verify API response from Alert MS
    Then I trigger CREATE Alert API request for Parent Alert on Alert MS for "<TestCaseRow5>"
    Then I verify API response from Alert MS
    Then I trigger UPDATE Alert API request for "first" child "<TestCaseRow1>" Child Alert in Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger UPDATE Alert API request for "second" child "<TestCaseRow2>" Child Alert in Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger UPDATE Alert API request for "third" child "<TestCaseRow3>" Child Alert in Alert MS
    Then I verify API response from Alert MS for UPDATE Request
    Then I trigger DELETE API request for Child Alert only on Alert MS
    Then Wait for "60" Secs
    Then I verify API response from Alert MS for DELETE Request
    Then I verify alert should not present in Alert Failure table
    Then I get ITSM Simulator Response for Current Alert
    Then I verify If all requests were sent to ITSM for Consolidation

    Examples:
      | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |  TestCaseRow5 |
      | Child15      | Child16      | Child17      |  Parent19     |


  @Functional @BVT
  Scenario Outline: Consolidation having Child at "<ChildLevel>" and Parent at "<ParentLevel>" Level

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
  #  Then I trigger GET Alert State API for current alert
  #  Then I verify "child list" in Alert State API response
    Then I trigger DELETE API request for Child 1 "<TestCaseRow1>" Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I trigger DELETE API request for Child 2 "<TestCaseRow2>" Alert on Alert MS
    Then I verify API response from Alert MS for DELETE Request
    Then I get ITSM Simulator Response for "<TestCaseRow3>" Alert
    Then I verify If alert reached till ITSM Simulator
    Examples:
      | ChildLevel | ParentLevel | TestCaseRow1 | TestCaseRow2 | TestCaseRow3 |
      | Endpoint   | Site        | Child25      | Child26      | Parent27     |
      | Site       | Client      | Child28      | Child29      | Parent30     |
      | Client     | Partner     | Child31      | Child32      | Parent33     |