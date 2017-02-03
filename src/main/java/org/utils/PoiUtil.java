package org.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
*TODO
*@author Aaron
*@date 2017年2月3日
*/
public class PoiUtil {

	public static void writeExcel(String[][] content,OutputStream output) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		for(int i=0;i<content.length;i++){
			String[] rowContent = content[i];
			HSSFRow row = sheet.createRow(i);
			for(int j=0;j<rowContent.length;j++){
				row.createCell(j).setCellValue(rowContent[j]);
			}
		}
		wb.write(output);
		output.flush();
		wb.close();
	}
	
	public static void main(String[] args) throws IOException {
		String[][] content = new String[][]{{"name","age"},{"aa","12"},{"bb","13"}};
		FileOutputStream fos = new FileOutputStream(new File("d:/test.xls"));
		writeExcel(content, fos);
		fos.close();
	}
	
}
