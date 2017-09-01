Feature: Auto Process Functionalities

@BVT @Regression
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
#|Platform001|
#|Platform002|
#|Platform003|
#|Platform004|
#|Platform005|
#|Platform006|
#|Platform007|
|Platform008|

@Regression
Scenario Outline: Verify Error Code 102 for create Alert api response for invalid partner value - C1915960
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with datatype for partner invalid
Then I verify create api response code is 102 for invalid partener datatype

Examples:
|TestCase|
|PartnerCode102|

@Regression
Scenario Outline: Verify Error Code 102 for create Alert api response for invalid site value - C1915961
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with datatype for site invalid
Then I verify api response code is 102 for invalid site datatype
Examples:
|TestCase|
|SiteCode102|

@Regression
Scenario Outline: Verify Error Code 103 for create Alert api response for invalid request body - C1915962
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with invalid request body
Then I verify api response code is 103 for invalid request body
Examples:
|TestCase|
|ErrorCode103|

@Regression
Scenario Outline: Verify Error Code 104 for create Alert api response for missing resource ID - C1915963
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with resource ID missing
Then I verify create api response code is 104 for missing resource ID

Examples:
|TestCase|
|ErrorCode104Res|

@Regression
Scenario Outline: Verify Error Code 104 for create Alert api response for missing condition ID - C1915964
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with condition ID missing
Then I verify api response code is 104 for missing condition ID

Examples:
|TestCase|
|ErrorCode104Con|

@Regression
Scenario Outline: Verify Error Code 104 for create Alert api response for missing request body - C1915965
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with request body missing
Then I verify api response code is 104 for missing request body

Examples:
|TestCase|
|ErrorCode104ReqBody|

@Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect partner ID - C1915966
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect partner ID
Then I verify create api response code is 105 for incorrect partner ID

Examples:
|TestCase|
|ErrorCode105Part|

@Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect site ID - C1915967
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect site ID
Then I verify create api response code is 105 for incorrect site ID

Examples:
|TestCase|
|ErrorCode105Site|

@Regression
Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect resource ID - C1915968
Given "CodesValidation" : "<TestCase>" : I trigger create alert API request with incorrect resource ID
Then I verify create api response code is 105 for incorrect resource ID

Examples:
|TestCase|
|ErrorCode105Res|

@Regression
Scenario Outline: Verify Error Code 102 for update Alert api response for invalid partner value - C1933215
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with datatype for partner invalid
Then I verify update api response code is 102 for invalid partener datatype

Examples:
|TestCase|
|PartnerCode102|

@Regression
Scenario Outline: Verify Error Code 103 for update Alert api response for invalid request body - C1933216
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with invalid request body
Then I verify update api response code is 103 for invalid request body
Examples:
|TestCase|
|ErrorCode103|

@Regression
Scenario Outline: Verify Error Code 104 for update Alert api response for missing condition ID - C1915964
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with condition ID missing
Then I verify update api response code is 104 for missing condition ID

Examples:
|TestCase|
|ErrorCode104Con|

@Regression
Scenario Outline: Verify Error Code 104 for update Alert api response for missing request body - C1915965
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with request body missing
Then I verify update api response code is 104 for missing request body

Examples:
|TestCase|
|ErrorCode104ReqBody|

@Defect
Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect partner ID - C1915966
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect partner ID
Then I verify update api response code is 105 for incorrect partner ID

Examples:
|TestCase|
|ErrorCode105Part|

@Defect
Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect site ID - C1915967
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect site ID
Then I verify update api response code is 105 for incorrect site ID

Examples:
|TestCase|
|ErrorCode105Site|

@Defect
Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect resource ID - C1915968
Given "CodesValidation" : "<TestCase>" : I trigger update alert API request with incorrect resource ID
Then I verify update api response code is 105 for incorrect resource ID

Examples:
|TestCase|
|ErrorCode105Res|



#Given I naviagte to ITS portal
#When I login to ITS portal
#And I navigate to New ticket window
#And I search ticket id on report page
#And I verify searched ticket details
#And I navigate to ticket detail page for ticket
#Then I verify ticket details on ticket details page of ITS portal




