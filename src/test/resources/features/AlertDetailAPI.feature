Feature: Auto Process Functionalities

@NewApi
Scenario Outline: Verify that valid data is coming for an alert detail api get request-C1915948

Given "AlertDetailData" : "<TestCase>" : I trigger GET request on alert detail API
Then I verify api response from the jmgtjobmanagement table

Examples:
|TestCase|
|AlertDtl|