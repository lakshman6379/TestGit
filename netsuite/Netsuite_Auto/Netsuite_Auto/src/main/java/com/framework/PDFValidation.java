package com.framework;

import java.awt.AWTException; 
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


	
	public class PDFValidation {

	       public String[] getPDFData(String filePath,HashMap<String, String> XLTestData) throws IOException, InterruptedException, AWTException {
	              Thread.sleep(4000);
	              
	              String fileName= XLTestData.get("invoiceNumber");
	        
	              System.out.println("Invoice Number : "+fileName);
	              PDFManager pdfManager = new PDFManager();
	              
	              System.out.println(filePath);

	              // Get the PDF file name
	              File dir = new File(filePath);

	              File[] files = dir.listFiles();
	              if (files == null || files.length == 0) {
	                     //     flag = false;
	              }
	              for (int i = 0; i < files.length; i++) {
	                     if(files[i].getName().contains(fileName)) {
	                           System.out.println("file name found as : "+files[i].getName());
	                           fileName=files[i].getName();
	                     }
	              }
	              pdfManager.setFilePath(filePath+"\\"+fileName);
	           

	            //  pdfManager.setFilePath("D:\\iHeartMedia\\Project_iheart\\RemoveAgencyCommissionPDFFile"+filesName);

	              String pdf =pdfManager.ToText();
	              String[] words = pdf.split(":");

	              System.out.println(words[0]);
	              System.out.println(words[1]);
	              System.out.println(words[2]);
	              System.out.println(words[3]);
	              System.out.println(words[4]);
	              System.out.println(words[5]);
	              System.out.println(words[6]);
	              System.out.println(words[7]);
	              System.out.println(words[8]);
	              System.out.println(words[9]);
	              System.out.println(words[10]);	           
	              return words;
	       }  
}
