package com.continuum.platform.alerting.api;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JsonRespParserUtility;
import com.continuum.utils.JunoAlertingAPIUtil;
import com.continuum.utils.JunoAlertingUtils;
import continuum.cucumber.KafkaProducerUtility;
import continuum.cucumber.Utilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlertingAPITest {

    private Logger logger = Logger.getLogger(AlertingAPITest.class);
    private String alertDetails, itsmUrl, testName, alertFailure, alertState,alertAutomationData, currentAlert, alertingAPIUrl, itsmIncidentDetails, itsmIncidentID, itsmPublicID, appender, itsmAPIUrl = "";
    private static String alertingUrl, kafkaServer, itsmIntegrationUrl, jasUrl;
    private Response alertingResponse;
    private List<String> alertId = new ArrayList<String>();
    private List<String> conditionId = new ArrayList<String>();
    private HashMap<String, String> actualDataInITSM = new HashMap<String, String>();
    private JSONArray filterArray = new JSONArray();
    private HashMap<String, String> currentRow = new HashMap<String, String>();


    public String getCurrentAlert() {
        return currentAlert;
    }

    public void setCurrentAlert(String currentAlert) {
        this.currentAlert = currentAlert;
    }

    public HashMap<String, String> getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(HashMap<String, String> currentRow) {
        this.currentRow = currentRow;
    }

    public JSONArray getFilterArray() {
        return filterArray;
    }

    public void setFilterArray(JSONArray filterArray) {
        this.filterArray.addAll(filterArray);
    }

    public void clearFilterArray() {
        this.filterArray.clear();
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setActualDataInITSM(String key, String value) {
        this.actualDataInITSM.put(key, value);
    }

    public String getActualDataInITSM(String key) {
        return this.actualDataInITSM.get(key);
    }

    public List<String> getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId.add(alertId);
    }

    public void setITSMIncidentId(String incidentID) {
        itsmIncidentID = incidentID;
    }

    public void setITSMPublicId(String publicID) {
        itsmPublicID = publicID;
    }

    public String getITSMIncidentId() {
        return itsmIncidentID;
    }

    public String getITSMPublicId() {
        return itsmPublicID;
    }

    public List<String> getConditionId() {
        return conditionId;
    }

    public void setConditionId(String alertId) {
        this.conditionId.add(alertId);
    }

    public static String getalertingUrl() {
        return alertingUrl;
    }

    public static void setalertingUrl(String alertingUrl) {
        AlertingAPITest.alertingUrl = alertingUrl;
    }

    public static void setItsmIntegrationUrl(String itsmUrl) {
        AlertingAPITest.itsmIntegrationUrl = itsmUrl;
    }

    public String getAlertDetails() {
        return alertDetails;
    }

    public void setAlertDetails(String alertDetails) {
        this.alertDetails = alertDetails;
    }

    public void setItsmIncidentDetails(String details) {
        this.itsmIncidentDetails = details;
    }

    public String getItsmUrl() {
        return itsmUrl;
    }

    public void setItsmUrl(String itsmUrl) {
        this.itsmUrl = itsmUrl;
    }

    public String getAlertFailureUrl() {
        return alertFailure;
    }

    public void setAlertFailureUrl(String alertFailureUrl) {
        this.alertFailure = alertFailureUrl;
    }

    public String getJasUrl() {
        return jasUrl;
    }

    public static void setJasUrl(String jasUrl1) {
        jasUrl = jasUrl1;
    }

    public Response getAlertDetailsResponse() {
        return alertingResponse;
    }

    public void setAlertDetailsResponse(Response alertingResponse) {
        this.alertingResponse = alertingResponse;
    }

    public static String getKafkaServer() {
        return kafkaServer;
    }

    public static void setKafkaServer(String kafkaServer) {
        AlertingAPITest.kafkaServer = kafkaServer;
    }

    public String triggerCreateAPI(String testName) {
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessing(getTestName());
            setConditionId(currentRow.get("conditionId"));
            logger.info("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingAPIUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert Creation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Exception Occurred in Alert Creation Failed " + e.getMessage() + " ]";
        }
        return errMsg;
    }

    public String reprocessAlert(String count) {
        String errMsg = "";
        try {
            String url = alertingUrl + Utilities.getMavenProperties("ReprocessAlert");
            String body = "[{\"alertId\" : \"" + alertId.get(0) + "\"}]";
            logger.info("Body : " + body);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(body, url));
            return errMsg;
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Reprocess Alert Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Reprocess Alert Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //  return false;
        }
    }

    public String verifyDuplicateAlertMsgInResponse() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                logger.info("Reprocessed of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                JSONArray respArray = (JSONArray) json;
                if (respArray == null) {
                    System.out.println("No Data Present in ITSM Simulator");
                    errMsg = errMsg + "[No Data Present in ITSM Simulator]";
                    return errMsg;
                    //return false;
                }
                int i = 0;
                while (i < respArray.size()) {
                    JSONObject resObject = respArray.getJSONObject(i);
                    if (alertId.contains((resObject.get("DuplicateAlertId").toString()))) {
                        //return true;
                        return errMsg;
                    }
                    i++;
                }
                //return false;
                errMsg = errMsg + "[No Data Present in ITSM Simulator]";
                return errMsg;
            } else {
                logger.info("Alert Not Reprocessed with Response Code : " + alertingResponse.getStatusCode());
                logger.info("Alert Not Reprocessed with Message : " + alertingResponse);
                errMsg = errMsg + "[Alert Not Reprocessed with Message : " + alertingResponse + " ]";
                errMsg = errMsg + "[Alert Not Reprocessed with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Alert Reprocessed Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Reprocessed Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }


    public String triggerCreateITSM_API(String testName) {
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessingITSM(getTestName());
            setConditionId(currentRow.get("conditionId"));
            logger.info("Incident Details : " + itsmIncidentDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(itsmIncidentDetails, itsmIntegrationUrl));
            //return true;
            return errMsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Incident Creation Failed with Error Message : " + e.getMessage());
            errMsg = "[Incident Creation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String triggerCreateITSM_API_AlertMS() {
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessingITSM(getTestName());
            setConditionId(currentRow.get("conditionId"));
            logger.info("Incident Details : " + itsmIncidentDetails);
            Thread.sleep(5000);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(itsmIncidentDetails, itsmAPIUrl));
            return errMsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Incident Creation Failed with Error Message : " + e.getMessage());
            errMsg = "[Incident Creation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String triggerParentCreateAPI(String testName) {
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessing(getTestName());
            JSON json = JSONSerializer.toJSON(alertDetails);
            JSONObject parentAlertDetails = (JSONObject) json;
            JSONArray alerts = new JSONArray();
            alerts.addAll(alertId);
            parentAlertDetails.put("alerts", alerts);
            logger.info(parentAlertDetails.toString());
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
            Thread.sleep(3000);
            return errMsg;
            // return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Parent Alert Creation Failed with Error Message : " + e.getMessage());
            //return false;
            errMsg = errMsg + "[Parent Alert Creation Failed with Error Message : " + e.getMessage() + "]";
            return errMsg;
        }
    }

    public String triggerParentUpdateAPI(String testName, int parent,int child) {
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessing(getTestName());
            JSON json = JSONSerializer.toJSON(alertDetails);
            JSONObject parentAlertDetails = (JSONObject) json;
            JSONArray alerts = new JSONArray();
            alerts.add(alertId.get(child));
            parentAlertDetails.put("alerts", alerts);
            logger.info(parentAlertDetails.toString());
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl + "/" + alertId.get(parent)));
            Thread.sleep(3000);
            return errMsg;
            // return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Parent Alert Creation Failed with Error Message : " + e.getMessage());
            //return false;
            errMsg = errMsg + "[Parent Alert Creation Failed with Error Message : " + e.getMessage() + "]";
            return errMsg;
        }
    }

    public String triggerParentWithSameConditionCreateAPI(String testName) {
        String errMsg = "";
        try {
            for (int i = 0; i < 4; i++) {
                setTestName(testName);
                preProcessing(getTestName());
                JSON json = JSONSerializer.toJSON(alertDetails);
                JSONObject parentAlertDetails = (JSONObject) json;
                JSONArray alerts = new JSONArray();
                alerts.add(alertId.get(i));
                alerts.add(alertId.get(i + 1));
                parentAlertDetails.put("alerts", alerts);
                logger.info(parentAlertDetails.toString());
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
                Thread.sleep(3000);
                if (alertingResponse.getStatusCode() == 201) {
                    logger.info(alertingResponse.getBody().asString());
                    setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    logger.info("Alert Created : " + getCurrentAlert());
                    i++;
                } else if (alertingResponse.getStatusCode() == 409) {
                    logger.info("Parent Alert not Created with Error Conflict");
                    errMsg = errMsg + "[Parent Alert not Created with Error Conflict]";
                    return errMsg;
                    //return false;
                } else {
                    logger.info("Parent Alert no created with Response Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Parent Alert no created with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                    //return false;
                }
            }
            //return true;
            return errMsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Parent Alert Creation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Parent Alert Creation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String triggerUpdateAPI() {
        String errMsg = "";
        try {
            preProcessing(getTestName());
            logger.info("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
            return errMsg;
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String triggerUpdateAPIChild(String testName, String countS) {
        String errMsg = "";
        int i = 0;
        if(countS.equalsIgnoreCase("first")){
            i = 0 ;
        }else if(countS.equalsIgnoreCase("second")){
            i = 1 ;
        }else if(countS.equalsIgnoreCase("third")){
            i = 2 ;
        }
        try {
            setTestName(testName);
            preProcessing(getTestName());
            logger.info("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
            return errMsg;
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String triggerUpdateAPIErrorResponse(String errorCode) {
        String errMsg = "";
        try {
            preProcessing(getTestName());
            appender = JunoAlertingUtils.timeStamp();
            setAlertDetails(appendITSMSimulatorErrorCode(getAlertDetails(), errorCode).replace("POST 1", "PUT_" + appender));
            logger.info("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
            return errMsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String appendITSMSimulatorErrorCode(String msg, String errorCode) {
        String details = msg;
        details = details.substring(0, details.length() - 1) + ",\n" +
                "    \"itsmSimulator\":{\n" +
                "        \"statusCode\":" + errorCode + "\n" +
                "    }\n" +
                "}";
        return details;
    }

    public String triggerUpdateAPI_ITSM() {
        String errMsg = "";
        try {
            preProcessingITSM(getTestName());
            logger.info("Incident Details : " + itsmIncidentDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(itsmIncidentDetails, itsmAPIUrl + "/" + getITSMIncidentId()));
            return errMsg;
            //return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Incident Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Incident Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            // return false;
        }
    }

    public String triggerDeleteAPI_ITSM() {
        String errMsg = "";
        try {
            preProcessingITSM(getTestName());
            logger.info("Incident Details : " + itsmIncidentDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(itsmIncidentDetails, itsmAPIUrl + "/" + getITSMIncidentId() + "/close"));
            return errMsg;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Incident Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Incident Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public boolean triggerDeleteAPI() {
        try {
            int i = 0;
            if (alertId.size() > 1) {
                for (i = 0; i < alertId.size(); i++) {
                    this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                    if (alertingResponse.getStatusCode() != 204) {
                        logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                        return false;
                    }
                    logger.info("Alert Deleted : " + alertId.get(i));
                }
            } else {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                logger.info("Alert Deletion Called for AlertID : " + alertId.get(i));
                return true;
            }
            return true;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public String triggerDeleteAPIWithBody() {
        String errMsg = "";
        try {
            int i = 0;
            if (alertId.size() > 1) {
                for (i = 0; i < alertId.size(); i++) {
                    this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
                    if (alertingResponse.getStatusCode() != 204) {
                        logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + " with Response Code : " + alertingResponse.getStatusCode());
                        errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + " with Response Code : " + alertingResponse.getStatusCode() + " ]";
                        return errMsg;
                    }
                    logger.info("Alert Deleted : " + alertId.get(i));
                }
            } else {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
                logger.info("Alert Deletion Called for AlertID : " + alertId.get(i));
                return errMsg;
                //  return true;
            }
            // return true;
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String triggerLastAlertDeleteAPIWithBody() {
        String errMsg = "";
        try {
            int i = alertId.size() - 1;
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
            if (alertingResponse.getStatusCode() != 204) {
                logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
            logger.info("Alert Deleted : " + alertId.get(i));
            return errMsg;
            //return true;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String triggerChildDeleteAPI() {
        String errMsg = "";
        try {
            for (int i = 0; i < alertId.size() - 1; i++) {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                if (alertingResponse.getStatusCode() != 204) {
                    logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                    //return false;
                }
                logger.info("Alert Deleted : " + alertId.get(i));
                Thread.sleep(5000);
            }
            logger.info("Alerts Deleted!!");
            //return true;
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            // return false;
        }
    }

    public String triggerChildDeleteAPI(int i) {
        String errMsg = "";
        try {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                if (alertingResponse.getStatusCode() != 204) {
                    logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                }
            logger.info("Alerts Deleted : " + alertId.get(i));
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String triggerAllChildDeleteAtATime() {
        String errMsg = "";
        try {
            int j = 0 ;
            for (int i = 0 ; i < alertId.size() - 1 ; i++) {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(j)));
                j ++ ;
                if (alertingResponse.getStatusCode() != 204) {
                    logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + " with Response Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + " with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                }
                logger.info("Alert Deleted : " + alertId.get(j-1));
            }
            logger.info("All Child Alerts Deleted!!");
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String triggerChildDeleteAPIForBothParent() {
        String errMsg = "";
        try {
            for (int i = 0; i < alertId.size() - 2; i++) {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                if (alertingResponse.getStatusCode() != 204) {
                    logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                    //return false;
                }
                logger.info("Alert Deleted : " + alertId.get(i));
                Thread.sleep(2000);
            }
            logger.info("Alerts Deleted!!");
            //return true;
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            // return false;
        }
    }

    public String triggerDeleteAPI(String alertID) {
        String errMsg = "";
        try {
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertID));
            if (alertingResponse.getStatusCode() != 204) {
                logger.info("Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                // return false;
            }
            logger.info("Alert Deleted : " + alertID);
            if (conditionId.contains(currentRow.get("conditionId"))) {
                conditionId.remove(currentRow.get("conditionId"));
            }
            //return true;
            return errMsg;

        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public void preProcessing(String testName) throws Exception {

        setCurrentRow(DataUtils.getTestRow("Test", testName));
        logger.info("Test Data Captured.");

        logger.info("Getting Host URL:" + alertingUrl);

        alertingAPIUrl = alertingUrl + Utilities.getMavenProperties("AlertingUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{endpoints}", currentRow.get("endpoints"));

        /*if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    alertingAPIUrl = alertingAPIUrl.replace("sites//endpoints//", "");
                } else {
                    alertingAPIUrl = alertingAPIUrl.replace("endpoints//", "");
                    alertingAPIUrl = alertingAPIUrl.replace("sites/", "clients/{clients}");
                    alertingAPIUrl = alertingAPIUrl.replace("{clients}", currentRow.get("clients"));
                }
            } else {
                alertingAPIUrl = alertingAPIUrl.replace("endpoints//", "");
                alertingAPIUrl = alertingAPIUrl.replace("sites", "clients/{clients}/sites");
                alertingAPIUrl = alertingAPIUrl.replace("{clients}", currentRow.get("clients"));
            }
        }*/

        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    alertingAPIUrl = alertingAPIUrl.replace("clients//sites//endpoints//", "");
                } else {
                    alertingAPIUrl = alertingAPIUrl.replace("sites//endpoints//", "");
                }
            } else {
                alertingAPIUrl = alertingAPIUrl.replace("endpoints//", "");
            }
        }

        setAlertDetails(currentRow.get("alertDetails"));

        logger.info(alertingAPIUrl);

    }

    public void preProcessingITSM(String testName) throws Exception {

        setCurrentRow(DataUtils.getTestRow("Test", testName));
        logger.info("Test Data Captured.");

        logger.info("Getting Host URL itsmIntegrationUrl:" + itsmIntegrationUrl);

        itsmAPIUrl = itsmIntegrationUrl + Utilities.getMavenProperties("ITSMUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{endpoints}", currentRow.get("endpoints"));

        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    itsmAPIUrl = itsmAPIUrl.replace("clients//sites//endpoints//", "");
                } else {
                    itsmAPIUrl = itsmAPIUrl.replace("sites//endpoints//", "");
                }
            } else {
                itsmAPIUrl = itsmAPIUrl.replace("endpoints//", "");
            }
        }

        setAlertDetails(currentRow.get("alertDetails"));
        setItsmIncidentDetails(currentRow.get("itsmPayload").replace("{alertID_AlertingMS}", getCurrentAlert()));
        logger.info(itsmAPIUrl);

    }

    public String verifyCreateAPIResponse() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerCreateAPI(getTestName());
                    return verifyCreateAPIResponse();
                } else {
                    logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode() + " ]";
                }
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.info("Alert Created : " + getCurrentAlert());
            } else {
                logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
            }
        } catch (Exception e) {
            logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Verification Failed with Error Message : " + e.getMessage() + " ]";
        }
        return errMsg;
    }

    public String verifyCreateAPIResponseParent() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerParentCreateAPI(getTestName());
                    return verifyCreateAPIResponseParent();
                } else {
                    logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode() + " ]";
                }
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.info("Alert Created : " + getCurrentAlert());
            } else {
                logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
            }
        } catch (Exception e) {
            logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Verification Failed with Error Message : " + e.getMessage() + " ]";
        }
        return errMsg;
    }

    public String verifyCreateAPIResponseITSM() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                setITSMIncidentId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setITSMPublicId(JsonPath.from(alertingResponse.getBody().asString()).get("publicId"));
                errMsg = errMsg + "[Error Code" + alertingResponse.getBody().asString() + " ]";
                return errMsg;
            } else if (alertingResponse.getStatusCode() == 200) {
                logger.info(alertingResponse.getBody().asString());
                setITSMIncidentId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setITSMPublicId(JsonPath.from(alertingResponse.getBody().asString()).get("publicId"));
                logger.info("Incident Created in ITSM : " + getITSMIncidentId());
                //return true;
                return errMsg;
            } else {
                logger.info("Incident Not Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Incident Not Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Incident Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Incident Verification Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String verifyDuplicateCreateAPIResponseITSM() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                setITSMIncidentId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setITSMPublicId(JsonPath.from(alertingResponse.getBody().asString()).get("publicId"));
                logger.info("Duplicate Incident found as expected : " + getITSMIncidentId());
                return errMsg;
            } else if (alertingResponse.getStatusCode() == 200) {
                logger.info(alertingResponse.getBody().asString());
                setITSMIncidentId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setITSMPublicId(JsonPath.from(alertingResponse.getBody().asString()).get("publicId"));
                logger.info("Incident Created in ITSM : " + getITSMIncidentId());
                logger.info("Public ID Created in ITSM : " + getITSMPublicId());
                errMsg = errMsg + "[Incident Created in ITSM : " + alertingResponse.getBody().asString() + " ]";
                return errMsg;
            } else {
                logger.info("Incident Not Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Incident Not Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Incident Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Incident Verification Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }


    public String verifyAlertSuspension() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerCreateAPI(getTestName());
                    return verifyAlertSuspension();
                } else {
                    logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                    //return false;
                }
            } else if (alertingResponse.getStatusCode() == 400) {
                logger.info(alertingResponse.getBody().asString());
                JSONObject suspensionResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (suspensionResponse.getInt("status") == 205) {
                    logger.info("Alert Not Created because of suspension with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
                    //return true;
                    return errMsg;
                }
                logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
                errMsg = errMsg + "[Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status") + " ]";
                return errMsg;
                //return false;
            } else {
                logger.info("Alert Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Verification Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }

    }

    public String verifyNewAlertCreation() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                if (JsonPath.from(alertingResponse.getBody().asString()).get("alertId").equals(currentAlert)) {
                    logger.info(alertingResponse.getBody().asString());
                    logger.info("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
                    errMsg = errMsg + "[New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode() + " ]";
                    errMsg = errMsg + "[New ALert is not getting created, Getting Conflict : " + alertingResponse.getBody().asString() + " ]";
                    return errMsg;
                    //return false;
                } else {
                    logger.info(alertingResponse.getBody().asString());
                    setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    triggerDeleteAPI(currentAlert);
                    if (alertingResponse.getStatusCode() == 204) {
                        alertId.remove(alertId.size() - 1);
                        Thread.sleep(5000);
                        triggerCreateAPI(getTestName());
                        return verifyNewAlertCreation();
                    } else {
                        logger.info("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                        errMsg = errMsg + "[Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode() + " ]";
                        return errMsg;
                        //return false;
                    }
                }
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.info("Alert Created : " + getCurrentAlert());
                return errMsg;
                // return true;
            } else {
                logger.info("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Alert Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Verification Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String verifyDuplicateAlertCreation() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.info(alertingResponse.getBody().asString());
                logger.info("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
                //setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                return errMsg;
                //return true;
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.info(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.info("Alert Created : " + getCurrentAlert());
                errMsg = errMsg + "[Alert Created : " + getCurrentAlert() + " ]";
                return errMsg;
                //return false;
            } else {
                logger.info("Alert Not giving Conflict with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Not giving Conflict with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //  return false;
            }
        } catch (Exception e) {
            logger.info("Duplicate Alert Verification Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Duplicate Alert Verification Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            // return false;
        }
    }

    public String verifyUpdateAPIResponse() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 204) {
                logger.info("Update of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                //  return true;
                return errMsg;
            } else {
                logger.info("Alert Not Updated with Response Code : " + alertingResponse.getStatusCode());
                logger.info("Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                errMsg = errMsg + "[Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status") + " ]";
                errMsg = errMsg + "[Alert Not Updated with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //   return false;
            }
        } catch (Exception e) {
            logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //  return false;
        }
    }

    public String verifyUpdateAPIResponse(String responseCode, String verb) throws InterruptedException {
        String errMsg = "";
        int count = 5 ;
        for (int i = 0; i < count; i++) {
            logger.info("Try Count ------> " + i);
            if(i != 0){
                Thread.sleep(10000);
                if(verb.equalsIgnoreCase("update")){
                    triggerUpdateAPI_ITSM();
                }else{
                    triggerDeleteAPI_ITSM();
                }
            }
            try {
                if (alertingResponse.getStatusCode() == Integer.parseInt(responseCode)) {
                    logger.info("Actual Response Code found : " + alertingResponse.getStatusCode());
                    logger.info("Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                    return errMsg;
                } else {
                    logger.info("Expected Response Not Code found. Actual -> " + alertingResponse.getStatusCode());
                    logger.info("Internal Status Code : " + alertingResponse.toString());
                    if(i == count) {
                        errMsg = errMsg + "[Expected Response Not Code found. Actual -> " + alertingResponse.getStatusCode() + " ]";
                        errMsg = errMsg + "[Internal Status Code : " + alertingResponse.toString() + " ]";
                        return errMsg;
                    }else{
                        continue ;
                    }
                }
            } catch (Exception e) {
                if(i == count) {
                    logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
                    errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
                    return errMsg;
                }else{
                    continue ;
                }
            }


        } // for loop
        return errMsg;
    }



    public String verifyUpdateAPIResponseITSM() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                logger.info("Update of ITSM Alert Done with Status Code : " + alertingResponse.getStatusCode());
                return errMsg;
                //return true;
            } else {
                logger.info("ITSM Incident Not Updated with Response Code : " + alertingResponse.getStatusCode());
                logger.info("ITSM Incident Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                errMsg = errMsg + "[ITSM Incident Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status") + " ]";
                errMsg = errMsg + "[ITSM Incident Not Updated with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("ITSM Incident Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[ITSM Incident Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            // return false;
        }
    }

    public String verifyNonExistingAlertAPIResponse() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 404) {
                if (JsonPath.from(alertingResponse.getBody().asString()).get("status").equals("203"))
                    logger.info("Update/Delete of Alert Failed with Status Code : " + alertingResponse.getStatusCode() + " And Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                //return true;
                return errMsg;
            } else if (alertingResponse.getStatusCode() == 204) {
                logger.info("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
                //return false;
                errMsg = errMsg + "[Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            } else {
                logger.info("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Alert Updation/Deletion Passed : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation/Deletion Passed : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }

    }

    public String verifyUpdateAPIResponseWithSnooze() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 202) {
                logger.info("Update of Alert Snoozed Status Code : " + alertingResponse.getStatusCode());
                return errMsg;
            } else if (alertingResponse.getStatusCode() == 204) {
                logger.info("Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
            logger.info("Alert Update is giving Response Code : " + alertingResponse.getStatusCode());
            errMsg = errMsg + "[Alert Update is giving Response Code : " + alertingResponse.getStatusCode() + " ]";
            return errMsg;
        } catch (Exception e) {
            logger.info("Alert Updation Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Updation Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String verifyDeleteAPIResponse() {
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 204) {
                logger.info("Delete of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                return errMsg;
                //return true;
            } else {
                logger.info("Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode());
                logger.info("Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                errMsg = errMsg + "[Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode() + " ]";
                errMsg = errMsg + "[Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status") + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "[Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public void triggerITSMSimulatorAPI() {
        try {
            itsmPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void itsmPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());
        logger.info("Getting Host URL:" + alertingUrl);


        itsmUrl = alertingUrl + Utilities.getMavenProperties("ITSMSimulatorUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{conditionId}", currentRow.get("conditionId"));

        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    itsmUrl = itsmUrl.replace("/clients//sites/", "");
                } else {
                    itsmUrl = itsmUrl.replace("/sites/", "");
                }
            }
        }

        logger.info(itsmUrl);

    }

    public void triggerAlertFailureAPI() {
        try {
            alertFailurePreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertFailure));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void triggerAlertStateAPI() {
        try {
            alertStatePreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertState));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert State API Call Failed With Error : " + e.getMessage());
        }
    }

    public void triggerAlertStateAPIParent(int parent) {
        try {
            alertStatePreProcessingParent(parent);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertState));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert State API Call Failed With Error : " + e.getMessage());
        }
    }

    public void alertFailurePreProcessing() throws Exception {
        logger.info("Getting Host URL:" + alertingUrl);
        alertFailure = alertingUrl + Utilities.getMavenProperties("AlertFailureUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{conditionId}", currentRow.get("conditionId"));
        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    alertFailure = alertFailure.replace("/clients//sites/", "");
                } else {
                    alertFailure = alertFailure.replace("/sites/", "");
                }
            }
        }
        logger.info("alertFailure : " + alertFailure);
    }

    public void alertStatePreProcessing() throws Exception {
        logger.info("Getting Host URL:" + alertingUrl);
        alertState = alertingUrl + Utilities.getMavenProperties("AlertStateUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{alertId}", getCurrentAlert());
        if (currentRow.get("sites").isEmpty()) {
            alertState = alertState.replace("/sites/", "");
        }
        if (currentRow.get("clients").isEmpty()) {
            alertState = alertState.replace("/clients/", "");
        }
        if (currentRow.get("partners").isEmpty()) {
            alertState = alertState.replace("/partners/", "");
        }
        logger.info("alertState API : " + alertState);
    }

    public void alertStatePreProcessingParent(int parent) throws Exception {
        logger.info("Getting Host URL:" + alertingUrl);
        alertState = alertingUrl + Utilities.getMavenProperties("AlertStateUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{alertId}", alertId.get(parent));
        if (currentRow.get("sites").isEmpty()) {
            alertState = alertState.replace("/sites/", "");
        }
        if (currentRow.get("clients").isEmpty()) {
            alertState = alertState.replace("/clients/", "");
        }
        if (currentRow.get("partners").isEmpty()) {
            alertState = alertState.replace("/partners/", "");
        }
        logger.info("alertState API : " + alertState);
    }

    public String getITSMSimulatorResponse() throws InterruptedException {
        String errMsg = "";
        Thread.sleep(5000);
        triggerITSMSimulatorAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                for (String alertID : alertId)
                    setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                //return true;
                return errMsg;
            } else {
                logger.info("Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode());
                //return false;
                errMsg = errMsg + "[Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Failed to save ITSM Response : " + e.getMessage());
            //return false;
            errMsg = errMsg + "[Failed to save ITSM Response : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    public String getAlertFailureResponse() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertFailureAPI();
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                for (String alertID : alertId)
                    setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                    if(filterArray==null){
                        logger.info("No Data in Filter array.");
                        errMsg = errMsg + "No Data in Filter array.";
                        return errMsg;
                    }
                //return true;
                return errMsg;
            } else {
                logger.info("Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode());
                //return false;
                errMsg = errMsg + "[Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Failed to save  Alert Failure Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save  Alert Failure Response : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String getAlertStateResponse() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertStateAPI();
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, getCurrentAlert()));
                return errMsg;
                // return true;
            } else {
                logger.info("Request to Alert State with Response Code : " + alertingResponse.getStatusCode());
                //return false;
                errMsg = errMsg + "[Request to Alert State with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Failed to save  Alert State Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save  Alert State Response : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String getAlertStateResponseParent(int parent) throws InterruptedException {
        Thread.sleep(5000);
        clearFilterArray();
        triggerAlertStateAPIParent(parent);
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertId.get(parent)));
                return errMsg;
                // return true;
            } else {
                logger.info("Request to Alert State with Response Code : " + alertingResponse.getStatusCode());
                //return false;
                errMsg = errMsg + "[Request to Alert State with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Failed to save  Alert State Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save  Alert State Response : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String getAlertFailureResponseNotPresent() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertFailureAPI();
        String errMsg = "";
        JSONArray filterArray1 = new JSONArray();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                try {
                    for (String alertID : alertId)
                        filterArray1.add(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                    int i = 0;
                    while (i < filterArray1.size()) {
                        JSONArray filterArray2 = null;
                        filterArray2 = (JSONArray) filterArray1.get(i);
                        if (filterArray2 == null) {
                            System.out.println("No Data Present in response as expected");
                            return errMsg;
                        }
                        if (filterArray2.size() < 1) {
                            i++;
                        } else {
                            errMsg = errMsg + "[Alert Not present in ITSM]";
                            return errMsg;
                        }
                    }
                } catch (Exception e) {

                }
                // return true;
                return errMsg;
            } else {
                logger.info("Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Failed to save  Alert Failure Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save  Alert Failure Response : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String verifyAlertCreationInJAS() throws InterruptedException {
        Thread.sleep(3000);
        triggerJASGetAlertAPI();
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
                    logger.info("Alert Id " + currentAlert + " Created in JAS.");
                    // return true;
                    return errMsg;
                } else {
                    logger.info("ALert Id Not Created in JAS!!");
                    errMsg = errMsg + "[ALert Id Not Created in JAS!!]";
                    return errMsg;
                    //  return false;
                }

            } else {
                logger.info("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //  return false;
            }
        } catch (Exception e) {
            logger.info("Failed to save JAS Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save JAS Response : " + e.getMessage() + " ]";
            return errMsg;
            //  return false;
        }
    }

    public String verifyAlertDeletionInJAS() throws InterruptedException {
        int i = 0;
        String errMsg = "";
        while (i < 10) {
            Thread.sleep(10000);
            triggerJASGetAlertAPI();
            try {
                if (alertingResponse.getStatusCode() == 404) {
                    JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                    if (jasResponse.get("status").equals(203)) {
                        logger.info("Alert Id " + currentAlert + " Deleted from JAS.");
                        return errMsg;
                        //return true;
                    } else {
                        logger.info("Alert Id Not Deleted from JAS!!");
                        errMsg = errMsg + "[Alert Id Not Deleted from JAS!!]";
                        return errMsg;
                        //return false;
                    }

                } else {
                    logger.info("Delete Request Pending with Response Code : " + alertingResponse.getStatusCode());
                    i++;
                    continue;
                }
            } catch (Exception e) {
                logger.info("Failed to save JAS Response : " + e.getMessage());
                errMsg = errMsg + "[Failed to save JAS Response : " + e.getMessage() + " ]";
                return errMsg;
                //return false;
            }
        }
        logger.info("Alert ID " + currentAlert + " Not Deleted!!");
        errMsg = errMsg + "[Alert ID " + currentAlert + " Not Deleted!!" + " ]";
        return errMsg;
        //return false;
    }

    public void triggerJASGetAlertAPI() {
        try {
            jasPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(jasUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void jasPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());

        setJasUrl(Utilities.getMavenProperties("DTJASHostUrlV1"));
        logger.info("Getting Host URL:" + jasUrl);
        jasUrl = jasUrl + Utilities.getMavenProperties("GetJASAlertUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{alert}", currentAlert);

        logger.info(jasUrl);
    }

    public void triggerAlertingGetAlertAPI() {
        try {
            getAlertPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertingAPIUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void getAlertPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());

        logger.info("Getting Host URL:" + alertingUrl);
        alertingAPIUrl = alertingUrl + Utilities.getMavenProperties("GetAlertUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{alert}", currentAlert);

        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    alertingAPIUrl = alertingAPIUrl.replace("/clients//sites/", "");
                } else {
                    alertingAPIUrl = alertingAPIUrl.replace("/sites/", "");
                }
            }
        }

        logger.info(alertingAPIUrl);
    }

    public String verifyAlertCreationInAlertingMS() throws InterruptedException {
        Thread.sleep(3000);
        triggerAlertingGetAlertAPI();
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
                    logger.info("Alert Id " + currentAlert + " Created in JAS.");
                    return errMsg;
                    //return true;
                } else {
                    logger.info("ALert Id Not Created in JAS!!");
                    errMsg = errMsg + "[ALert Id Not Created in JAS!!]";
                    return errMsg;
                    // return false;
                }

            } else {
                logger.info("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Failed to save JAS Response : " + e.getMessage());
            errMsg = errMsg + "[Failed to save JAS Response : " + e.getMessage() + "]";
            return errMsg;
            //return false;
        }

    }

    public String verifyAlertDeletionInAlertingMS() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertingGetAlertAPI();
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 404) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(203)) {
                    logger.info("Alert Id " + currentAlert + " Deleted from JAS.");
                    return errMsg;
                    //return true;
                } else {
                    logger.info("Alert Id Not Deleted from JAS!!");
                    errMsg = errMsg + "[Alert Id Not Deleted from JAS!!]";
                    return errMsg;
                    //return false;
                }

            } else {
                logger.info("Alert ID " + currentAlert + " Not Deleted!!");
                errMsg = errMsg + "[Alert ID " + currentAlert + " Not Deleted!!]";
                return errMsg;
                //return false;
            }
        } catch (Exception e) {
            logger.info("Exception Occurred : " + e.getMessage());
            errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }
    }

    public String triggerAlertDelete(int alertCount, String testName){
        String errMsg = "";
        try {
            setTestName(testName);
            preProcessing(getTestName());
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(alertCount)));
            if (alertingResponse.getStatusCode() != 204) {
                logger.info("Alert ID Deletion Failed for : " + alertId.get(alertCount) + " with Response Code : " + alertingResponse.getStatusCode());
                errMsg = errMsg + "[Alert ID Deletion Failed for : " + alertId.get(alertCount) + " with Response Code : " + alertingResponse.getStatusCode() + " ]";
                return errMsg;
            }
            logger.info("Alert Deleted : " + alertId.get(alertCount));
        }catch(Exception e){
            logger.info("Exception occured : " + alertId.get(alertCount) + " with Response Code : " + alertingResponse.getStatusCode());
            errMsg = errMsg + "[Exception occured : " + alertId.get(alertCount) + " with Response Code : " + alertingResponse.getStatusCode() + " ]";
            return errMsg;
        }
        return errMsg;
    }

    public String verifyIncidentDeletionInITMS() throws InterruptedException {
        Thread.sleep(5000);
        String errMsg = "";
        try {
            if (alertingResponse.getStatusCode() == 200) {
                logger.info("Incident Id " + currentAlert + " Deleted from ITSM.");
                return errMsg;
            } else {
                logger.info("Incident ID " + currentAlert + " Not Deleted!!");
                errMsg = errMsg + "[Incident ID " + currentAlert + " Not Deleted!!]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Failed to Delete Incident : " + e.getMessage());
            errMsg = errMsg + "[Failed to Delete Incident : " + e.getMessage() + " ]";
            return errMsg;
        }
    }

    @SneakyThrows
    public String verifyITSMSimulatorResponse() throws Exception {
        String errMsg = "";
        boolean flag = false;
        int i = 0;
        int tryCount = 15;
        forLoop:
        for (int x = 0; x < tryCount; x++) {
            if (x != 0) {
                filterArray.clear();
                getITSMSimulatorResponse();
            }
            JsonPath filterPath = JsonPath.from(filterArray.toString());
            logger.info("Try " + x + " ------>>" + filterPath.getList("action"));
            try {
                if (filterArray.size() > 0) {
                    while (i < filterArray.size()) {
                        if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                            if (filterArray.getJSONObject(i + 1).get("action").equals("PUT")) {
                                if (filterArray.getJSONObject(i + 2).get("action").equals("DELETE")) {
                                    logger.info("All 3 Requests Reached till ITSM");
                                    i = i + 3;
                                    return errMsg;
                                } else {
                                    if (x == tryCount - 1) {
                                        logger.info("Delete Requests is not reached till ITSM");
                                        errMsg = errMsg + "[Delete Requests is not reached till ITSM]";
                                        return errMsg;
                                    } else {
                                        continue forLoop;
                                    }
                                }
                            } else {
                                if (x == tryCount - 1) {
                                    logger.info("Update Requests is not reached till ITSM");
                                    errMsg = errMsg + "[Update Requests is not reached till ITSM]";
                                    return errMsg;
                                } else {
                                    continue forLoop;
                                }
                            }
                        } else {
                            if (x == tryCount - 1) {
                                logger.info("Create Requests is not reached till ITSM");
                                errMsg = errMsg + "[Create Requests is not reached till ITSM]";
                                return errMsg;
                            } else {
                                continue forLoop;
                            }
                        }
                    }
                } else {
                    if (x == tryCount - 1) {
                        logger.info("No Alerts Reached till ITSM!!");
                        errMsg = errMsg + "[No Alerts Reached till ITSM]";
                        return errMsg;
                    } else {
                        continue forLoop;
                    }
                }
            } catch (Exception e) {
                if (x == tryCount - 1) {
                    logger.info("No Alerts Reached till ITSM!!");
                    errMsg = errMsg + "[No Alerts Reached till ITSM]";
                    for (int z = 0; z < filterArray.size(); z++) {
                        logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                        errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
                    }
                    logger.info("Exception Occurred : " + e.getMessage());
                    errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
                    return errMsg;
                } else {
                    continue forLoop;
                }
            }
        }
        if (!flag) {
            for (int z = 0; z < filterArray.size(); z++) {
                logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
            }
            return errMsg;
        }
        return errMsg;
    }

    @SneakyThrows
    public String verifyITSMSimulatorResponseParent(String testData) throws Exception {
        String errMsg = "";
        boolean flag = false;
        int i = 0;
        int tryCount = 15;
        setTestName(testData);
        preProcessing(getTestName());
        forLoop:
        for (int x = 0; x < tryCount; x++) {
            if (x != 0) {
                filterArray.clear();
                getITSMSimulatorResponse();
            }
            JsonPath filterPath = JsonPath.from(filterArray.toString());
            logger.info("Try " + x + " ------>>" + filterPath.getList("action"));
            try {
                if (filterArray.size() > 0) {
                    while (i < filterArray.size()) {
                        if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                            if (filterArray.getJSONObject(i + 1).get("action").equals("PUT")) {
                                if (filterArray.getJSONObject(i + 2).get("action").equals("DELETE")) {
                                    logger.info("All 3 Requests Reached till ITSM");
                                    i = i + 3;
                                    return errMsg;
                                } else {
                                    if (x == tryCount - 1) {
                                        logger.info("Delete Requests is not reached till ITSM");
                                        errMsg = errMsg + "[Delete Requests is not reached till ITSM]";
                                        return errMsg;
                                    } else {
                                        continue forLoop;
                                    }
                                }
                            } else {
                                if (x == tryCount - 1) {
                                    logger.info("Update Requests is not reached till ITSM");
                                    errMsg = errMsg + "[Update Requests is not reached till ITSM]";
                                    return errMsg;
                                } else {
                                    continue forLoop;
                                }
                            }
                        } else {
                            if (x == tryCount - 1) {
                                logger.info("Create Requests is not reached till ITSM");
                                errMsg = errMsg + "[Create Requests is not reached till ITSM]";
                                return errMsg;
                            } else {
                                continue forLoop;
                            }
                        }
                    }
                } else {
                    if (x == tryCount - 1) {
                        logger.info("No Alerts Reached till ITSM!!");
                        errMsg = errMsg + "[No Alerts Reached till ITSM]";
                        return errMsg;
                    } else {
                        continue forLoop;
                    }
                }
            } catch (Exception e) {
                if (x == tryCount - 1) {
                    logger.info("No Alerts Reached till ITSM!!");
                    errMsg = errMsg + "[No Alerts Reached till ITSM]";
                    for (int z = 0; z < filterArray.size(); z++) {
                        logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                        errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
                    }
                    logger.info("Exception Occurred : " + e.getMessage());
                    errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
                    return errMsg;
                } else {
                    continue forLoop;
                }
            }
        }
        if (!flag) {
            for (int z = 0; z < filterArray.size(); z++) {
                logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
            }
            return errMsg;
        }
        return errMsg;
    }

    @SneakyThrows
    public String verifyPOSTinITSM() throws Exception {
        String errMsg = "";
        boolean flag = false;

             //   filterArray.clear();
             //   getITSMSimulatorResponse();
            JsonPath filterPath = JsonPath.from(filterArray.toString());
            try {
                if (filterArray.size() > 0) {
                    int i = filterArray.size() - 1 ;
                   // while (i < filterArray.size()) {
                        if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                            try {
                                if (filterArray.getJSONObject(i + 1).get("action").equals("PUT")) {
                                    logger.info("Update Requests reached till ITSM - Not expected");
                                    errMsg = errMsg + "[Update Requests reached till ITSM - Not expected]";
                                    return errMsg;
                                } else {
                                    return errMsg;
                                }
                             }catch(Exception e){
                                return errMsg;
                            }
                        } else {
                            logger.info("Create Requests is not reached till ITSM");
                            errMsg = errMsg + "[Create Requests is not reached till ITSM]";
                            return errMsg;
                        }

                } else {
                        logger.info("No Alerts Reached till ITSM!!");
                        errMsg = errMsg + "[No Alerts Reached till ITSM]";
                        return errMsg;
                }
            } catch (Exception e) {
                    logger.info("No Alerts Reached till ITSM!!");
                    errMsg = errMsg + "[No Alerts Reached till ITSM]";
                    for (int z = 0; z < filterArray.size(); z++) {
                        logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                        errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
                    }
                    logger.info("Exception Occurred : " + e.getMessage());
                    errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
                    return errMsg;
                }


    }

    @SneakyThrows
    public String verifyPOST_DelinITSM() throws Exception {
        String errMsg = "";
        boolean flag = false;
        JsonPath filterPath = JsonPath.from(filterArray.toString());
        int i = 0 ;
        try {
            if (filterArray.size() > 0) {
                if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                    if (filterArray.getJSONObject(i+1).get("action").equals("DELETE")) {
                        return errMsg;
                    }else {
                        logger.info("Delete Requests is not reached till ITSM");
                        errMsg = errMsg + "[Delete Requests is not reached till ITSM]";
                        return errMsg;
                    }
                } else {
                    logger.info("Create Requests is not reached till ITSM");
                    errMsg = errMsg + "[Create Requests is not reached till ITSM]";
                    return errMsg;
                }
            } else {
                logger.info("No Alerts Reached till ITSM!!");
                errMsg = errMsg + "[No Alerts Reached till ITSM]";
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("No Alerts Reached till ITSM!!");
            errMsg = errMsg + "[No Alerts Reached till ITSM]";
            for (int z = 0; z < filterArray.size(); z++) {
                logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
            }
            logger.info("Exception Occurred : " + e.getMessage());
            errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
            return errMsg;
        }
    }

    @SneakyThrows
    public String verifyITSMSimulatorResponseConsolidation() throws Exception {
        String errMsg = "";
        boolean flag = false;
        int i = 0;
        int tryCount = 15;
        forLoop:
        for (int x = 0; x < tryCount; x++) {
            if (x != 0) {
                filterArray.clear();
                getITSMSimulatorResponse();
            }
            JsonPath filterPath = JsonPath.from(filterArray.toString());
            logger.info("Try " + x + " ------>>" + filterPath.getList("action"));
            try {
                if (filterArray.size() > 0) {
                    while (i < filterArray.size()) {
                        if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                            if (filterArray.getJSONObject(i + 1).get("action").equals("PUT")) {
                                if (filterArray.getJSONObject(i + 2).get("action").equals("PUT")) {
                                    if (filterArray.getJSONObject(i + 3).get("action").equals("PUT")) {
                                        if (filterArray.getJSONObject(i + 4).get("action").equals("PUT")) {
                                            if (filterArray.getJSONObject(i + 5).get("action").equals("PUT")) {
                                                if (filterArray.getJSONObject(i + 6).get("action").equals("DELETE")) {
                                                    logger.info("All 3 Requests Reached till ITSM");
                                                    i = i + 3;
                                                    return errMsg;
                                                } else {
                                                    if (x == tryCount - 1) {
                                                        logger.info("Delete Requests is not reached till ITSM");
                                                        errMsg = errMsg + "[Delete Requests is not reached till ITSM]";
                                                        return errMsg;
                                                    } else {
                                                        continue forLoop;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (x == tryCount - 1) {
                                    logger.info("Update Requests is not reached till ITSM");
                                    errMsg = errMsg + "[Update Requests is not reached till ITSM]";
                                    return errMsg;
                                } else {
                                    continue forLoop;
                                }
                            }
                        } else {
                            if (x == tryCount - 1) {
                                logger.info("Create Requests is not reached till ITSM");
                                errMsg = errMsg + "[Create Requests is not reached till ITSM]";
                                return errMsg;
                            } else {
                                continue forLoop;
                            }
                        }
                    }
                } else {
                    if (x == tryCount - 1) {
                        logger.info("No Alerts Reached till ITSM!!");
                        errMsg = errMsg + "[No Alerts Reached till ITSM]";
                        return errMsg;
                    } else {
                        continue forLoop;
                    }
                }
            } catch (Exception e) {
                if (x == tryCount - 1) {
                    logger.info("No Alerts Reached till ITSM!!");
                    errMsg = errMsg + "[No Alerts Reached till ITSM]";
                    for (int z = 0; z < filterArray.size(); z++) {
                        logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                        errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
                    }
                    logger.info("Exception Occurred : " + e.getMessage());
                    errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
                    return errMsg;
                } else {
                    continue forLoop;
                }
            }
        }
        if (!flag) {
            for (int z = 0; z < filterArray.size(); z++) {
                logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
            }
            return errMsg;
        }
        return errMsg;
    }

    public String verifyChildListITSMSimulatorResponse() throws Exception {
        String errMsg = "";
        List actualChildConditionId = null;
        int i = 0;
        int tryCount = 15;
        forLoop:
        for (int x = 0; x < tryCount; x++) {
            if (x != 0) {
                filterArray.clear();
                getITSMSimulatorResponse();
            }
            JsonPath filterPath = JsonPath.from(filterArray.toString());
            actualChildConditionId = new ArrayList<String>();
            logger.info("Try " + x + " ------>>" + filterPath.getList("action"));
            try {
                if (filterArray.size() > 0) {
                    while (i < filterArray.size()) {
                        if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                            JSONObject jsonObj = filterArray.getJSONObject(i);
                            JSONObject jsonObjPayload = jsonObj.getJSONObject("payload");
                            JSONArray jsonObjRootCauseArr = (JSONArray) jsonObjPayload.get("rootcause");
                            int z = 0;
                            if (jsonObjRootCauseArr == null) {
                                if (x == tryCount - 1) {
                                    logger.info("No Data Present in ITSM Simulator");
                                    errMsg = errMsg + "[No Data Present in ITSM Simulator]";
                                    return errMsg;
                                } else {
                                    continue forLoop;
                                }
                            } else {
                                while (z < jsonObjRootCauseArr.size()) {
                                    JSONObject resObject = jsonObjRootCauseArr.getJSONObject(z);
                                    logger.info("resObject : " + resObject);
                                    actualChildConditionId.add(resObject.getString("conditionId"));
                                    z++;
                                }
                            }
                        }
                        i++;
                    }
                } else {
                    if (x == tryCount - 1) {
                        logger.info("No Alerts Reached till ITSM!!");
                        errMsg = errMsg + "[No Alerts Reached till ITSM]";
                        return errMsg;
                    } else {
                        continue forLoop;
                    }
                }
            } catch (Exception e) {
                if (x == tryCount - 1) {
                    logger.info("No Alerts Reached till ITSM!!");
                    errMsg = errMsg + "[No Alerts Reached till ITSM]";
                    for (int z = 0; z < filterArray.size(); z++) {
                        logger.info("Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action"));
                        errMsg = errMsg + "[Exception Occurred -> FilterArray : " + filterArray.getJSONObject(z).get("action") + "]";
                    }
                    logger.info("Exception Occurred : " + e.getMessage());
                    errMsg = errMsg + "[Exception Occurred : " + e.getMessage() + "]";
                    return errMsg;
                } else {
                    continue forLoop;
                }
            }
        }
        if (conditionId.size() == actualChildConditionId.size()) {
            if (conditionId.containsAll(actualChildConditionId)) {
                return errMsg;
            }
            errMsg = errMsg + "[Parent not contains all Child. Excepted -> " + conditionId + ". Actual -> " + actualChildConditionId + ".]";
            return errMsg;
        } else {
            errMsg = errMsg + "[Child count is not same. Excepted -> " + conditionId.size() + ". Actual -> " + actualChildConditionId.size() + ".]";
            return errMsg;
        }
    }

    public String validateActualDataInITSM() throws InterruptedException {
        String errMsg = "";
        getActualDataInITSM();
        boolean flag = true;
        try {
            if (!getCurrentAlert().equalsIgnoreCase(actualDataInITSM.get("alertId"))) {
                flag = false;
                logger.info("Data Mismatch in Alert ID : Expected -> " + getCurrentAlert() + " :: Actual ->" + actualDataInITSM.get("alertId"));
                errMsg = errMsg + "[Data Mismatch in Alert ID : Expected -> " + getCurrentAlert() + " :: Actual ->" + actualDataInITSM.get("alertId") + " ]";
            }
            if (!currentRow.get("partners").equalsIgnoreCase(actualDataInITSM.get("partnerId"))) {
                flag = false;
                logger.info("Data Mismatch in Patner ID : Expected -> " + currentRow.get("partners") + " :: Actual ->" + actualDataInITSM.get("partnerId"));
                errMsg = errMsg + "[Data Mismatch in Patner ID : Expected -> " + currentRow.get("partners") + " :: Actual ->" + actualDataInITSM.get("alertId") + " ]";
            }
            if (!currentRow.get("clients").equalsIgnoreCase(actualDataInITSM.get("clientId"))) {
                flag = false;
                logger.info("Data Mismatch in Client ID : Expected -> " + currentRow.get("clients") + " :: Actual ->" + actualDataInITSM.get("clientId"));
                errMsg = errMsg + "[Data Mismatch in Client ID : Expected -> " + currentRow.get("clients") + " :: Actual ->" + actualDataInITSM.get("clientId") + " ]";
            }
            if (!currentRow.get("sites").equalsIgnoreCase(actualDataInITSM.get("siteId"))) {
                flag = false;
                logger.info("Data Mismatch in Sites ID : Expected -> " + currentRow.get("sites") + " :: Actual ->" + actualDataInITSM.get("siteId"));
                errMsg = errMsg + "[Data Mismatch in Sites ID : Expected -> " + currentRow.get("sites") + " :: Actual ->" + actualDataInITSM.get("siteId") + " ]";
            }
            //     if (!currentRow.get("resourceId").equalsIgnoreCase(actualDataInITSM.get("resourceId"))) {
            //         flag = false;
            //         logger.info("Data Mismatch in Resource ID : Expected -> " + currentRow.get("resourceId") + " :: Actual ->" + actualDataInITSM.get("resourceId"));
            //     }
            if (!currentRow.get("endpoints").equalsIgnoreCase(actualDataInITSM.get("endpointId"))) {
                flag = false;
                logger.info("Data Mismatch in Endpoint ID : Expected -> " + currentRow.get("endpoints") + " :: Actual ->" + actualDataInITSM.get("endpointId"));
                errMsg = errMsg + "[Data Mismatch in Endpoint ID : Expected -> " + currentRow.get("endpoints") + " :: Actual ->" + actualDataInITSM.get("endpointId") + " ]";
            }
            if (!currentRow.get("conditionId").equalsIgnoreCase(actualDataInITSM.get("conditionId"))) {
                flag = false;
                logger.info("Data Mismatch in Condition ID : Expected -> " + currentRow.get("conditionId") + " :: Actual ->" + actualDataInITSM.get("conditionId"));
                errMsg = errMsg + "[Data Mismatch in Condition ID : Expected -> " + currentRow.get("conditionId") + " :: Actual ->" + actualDataInITSM.get("conditionId") + " ]";
            }
            //     JSONParser parser = new JSONParser();
            //     JSONObject jsonObj = (JSONObject) parser.parse(currentRow.get("alertDetails"));
            org.json.JSONObject jsonObj = new org.json.JSONObject(currentRow.get("alertDetails"));
            org.json.JSONObject jsonObj1 = jsonObj.getJSONObject("alertDetails");
            if (!jsonObj1.getString("Test").equalsIgnoreCase(actualDataInITSM.get("Test"))) {
                flag = false;
                logger.info("Data Mismatch in Test : Expected -> " + jsonObj1.getString("Test") + " :: Actual ->" + actualDataInITSM.get("Test"));
                errMsg = errMsg + "[Data Mismatch in Test : Expected -> " + jsonObj1.getString("Test") + " :: Actual ->" + actualDataInITSM.get("Test") + " ]";
            }
            if (!jsonObj1.getString("Type").equalsIgnoreCase(actualDataInITSM.get("Type"))) {
                flag = false;
                logger.info("Data Mismatch in Type : Expected -> " + jsonObj1.getString("Type") + " :: Actual ->" + actualDataInITSM.get("Type"));
                errMsg = errMsg + "[Data Mismatch in Type : Expected -> " + jsonObj1.getString("Type") + " :: Actual ->" + actualDataInITSM.get("Type") + " ]";
            }
        } catch (Exception e) {
            logger.info("Exception Occurred : " + e);
            errMsg = errMsg + "[Exception Occurred : " + e + " ]";
            flag = false;
        }
        //return flag;
        return errMsg;
    }

    public String validateITSMData(String field, String value) throws InterruptedException {
        getActualDataInITSM();
        boolean flag = true;
        String errMsg = "";
        if (!value.equalsIgnoreCase(actualDataInITSM.get(field))) {
            flag = false;
            logger.info("Data Mismatch in " + field + " : Expected -> " + value + " :: Actual ->" + actualDataInITSM.get("field"));
            errMsg = errMsg + "[Data Mismatch in " + field + " : Expected -> " + value + " :: Actual ->" + actualDataInITSM.get("field") + " ]";
        }
        return errMsg;
    }

    public String validateITSMDataTest() throws InterruptedException {
        String errMsg = getActualDataInITSMPOSTTypeValidate("PUT_" + appender);
        if (errMsg.length() < 3) {
            logger.info("Expected request found in ITSM Simulator response.");
            return errMsg;
        }
        logger.info("Expected request not found in ITSM Simulator response.");
        errMsg = errMsg + "[Expected POST request not reached in ITSM having type : " + "PUT_" + appender + " ]";
        return errMsg;
    }

    public String validateAlertState(List<String> factorList) throws InterruptedException {
        String errMsg = "";
        try {
            if (filterArray.size() > 0) {
                JSONObject jsonObj = filterArray.getJSONObject(0);
                for (String factor : factorList) {
                    switch (factor) {
                        case "child list":
                            //String expectedChildList = alertId.get(0);
                            String actualChildList = jsonObj.getString("childlist");
                            logger.info("Actual Child List - > " + actualChildList);
                            logger.info("Expected Child List including parent - > " + alertId);
                            String[] arrActual = actualChildList.split(",");
                            for (String childAlert : arrActual) {
                                if (!alertId.contains(childAlert)) {
                                    errMsg = errMsg + "[Child Alert : " + childAlert + " not present in Alert ID List.]";
                                }
                            }
                            break;
                    }
                }
            } else {
                logger.info("No data present in Alert State");
                errMsg = errMsg + "[No data present in Alert State. As filterArray size is 0]";
            }
        } catch (Exception e) {
            logger.info("Exception Occurred " + e);
            errMsg = errMsg + "Exception Occured " + e.toString() + " ]";
        }
        return errMsg;
    }

    public String validateActualDataInITSM(String expSourceSystem) throws InterruptedException {
        String errMsg = "";
        getActualDataInITSM();
        boolean flag = true;
        try {
            if (!expSourceSystem.equalsIgnoreCase(actualDataInITSM.get("sourceSystem"))) {
                flag = false;
                logger.info("Data Mismatch in Source System in Payload : Expected -> " + expSourceSystem + " :: Actual ->" + actualDataInITSM.get("sourceSystem"));
                errMsg = errMsg + "[Data Mismatch in Source System in Payload : Expected -> " + expSourceSystem + " :: Actual ->" + actualDataInITSM.get("sourceSystem") + " ]";
            }
            if (!expSourceSystem.equalsIgnoreCase(actualDataInITSM.get("sourceSystem1"))) {
                flag = false;
                logger.info("Data Mismatch in Source System : Expected -> " + expSourceSystem + " :: Actual ->" + actualDataInITSM.get("sourceSystem1"));
                errMsg = errMsg + "[Data Mismatch in Source System : Expected -> " + expSourceSystem + " :: Actual ->" + actualDataInITSM.get("sourceSystem1") + " ]";
            }
        } catch (Exception e) {
            logger.info("Exception Occurred : " + e);
            errMsg = errMsg + "[Exception Occurred : " + e + " ]";
            flag = false;
        }
        return errMsg;
    }

    public String validateNoDataAlertState() throws InterruptedException {
        String errMsg = "";
        try {
            if (filterArray.size() == 0) {
                return errMsg;
            } else {
                logger.info("Parent present in Alert State");
                errMsg = errMsg + "[Parent present in Alert State]";
            }
        } catch (Exception e) {
            logger.info("Exception Occurred " + e);
            errMsg = errMsg + "Exception Occured " + e.toString() + " ]";
        }
        return errMsg;
    }

    public void getActualDataInITSM() throws InterruptedException {
        JsonPath filterPath = JsonPath.from(filterArray.toString());
        logger.info(filterPath.getList("action"));
        int i = 0;
        try {
            if (filterArray.size() > 0) {
                while (i < filterArray.size()) {
                    if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                        JSONObject jsonObj = filterArray.getJSONObject(i);
                        JSONObject jsonObjPayload = jsonObj.getJSONObject("payload");
                        setActualDataInITSM("alertId", jsonObjPayload.getString("alertId"));
                        setActualDataInITSM("partnerId", jsonObjPayload.getString("partnerId"));
                        setActualDataInITSM("clientId", jsonObjPayload.getString("clientId"));
                        setActualDataInITSM("siteId", jsonObjPayload.getString("siteId"));
                        //setActualDataInITSM("resourceId", jsonObjPayload.getString("resourceId"));
                        setActualDataInITSM("endpointId", jsonObjPayload.getString("endpointId"));
                        setActualDataInITSM("conditionId", jsonObjPayload.getString("conditionId"));
                        setActualDataInITSM("sourceSystem", jsonObjPayload.getString("sourcesystem"));
                        setActualDataInITSM("statuscode", Integer.toString(jsonObj.getInt("statuscode")));
                        setActualDataInITSM("sourceSystem1", Double.toString(jsonObj.getDouble("source_system")));
                        JSONObject jsonObjAlertDetails = jsonObjPayload.getJSONObject("alertDetails");
                        setActualDataInITSM("Test", jsonObjAlertDetails.getString("Test"));
                        setActualDataInITSM("Type", jsonObjAlertDetails.getString("Type"));
                        break;
                    }
                    i++;
                }
            } else {
                logger.info("No Alerts Reached till ITSM!!");
            }
        } catch (Exception e) {
            logger.info("Exception Occured : " + e.getMessage());
        }
    }

    public String getActualDataInITSMPOSTTypeValidate(String expText) throws InterruptedException {
        JsonPath filterPath = JsonPath.from(filterArray.toString());
        String errMsg = "";
        boolean foundFlag = false;
        logger.info(filterPath.getList("action"));
        int i = 0;
        try {
            if (filterArray.size() > 0) {
                while (i < filterArray.size()) {
                    if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                        JSONObject jsonObj = filterArray.getJSONObject(i);
                        JSONObject jsonObjPayload = jsonObj.getJSONObject("payload");
                        JSONObject jsonObjAlertDetails = jsonObjPayload.getJSONObject("alertDetails");
                        if (jsonObjAlertDetails.getString("Test").equalsIgnoreCase(expText)) {
                            foundFlag = true;
                            setActualDataInITSM("Test", jsonObjAlertDetails.getString("Test"));
                            setActualDataInITSM("Type", jsonObjAlertDetails.getString("Type"));
                            setActualDataInITSM("alertId", jsonObjPayload.getString("alertId"));
                            setActualDataInITSM("partnerId", jsonObjPayload.getString("partnerId"));
                            setActualDataInITSM("clientId", jsonObjPayload.getString("clientId"));
                            setActualDataInITSM("siteId", jsonObjPayload.getString("siteId"));
                            //setActualDataInITSM("resourceId", jsonObjPayload.getString("resourceId"));
                            setActualDataInITSM("endpointId", jsonObjPayload.getString("endpointId"));
                            setActualDataInITSM("conditionId", jsonObjPayload.getString("conditionId"));
                            setActualDataInITSM("statuscode", Integer.toString(jsonObj.getInt("statuscode")));
                            break;
                        }
                    }
                    i++;
                }
                if (!foundFlag) {
                    errMsg = errMsg + "[Expected POST request not reached in ITSM having type : " + expText + " ]";
                }
            } else {
                errMsg = errMsg + "[No Alert reached till ITSM]";
                logger.info("Alerts Not Reached till ITSM!!");
            }
        } catch (Exception e) {
            logger.info("Exception Occured : " + e.getMessage());
            errMsg = errMsg + "[Exception occured : " + e.getMessage() + "]";
        }
        return errMsg;
    }

    public String verifyITSMResponseForChildAlert() throws InterruptedException {
        String errMsg = "";
        try {
            if (filterArray.isEmpty()) {
                logger.info("No Child Alerts Reached Till ITSM.");
                //return true;
                return errMsg;
            } else {
                logger.info("Child alert reached till ITSM!!");
                errMsg = errMsg + "[Child alert reached till ITSM!!]";
                //return false;
                return errMsg;
            }
        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            errMsg = errMsg + "Alert Deletion Failed with Error Message : " + e.getMessage() + " ]";
            return errMsg;
            //return false;
        }

    }

    public String verifyRemediateURLITSMRequest() throws InterruptedException {
        String errMsg = "";
        try {
            int i = 0;
            while (i < filterArray.size()) {
                JSONObject filterObj = filterArray.getJSONObject(i);
                if (filterObj.get("action").equals("POST")) {
                    JSONObject payloadObj = filterObj.getJSONObject("payload");
                    if (payloadObj.get("remediateUrl").equals(null) || payloadObj.get("fetchDataUrl").equals(null)) {
                        logger.info("URL for Remediate or FetchMoreData is Null");
                        errMsg = errMsg + "[URL for Remediate or FetchMoreData is Null]";
                        return errMsg;
                    } else {
                        logger.info("Remediate URL : " + payloadObj.get("remediateUrl"));
                        logger.info("FetchMore URL : " + payloadObj.get("fetchDataUrl"));
                        return errMsg;
                    }
                }
                i++;
            }
            logger.info("There is No POST Request in ITSM Simulator");
            errMsg = errMsg + "[There is No POST Request in ITSM Simulator]";
        } catch (Exception e) {
            logger.info("Exception occured " + e.getMessage());
            errMsg = errMsg + "[Exception occured : " + e.getMessage() + " ]";
        }
        return errMsg;
    }

    public String triggerManualClosure(String kafkaMessageType) {
        String kafkaMessage;
        String errMsg = "";
        switch (kafkaMessageType) {
            case "AlertID":
                String transactionID = "TEST_" + JunoAlertingUtils.timeStamp();
                kafkaMessage = "{\"alertId\":\"" + getCurrentAlert() + "\",\"transactionId\":\"" + transactionID + "\"}";
                break;
            case "MetaData":
                kafkaMessage = getManualClosureMetadata();
                break;
            default:
                logger.info("Message Type is Invalid!!");
                errMsg = "[Message Type is Invalid!!]";
                return errMsg;
            //return false;
        }
        logger.info("Posting Kafka Message to Kafka topic : " + kafkaMessage);
        KafkaProducerUtility.postMessage(kafkaServer, Utilities.getMavenProperties("KafkaTopic"), kafkaMessage);
        // return true;
        return errMsg;
    }

    public String triggerManualClosure1(String kafkaMessageType, int alertCount) {
        String kafkaMessage;
        String errMsg = "";
        switch (kafkaMessageType) {
            case "AlertID":
                String transactionID = "TEST_" + JunoAlertingUtils.timeStamp();
                kafkaMessage = "{\"alertId\":\"" + alertId.get(alertCount) + "\",\"transactionId\":\"" + transactionID + "\"}";
                break;
            case "MetaData":
                kafkaMessage = getManualClosureMetadata();
                break;
            default:
                logger.info("Message Type is Invalid!!");
                errMsg = "[Message Type is Invalid!!]";
                return errMsg;
            //return false;
        }
        logger.info("Posting Kafka Message to Kafka topic : " + kafkaMessage);
        KafkaProducerUtility.postMessage(kafkaServer, Utilities.getMavenProperties("KafkaTopic"), kafkaMessage);
        // return true;
        return errMsg;
    }

    public String getManualClosureMetadata() {

        HashMap<String, String> currentRow = new HashMap<String, String>();

        JSONObject manualClosureMessage = new JSONObject();
        manualClosureMessage.put("clientid", currentRow.get("clients"));
        manualClosureMessage.put("siteid", currentRow.get("sites"));
        manualClosureMessage.put("partnerid", currentRow.get("partners"));
        //manualClosureMessage.put("resourceid", currentRow.get("resourceid"));
        manualClosureMessage.put("endpointid", currentRow.get("endpoints"));
        manualClosureMessage.put("conditionid", currentRow.get("conditionid"));

        return manualClosureMessage.toString();
    }

    public boolean waitForSnooze(int duration) throws InterruptedException {

        TimeUnit.SECONDS.sleep(duration);
        return true;

    }

    public void waitForSleep(int duration) throws InterruptedException {
        logger.info("Pause for : " + duration + " seconds");
        Thread.sleep(duration);
    }

    /*
     * public void verifyGETApiResponse() throws ParseException{
     *
     * JSONParser j_parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE); JSONObject
     * j_obj = (JSONObject) j_parser.parse(alertingResponse.toString());
     *
     * if(j_obj.containsKey("PartnerId"))
     * Assert.assertEquals((String)j_obj.get("summary"), "fetch from db");
     *
     * }
     */


/*	public ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,String query) throws Exception {

		//DatabaseUtility.getListByQuery(databaseName, sqlServerURL, username, password, query, column);
	}*/


    public void closeTest() {
        alertId.clear();
        filterArray.clear();
    }

    public String triggerAlertAutomationDataAPI() throws InterruptedException {
            Thread.sleep(5000);
            triggerAlertStateAPI();
            String errMsg = "";
            try {
                if (alertingResponse.getStatusCode() == 200) {
                    JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                    setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, getCurrentAlert()));
                    return errMsg;
                    // return true;
                } else {
                    logger.info("Request to Alert State with Response Code : " + alertingResponse.getStatusCode());
                    //return false;
                    errMsg = errMsg + "[Request to Alert State with Response Code : " + alertingResponse.getStatusCode() + " ]";
                    return errMsg;
                }
            } catch (Exception e) {
                logger.info("Failed to save  Alert State Response : " + e.getMessage());
                errMsg = errMsg + "[Failed to save  Alert State Response : " + e.getMessage() + " ]";
                return errMsg;
                //return false;
            }
    }

    public String getAlertAutomationDataAPI() {
        try {
            automationDataPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertAutomationData));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert automation API Call Failed With Error : " + e.getMessage());
        }
        return null;
    }

    private void automationDataPreProcessing() {
        logger.info("Getting Host URL:" + alertingUrl);
        alertAutomationData = alertingUrl + Utilities.getMavenProperties("GetAutomationUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{endpoints}", currentRow.get("endpoints"))
                .replace("{alert}", currentAlert);
        if (currentRow.get("sites").isEmpty()) {
            alertAutomationData = alertAutomationData.replace("/sites/", "");
        }
        if (currentRow.get("clients").isEmpty()) {
            alertAutomationData = alertAutomationData.replace("/clients/", "");
        }
        if (currentRow.get("partners").isEmpty()) {
            alertAutomationData = alertAutomationData.replace("/partners/", "");
        }
        logger.info("alertAutomationData API >> : " + alertAutomationData);
    }
}
