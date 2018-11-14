Feature: Auto Process Functionalities

@V2Regression
Scenario Outline: Verify for a create alert api a request is created in PASReqQueue table-C1915948,C1915949,C1915950,C1915951

Given "PlatformAlertingCreateAlertV2" : "<TestCase>" : I trigger create alert API
Then AlertID should get generated
#And I verify create alert api request in PAS_ReqQueue table
#And I verify create alert request is archived in PAS_ReqQueueArchive table
#And I verify an alert entry is created in pas_reqcons table on successful processing of an Alert request
#And I trigger update alert API
#And I verify update alert api request in PAS_ReqQueue table
#And I verify update alert request is archived in PAS_ReqQueueArchive table
#And I verify an alert entry is created in pas_reqcons table on successful processing of an update Alert request
#And I trigger auto close alert API
#And I verify delete alert api request in PAS_ReqQueue table
#And I verify delete alert request is archived in PAS_ReqQueueArchive table
#And I verify an archived alert entry is created in PAS_ReqConsArchive table on successfull processing of close alert request
#And I verify create alert api request is deleted from pas_reqcons table

Examples:
|TestCase|
|Platform001|
|Platform002|
|Platform003|
|Platform004|
#|Platform005|
|Platform006|
|Platform007|
|Platform008|
|Platform010|
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
#|Platform033|
#|Platform034|
#|Platform035|
#|Platform036|
#|Platform037|
#|Platform038|
#|Platform039|
#|Platform040|
#|Platform041|
#|Platform042|
#|Platform043|
#|Platform044|
#|Platform045|
#|Platform046|
#|Platform047|
#|Platform048|
#|Platform049|
#|Platform050|
#|Platform051|
#|Platform052|
#|Platform053|
#|Platform054|
#|Platform055|
#|Platform056|
#|Platform057|
#|Platform058|
#|Platform059|
#|Platform060|
#|Platform061|
#|Platform062|
#|Platform063|
#|Platform064|
#|Platform065|
#|Platform066|
#|Platform067|
#|Platform068|
#|Platform069|
#|Platform070|
#|Platform071|
#|Platform072|
#|Platform073|
#|Platform074|
#|Platform075|
#|Platform076|
#|Platform077|
#|Platform078|
#|Platform079|
#|Platform080|
#|Platform081|
#|Platform082|
#|Platform083|
#|Platform084|
#|Platform085|
#|Platform086|
#|Platform087|
#|Platform088|
#|Platform089|
#|Platform090|
#|Platform091|
#|Platform092|
#|Platform093|
#|Platform094|
#|Platform095|
#|Platform096|
#|Platform097|
#|Platform098|
#|Platform099|
#|Platform100|
#|Platform101|
#|Platform102|
#|Platform103|
#|Platform104|
#|Platform105|
#|Platform106|
#|Platform107|
#|Platform108|
#|Platform109|
#|Platform110|
#|Platform111|
#|Platform112|
#|Platform113|
#|Platform114|
#|Platform115|
#|Platform116|
#|Platform117|
#|Platform118|
#|Platform119|
#|Platform120|
#|Platform121|
#|Platform122|
#|Platform123|
#|Platform124|
#|Platform125|
#|Platform126|
#|Platform127|
#|Platform128|
#|Platform129|
#|Platform130|
#|Platform131|
#|Platform132|
#|Platform133|
#|Platform134|
#|Platform135|
#|Platform136|
#|Platform137|
#|Platform138|
#|Platform139|
#|Platform140|
#|Platform141|
#|Platform142|
#|Platform143|
#|Platform144|
#|Platform145|
#|Platform146|
#|Platform147|
#|Platform148|
#|Platform149|
#|Platform150|
#|Platform151|
#|Platform152|
#|Platform153|
#|Platform154|
#|Platform155|
#|Platform156|
#|Platform157|
#|Platform158|
#|Platform159|
#|Platform160|
#|Platform161|
#|Platform162|
#|Platform163|
#|Platform164|
#|Platform165|
#|Platform166|
#|Platform167|
#|Platform168|
#|Platform169|
#|Platform170|
#|Platform171|
#|Platform172|
#|Platform173|
#|Platform175|
#|Platform176|
#|Platform177|
#|Platform178|
#|Platform179|
#|Platform180|
#|Platform181|
#|Platform182|
#|Platform183|
#|Platform184|
#|Platform185|
#|Platform186|
#|Platform187|
#|Platform188|
#|Platform189|
#|Platform190|
#|Platform191|
#|Platform192|
#|Platform193|
#|Platform194|
#|Platform195|
#|Platform196|
#|Platform197|
#|Platform198|
#|Platform199|
#|Platform200|
#|Platform201|
#|Platform202|
#|Platform203|
#|Platform204|
#|Platform205|
#|Platform206|
#|Platform207|
#|Platform208|
#|Platform209|
#|Platform210|
#|Platform211|
#|Platform212|
#|Platform213|
#|Platform214|
#|Platform215|
#|Platform216|
#|Platform217|
#|Platform218|
#|Platform219|
#|Platform220|
#|Platform221|
#|Platform222|
#|Platform223|
#|Platform224|
#|Platform225|
#|Platform226|
#|Platform227|
#|Platform228|
#|Platform229|
#|Platform230|
#|Platform231|
#|Platform232|
#|Platform233|
#|Platform234|
#|Platform235|
#|Platform236|
#|Platform237|
#|Platform238|
#|Platform239|
#|Platform240|
#|Platform241|
#|Platform242|
#|Platform243|
#|Platform244|
#|Platform245|
#|Platform246|
#|Platform247|
#|Platform248|
#|Platform249|
#|Platform250|
#|Platform251|
#|Platform252|
#|Platform253|
#|Platform254|
#|Platform255|
#|Platform256|
#|Platform257|
#|Platform258|
#|Platform259|
#|Platform260|
#|Platform261|
#|Platform262|
#|Platform263|
#|Platform264|
#|Platform265|
#|Platform266|
#|Platform267|
#|Platform268|
#|Platform269|
#|Platform270|
#|Platform271|
#|Platform272|
#|Platform273|
#|Platform274|
#|Platform275|
#|Platform276|
#|Platform277|
#|Platform278|
#|Platform279|
#|Platform280|
#|Platform281|
#|Platform282|
#|Platform283|
#|Platform284|
#|Platform285|
#|Platform286|
#|Platform287|
#|Platform288|
#|Platform289|
#|Platform290|
#|Platform291|
#|Platform292|
#|Platform293|
#|Platform294|
#|Platform295|
#|Platform296|
#|Platform297|
#|Platform298|
#|Platform299|
#|Platform300|
#|Platform301|
#|Platform302|
#|Platform303|
#|Platform304|
#|Platform305|
#|Platform306|
#|Platform307|
#|Platform308|
#|Platform309|
#|Platform310|
#|Platform311|
#|Platform312|
#|Platform313|
#|Platform314|
#|Platform315|
#|Platform316|
#|Platform317|
#|Platform318|
#|Platform319|
#|Platform320|
#|Platform321|
#|Platform322|
#|Platform323|
#|Platform324|
#|Platform325|
#|Platform326|
#|Platform327|
#|Platform328|
#|Platform329|
#|Platform330|
#|Platform331|
#|Platform332|
#|Platform333|
#|Platform334|
#|Platform335|
#|Platform336|
#|Platform337|
#|Platform338|
#|Platform339|
#|Platform340|
#|Platform341|
#|Platform342|
#|Platform343|
#|Platform344|
#|Platform345|
#|Platform346|
#|Platform347|
#|Platform348|
#|Platform349|
#|Platform350|
#|Platform351|
#|Platform352|
#|Platform353|
#|Platform354|
#|Platform355|
#|Platform356|
#|Platform357|
#|Platform358|
#|Platform359|
#|Platform360|
#|Platform361|
#|Platform362|
#|Platform363|
#|Platform364|
#|Platform365|
#|Platform366|
#|Platform367|
#|Platform368|
#|Platform369|
#|Platform370|
#|Platform371|
#|Platform372|
#|Platform373|
#|Platform374|
#|Platform375|
#|Platform376|
#|Platform377|
#|Platform378|
#|Platform379|
#|Platform380|
#|Platform381|
#|Platform382|
#|Platform383|
#|Platform384|
#|Platform385|
#|Platform386|
#|Platform387|
#|Platform388|
#|Platform389|
#|Platform390|
#|Platform391|
#|Platform392|
#|Platform393|
#|Platform394|
#|Platform395|
#|Platform396|
#|Platform397|
#|Platform398|
#|Platform399|
#|Platform400|
#|Platform401|
#|Platform402|
#|Platform403|
#|Platform404|
#|Platform405|
#|Platform406|
#|Platform407|
#|Platform408|
#|Platform409|
#|Platform410|


