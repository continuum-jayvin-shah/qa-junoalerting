Feature: Alerting-JAS-Alignment

@V2Regression
Scenario Outline: Verify for a create alert api for DM2.0 Conditions-C4105995,C4105996,C4105997,C4105998,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3998498,C3994724,C3994725,C399477
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
Scenario Outline: Verify for a create alert api for Windows Service Conditions-C4105995,C4105996,C4105997,C4105998,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3998498,C3994724,C3994725,C399477
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
Scenario Outline: Verify for a create alert api for Veritas Conditions-C4105995,C4105996,C4105997,C4105998,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3998498,C3994724,C3994725,C399477
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
Scenario Outline: Verify for a create alert api for VSS Conditions-C4105995,C4105996,C4105997,C4105998,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3998498,C3994724,C3994725,C399477
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
Scenario Outline: Verify for a create alert api for SQL Conditions-C4105995,C4105996,C4105997,C4105998,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3998498,C3994724,C3994725,C399477
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
