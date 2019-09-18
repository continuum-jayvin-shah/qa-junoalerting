package com.continuum.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.testng.Assert;

public class DataUtils {

	private static HashMap<String, String> testRow;
	private static String fileName;

	/*
	 * public static void main(String[] args) throws IOException { // TODO
	 * Auto-generated method stub HashMap<String, String> rowData = getTestData(
	 * "C:\\Users\\sanjot.mansukh\\Desktop\\Auvik\\StackSwitchPortStatusChange.xls",
	 * null, "Auvik", "AUVIKSC001");
	 * System.out.println(rowData.get("Condition name")); }
	 */

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		DataUtils.fileName = fileName;
	}

	public static HashMap<String, String> getTestRow() {
		return testRow;
	}

	/**
	 * Fetch the test data for a test case based on test case ID
	 *
	 * @param filePath
	 *            test data xls location
	 * @param workBook
	 *            name
	 * @param sheetName
	 *            name
	 * @param testCaseId
	 *            test id
	 * @return testData data
	 * @throws IOException
	 */
	public synchronized static void setTestRow(String filePath, String sheetName,
			String testCaseId) throws IOException {
		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + filePath;
		System.out.println("Reading validation points from excel file " + filePath);
		
		HSSFRow row = null;
		HSSFCell cell = null;

		// Establish connection to work sheet
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFilePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// System.out.println(wb.getSheetName(1));
		HSSFSheet sheet = wb.getSheet(sheetName);
		Hashtable<String, Integer> excelrRowColumnCount = new Hashtable();
		excelrRowColumnCount = findRowColumnCount(sheet, excelrRowColumnCount);

		// function call to find excel header fields
		Hashtable<String, Integer> excelHeaders = new Hashtable();
		excelHeaders = readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount);
		HashMap<String, String> data = null;
		ArrayList<String> header = new ArrayList();
		ArrayList<String> matcher = null;
		// HashMap<String, String> matcherList = new HashMap<>();
		testRow = new HashMap();
		int idcounter = 1;

		// Get all header
		row = sheet.getRow(0);
		if (row != null) {
			for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {
				cell = sheet.getRow(0).getCell(c);
				if (cell != null) {
					String temp = convertHSSFCellToString(row.getCell(c));
					header.add(temp);
				}
			}
		}

		// Get test data set
		for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);
			if (row != null) {
				HSSFCell tempCell = sheet.getRow(r).getCell(0);
				if (tempCell != null) {
					String tcID = convertHSSFCellToString(row.getCell(0));
					if (tcID.equalsIgnoreCase(testCaseId)) {
						if (idcounter > 1) {
							Assert.fail("Please check the workBook' file's '" + sheetName
									+ "' sheet, its mapped with more than one id: " + testCaseId);
						}
						data = new HashMap();
						matcher = new ArrayList();
						matcher.add(tcID);
						for (int c = 1; c < excelrRowColumnCount.get("ColumnCount"); c++) {
							cell = sheet.getRow(r).getCell(c);
							String temp = convertHSSFCellToString(row.getCell(c));
							matcher.add(temp);
						}
						// Add all the test data to a Map
						for (int i = 0; i < matcher.size(); i++) {
							data.put(header.get(i), matcher.get(i));
						}
						testRow.putAll(data);
						idcounter++;
					}
				}
			}
		}

		// matcherList;
	}
	
	/**
	 * Fetch the test data for a test case based on test case ID
	 *
	 * @param filePath
	 *            test data xls location
	 * @param workBook
	 *            name
	 * @param sheetName
	 *            name
	 * @param testCaseId
	 *            test id
	 * @return testData data
	 * @throws IOException
	 */
	public synchronized static HashMap<String, String> getTestRow(String sheetName,String testCaseId) throws IOException {
		String excelFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\" + fileName;
		System.out.println("Reading validation points from excel file " + fileName + excelFilePath);
		
		HSSFRow row = null;
		HSSFCell cell = null;

		// Establish connection to work sheet
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFilePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// System.out.println(wb.getSheetName(1));
		HSSFSheet sheet = wb.getSheet(sheetName);
		Hashtable<String, Integer> excelrRowColumnCount = new Hashtable();
		excelrRowColumnCount = findRowColumnCount(sheet, excelrRowColumnCount);

		// function call to find excel header fields
		Hashtable<String, Integer> excelHeaders = new Hashtable();
		excelHeaders = readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount);
		HashMap<String, String> data = null;
		ArrayList<String> header = new ArrayList();
		ArrayList<String> matcher = null;
		// HashMap<String, String> matcherList = new HashMap<>();
		testRow = new HashMap();
		int idcounter = 1;

		// Get all header
		row = sheet.getRow(0);
		if (row != null) {
			for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {
				cell = sheet.getRow(0).getCell(c);
				if (cell != null) {
					String temp = convertHSSFCellToString(row.getCell(c));
					header.add(temp);
				}
			}
		}

		// Get test data set
		for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);
			if (row != null) {
				HSSFCell tempCell = sheet.getRow(r).getCell(0);
				if (tempCell != null) {
					String tcID = convertHSSFCellToString(row.getCell(0));
					if (tcID.equalsIgnoreCase(testCaseId)) {
						if (idcounter > 1) {
							Assert.fail("Please check the workBook' file's '" + sheetName
									+ "' sheet, its mapped with more than one id: " + testCaseId);
						}
						data = new HashMap();
						matcher = new ArrayList();
						matcher.add(tcID);
						for (int c = 1; c < excelrRowColumnCount.get("ColumnCount"); c++) {
							cell = sheet.getRow(r).getCell(c);
							String temp = convertHSSFCellToString(row.getCell(c));
							matcher.add(temp);
						}
						// Add all the test data to a Map
						for (int i = 0; i < matcher.size(); i++) {
							data.put(header.get(i), matcher.get(i));
						}
						testRow.putAll(data);
						idcounter++;
					}
				}
			}
		}
		return testRow;
		// matcherList;
	}
	

	/**
	 * findRowColumnCount method to get total no of row and column count in a
	 * excel work sheet
	 *
	 * @param sheet
	 *            name
	 * @param rowColumnCount
	 *            as Hashtable
	 * @return Hashtable (returns row count and column count)
	 */

	public static Hashtable<String, Integer> findRowColumnCount(HSSFSheet sheet,
			Hashtable<String, Integer> rowColumnCount) {

		HSSFRow row = null;
		int rows;
		rows = sheet.getPhysicalNumberOfRows();
		int cols = 0;
		int tmp = 0;
		int counter = 0;
		String temp = null;

		for (int i = 0; i < 10 || i < rows; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				temp = convertHSSFCellToString(row.getCell(0));
				if (!temp.isEmpty()) {
					counter++;
				}
				tmp = sheet.getRow(i).getPhysicalNumberOfCells();
				if (tmp > cols) {
					cols = tmp;
				}
			}
		}

		rowColumnCount.put("RowCount", counter);
		rowColumnCount.put("ColumnCount", cols);

		return rowColumnCount;
	}

	/**
	 * convertHSSFCellToString method to convert the HSSFCell value to its
	 * equivalent string value
	 *
	 * @param cell
	 *            value
	 * @return String cellValue
	 */
	public static String convertHSSFCellToString(HSSFCell cell) {
		String cellValue = null;
		if (cell != null) {
			cell.setCellType(1);
			cellValue = cell.toString();
			cellValue = cellValue.trim();
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	/**
	 * readExcelHeaders method read the excel headers column wise sheet
	 *
	 * @param sheet
	 *            name
	 * @param excelHeaders
	 *            (Hashtable)
	 * @param rowColumnCount
	 *            (Hashtable)
	 * @return excelHeaders (returns Header column values)
	 */
	public static Hashtable<String, Integer> readExcelHeaders(HSSFSheet sheet, Hashtable<String, Integer> excelHeaders,
			Hashtable<String, Integer> rowColumnCount) {

		HSSFRow row = null;
		HSSFCell cell = null;
		for (int r = 0; r < rowColumnCount.get("RowCount"); r++) {
			row = sheet.getRow(r);

			if (row != null) {
				for (int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {
					cell = row.getCell(c);
					if (cell != null) {
						excelHeaders.put(cell.toString(), c);
					}
				}
				break;
			}
		}
		return excelHeaders;
	}

	/*
	 * public static String getOSBrowserInfo() { // get the os and browser
	 * details(name and version) String browser = ((RemoteWebDriver)
	 * driver).getCapabilities().getBrowserName(); String version =
	 * ((RemoteWebDriver)
	 * driver).getCapabilities().getVersion().split("\\.")[0]; Platform platform
	 * = ((RemoteWebDriver) driver).getCapabilities().getPlatform(); String
	 * osDetails = getOsDetails(driver); String browserDetails = browser +
	 * version; String osAndBrowserInfo = osDetails + "_" + browserDetails; }
	 */

}
