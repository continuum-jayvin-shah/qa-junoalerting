Feature: Resource level suspension scenarios

Scenario Outline: Verify for resource level suspension if Alert is suspended monthly recursive rule with end date
Given "Suspension" : "<TestCase>" I am able to login to ITS Portal
And I am able to Navigate to Intellimon Alert Suspension Section
And I apply resource level Alert suspension weekly for 5 minutes
And I wait for x minutes for suspesnion to get applied
When I trigger create alert API
Then I verify create api response code is 205 for invalid partener datatype 

Examples:
|TestCase|
|Suspension01|

Scenario Outline: Verify Alert should get deleted for resouce where suspension rule is applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for resouce where suspension rules are not applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Resource.
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype

Examples:
|TestCase|
|Platform008|