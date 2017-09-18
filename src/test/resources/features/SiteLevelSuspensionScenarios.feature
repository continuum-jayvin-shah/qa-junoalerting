Feature: Site level suspension scenarios


Background: Verify for site level suspension if Alert is suspended weekly with end date
Given  I apply site level Alert suspension for 5 minutes
And I wait for x minutes for suspesnion to get applied
When I trigger create alert API
Then I verify create api response code is 205 for invalid partener datatype 

Scenario Outline: Verify Alert should get generated for condition where suspension rules are not applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for condition where suspension rule is applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated for site where suspension rules are not applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get deleted for site where suspension rule is applied
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype 

Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify Alert should get generated when rule is expired for Site.
When I trigger create alert API
Then I verify create api response code is 201 for invalid partener datatype

Examples:
|TestCase|
|Platform008|