Feature: Suspension scenarios

Scenario Outline: Verify for site level suspension if family is suspended weekly with No end date
Given  I apply site level Alert suspension for 5 minutes
Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify for site level suspension if family is suspended weekly recursive with end date
Given  I apply site level Alert suspension for 5 minutes
Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify for resource level suspension if family is suspended monthly on spacific date with no end date
Given  I apply site level Alert suspension for 5 minutes
Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify for member level suspension if Alert is suspended one-time No end date
Given  I apply site level Alert suspension for 5 minutes
Examples:
|TestCase|
|Platform008|

Scenario Outline: Verify for site level suspension if Alert is suspended weekly recursive with No end date
Given  I apply site level Alert suspension for 5 minutes
Examples:
|TestCase|
|Platform008|