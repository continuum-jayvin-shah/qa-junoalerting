Feature: Site level suspension scenarios

@SiteSuspension
Scenario Outline: Verify for site level suspension if Alert is suspended weekly with end date
Given "EmailNotification" : "<TestCase>" I apply site level family suspension for 5 minutes
And I wait for x minutes for suspesnion to get applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 205 for suspended partner
Examples:
|TestCase|
|Email01|

Scenario Outline: Verify Alert should get generated for condition where suspension rules are not applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for condition where suspension rule is applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for site where suspension rules are not applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for site where suspension rule is applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Site.
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype

Examples:
|TestCase|
|Platform008|