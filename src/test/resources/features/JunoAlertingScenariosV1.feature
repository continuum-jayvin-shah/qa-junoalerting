Feature: Auto Process Functionalities

@V1Regression
Scenario Outline: Verify for a create alert api a request is created in PASReqQueue table-C1915948,C1915949,C1915950,C1915951

Given "PlatformAlertingCreateAlert" : "<TestCase>" : I trigger create alert API
And I verify create alert api request in PAS_ReqQueue table
And I verify create alert request is archived in PAS_ReqQueueArchive table
And I verify an alert entry is created in pas_reqcons table on successful processing of an Alert request
And I trigger update alert API
And I verify update alert api request in PAS_ReqQueue table
And I verify update alert request is archived in PAS_ReqQueueArchive table
And I verify an alert entry is created in pas_reqcons table on successful processing of an update Alert request
And I trigger auto close alert API
And I verify delete alert api request in PAS_ReqQueue table
And I verify delete alert request is archived in PAS_ReqQueueArchive table
And I verify an archived alert entry is created in PAS_ReqConsArchive table on successfull processing of close alert request
And I verify create alert api request is deleted from pas_reqcons table

Examples:
|TestCase|
|Platform001|
|Platform002|
|Platform003|
|Platform004|
|Platform005|
#|Platform006|
#|Platform007|
#|Platform008|

@V1Regression @102
Scenario Outline: Verify Error Code 102 for create Alert api response for invalid partner value - C1915960
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with datatype for partner invalid
Then I verify create api response code is 102 for invalid partener datatype

Examples:
|TestCase|
|PartnerCode102|

@V1Regression @102
Scenario Outline: Verify Error Code 102 for create Alert api response for invalid site value - C1915961
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with datatype for site invalid
Then I verify api response code is 102 for invalid site datatype
Examples:
|TestCase|
|SiteCode102|

@V1Regression @103
Scenario Outline: Verify Error Code 103 for create Alert api response for invalid request body - C1915962
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with invalid request body
Then I verify api response code is 103 for invalid request body
Examples:
|TestCase|
|ErrorCode103|

@V1Regression @104
Scenario Outline: Verify Error Code 104 for create Alert api response for missing resource ID - C1915963
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with resource ID missing
Then I verify create api response code is 104 for missing resource ID

Examples:
|TestCase|
|ErrorCode104Res|

@V1Regression @104
Scenario Outline: Verify Error Code 104 for create Alert api response for missing condition ID - C1915964
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with condition ID missing
Then I verify api response code is 104 for missing condition ID

Examples:
|TestCase|
|ErrorCode104Con|

@V1Regression
Scenario Outline: Verify Error Code 104 for create Alert api response for missing request body - C1915965
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with request body missing
Then I verify api response code is 104 for missing request body

Examples:
|TestCase|
|ErrorCode104ReqBody|

@V1Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect partner ID - C1915966
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect partner ID
Then I verify create api response code is 105 for incorrect partner ID

Examples:
|TestCase|
|ErrorCode105Part|

@V1Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect site ID - C1915967
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect site ID
Then I verify create api response code is 105 for incorrect site ID

Examples:
|TestCase|
|ErrorCode105Site|

@V1Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect resource ID - C1915968
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect resource ID
Then I verify create api response code is 105 for incorrect resource ID

Examples:
|TestCase|
|ErrorCode105Res|

@V1Regression
Scenario Outline: Verify Error Code 102 for update Alert api response for invalid partner value - C1933215
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with datatype for partner invalid
Then I verify update api response code is 102 for invalid partener datatype

Examples:
|TestCase|
|PartnerCode102|

@V1Regression @Defect
Scenario Outline: Verify Error Code 103 for update Alert api response for invalid request body - C1933216
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with invalid request body
Then I verify update api response code is 103 for invalid request body
Examples:
|TestCase|
|ErrorCode103|

@V1Regression
Scenario Outline: Verify Error Code 104 for update Alert api response for missing resource ID - C1933217
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with resource ID missing
Then I verify update api response code is 104 for resource condition ID

Examples:
|TestCase|
|ErrorCode104Res|

