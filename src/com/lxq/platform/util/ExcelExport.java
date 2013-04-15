package com.lxq.platform.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出excel文件
 * @author lizi
 *
 */
public class ExcelExport
{
	/**
	 * 导出excel文件
	 * @param header 标题头
	 * @param data 数据集合
	 * @param out 输出流
	 * @throws IOException
	 */
	public static void export(List<Map<String,String>> header,List<Map<String,String>> data , OutputStream out) throws IOException
	{
		HSSFWorkbook wb = new HSSFWorkbook();
			
		HSSFCellStyle header_style = wb.createCellStyle();
		header_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont header_font = wb.createFont();
		header_font.setFontHeightInPoints((short) 11);//字号
		header_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		header_style.setFont(header_font);
		
		HSSFCellStyle data_style = wb.createCellStyle();
		HSSFFont data_font = wb.createFont();  
		data_font.setFontHeightInPoints((short) 11);//字号
		data_style.setFont(data_font);
		
		HSSFSheet sheet = wb.createSheet();//创建一个工作簿
		
		HSSFRow row_header = sheet.createRow(0);//行头信息
		List<String> headerNames = new ArrayList<String>();
		for(int i = 0 ; i < header.size() ; i ++){
			Map<String,String> header_column = header.get(i);
			headerNames.add(header_column.get("name"));
			HSSFCell cell = row_header.createCell(i);
			cell.setCellValue(new HSSFRichTextString(header_column.get("header")));
			cell.setCellStyle(header_style);
		}
		
		int rowId = 0;
		for(int i = 0 ; i < data.size() ; i ++){
			HSSFRow row_data = sheet.createRow(++rowId);//行头信息
			
			Map<String,String> data_column = data.get(i);
			for(int j = 0 ; j < headerNames.size() ; j ++){
				HSSFCell cell = row_data.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(data_column.get(headerNames.get(j))));
				cell.setCellStyle(data_style);
			}
		}
		
		wb.write(out);
	}
}
