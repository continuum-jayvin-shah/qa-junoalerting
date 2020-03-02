package com.continuum.utils;

import continuum.cucumber.DatabaseUtility;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import continuum.cucumber.Utilities;
import org.apache.log4j.Logger;

public class DatabaseUtil {

    private static String siteCode, resouceName, legacyId, alertId;
    private static int conditionId = 0;
    private static Connection connection;
    private static Logger logger = Logger.getLogger("DatabaseUtil");

    private static final String DB_SERVER_URL = Utilities.getMavenProperties("DBserverURLQA");
    private static final String DB_NAME = Utilities.getMavenProperties("DBnameQA");
    private static final String DB_USER_NAME = Utilities.getMavenProperties("DBusernameQA");
    private static final String DB_PASSWORD = Utilities.getMavenProperties("DBpasswordQA");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa");
    private static final String LDS_DESCRIPTION = Utilities.getMavenProperties("lowDiskSpaceDescriptionWithTime");
    private static final String ALERT_DETAILS_ID = "AlertDetailsID";


    String queryToGetRegID = "Select top 1 * from Regmain with(NOLOCK)where guid = <GUID> and SiteCode=<SITECODE> order by regid desc;";

    public static Connection createConnection() {
        Connection connection;
        connection = DatabaseUtility.createConnection(DB_NAME, DB_SERVER_URL, DB_USER_NAME, DB_PASSWORD);
        return connection;
    }

    public static void setSiteCode(String siteCode1) {
        siteCode = siteCode1;
    }

    public static String getSiteCode() {
        return siteCode;
    }

    public static void setResourceName(String resouceName1) {
        resouceName = resouceName1;
    }

    public static String getResourceName() {
        return resouceName;
    }

    public static void setLegacyId(String legacyId1) {
        legacyId = legacyId1;
    }

    public static String getLegacyId() {
        return legacyId;
    }

    public static void setConditonId(int conditionId1) {
        conditionId = conditionId1;
    }

    public static int getConditionId() {
        return conditionId;
    }

    public static void setAlertId(String alertId1) {
        alertId = alertId1;
    }

    public static String getAlertID1() {
        return alertId;
    }

    public static boolean changeAllActualAlertStatusForEndPoint(int status, int reggID) throws SQLException {
        connection = createConnection();
        try {
            final PreparedStatement statement = connection.prepareStatement(Utilities.getMavenProperties("QUERY_TO_CHANGE_ALL_ENDPOINT_ALERT_STATUS"));
            statement.setInt(1, status);
            statement.setInt(2, reggID);
            int rowCount = statement.executeUpdate();
            logger.info("All Actual Alerts status changed " + status + " with RegID " + reggID
                    + ". Query status " + rowCount);
        } catch (SQLException se) {
            logger.info("Unable to Execute the Update query to udate the status");
            throw se;
        } finally {
            closeConnection();
        }
        return true;
    }

    public static void closeConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static String getAlertDetailsId(String alertId) throws InterruptedException {
        connection = createConnection();
        int attempt = 0;
        String rowValue = null;
        while (attempt != 5) {
            String sqlQueryToGetAlertDetails = Utilities
                    .getMavenProperties("sqlQueryToGetAlertDetailsFromAlertSync") + alertId;
            Map<String, String> map = new HashMap<String, String>();
            try {
                Statement stmt;
                ResultSet rs;
                stmt = createConnection().createStatement();
                rs = stmt.executeQuery(sqlQueryToGetAlertDetails);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        String columnName = rsmd.getColumnName(i);
                        String columnValue = rs.getString(i);
                        map.put(columnName, columnValue);
                    }
                }
                rowValue = (String) map.get(ALERT_DETAILS_ID);
                if (rowValue != null) {
                    break;
                }
                Thread.sleep(2000);
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return rowValue;
    }

    public static String createLowDiskSpaceAlertInDatabase() throws SQLException {
        final String siteCodeStr = getSiteCode();
        final String resourceNameStr = getResourceName();
        final String legacyId = getLegacyId();
        final int conditionId = 5;
        final Timestamp time = getCurrentDateInDatabase();
        final String dateForDescription = DATE_FORMATTER.format(new Date(time.getTime()));
        final String description = LDS_DESCRIPTION.replaceAll("TIME_REPLACE", dateForDescription);
        String columnValue = null;

        final String query = "USE NOCBO DECLARE @JobId1 bigint exec\r\n"
                + "JobManagement_SOD  'ContinuumTesting_QA','Server Performance Alert','" + description + "',\r\n"
                + "1,'Emergency\\LowDiskSpace',2,0,Null,'" + time.toString() + "','" + time.toString() + "',Null,'"
                + siteCodeStr + "','','" + resourceNameStr + "','SAM',Null,\r\n" + "@jobid1 output,'GPM','"
                + time.toString()
                + "','Emergency\\LowDiskSpace\\C:',0,'ERROR','Generated by Critical Data Checking Process',Null,10,1,"
                + legacyId + ",Null,Null,Null,'" + time.toString() + "',Null," + conditionId
                + ",Null,Null SELECT @jobid1";
        final Statement stat = createConnection().createStatement();
        try {
            final ResultSet rs = stat.executeQuery(query);
            if (rs.next()) {
                columnValue = rs.getString(1);
            }
        } catch (SQLException e) {
            logger.info("Unable to execute the SQL Query " + query);
            throw e;
        } finally {
            stat.close();
            closeConnection();
        }
        return columnValue;
    }

