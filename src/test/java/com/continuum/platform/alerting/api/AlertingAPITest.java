package com.continuum.platform.alerting.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.continuum.utils.DataUtils;
import com.continuum.utils.JsonRespParserUtility;
import com.continuum.utils.JunoAlertingAPIUtil;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import continuum.cucumber.KafkaProducerUtility;
import continuum.cucumber.Utilities;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.json.simple.parser.JSONParser;

public class AlertingAPITest {

    private Logger logger = Logger.getLogger(AlertingAPITest.class);
    private String alertDetails, itsmUrl, testName, alertFailure, alertState, currentAlert, jasUrl, alertingAPIUrl = "";
    private static String alertingUrl, kafkaServer, itsmIntegrationUrl;
    private Response alertingResponse;
    private List<String> alertId = new ArrayList<String>();
    private List<String> conditionId = new ArrayList<String>();
    private HashMap<String, String> actualDataInITSM = new HashMap<String, String>();
    private JSONArray filterArray = new JSONArray();
    private HashMap<String, String> currentRow = new HashMap<String, String>();

    public AlertingAPITest() {
        // TODO Auto-generated constructor stub
    }

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

    public void setJasUrl(String jasUrl) {
        this.jasUrl = jasUrl;
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

    public boolean triggerCreateAPI(String testName) {
        try {
            setTestName(testName);
            preProcessing(getTestName());
            setConditionId(currentRow.get("conditionId"));
            logger.info("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, alertingAPIUrl));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Alert Creation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean reprocessAlert(String count) {
        try {
            String url = alertingUrl + Utilities.getMavenProperties("ReprocessAlert");
            String body = "[{\"alertId\" : \"" + alertId.get(0) + "\"}]";
            logger.debug("Body : " + body);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(body, url));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Reprocess Alert Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean verifyDuplicateAlertMsgInResponse() {
        try {
            if (alertingResponse.getStatusCode() == 200) {
                logger.debug("Reprocessed of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                JSONArray respArray = (JSONArray) json;
                if (respArray == null) {
                    System.out.println("No Data Present in ITSM Simulator");
                    return false;
                }
                int i = 0;
                while (i < respArray.size()) {
                    JSONObject resObject = respArray.getJSONObject(i);
                    if (alertId.contains((resObject.get("DuplicateAlertId").toString()))) {
                        return true;
                    }
                    i++;
                }
                return false;
            } else {
                logger.debug("Alert Not Reprocessed with Response Code : " + alertingResponse.getStatusCode());
                logger.debug("Alert Not Reprocessed with Message : " + alertingResponse);
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Reprocessed Failed with Error Message : " + e.getMessage());
            return false;
        }
    }


    public boolean triggerCreateITSM_API(String testName) {
        try {
            setTestName(testName);
            preProcessingITSM(getTestName());
            setConditionId(currentRow.get("conditionId"));
            logger.debug("Incident Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(alertDetails, itsmIntegrationUrl));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Incident Creation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean triggerParentCreateAPI(String testName) {
        try {
            setTestName(testName);
            preProcessing(getTestName());
            JSON json = JSONSerializer.toJSON(alertDetails);
            JSONObject parentAlertDetails = (JSONObject) json;
            JSONArray alerts = new JSONArray();
            alerts.addAll(alertId);
            parentAlertDetails.put("alerts", alerts);
            logger.debug(parentAlertDetails.toString());
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Parent Alert Creation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean triggerParentWithSameConditionCreateAPI(String testName) {
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
                logger.debug(parentAlertDetails.toString());
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.postWithFormParameters(parentAlertDetails.toString(), alertingAPIUrl));
                Thread.sleep(3000);
                if (alertingResponse.getStatusCode() == 201) {
                    logger.debug(alertingResponse.getBody().asString());
                    setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    logger.debug("Alert Created : " + getCurrentAlert());
                    i++;
                } else if (alertingResponse.getStatusCode() == 409) {
                    logger.debug("Parent Alert not Created with Error Conflict");
                    return false;
                } else {
                    logger.debug("Parent Alert no created with Response Code : " + alertingResponse.getStatusCode());
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Parent Alert Creation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean triggerUpdateAPI() {
        try {
            preProcessing(getTestName());
            logger.debug("Alert Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, alertingAPIUrl + "/" + getCurrentAlert()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean triggerUpdateAPI_ITSM() {
        try {
            preProcessingITSM(getTestName());
            logger.debug("Incident Details : " + alertDetails);
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.putWithFormParameters(alertDetails, itsmIntegrationUrl + "/" + getCurrentAlert()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Incident Updation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean triggerDeleteAPI() {
        try {
            int i = 0;
            if (alertId.size() > 1) {
                for (i = 0; i < alertId.size(); i++) {
                    this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                    if (alertingResponse.getStatusCode() != 204) {
                        logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                        return false;
                    }
                    logger.debug("Alert Deleted : " + alertId.get(i));
                }
            } else {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                logger.debug("Alert Deletion Called for AlertID : " + alertId.get(i));
                return true;
            }
            return true;
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean triggerDeleteAPIWithBody() {
        try {
            int i = 0;
            if (alertId.size() > 1) {
                for (i = 0; i < alertId.size(); i++) {
                    this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
                    if (alertingResponse.getStatusCode() != 204) {
                        logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                        return false;
                    }
                    logger.debug("Alert Deleted : " + alertId.get(i));
                }
            } else {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
                logger.debug("Alert Deletion Called for AlertID : " + alertId.get(i));
                return true;
            }
            return true;
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean triggerLastAlertDeleteAPIWithBody() {
        try {
            int i = alertId.size() - 1;
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deleteWithBody(alertDetails, alertingAPIUrl + "/" + alertId.get(i)));
            if (alertingResponse.getStatusCode() != 204) {
                logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
            logger.debug("Alert Deleted : " + alertId.get(i));
            return true;
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean triggerChildDeleteAPI() {
        try {
            for (int i = 0; i < alertId.size() - 1; i++) {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                if (alertingResponse.getStatusCode() != 204) {
                    logger.info("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                    return false;
                }
                logger.info("Alert Deleted : " + alertId.get(i));
                Thread.sleep(2000);
            }
            logger.info("Alerts Deleted!!");
            return true;

        } catch (Exception e) {
            logger.info("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean triggerChildDeleteAPIForBothParent() {
        try {
            for (int i = 0; i < alertId.size() - 2; i++) {
                this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertId.get(i)));
                if (alertingResponse.getStatusCode() != 204) {
                    logger.debug("Alert ID Deletion Failed for : " + alertId.get(i) + "with Response Code : " + alertingResponse.getStatusCode());
                    return false;
                }
                logger.debug("Alert Deleted : " + alertId.get(i));
                Thread.sleep(2000);
            }
            logger.debug("Alerts Deleted!!");
            return true;

        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean triggerDeleteAPI(String alertID) {
        try {
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.deletePathParameters(alertingAPIUrl + "/" + alertID));
            if (alertingResponse.getStatusCode() != 204) {
                logger.debug("Alert ID Deletion Failed for : " + alertID + "with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
            logger.debug("Alert Deleted : " + alertID);
            if (conditionId.contains(currentRow.get("conditionId"))) {
                conditionId.remove(currentRow.get("conditionId"));
            }
            return true;

        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public void preProcessing(String testName) throws Exception {

        setCurrentRow(DataUtils.getTestRow("Test", testName));
        logger.debug("Test Data Captured.");

        logger.debug("Getting Host URL:" + alertingUrl);

        alertingAPIUrl = alertingUrl + Utilities.getMavenProperties("AlertingUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{endpoints}", currentRow.get("endpoints"));

        if (currentRow.get("endpoints").isEmpty()) {
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
        }
        setAlertDetails(currentRow.get("alertDetails"));

        logger.debug(alertingAPIUrl);

    }

    public void preProcessingITSM(String testName) throws Exception {

        setCurrentRow(DataUtils.getTestRow("Test", testName));
        logger.debug("Test Data Captured.");
        AlertingAPITest.setItsmIntegrationUrl(Utilities.getMavenProperties("DTITSMHostUrlV2"));
        logger.debug("Getting Host URL itsmIntegrationUrl:" + itsmIntegrationUrl);

        itsmIntegrationUrl = itsmIntegrationUrl + Utilities.getMavenProperties("ITSMUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{endpoints}", currentRow.get("endpoints"));

        if (currentRow.get("endpoints").isEmpty()) {
            if (currentRow.get("sites").isEmpty()) {
                if (currentRow.get("clients").isEmpty()) {
                    itsmIntegrationUrl = itsmIntegrationUrl.replace("clients//sites//endpoints//", "");
                } else {
                    itsmIntegrationUrl = itsmIntegrationUrl.replace("endpoints//", "");
                    itsmIntegrationUrl = itsmIntegrationUrl.replace("sites/", "clients/{clients}");
                    itsmIntegrationUrl = itsmIntegrationUrl.replace("{clients}", currentRow.get("clients"));
                }
            } else {
                itsmIntegrationUrl = itsmIntegrationUrl.replace("endpoints//", "");
                itsmIntegrationUrl = itsmIntegrationUrl.replace("sites", "clients/{clients}/sites");
                itsmIntegrationUrl = itsmIntegrationUrl.replace("{clients}", currentRow.get("clients"));
            }
        }
        setAlertDetails(currentRow.get("alertDetails"));

        logger.debug(itsmIntegrationUrl);

    }

    public boolean verifyCreateAPIResponse() {
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerCreateAPI(getTestName());
                    return verifyCreateAPIResponse();
                } else {
                    logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    return false;
                }
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.debug("Alert Created : " + getCurrentAlert());
                return true;
            } else {
                logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyCreateAPIResponseITSM() {
        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                return false;
                /*triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerCreateITSM_API(getTestName());
                    return verifyCreateAPIResponseITSM();
                } else {
                    logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    return false;
                }*/
            } else if (alertingResponse.getStatusCode() == 200) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("id"));
                logger.debug("Incident Created in ITSM : " + getCurrentAlert());
                return true;
            } else {
                logger.debug("Incident Not Created with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Incident Verification Failed with Error Message : " + e.getMessage());
            return false;
        }
    }


    public boolean verifyAlertSuspension() {

        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                triggerDeleteAPI(currentAlert);
                if (alertingResponse.getStatusCode() == 204) {
                    alertId.remove(alertId.size() - 1);
                    Thread.sleep(5000);
                    triggerCreateAPI(getTestName());
                    return verifyAlertSuspension();
                } else {
                    logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                    return false;
                }
            } else if (alertingResponse.getStatusCode() == 400) {
                logger.debug(alertingResponse.getBody().asString());
                JSONObject suspensionResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (suspensionResponse.getInt("status") == 205) {
                    logger.debug("Alert Not Created because of suspension with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
                    return true;
                }
                logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode() + " And Internal Response Code : " + suspensionResponse.get("status"));
                return false;
            } else {
                logger.debug("Alert Created with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyNewAlertCreation() {

        try {
            if (alertingResponse.getStatusCode() == 409) {
                if (JsonPath.from(alertingResponse.getBody().asString()).get("alertId").equals(currentAlert)) {
                    logger.debug(alertingResponse.getBody().asString());
                    logger.debug("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
                    return false;
                } else {
                    logger.debug(alertingResponse.getBody().asString());
                    setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                    triggerDeleteAPI(currentAlert);
                    if (alertingResponse.getStatusCode() == 204) {
                        alertId.remove(alertId.size() - 1);
                        Thread.sleep(5000);
                        triggerCreateAPI(getTestName());
                        return verifyNewAlertCreation();
                    } else {
                        logger.debug("Delete of Alert Fail with Status Code : " + alertingResponse.getStatusCode());
                        return false;
                    }
                }
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.debug("Alert Created : " + getCurrentAlert());
                return true;

            } else {
                logger.debug("Alert Not Created with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Verification Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyDuplicateAlertCreation() {

        try {
            if (alertingResponse.getStatusCode() == 409) {
                logger.debug(alertingResponse.getBody().asString());
                logger.debug("New ALert is not getting created, Getting Conflict : " + alertingResponse.getStatusCode());
                //setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                return true;
            } else if (alertingResponse.getStatusCode() == 201) {
                logger.debug(alertingResponse.getBody().asString());
                setAlertId(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                setCurrentAlert(JsonPath.from(alertingResponse.getBody().asString()).get("alertId"));
                logger.debug("Alert Created : " + getCurrentAlert());
                return false;
            } else {
                logger.debug("Alert Not giving Conflict with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Duplicate Alert Verification Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyUpdateAPIResponse() {

        try {
            if (alertingResponse.getStatusCode() == 204) {
                logger.debug("Update of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                return true;
            } else {
                logger.debug("Alert Not Updated with Response Code : " + alertingResponse.getStatusCode());
                logger.debug("Alert Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyUpdateAPIResponse(String responseCode) throws InterruptedException {

        try {
            if (alertingResponse.getStatusCode() == Integer.parseInt(responseCode)) {
                logger.debug("Actual Response Code found : " + alertingResponse.getStatusCode());
                logger.debug("Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                return true;
            } else {
                logger.debug("Expected Response Not Code found. Actual -> " + alertingResponse.getStatusCode());
                logger.debug("Internal Status Code : " + alertingResponse.toString());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
            return false;
        }
    }

    public boolean verifyUpdateAPIResponseITSM() {

        try {
            if (alertingResponse.getStatusCode() == 200) {
                logger.debug("Update of ITSM Alert Done with Status Code : " + alertingResponse.getStatusCode());
                return true;
            } else {
                logger.debug("ITSM Incident Not Updated with Response Code : " + alertingResponse.getStatusCode());
                logger.debug("ITSM Incident Not Updated with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                return false;
            }
        } catch (Exception e) {
            logger.debug("ITSM Incident Updation Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyNonExistingAlertAPIResponse() {

        try {
            if (alertingResponse.getStatusCode() == 404) {
                if (JsonPath.from(alertingResponse.getBody().asString()).get("status").equals("203"))
                    logger.debug("Update/Delete of Alert Failed with Status Code : " + alertingResponse.getStatusCode() + " And Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                return true;
            } else if (alertingResponse.getStatusCode() == 204) {
                logger.debug("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
                return false;
            } else {
                logger.debug("Alert Updated/Deleted with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Updation/Deletion Passed : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyUpdateAPIResponseWithSnooze() {

        try {
            if (alertingResponse.getStatusCode() == 202) {
                logger.debug("Update of Alert Snoozed Status Code : " + alertingResponse.getStatusCode());
                return true;
            } else if (alertingResponse.getStatusCode() == 204) {
                logger.debug("Alert Updated during snooze period with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
            logger.debug("Alert Update is giving Response Code : " + alertingResponse.getStatusCode());
            return false;
        } catch (Exception e) {
            logger.debug("Alert Updation Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyDeleteAPIResponse() {

        try {
            if (alertingResponse.getStatusCode() == 204) {
                logger.debug("Delete of Alert Done with Status Code : " + alertingResponse.getStatusCode());
                return true;
            } else {
                logger.debug("Alert Not Deleted with Response Code : " + alertingResponse.getStatusCode());
                logger.debug("Alert Not Deleted with Internal Status Code : " + JsonPath.from(alertingResponse.getBody().asString()).get("status"));
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public void triggerITSMSimulatorAPI() {
        try {
            itsmPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(itsmUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void itsmPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());
        logger.debug("Getting Host URL:" + alertingUrl);


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

        logger.debug(itsmUrl);

    }

    public void triggerAlertFailureAPI() {
        try {
            alertFailurePreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertFailure));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void triggerAlertStateAPI() {
        try {
            alertStatePreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertState));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Alert State API Call Failed With Error : " + e.getMessage());
        }
    }

    public void alertFailurePreProcessing() throws Exception {
        logger.debug("Getting Host URL:" + alertingUrl);
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
        logger.debug("alertFailure : " + alertFailure);
    }

    public void alertStatePreProcessing() throws Exception {
        logger.debug("Getting Host URL:" + alertingUrl);
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
        logger.debug("alertState API : " + alertState);
    }

    public boolean getITSMSimulatorResponse() throws InterruptedException {
        Thread.sleep(5000);
        triggerITSMSimulatorAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                for (String alertID : alertId)
                    setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                return true;
            } else {
                logger.debug("Request to ITSM Failed with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save ITSM Response : " + e.getMessage());
            return false;
        }

    }

    public boolean getAlertFailureResponse() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertFailureAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                for (String alertID : alertId)
                    setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                return true;
            } else {
                logger.debug("Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save  Alert Failure Response : " + e.getMessage());
            return false;
        }

    }

    public boolean getAlertStateResponse() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertStateAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                setFilterArray(JsonRespParserUtility.parseResponseData((JSONObject) json, getCurrentAlert()));
                return true;
            } else {
                logger.debug("Request to Alert State with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save  Alert State Response : " + e.getMessage());
            return false;
        }
    }

    public boolean getAlertFailureResponseNotPresent() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertFailureAPI();
        JSONArray filterArray1 = new JSONArray();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSON json = JSONSerializer.toJSON(alertingResponse.getBody().asString());
                for (String alertID : alertId)
                    filterArray1.add(JsonRespParserUtility.parseResponseData((JSONObject) json, alertID));
                int i = 0;
                while (i < filterArray1.size()) {
                    JSONArray filterArray2 = (JSONArray) filterArray1.get(i);
                    if (filterArray2.size() < 1) {
                        i++;
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                logger.debug("Request to Alert Failure with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save  Alert Failure Response : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyAlertCreationInJAS() throws InterruptedException {
        Thread.sleep(3000);
        triggerJASGetAlertAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
                    logger.debug("Alert Id " + currentAlert + " Created in JAS.");
                    return true;
                } else {
                    logger.debug("ALert Id Not Created in JAS!!");
                    return false;
                }

            } else {
                logger.debug("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save JAS Response : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyAlertDeletionInJAS() throws InterruptedException {
        int i = 0;
        while (i < 10) {
            Thread.sleep(10000);
            triggerJASGetAlertAPI();
            try {
                if (alertingResponse.getStatusCode() == 404) {
                    JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                    if (jasResponse.get("status").equals(203)) {
                        logger.debug("Alert Id " + currentAlert + " Deleted from JAS.");
                        return true;
                    } else {
                        logger.debug("Alert Id Not Deleted from JAS!!");
                        return false;
                    }

                } else {
                    logger.debug("Delete Request Pending with Response Code : " + alertingResponse.getStatusCode());
                    i++;
                    continue;
                }
            } catch (Exception e) {
                logger.debug("Failed to save JAS Response : " + e.getMessage());
                return false;
            }
        }
        logger.debug("Alert ID " + currentAlert + " Not Deleted!!");
        return false;
    }

    public void triggerJASGetAlertAPI() {
        try {
            jasPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(jasUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void jasPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());

        setJasUrl(Utilities.getMavenProperties("DTJASHostUrlV1"));
        logger.debug("Getting Host URL:" + jasUrl);
        jasUrl = jasUrl + Utilities.getMavenProperties("GetJASAlertUrlSchema")
                .replace("{partners}", currentRow.get("partners"))
                .replace("{sites}", currentRow.get("sites"))
                .replace("{clients}", currentRow.get("clients"))
                .replace("{alert}", currentAlert);

        logger.debug(jasUrl);
    }

    public void triggerAlertingGetAlertAPI() {
        try {
            getAlertPreProcessing();
            this.setAlertDetailsResponse(JunoAlertingAPIUtil.getWithNoParameters(alertingAPIUrl));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ITSM Simulator API Call Failed With Error : " + e.getMessage());
        }
    }

    public void getAlertPreProcessing() throws Exception {

        //HashMap<String, String> currentRow = new HashMap<String, String>();

        //currentRow.putAll(DataUtils.getTestRow());

        logger.debug("Getting Host URL:" + alertingUrl);
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

        logger.debug(alertingAPIUrl);
    }

    public boolean verifyAlertCreationInAlertingMS() throws InterruptedException {
        Thread.sleep(3000);
        triggerAlertingGetAlertAPI();
        try {
            if (alertingResponse.getStatusCode() == 200) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(1) && jasResponse.get("alertId").equals(currentAlert)) {
                    logger.debug("Alert Id " + currentAlert + " Created in JAS.");
                    return true;
                } else {
                    logger.debug("ALert Id Not Created in JAS!!");
                    return false;
                }

            } else {
                logger.debug("Request to JAS Failed with Response Code : " + alertingResponse.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save JAS Response : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyAlertDeletionInAlertingMS() throws InterruptedException {
        Thread.sleep(5000);
        triggerAlertingGetAlertAPI();
        try {
            if (alertingResponse.getStatusCode() == 404) {
                JSONObject jasResponse = (JSONObject) JSONSerializer.toJSON(alertingResponse.getBody().asString());
                if (jasResponse.get("status").equals(203)) {
                    logger.debug("Alert Id " + currentAlert + " Deleted from JAS.");
                    return true;
                } else {
                    logger.debug("Alert Id Not Deleted from JAS!!");
                    return false;
                }

            } else {
                logger.debug("Alert ID " + currentAlert + " Not Deleted!!");
                return false;
            }
        } catch (Exception e) {
            logger.debug("Failed to save JAS Response : " + e.getMessage());
            return false;
        }
    }

    public boolean verifyITSMSimulatorResponse() throws InterruptedException {

        JsonPath filterPath = JsonPath.from(filterArray.toString());
        logger.debug(filterPath.getList("action"));
        int i = 0;

        try {
            if (filterArray.size() > 0) {
                while (i < filterArray.size()) {
                    if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                        if (filterArray.getJSONObject(i + 1).get("action").equals("PUT")) {
                            if (filterArray.getJSONObject(i + 2).get("action").equals("DELETE")) {
                                logger.debug("All 3 Requests Reached till ITSM");
                                i = i + 3;
                            } else {
                                logger.debug("Delete Requests is not reached till ITSM : ");
                                return false;
                            }
                        } else {
                            logger.debug("Update Requests is not reached till ITSM : ");
                            return false;
                        }
                    } else {
                        logger.debug("Create Requests is not reached till ITSM : ");
                        return false;
                    }
                }
                return true;
            } else {
                logger.debug("No Alerts Reached till ITSM!!");
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyChildListITSMSimulatorResponse() throws InterruptedException {
        JsonPath filterPath = JsonPath.from(filterArray.toString());
        List actualChildConditionId = new ArrayList<String>();
        logger.debug(filterPath.getList("action"));
        int i = 0;
        try {
            if (filterArray.size() > 0) {
                while (i < filterArray.size()) {
                    if (filterArray.getJSONObject(i).get("action").equals("POST")) {
                        JSONObject jsonObj = filterArray.getJSONObject(i);
                        JSONObject jsonObjPayload = jsonObj.getJSONObject("payload");
                        JSONArray jsonObjRootCauseArr = (JSONArray) jsonObjPayload.get("rootcause");
                        int z = 0;
                        if (jsonObjRootCauseArr == null) {
                            System.out.println("No Data Present in ITSM Simulator");
                            return false;
                        } else {
                            while (z < jsonObjRootCauseArr.size()) {
                                JSONObject resObject = jsonObjRootCauseArr.getJSONObject(z);
                                logger.debug("resObject : " + resObject);
                                actualChildConditionId.add(resObject.getString("conditionId"));
                                z++;
                            }
                        }
                    }
                    i++;
                }
            } else {
                logger.debug("No Alerts Reached till ITSM!!");
                return false;
            }
        } catch (Exception e) {
            logger.debug("Child Alert validation failed in ITSM simulator : " + e.getMessage());
            return false;
        }
        if (conditionId.size() == actualChildConditionId.size()) {
            if (conditionId.containsAll(actualChildConditionId)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean validateActualDataInITSM() throws InterruptedException {
        getActualDataInITSM();
        boolean flag = true;
        try {
            if (!getCurrentAlert().equalsIgnoreCase(actualDataInITSM.get("alertId"))) {
                flag = false;
                logger.debug("Data Mismatch in Alert ID : Expected -> " + getCurrentAlert() + " :: Actual ->" + actualDataInITSM.get("alertId"));
            }
            if (!currentRow.get("partners").equalsIgnoreCase(actualDataInITSM.get("partnerId"))) {
                flag = false;
                logger.debug("Data Mismatch in Patner ID : Expected -> " + currentRow.get("partners") + " :: Actual ->" + actualDataInITSM.get("partnerId"));
            }
            if (!currentRow.get("clients").equalsIgnoreCase(actualDataInITSM.get("clientId"))) {
                flag = false;
                logger.debug("Data Mismatch in CLient ID : Expected -> " + currentRow.get("clients") + " :: Actual ->" + actualDataInITSM.get("clientId"));
            }
            if (!currentRow.get("sites").equalsIgnoreCase(actualDataInITSM.get("siteId"))) {
                flag = false;
                logger.debug("Data Mismatch in Sites ID : Expected -> " + currentRow.get("sites") + " :: Actual ->" + actualDataInITSM.get("siteId"));
            }
            //     if (!currentRow.get("resourceId").equalsIgnoreCase(actualDataInITSM.get("resourceId"))) {
            //         flag = false;
            //         logger.debug("Data Mismatch in Resource ID : Expected -> " + currentRow.get("resourceId") + " :: Actual ->" + actualDataInITSM.get("resourceId"));
            //     }
            if (!currentRow.get("endpoints").equalsIgnoreCase(actualDataInITSM.get("endpointId"))) {
                flag = false;
                logger.debug("Data Mismatch in Endpoint ID : Expected -> " + currentRow.get("endpoints") + " :: Actual ->" + actualDataInITSM.get("endpointId"));
            }
            if (!currentRow.get("conditionId").equalsIgnoreCase(actualDataInITSM.get("conditionId"))) {
                flag = false;
                logger.debug("Data Mismatch in Condition ID : Expected -> " + currentRow.get("conditionId") + " :: Actual ->" + actualDataInITSM.get("conditionId"));
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(currentRow.get("alertDetails"));
            JSONObject jsonObj1 = jsonObj.getJSONObject("alertDetails");
            if (!jsonObj1.getString("Test").equalsIgnoreCase(actualDataInITSM.get("Test"))) {
                flag = false;
                logger.debug("Data Mismatch in Test : Expected -> " + jsonObj1.getString("Test") + " :: Actual ->" + actualDataInITSM.get("alertId"));
            }
            if (!jsonObj1.getString("Type").equalsIgnoreCase(actualDataInITSM.get("Type"))) {
                flag = false;
                logger.debug("Data Mismatch in Type : Expected -> " + jsonObj1.getString("Type") + " :: Actual ->" + actualDataInITSM.get("alertId"));
            }
        } catch (Exception e) {
            logger.debug("Exception Occurred : " + e);
            flag = false;
        }
        return flag;
    }

    public boolean validateITSMData(String field, String value) throws InterruptedException {
        getActualDataInITSM();
        boolean flag = true;
        if (!value.equalsIgnoreCase(actualDataInITSM.get(field))) {
            flag = false;
            logger.debug("Data Mismatch in " + field + " : Expected -> " + value + " :: Actual ->" + actualDataInITSM.get("field"));
        }
        return flag;
    }

    public String validateAlertState(List<String> factorList) throws InterruptedException {
        String errMsg = "";
        try {
            if (filterArray.size() > 0) {
                JSONObject jsonObj = filterArray.getJSONObject(0);
                for (String factor : factorList) {
                    switch (factor) {
                        case "child list":
                            String expectedChildList = alertId.get(0);
                            String actualChildList = jsonObj.getString("childlist");
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
                logger.debug("No data present in Alert State");
                errMsg = "[No data present in Alert State. As filterArray size is 0]";
            }
        } catch (Exception e) {
            logger.debug("Exception Occurred " + e);
            errMsg = "Exception Occured " + e.toString() + "]";
        }
        return errMsg;
    }

    public void getActualDataInITSM() throws InterruptedException {
        JsonPath filterPath = JsonPath.from(filterArray.toString());
        logger.debug(filterPath.getList("action"));
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
                        setActualDataInITSM("resourceId", jsonObjPayload.getString("resourceId"));
                        setActualDataInITSM("endpointId", jsonObjPayload.getString("endpointId"));
                        setActualDataInITSM("conditionId", jsonObjPayload.getString("conditionId"));
                        setActualDataInITSM("statuscode", Integer.toString(jsonObj.getInt("statuscode")));
                        JSONObject jsonObjAlertDetails = jsonObjPayload.getJSONObject("alertdetails");
                        setActualDataInITSM("Test", jsonObjAlertDetails.getString("Test"));
                        setActualDataInITSM("Type", jsonObjAlertDetails.getString("Type"));
                        break;
                    }
                    i++;
                }
            } else {
                logger.debug("No Alerts Reached till ITSM!!");
            }
        } catch (Exception e) {
            logger.debug("Exception Occured : " + e.getMessage());
        }
    }

    public boolean verifyITSMResponseForChildAlert() throws InterruptedException {

        try {
            if (filterArray.isEmpty()) {
                logger.debug("No Child Alerts Reached Till ITSM.");
                return true;
            } else {
                logger.debug("Child alert reached till ITSM!!");
                return false;
            }
        } catch (Exception e) {
            logger.debug("Alert Deletion Failed with Error Message : " + e.getMessage());
            return false;
        }

    }

    public boolean verifyRemediateURLITSMRequest() throws InterruptedException {

        int i = 0;
        while (i < filterArray.size()) {
            JSONObject filterObj = filterArray.getJSONObject(i);
            if (filterObj.get("action").equals("POST")) {
                JSONObject payloadObj = filterObj.getJSONObject("payload");
                if (payloadObj.get("remediateurl").equals(null) || payloadObj.get("fetchdataurl").equals(null)) {
                    logger.debug("URL for Remediate or FetchMoreData is Null");
                    return false;
                } else {
                    logger.debug("Remediate URL : " + payloadObj.get("remediateurl"));
                    logger.debug("FetchMore URL : " + payloadObj.get("fetchdataurl"));
                    return true;
                }
            }
            i++;
        }
        logger.debug("There is No POST Request in ITSM Simulator");
        return false;
    }

    public boolean triggerManualClosure(String kafkaMessageType) {

        String kafkaMessage;

        switch (kafkaMessageType) {
            case "AlertID":
                kafkaMessage = "{\"alertId\":\"" + getCurrentAlert() + "\",\"transactionId\":\"TEST\"}";
                break;
            case "MetaData":
                kafkaMessage = getManualClosureMetadata();
                break;
            default:
                logger.debug("Message Type is Invalid!!");
                return false;
        }

        logger.debug("Posting Kafka Message to Kafka topic : " + kafkaMessage);
        KafkaProducerUtility.postMessage(kafkaServer, Utilities.getMavenProperties("KafkaTopic"), kafkaMessage);
        return true;

    }

    public String getManualClosureMetadata() {

        HashMap<String, String> currentRow = new HashMap<String, String>();

        JSONObject manualClosureMessage = new JSONObject();
        manualClosureMessage.put("clientid", currentRow.get("clients"));
        manualClosureMessage.put("siteid", currentRow.get("sites"));
        manualClosureMessage.put("partnerid", currentRow.get("partners"));
        manualClosureMessage.put("resourceid", currentRow.get("resourceid"));
        manualClosureMessage.put("endpointid", currentRow.get("endpoints"));
        manualClosureMessage.put("conditionid", currentRow.get("conditionid"));

        return manualClosureMessage.toString();
    }

    public boolean waitForSnooze(int duration) throws InterruptedException {

        TimeUnit.SECONDS.sleep(duration);
        return true;

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

}
