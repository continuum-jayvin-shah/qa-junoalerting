Feature: Auto Process Functionalities

@BVT
Scenario Outline: Verify for a create alert api a request is created in PASReqQueue table-C1915948,C1915949,C1915950,C1915951

Given "PlatformAlertingCreateAlert" : "<TestCase>" : I trigger create alert API
And I verify create alert api request in PAS_ReqQueue table
And I verify create alert request is archived in PAS_ReqQueueArchive table
And I verify an alert entry is created in pas_reqcons table on successful processing of an Alert request
And I trigger update alert API
And I verify create alert api request in PAS_ReqQueue table
And I verify create alert request is archived in PAS_ReqQueueArchive table
And I verify an alert entry is created in pas_reqcons table on successful processing of an Alert request
And I trigger auto close alert API
And I verify create alert api request in PAS_ReqQueue table
And I verify create alert request is archived in PAS_ReqQueueArchive table
And I verify an archived alert entry is created in PAS_ReqConsArchive table on successfull processing of close alert request
And I verify create alert api request is deleted from PAS_ReqQueue table

#Given I naviagte to ITS portal
#When I login to ITS portal
#And I navigate to New ticket windowds
#And I search ticket id on report page
#And I verify searched ticket details
#And I navigate to ticket detail page for ticket
#Then I verify ticket details on ticket details page of ITS portal

Examples:
|TestCase|
#|Platform001|
#|Platform002|
#|Platform003|
|Platform004|
#|Platform005|
#|Platform006|
#|Platform007|
#|Platform008|
#|Platform009|
#|Platform010|
#|Platform011|
#|Platform012|
#|Platform013|
#|Platform014|
#|Platform015|
#|Platform016|
#|Platform017|
#|Platform018|
#|Platform019|
#|Platform020|
#|Platform021|
#|Platform022|
#|Platform023|
#|Platform024|
#|Platform025|
#|Platform026|
#|Platform027|
#|Platform028|
#|Platform029|
#|Platform030|
#|Platform031|
#|Platform032|


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