Feature: Resource level suspension scenarios
@resource
Scenario Outline: Verify for resource level suspension if Alert is suspended weekly with end date
Given "EmailNotification" : "<TestCase>" I apply resource level family suspension for 5 minutes
And I wait for x minutes for suspesnion to get applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 205 for suspended partner
Examples:
|TestCase|
|Email01|

Scenario Outline: Verify Alert should get deleted for resouce where suspension rule is applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for resouce where suspension rules are not applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Resource.
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype

Examples:
|TestCase|
|Platform008|