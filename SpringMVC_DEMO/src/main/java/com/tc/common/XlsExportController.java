package com.tc.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 公共Excel导出Controller
 */
public abstract class XlsExportController extends BaseController {

	//获取表格标题
	public abstract String getSheetTitle();

	//获取头部信息
	public abstract Map<String, String> getHeaderMap();

	//获取数据集
	public abstract List<?> getDataset();

	//获取文件名称
	public abstract String getFileName();
	
	/**
	 * 生成工作簿
	 * @return 返回生成的工作簿
	 */
	public HSSFWorkbook generateWorkbook(){
		String title = getSheetTitle();

		Map<String, String> headerMap = getHeaderMap();

		List<?> dataset = getDataset();

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		int headCount = 0;
		for (String value : headerMap.values()) {
			HSSFCell cell = row.createCell(headCount++);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(value);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<?> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			rowHandler(workbook, index, row);//行处理
			Object obj = it.next();
			int cellCount = 0;
			for (String headerKey : headerMap.keySet()) {
				Object cellValue="";
				HSSFCell cell = row.createCell(cellCount++);
				cell.setCellStyle(style2);
				sheet.setDefaultRowHeightInPoints(15);

				if (obj instanceof Map) {// map型的数据
					Map dataMap = ((Map) obj);
					cellValue = dataMap.get(headerKey.toUpperCase());
				} else { //对象类型的数据
					try {
						String getMethodName= "get" + headerKey.substring(0, 1).toUpperCase();
						if(headerKey.length() > 1){
							getMethodName += headerKey.substring(1);
						}
						Method getMethod = obj.getClass().getDeclaredMethod(getMethodName);

						cellValue = getMethod.invoke(obj);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				//单元格内容特殊处理
				cellValue = cellValueHandler(headerKey, cellValue);
				cellHandler(workbook,headerKey, cell);
				cell.setCellValue(String.valueOf(cellValue));
			}
		}
		
		return workbook;
	}

	/**
	 * 单元格行自定义
	 * @param workbook
	 * @param rowIndex
	 * @param row
	 */
	public void rowHandler(HSSFWorkbook workbook, int rowIndex, HSSFRow row) {
	}

	/**
	 * 单元格自定义
	 *
	 * @param workbook
	 * @param headerKey
	 * @param cell
	 */
	public void cellHandler(HSSFWorkbook workbook, String headerKey, HSSFCell cell) {
	}

	/**
	 * 单元格内容特殊处理(如果需要特殊处理就重写然后写处理逻辑)
	 * @param headerKey
	 * @param cellValue
	 * @return
	 */
	public Object cellValueHandler(String headerKey, Object cellValue){
		return null == cellValue ? "" : cellValue;
	}

	/**
	 * 导出表格
	 * @param request 请求request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity<byte []> exportExcel(HttpServletRequest request) throws IOException {
		String fileName = getFileName();
		
		//生成工作簿
		HSSFWorkbook workbook = generateWorkbook();
		
		//获取保存路径
		String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/export");

		fileName = saveExcelFile(savePath, fileName, workbook);

		if (StringUtils.isNotEmpty(fileName)) {
			String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
			String realPath = request.getSession().getServletContext().getRealPath("WEB-INF/export/");
			File file = new File(realPath, fileName);
			if (file.exists()) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", dfileName);
				return new ResponseEntity<byte[]>(
						FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
		}
		return null;
	}
	
	/**
	 * 将工作簿保存到文件中
	 * 
	 * @param savePath
	 * @param fileName
	 * @param workbook
	 * @return
	 */
	private String saveExcelFile(String savePath, String fileName, HSSFWorkbook workbook){
		
		FileOutputStream fileOutputStream = null;
		try {
			 File path = new File(savePath);
	        if(!path.exists()&&!path.isDirectory()){
	        	logger.info("目录或文件不存在！");
	        	path.mkdir();
	        }
		    
			String filePath = savePath + File.separator + fileName + ".xls";
			fileOutputStream = new FileOutputStream(filePath);
			workbook.write(fileOutputStream);
			return fileName + ".xls";
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return null;
	}
}
