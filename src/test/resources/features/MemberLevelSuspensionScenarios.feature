Feature: Member level suspension scenarios

@suspension
Scenario Outline: Verify for member level suspension if family is suspended one-time until time
Given "EmailNotification" : "<TestCase>" I apply member level family suspension for 5 minutes
And I wait for x minutes for suspesnion to get applied
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 205 for suspended partner 

Examples:
|TestCase|
|Email01|


Scenario Outline: Verify Alert should get deleted for member where suspension rule is applied
When "EmailNotification" : "<TestCase>" I trigger delete alert API
Then I verify delete api response code is 204 for suspended partner

Examples:
|TestCase|
|Email01|

@suspension
Scenario Outline: Verify Alert should get generated when rule is expired for Memeber.
When "EmailNotification" : "<TestCase>" I trigger create alert API
Then I verify create api response code is 201 for suspended partner 

Examples:
|TestCase|
|Email01|

#Scenario Outline: Verify Alert should get generated for members where suspension rules are not applied
#When "EmailNotification" : "<TestCase>" I trigger create alert API
#Then I verify create api response code is 201 for suspended partner

#Examples:
#|TestCase|
#|Platform008|

#Scenario Outline: Verify Alert should get generated for family where suspension rules are not applied
#When "EmailNotification" : "<TestCase>" I trigger create alert API
#Then I verify create api response code is 201 for suspended partner 

#Examples:
#|TestCase|
#|Platform008|

#Scenario Outline: Verify Alert should get deleted for member where suspension rule is applied
#When "EmailNotification" : "<TestCase>" I trigger delete alert API
#Then I verify create api response code is 204 for invalid partener datatype 

#Examples:
#|TestCase|
#|Platform008|

#Scenario Outline: Verify Alert should get deleted for family where suspension rule is applied
#When "EmailNotification" : "<TestCase>" I trigger delete alert API
#Then I verify create api response code is 201 for invalid partener datatype 

#Examples:
#|TestCase|
#|Platform008|

#Scenario Outline: Verify Alert should get generated when rule is expired for Memeber.
#When I trigger create alert API
#Then I verify create api response code is 201 for invalid partener datatype 

#Examples:
#|TestCase|
#|Platform008|