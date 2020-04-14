package com.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Excel_Writer {
	
	
	public void writetoExcel(String colValue,int columnNumber, String colDescription ){
		 try {

	           FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\Data\\AustraliaEcomSale.xlsx"));

	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            XSSFCell cell = sheet.getRow(0).createCell(columnNumber);
	            
	            cell.setCellValue(colDescription);
	            cell = sheet.getRow(1).createCell(columnNumber);
	            cell.setCellValue(colValue);

	            file.close();

	            FileOutputStream outFile =new FileOutputStream(new File(System.getProperty("user.dir")+"\\Data\\AustraliaEcomSale.xlsx"));
	            workbook.write(outFile);
	       
	            outFile.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}
	
	public void WritePOOrder(String purchaseOrderNumber) {
		try {

			FileInputStream file = new FileInputStream(
					new File(System.getProperty("user.dir") + "\\Data\\UKPO_TestData.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFCell cell = null; 
			

			cell = sheet.getRow(1).getCell(14);
			cell.setCellValue(purchaseOrderNumber);

			file.close();

			FileOutputStream outFile = new FileOutputStream(
					new File(System.getProperty("user.dir") + "\\Data\\UKPO_TestData.xlsx"));
			workbook.write(outFile);

			outFile.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}

