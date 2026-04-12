package com.skillio.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class ExcelUtil {
	private String filePath;

	public ExcelUtil() {
		// TODO Auto-generated constructor stub
	}

	public ExcelUtil(String filePath) {
		this.filePath = filePath;
	}

	// This method accepts path of the excel file and sheet name and returns the
	// data in the form of list of data present in row in the excel sheet and the
	// key of the map is the column name and value is the cell value
	public List getRowData(String filePath, String sheetName, int rowNum) throws IOException {
		System.out.println("Reading data from file: " + filePath + ", sheet: " + sheetName + ", row: " + rowNum);
		XSSFWorkbook workbook = null;
		System.out.println("Attempting to load Excel file...");
		FileInputStream fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		System.out.println("Excel file loaded successfully.");
		XSSFSheet sheet = workbook.getSheet(sheetName);
		System.out.println("Sheet '" + sheetName + "' loaded successfully.");
		List rowData = new ArrayList<>();
		Row row = sheet.getRow(rowNum);
		int cells = row.getLastCellNum();
		System.out.println("Total cells in the row: " + cells);
		for (int i = 0; i < cells; i++) {
			switch (row.getCell(i).getCellType()) {
			case STRING:
				System.out.println("String Cell: " + row.getCell(i).getStringCellValue());
				rowData.add(row.getCell(i).getStringCellValue());
				break;
			case NUMERIC:
				System.out.println("Numeric Cell: " + row.getCell(i).getNumericCellValue());
				rowData.add((int) row.getCell(i).getNumericCellValue());
				break;
			case BOOLEAN:
				rowData.add(row.getCell(i).getBooleanCellValue());
				break;
			case BLANK:
				rowData.add(null);
				break;
			case ERROR:
				rowData.add(row.getCell(i).getErrorCellValue());
				break;
			default:
				rowData.add(row.getCell(i).getStringCellValue());
				break;
			}
		}
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowData;
	}

	public List getRowData(String sheetName, int rowNum) throws IOException {
		return getRowData(this.filePath, sheetName, rowNum);
	}

	// Reads all values from a specified column (0-based index) and returns them as
	// a
	// List<Object> so that all cell types (String, Number, Boolean, Date, null) are
	// supported.
	public List<Object> getColumnData(String filePath, String sheetName, int colNum) {
		List<Object> columnData = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				return columnData; // empty list if sheet not found
			}

			DataFormatter formatter = new DataFormatter();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			int lastRow = sheet.getLastRowNum();
			for (int r = 0; r <= lastRow; r++) {
				Row row = sheet.getRow(r);
				if (row == null) {
					columnData.add(null);
					continue;
				}
				Cell cell = row.getCell(colNum);
				if (cell == null) {
					columnData.add(null);
					continue;
				}
				CellType type = cell.getCellType();
				Object value = null;
				if (type == CellType.FORMULA) {
					CellValue cv = evaluator.evaluate(cell);
					if (cv == null) {
						value = null;
					} else {
						switch (cv.getCellType()) {
						case STRING:
							value = cv.getStringValue();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								value = cell.getDateCellValue();
							} else {
								value = cv.getNumberValue();
							}
							break;
						case BOOLEAN:
							value = cv.getBooleanValue();
							break;
						case BLANK:
							value = null;
							break;
						case ERROR:
							value = cv.getErrorValue();
							break;
						default:
							value = formatter.formatCellValue(cell, evaluator);
						}
					}
				} else {
					switch (type) {
					case STRING:
						value = cell.getStringCellValue();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							value = cell.getDateCellValue();
						} else {
							value = cell.getNumericCellValue();
						}
						break;
					case BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case BLANK:
						value = null;
						break;
					case ERROR:
						value = cell.getErrorCellValue();
						break;
					default:
						value = formatter.formatCellValue(cell);
					}
				}
				columnData.add(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnData;
	}

}