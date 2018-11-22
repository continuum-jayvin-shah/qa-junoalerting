Feature: Alerting-JAS-Alignment

@V2Regression
Scenario Outline: Verify for a create alert api for DM2.0 Conditions-C1915948,C1915949,C1915950,C1915951
Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
And I trigger GetAPI to verify alerts exists
And I trigger update alert API
And I trigger auto close alert API

Examples:
|TestCase|
|Platform001|
|Platform002|
#|Platform003|
#|Platform004|
#|Platform005|
#|Platform006|

@V2Regression
Scenario Outline: Verify for a create alert api for Windows Service Conditions-C1915948,C1915949,C1915950,C1915951
Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
And I trigger GetAPI to verify alerts exists
And I trigger update alert API
And I trigger auto close alert API

Examples:
|TestCase|
|Platform010|
|Platform011|
|Platform012|
|Platform013|
|Platform014|


@V2Regression
Scenario Outline: Verify for a create alert api for Veritas Conditions-C1915948,C1915949,C1915950,C1915951
Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
And I trigger GetAPI to verify alerts exists
And I trigger update alert API
And I trigger auto close alert API

Examples:
|TestCase|
|Platform007|
#|Platform002|
#|Platform003|
#|Platform004|
#|Platform005|
#|Platform006|

@V2Regression
Scenario Outline: Verify for a create alert api for VSS Conditions-C1915948,C1915949,C1915950,C1915951
Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
And I trigger GetAPI to verify alerts exists
And I trigger update alert API
And I trigger auto close alert API

Examples:
|TestCase|
|Platform008|
#|Platform003|
#|Platform004|
#|Platform005|
#|Platform006|

@V2Regression
Scenario Outline: Verify for a create alert api for SQL Conditions-C1915948,C1915949,C1915950,C1915951
Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
And I trigger GetAPI to verify alerts exists
And I trigger update alert API
And I trigger auto close alert API

Examples:
|TestCase|
|Platform009|
#|Platform003|
#|Platform004|
#|Platform005|
#|Platform006|