#
#@V2Regression @102
#Scenario Outline: Verify Error Code 102 for create Alert api response for invalid partner value - C1915960
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with datatype for partner invalid
#Then I verify create api response code is 102 for invalid partener datatype
#
#Examples:
#|TestCase|
#|PartnerCode102|
#
#@V2Regression @102
#Scenario Outline: Verify Error Code 102 for create Alert api response for invalid site value - C1915961
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with datatype for site invalid
#Then I verify api response code is 102 for invalid site datatype
#Examples:
#|TestCase|
#|SiteCode102|
#
#@V2Regression @103
#Scenario Outline: Verify Error Code 103 for create Alert api response for invalid request body - C1915962
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with invalid request body
#Then I verify api response code is 103 for invalid request body
#Examples:
#|TestCase|
#|ErrorCode103|
#
#@V2Regression @104
#Scenario Outline: Verify Error Code 104 for create Alert api response for missing resource ID - C1915963
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with resource ID missing
#Then I verify create api response code is 104 for missing resource ID
#
#Examples:
#|TestCase|
#|ErrorCode104Res|
#
#@V2Regression @104
#Scenario Outline: Verify Error Code 104 for create Alert api response for missing condition ID - C1915964
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with condition ID missing
#Then I verify api response code is 104 for missing condition ID
#
#Examples:
#|TestCase|
#|ErrorCode104Con|
#
#@V2Regression
#Scenario Outline: Verify Error Code 104 for create Alert api response for missing request body - C1915965
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with request body missing
#Then I verify api response code is 104 for missing request body
#
#Examples:
#|TestCase|
#|ErrorCode104ReqBody|
#
#@V2Regression
#Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect partner ID - C1915966
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with incorrect partner ID
#Then I verify create api response code is 105 for incorrect partner ID
#
#Examples:
#|TestCase|
#|ErrorCode105Part|
#
#@V2Regression
#Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect site ID - C1915967
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with incorrect site ID
#Then I verify create api response code is 105 for incorrect site ID
#
#Examples:
#|TestCase|
#|ErrorCode105Site|
#
#@V2Regression
#Scenario Outline: Verify Error Code 105 for create Alert api response for incorrect resource ID - C1915968
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with incorrect resource ID
#Then I verify create api response code is 105 for incorrect resource ID
#
#Examples:
#|TestCase|
#|ErrorCode105Res|
#
#@V2Regression
#Scenario Outline: Verify Error Code 102 for update Alert api response for invalid partner value - C1933215
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with datatype for partner invalid
#Then I verify update api response code is 102 for invalid partener datatype
#
#Examples:
#|TestCase|
#|PartnerCode102|
#
#@V2Regression @Defect
#Scenario Outline: Verify Error Code 103 for update Alert api response for invalid request body - C1933216
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with invalid request body
#Then I verify update api response code is 103 for invalid request body
#Examples:
#|TestCase|
#|ErrorCode103|
#
#@V2Regression
#Scenario Outline: Verify Error Code 104 for update Alert api response for missing resource ID - C1933217
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with resource ID missing
#Then I verify update api response code is 104 for resource condition ID
#
#Examples:
#|TestCase|
#|ErrorCode104Res|
#
#@V2Regression
#Scenario Outline: Verify Error Code 104 for update Alert api response for missing condition ID - C1933218
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with condition ID missing
#Then I verify update api response code is 104 for missing condition ID
#
#Examples:
#|TestCase|
#|ErrorCode104Con|
#
#@V2Regression
#Scenario Outline: Verify Error Code 104 for update Alert api response for missing request body - C1933219
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with request body missing
#Then I verify update api response code is 104 for missing request body
#
#Examples:
#|TestCase|
#|ErrorCode104ReqBody|
#
#@V2Regression
#Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect partner ID - C1933220
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with incorrect partner ID
#Then I verify update api response code is 105 for incorrect partner ID
#
#Examples:
#|TestCase|
#|ErrorCode105Part|
#
#@V2Regression
#Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect site ID - C1933221
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with incorrect site ID
#Then I verify update api response code is 105 for incorrect site ID
#
#Examples:
#|TestCase|
#|ErrorCode105Site|

