package com.framework;

import java.io.File; 
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFManager {
	
	

	       private PDFParser parser;
	       private PDFTextStripper pdfStripper;
	       private PDDocument pdDoc ;
	       private COSDocument cosDoc ;

	       private String Text ;
	       private String filePath;
	       private File file;

	       public PDFManager() {

	       }
	       public String ToText() throws IOException, InterruptedException
	       {
	              Thread.sleep(10000);
	              this.pdfStripper = null;
	              this.pdDoc = null;
	              this.cosDoc = null;

//	            file = new File(filePath);
//	            FileInputStream fr = new FileInputStream(filePath);
//	            parser = new PDFParser((RandomAccessRead) fr); // update for PDFBox V 2.0
	//
//	            parser.parse()
//	                         cosDoc = parser.getDocument();
//	                         pdfStripper = new PDFTextStripper();
//	                         pdDoc = new PDDocument(cosDoc);
	              pdDoc = PDDocument.load(new File(filePath));
	              pdfStripper = new PDFTextStripper();
	                           pdDoc.getNumberOfPages();
	                           pdfStripper.setStartPage(1);
	                           pdfStripper.setEndPage(3);

	                           // reading text from page 1 to 10
	                           // if you want to get text from full pdf file use this code
	                           // pdfStripper.setEndPage(pdDoc.getNumberOfPages());

	                           Text = pdfStripper.getText(pdDoc);
	                           System.out.println(pdfStripper.getTextMatrix());

	                           return Text;
	       }
	       public void setFilePath(String filePath) {
	              this.filePath = filePath;
	       }



}
