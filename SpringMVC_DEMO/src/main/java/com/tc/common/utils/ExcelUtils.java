package com.tc.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelUtils {
	public final static String EXCEL_SUFFIX_2003 = "xls";
	public final static String EXCEL_SUFFIX_2007 = "xlsx";

	public final static int EXCEL_VERSION_2003 = 0;
	public final static int EXCEL_VERSION_2007 = 1;
	public final static int EXCEL_VERSION_ERROR = 2; // 非Excel文件
	
	/**
	 * 得到EXCEL版本
	 * @param file
	 * @return
	 */
	public static int getExcelVesion(File file) {
		int index = file.getName().lastIndexOf(".");
		if (index > -1) {
			if (EXCEL_SUFFIX_2003.equals(file.getName().substring(index + 1)))
				return EXCEL_VERSION_2003;
			else if (EXCEL_SUFFIX_2007.equals(file.getName().substring(
					index + 1)))
				return EXCEL_VERSION_2007;
		}
		return EXCEL_VERSION_ERROR;
	}

	/**
	 * 根据不同的EXCEL版本得到相同的WORKBOOK对象
	 * @param file
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public static Workbook getWorkbook(File file) throws FileNotFoundException,
			IOException {
		int version = getExcelVesion(file);
		if (version == EXCEL_VERSION_2003)
			return new HSSFWorkbook(new FileInputStream(file));
		if (version == EXCEL_VERSION_2007)
			return new XSSFWorkbook(new FileInputStream(file));
		return null;
	}
	
	/**
	 * 获得单元格字符串值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
				return cell.getCellFormula().trim();
			case Cell.CELL_TYPE_NUMERIC:
				return "" + (long) cell.getNumericCellValue();
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().trim();
			case Cell.CELL_TYPE_BOOLEAN:
				return "" + cell.getBooleanCellValue();
			case Cell.CELL_TYPE_BLANK:
				return "";
			default:
				return "";
		}

	}

	public static String getCellValue(Cell cell, Object obj ) {
		if (cell == null)
			return "";

		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
				return cell.getCellFormula().trim();
			case Cell.CELL_TYPE_NUMERIC:
				if( obj instanceof Date) {
					Date dt = cell.getDateCellValue();
					if( dt != null ) {
						SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						return fo.format( dt );
					}
					else return "";
				}
				else if( obj instanceof Long)
					return String.format("%d", (long) cell.getNumericCellValue());
				else if( obj instanceof Double)
					return String.format("%lf", cell.getNumericCellValue());
				else if( obj instanceof Float)
					return String.format("%f", cell.getNumericCellValue());
				else return String.format("%d", (int) cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().trim();
			case Cell.CELL_TYPE_BOOLEAN:
				return "" + cell.getBooleanCellValue();
			case Cell.CELL_TYPE_BLANK:
				return "";
			default:
				return "";
		}

	}
}