#@V2Regression
#Scenario Outline: Verify Error Code 105 for update Alert api response for incorrect resource ID - C1933222
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with incorrect resource ID
#Then I verify update api response code is 105 for incorrect resource ID

#Examples:
#|TestCase|
#|ErrorCode105Res|
#
#@V2Regression
#Scenario Outline: Verify Error Code 102 for delete Alert api response for invalid partner value - C1933225
#Given "CodesValidationV2" : "<TestCase>" : I trigger delete alert API request with datatype for partner invalid
#Then I verify delete api response code is 102 for invalid partener datatype
#
#Examples:
#|TestCase|
#|PartnerCode102|
#
#@V2Regression
#Scenario Outline: Verify Error Code 404 for delete Alert api response for invalid alert value - C1933227, C2130258
#Given "CodesValidationV2" : "<TestCase>" : I trigger delete alert API request with invalid alertID
#Then I verify delete api response code is 203 for invalid alertID
#Then I verify delete api status code is 404 for invalid alertID
#
#Examples:
#|TestCase|
#|ErrorCode103|
#
#@V2Regression
#Scenario Outline: Verify Error Code 404 for update Alert api response for invalid alert value - C1933228, C2130259
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with invalid alertID
#Then I verify update api response code is 203 for invalid alertID
#Then I verify update api status code is 404 for invalid alertID
#
#Examples:
#|TestCase|
#|ErrorCode103|
#
#@V2Regression @202
#Scenario Outline: Verify Error Code 202 for create Alert api response when alert already exist - C1933226
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request when alert already exist
#Then I verify create api response code is 202 when alert already exist
#
#Examples:
#|TestCase|
#|ErrorCode103|
#
#@V2Regression @107
#Scenario Outline: Verify Error Code 107 for create Alert api response for incorrect condition ID - C1930254
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with incorrect condition ID
#Then I verify create api response code is 107 triggered with incorrect condition ID
#
#Examples:
#|TestCase|
#|ErrorCode107|
#
#@V2Regression
#Scenario Outline: Verify Error Code 108 for create Alert api response for incorrect post body - C1930255
#Given "CodesValidationV2" : "<TestCase>" : I trigger create alert API request with incorrect post  ID
#Then I verify create api response code is 108 triggered with incorrect post ID
#
#Examples:
#|TestCase|
#|ErrorCode108|



#@V2Regression
#Scenario Outline: Verify Error Code 108 for update Alert api response for incorrect post body - C1930256
#Given "CodesValidationV2" : "<TestCase>" : I trigger update alert API request with incorrect post  ID
#Then I verify update api response code is 108 triggered with incorrect post ID

#Examples:
#|TestCase|
#|ErrorCode108|

#
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
#