#Scenario Outline: AUVIKSC001 : Verify for a create alert api, a request is created in PAS_ReqQueue table.
#
#Given "PlatformAlertingCreateAlert" : "<TestCase>" : I trigger create alert API
#When I get the create alert api response 
#Then I verify a new request is created in PAS_ReqQueue table
#And I verify create alert request has a unique CorrelationID in form of GUID/UID
#And I verify RegId of create alert request in PAS_ReqQueue table is with respect to resourceId in create alert api body
#And I verify ConditionId of create alert request in PAS_ReqQueue table is with respect to conditionId in create alert api body
#And I verify SiteId of create alert request in PAS_ReqQueue table is with respect to sites in create alert api URL.
#And I verify MemberId of create alert request in PAS_ReqQueue table is with respect to partners in create alert api URL.
#And I verify InputReq of create alert request in PAS_ReqQueue table is with respect to alertDetails json in create alert api body.
#And I verify Operation of create alert request in PAS_ReqQueue is always 1.
#And I verify DcDtime of create alert request in PAS_ReqQueue is as per the request TimeStamp in format MM/DD/YYYY HH:MM:SS AM/PM.
#And I verify UpDcDtime of create alert request in PAS_ReqQueue is as per the request TimeStamp in format MM/DD/YYYY HH:MM:SS AM/PM.
#
#
#Examples:
#|TestCase|
#|Platform001|
#
#Scenario Outline: AUVIKSC001 : create alert request is archived in PAS_ReqQueueArchive table on successfull processing
#
#Given "PlatformAlertingCreateAlert" : "<TestCase>" : I trigger create alert API
#And I get the create alert api request 
#When I verify create alert api request is processed 
#Then I verify create alert api request is archived in PAS_ReqQueueArchive table
#And I verify CorrelationID of archived create alert request in PAS_ReqQueueArchive table is with respect to CorrelationID of create alert request before processing.
#And I verify RegId of archived create alert request in PAS_ReqQueueArchive table is with respect to resourceId in create alert api body.
#And I verify ConditionId of archived create alert request in PAS_ReqQueueArchive table is with respect to conditionId in create alert api body.
#And I verify SiteId of archived create alert request in PAS_ReqQueueArchive table is with respect to 'sites' in create alert api URL.
#And I verify MemberId of archived create alert request in PAS_ReqQueueArchive table is with respect to 'partners' in create alert api URL.
#And I verify InputReq of archived create alert request in PAS_ReqQueueArchive table is with respect to 'alertDetails' json in create alert api body.
#And I verify Operation of archived create alert request in PAS_ReqQueueArchive is always 1.
#And I verify InsertedOn of archived create alert request in PAS_ReqQueueArchive is as per the request TimeStamp after processing in format MM/DD/YYYY HH:MM:SS AM/PM.
#
#
#Examples:
#|TestCase|
#|Platform001|
#
#
#
#Scenario Outline: AUVIKSC001 : An Alert entry is created in pas_reqcons table on successful processing of an Alert request
#
#Given "PlatformAlertingCreateAlert" : "<TestCase>" : I trigger create alert API
#And I get the create alert api request 
#When I verify create alert api request is processed 
#Then I verify create alert api request is archived in PAS_ReqQueueArchive table
#And I verify CorrelationID of archived create alert request in PAS_ReqQueueArchive table is with respect to CorrelationID of create alert request before processing.
#And I verify RegId of archived create alert request in PAS_ReqQueueArchive table is with respect to resourceId in create alert api body.
#And I verify ConditionId of archived create alert request in PAS_ReqQueueArchive table is with respect to conditionId in create alert api body.
#And I verify SiteId of archived create alert request in PAS_ReqQueueArchive table is with respect to 'sites' in create alert api URL.
#And I verify MemberId of archived create alert request in PAS_ReqQueueArchive table is with respect to 'partners' in create alert api URL.
#And I verify InputReq of archived create alert request in PAS_ReqQueueArchive table is with respect to 'alertDetails' json in create alert api body.
#And I verify Operation of archived create alert request in PAS_ReqQueueArchive is always 1.
#And I verify InsertedOn of archived create alert request in PAS_ReqQueueArchive is as per the request TimeStamp after processing in format MM/DD/YYYY HH:MM:SS AM/PM.
#
#
#Examples:
#|TestCase|
#|Platform001|