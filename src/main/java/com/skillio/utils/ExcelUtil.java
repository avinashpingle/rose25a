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

	// This method accepts path of the excel file and sheet name and returns the
	// data in the form of list of data present in row in the excel sheet and the
	// key of the map is the column name and value is the cell value
	public List getRowData(String filePath, String sheetName, int rowNum) {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheet(sheetName);
		List rowData = new ArrayList<>();
		int rows = sheet.getLastRowNum();
		Row row = sheet.getRow(rowNum);
		int cells = row.getLastCellNum();
		for (int i = 0; i < cells; i++) {
			rowData.add(row.getCell(i).getStringCellValue());
		}
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowData;
	}
	
	// Reads all values from a specified column (0-based index) and returns them as a
	// List<Object> so that all cell types (String, Number, Boolean, Date, null) are supported.
	public List<Object> getColumnData(String filePath, String sheetName, int colNum) {
		List<Object> columnData = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
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