    public static Timestamp getCurrentDateInDatabase() throws SQLException {
        connection = createConnection();
        final String sqlQuery = "select getDate()";
        Statement statement = connection.createStatement();
        Timestamp currentDate = null;
        try {
            ResultSet rs = statement.executeQuery(sqlQuery);
            if (rs.next()) {
                currentDate = rs.getTimestamp(1);
            }
        } catch (SQLException e) {
            logger.info("Error ocuured while executing getDate() SQL query : " + sqlQuery);
            throw e;
        } finally {
            closeConnection();
        }
        return currentDate;
    }

    public static String getRegID(String databaseName1, String sqlServerURL1, String username1, String password1,
                                  String guid,
                                  String siteCode) throws Exception {
        String regID = null;
        String query = "Select top 1 * from Regmain with(NOLOCK)where guid = '<guid>' and SiteCode='<sitecode>' order by regid desc;";
        query = query.replace("<guid>", guid);
        query = query.replace("<sitecode>", siteCode);
        ResultSet rs = executeQuery(databaseName1, sqlServerURL1, username1, password1, query);
        while (rs.next()) {
            regID = rs.getString("RegId");
        }
        return regID;
    }

    public static String getTicketId(String databaseName, String sqlServerURL, String username, String password,
                                     String regID,
                                     String conditionID)
            throws Exception {
        String ticketID = null;
        String query = "Select * from NetworkMgmt_Tkt_Cons with(NOLOCK) where ParentRegId=<regID> and ConditionId=<conditionID>;";
        query = query.replace("<regID>", regID);
        query = query.replace("<conditionID>", conditionID);
        ResultSet rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            ticketID = rs.getString("TicketId");
        }
        return ticketID;
    }

    public static String getTicketIdFromHistory(String databaseName, String sqlServerURL, String username, String password,
                                                String regID,
                                                String conditionID)
            throws Exception {
        String ticketID = null;
        String query = "Select * from NetworkMgmt_Tkt_Cons_history with(NOLOCK) where ParentRegId=<regID> and ConditionId=<conditionID>;";
        query = query.replace("<regID>", regID);
        query = query.replace("<conditionID>", conditionID);
        ResultSet rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            ticketID = rs.getString("TicketId");
        }
        return ticketID;
    }

    public static String getAlertId(String databaseName, String sqlServerURL, String username, String password,
                                    String regID,
                                    String conditionID) throws Exception {
        String ticketID = null;
        String query = "Select * from NetworkMgmt_Tkt_Cons with(NOLOCK) where ParentRegId=<regID> and ConditionId=<conditionID>;";
        query = query.replace("<regID>", regID);
        query = query.replace("<conditionID>", conditionID);
        ResultSet rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            ticketID = rs.getString("AlertId");
        }
        return ticketID;
    }

    public static void main(String[] args) throws Exception {
        String databaseName1 = "itsupport247db";
        String sqlServerURL1 = "jdbc:sqlserver://10.2.40.45:1433";
        String username1 = "QA Automation";
        String password1 = "aUt0T3$t#958";
        String query1 = "Select top 1 * from Regmain with(NOLOCK)where guid = 'A201607123100' and SiteCode='ContinuumVault' order by regid desc;";

        ResultSet rs = executeQuery(databaseName1, sqlServerURL1, username1, password1, query1);

        while (rs.next()) {
            System.out.println(rs.getString("RegId"));
        }

        String databaseName = "ITSAlertGenerator";
        String sqlServerURL = "jdbc:sqlserver://10.2.40.46:1433";
        String username = "QA Automation";
        String password = "aUt0T3$t#958";
        String query = "Select * from NetworkMgmt_Tkt_Cons with(NOLOCK) where ParentRegId=53742595 and ConditionId=11826;";


        rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            System.out.println(rs.getString("TicketId"));
        }

        databaseName = "ITSAlertGenerator";
        sqlServerURL = "jdbc:sqlserver://10.2.40.46:1433";
        username = "QA Automation";
        password = "aUt0T3$t#958";
        query = "Select * from NetworkMgmt_Tkt_Cons_history with(NOLOCK) where ParentRegId=53742595 and ConditionId=11826;";

        rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            System.out.println(rs.getString("TicketId"));
        }

        databaseName = "ITSAlertGenerator";
        sqlServerURL = "jdbc:sqlserver://10.2.40.46:1433";
        username = "QA Automation";
        password = "aUt0T3$t#958";
        query = "Select * from NetworkMgmt_Tkt_Cons with(NOLOCK) where ParentRegId=53742595 and ConditionId=11801;";

        rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            System.out.println(rs.getString("AlertId"));
        }

        databaseName = "ITSAlertGenerator";
        sqlServerURL = "jdbc:sqlserver://10.2.40.46:1433";
        username = "QA Automation";
        password = "aUt0T3$t#958";
        query = "Select * from NetworkMgmt_Tkt_Cons_history with(NOLOCK) where ParentRegId=53742595 and ConditionId=11801;";

        rs = executeQuery(databaseName, sqlServerURL, username, password, query);

        while (rs.next()) {
            System.out.println(rs.getString("AlertId"));
        }
    }

    public String getRegId(HashMap<String, String> dataRow) {
        // String dataRow
        return queryToGetRegID;

    }

    public static ResultSet executeQuery(String databaseName, String sqlServerURL, String username, String password,
                                         String query)
            throws Exception {
        Connection conn = DatabaseUtility.createConnection(databaseName, sqlServerURL, username, password);
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Table doesn not exist");
            e.printStackTrace();
            throw e;

        }
        return rs;
    }
}
