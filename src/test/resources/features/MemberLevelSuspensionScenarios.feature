Feature: Member level suspension scenarios

@suspension
Scenario Outline: Verify for member level suspension if family is suspended one-time until time
Given "PlatformAlertingCreateAlert" : "<TestCase>" I apply member level family suspension for 5 minutes
And I wait for x minutes for suspesnion to get applied
When I trigger create alert API
Then I verify create api response code is 205 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for members where suspension rules are not applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for family where suspension rules are not applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for member where suspension rule is applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for family where suspension rule is applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Memeber.
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Memeber.
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|