package com.continuum.utils;

import continuum.cucumber.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseUtil {

	String queryToGetRegID = "Select top 1 * from Regmain with(NOLOCK)where guid = <GUID> and SiteCode=<SITECODE> order by regid desc;";

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
