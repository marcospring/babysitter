package com.zhangk.babysitter.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelOperator {
	private HSSFWorkbook excelBook;
	private List<HSSFSheet> sheets;
	private int sheetsSize;

	public ExcelOperator(InputStream in) {
		try {
			excelBook = new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HSSFWorkbook getExcelBook() {
		return excelBook;
	}

	public List<HSSFSheet> getSheets() {
		if (excelBook == null)
			return null;
		if (sheets == null) {
			sheets = new ArrayList<HSSFSheet>();
			int size = getSheetsSize();
			for (int i = 0; i < size; i++) {
				sheets.add(excelBook.getSheetAt(i));
			}
		}
		return sheets;
	}

	public int getSheetsSize() {
		if (excelBook == null)
			return 0;
		sheetsSize = excelBook.getNumberOfSheets();
		return sheetsSize;
	}

	public HSSFCell getCell(int sheetIndex, int rowIndex, int columnIndex) {
		if (sheetIndex > getSheetsSize())
			throw new IndexOutOfBoundsException();
		HSSFSheet sheet = getSheets().get(sheetIndex);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(columnIndex);
		return cell;
	}

	public String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = " ";
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数值类型
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			break;
		case Cell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
}