Feature: Alerting-JAS-Alignment

#@V2Regression
#Scenario Outline: Verify for a create alert api for DM2.0 Conditions-C4105995,C4105996,C4105997,C4429075,C4429076,C4429077,C3998495,C3998496,C3998497,C3994724,C4502020,C3994775
#Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
#Then AlertID should get generated
#And I trigger GetAPI to verify alerts exists
#And I trigger update alert API
#And I trigger auto close alert API
#
#Examples:
#|TestCase|
#|Platform001|
#|Platform002|
##|Platform003|
##|Platform004|
##|Platform005|
##|Platform006|

@V2Regression
Scenario Outline: Verify for a create alert api for Windows Service Conditions-C4475873,C4475877,C4475881,C4476574,C4501336,C4501340,C4503372,C4503376,C4503380,C4501344,C4502019,C4502024
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
Scenario Outline: Verify for a create alert api for Veritas Conditions-C4475874,C4475878,C4475882,C4476562,C4501333,C4501337,C4503373,C4503377,C4503381,C4501341,C3994725,C4502021
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
Scenario Outline: Verify for a create alert api for VSS Conditions-C4475875,C4475879,C4475883,C4476563,C4501334,C4501338,C4503374,C4503378,C4503382,C4501342,C4502017,C4502022
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
Scenario Outline: Verify for a create alert api for SQL Conditions-C4475876,C4475880,C4475884,C4476566,C4501335,C4501339,C4503375,C4503379,C4503383,C4501343,C4502018,C4502023
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


@V211Regression
Scenario Outline: Verify Error Code 404 with 203 for delete Alert api response for invalid alert value -C4475876,C4475880,C4475884,C4476566,C4501335,C4501339,C4503375,C4503379,C4503383,C4501343,C4502018,C4502023
Given "CodesValidationV2" : "<TestCase>" : I trigger delete alert API request with invalid alertID
Then I verify delete api response code is 203 for invalid alertID
Then I verify delete api status code is 404 for invalid alertID

Examples:
|TestCase|
|ErrorCode103|

@V211Regression
Scenario Outline: Verify Error Code 404 with 203 for update Alert api response for invalid alert value -C4475876,C4475880,C4475884,C4476566,C4501335,C4501339,C4503375,C4503379,C4503383,C4501343,C4502018,C4502023
Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with invalid alertID
Then I verify update api response code is 203 for invalid alertID
Then I verify update api status code is 404 for invalid alertID

Examples:
|TestCase|
|ErrorCode103|