@V1Regression
Scenario Outline: Verify Error Code 104 for update Alert api response for missing condition ID - C1933218
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with condition ID missing
Then I verify update api response code is 104 for missing condition ID

Examples:
|TestCase|
|ErrorCode104Con|

@V1Regression
Scenario Outline: Verify Error Code 104 for update Alert api response for missing request body - C1933219
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with request body missing
Then I verify update api response code is 104 for missing request body

Examples:
|TestCase|
|ErrorCode104ReqBody|

@V1Regression
Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect partner ID - C1933220
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect partner ID
Then I verify update api response code is 105 for incorrect partner ID

Examples:
|TestCase|
|ErrorCode105Part|

@V1Regression
Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect site ID - C1933221
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect site ID
Then I verify update api response code is 105 for incorrect site ID

Examples:
|TestCase|
|ErrorCode105Site|

#@V1Regression
#Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect resource ID - C1933222
#Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect resource ID
#Then I verify update api response code is 105 for incorrect resource ID

#Examples:
#|TestCase|
#|ErrorCode105Res|

@V1Regression
Scenario Outline: Verify Error Code 102 for delete Alert api response for invalid partner value - C1933225
Given "CodesValidation" : "<TestCase>" : I trigger delete alert API request with datatype for partner invalid
Then I verify delete api response code is 102 for invalid partener datatype

Examples:
|TestCase|
|PartnerCode102|

@V1Regression
Scenario Outline: Verify Error Code 404 for delete Alert api response for invalid alert value - C1933227, C2130258
Given "CodesValidation" : "<TestCase>" : I trigger delete alert API request with invalid alertID
Then I verify delete api response code is 203 for invalid alertID
Then I verify delete api status code is 404 for invalid alertID

Examples:
|TestCase|
|ErrorCode103|

@V1Regression
Scenario Outline: Verify Error Code 404 for update Alert api response for invalid alert value - C1933228, C2130259
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with invalid alertID
Then I verify update api response code is 203 for invalid alertID
Then I verify update api status code is 404 for invalid alertID

Examples:
|TestCase|
|ErrorCode103|

@V1Regression @202
Scenario Outline: Verify Error Code 202 for create Alert api response when alert already exist - C1933226
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request when alert already exist
Then I verify create api response code is 202 when alert already exist

Examples:
|TestCase|
|ErrorCode103|

@V1Regression @107
Scenario Outline: Verify Error Code 107 for create Alert api response for incorrect condition ID - C1930254
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect condition ID
Then I verify create api response code is 107 triggered with incorrect condition ID

Examples:
|TestCase|
|ErrorCode107|

@V1Regression
Scenario Outline: Verify Error Code 108 for create Alert api response for incorrect post body - C1930255
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect post  ID
Then I verify create api response code is 108 triggered with incorrect post ID

Examples:
|TestCase|
|ErrorCode108|

#@V1Regression
#Scenario Outline: Verify Error Code 108 for update Alert api response for incorrect post body - C1930256
#Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect post  ID
#Then I verify update api response code is 108 triggered with incorrect post ID

#Examples:
#|TestCase|
#|ErrorCode108|

#@Email1
#Scenario Outline: Verify I should be able to set Email Notification Rule at resource Level
#Given "EmailNotification" : "<TestCase>" I am able to login to ITS Portal
#Then I am able to Navigate to Intellimon Email Extension Section
#Then I should be able to set a resource level rule
#Then "EmailNotification" : "<TestCase>" I verify the email params in SaazOnline Live table
#Examples:
#|TestCase|
#|Email01|
#
#@Email
#Scenario Outline: Verify I should be able to set Email Notification Rule at site Level
#Given "EmailNotification" : "<TestCase>" I am able to login to ITS Portal
#Then I am able to Navigate to Intellimon Email Extension Section
#Then I should be able to set a site level rule
#Examples:
#|TestCase|
#|Email02|
#
#@Email
#Scenario Outline: Verify I should be able to set Email Notification Rule at member Level
#Given "EmailNotification" : "<TestCase>" I am able to login to ITS Portal
#Then I am able to Navigate to Intellimon Email Extension Section
#Then I should be able to set a Member level rule
#Examples:
#|TestCase|
#|Email02|
