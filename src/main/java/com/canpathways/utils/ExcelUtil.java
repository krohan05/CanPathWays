package com.canpathways.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	public Workbook wb;
	public Sheet sh;
	public Row row;
	public Cell cell;

	public ExcelUtil(String testDataSheetPath) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(testDataSheetPath);
			wb = WorkbookFactory.create(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Object[][] getTestData(String sheetName) {

		System.out.println("reading the data from sheet: " + sheetName);
		Object data[][] = null;

		sh = wb.getSheet(sheetName);

		data = new Object[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];

		for (int i = 0; i < sh.getLastRowNum(); i++) {
			for (int j = 0; j < sh.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sh.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}

	public String getDataForKnownValue(String sheetName, String coulumName, String knownValueFromFirstRow) {

		try {
			int colNum = 0;
			int rowNum = 0;
			sh = wb.getSheet(sheetName);

			for (int i = 0; i < sh.getRow(0).getLastCellNum(); i++) {
				if (sh.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(coulumName)) {
					colNum = i;
					break;
				}
			}

			for (int i = 0; i <= sh.getLastRowNum(); i++) {
				if (sh.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(knownValueFromFirstRow)) {
					rowNum = i;
					break;
				}
			}

			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cell.getStringCellValue();
	}
}
