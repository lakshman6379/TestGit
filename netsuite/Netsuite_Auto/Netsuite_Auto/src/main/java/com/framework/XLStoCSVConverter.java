package com.framework;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLStoCSVConverter {
	static int PasswordCol = 0;
	int RowNumber = 1;
	public StringBuffer convertSelectedSheetInXLXSFileToCSV(File xlsxFile, int sheetIdx) throws Exception {
		StringBuffer sb = null;
        FileInputStream fileInStream = new FileInputStream(xlsxFile);
        
        // Open the xlsx and get the requested sheet from the workbook
        XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
        XSSFSheet selSheet = workBook.getSheetAt(sheetIdx);
        sb = new StringBuffer();
        // Iterate through all the rows in the selected sheet
        Iterator<Row> rowIterator = selSheet.iterator();
        while (rowIterator.hasNext()) {
        	//if(RowNumber == 1 &&)
        		
            Row row = rowIterator.next();
            System.out.println(row.getLastCellNum());
            // Iterate through all the columns in the row and build ","
            // separated string
            Iterator<Cell> cellIterator = row.cellIterator();
            int rownum = 0;
            while (cellIterator.hasNext()) {
            	rownum++;
                Cell cell = cellIterator.next();
                if (sb.length() != 0 && rownum <= row.getLastCellNum() && rownum != 1) {
                    sb.append(",");
                }
                 
                // If you are using poi 4.0 or over, change it to
                // cell.getCellType
                if(cell.getCellType() == cell.CELL_TYPE_STRING)
                    sb.append(cell.getStringCellValue());
               
                if(cell.getCellType() == cell.CELL_TYPE_NUMERIC)
                    sb.append(cell.getNumericCellValue());
                
                if(cell.getCellType() == cell.CELL_TYPE_BOOLEAN)
                    sb.append(cell.getBooleanCellValue());
            
            }
            sb.append("\r");
        }
        
        return(sb);
    }
}