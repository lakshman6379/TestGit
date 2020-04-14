package com.framework;
import java.io.File;
import java.util.*; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Excel_Reader {
	public FileOutputStream fileOut;
	public String path;
	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public static String cFileNameWithPath;
	public static String cSheetName;
	public static String cTcID;
	public static String cTcValue;
	public  XSSFSheet sheet;
	public XSSFRow row;

	public XSSFCell cell;

	public Excel_Reader(String path) {

		this.path = path;

		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	public int GetRowCount(String sheetName)
	{
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int totalRow = sheet.getLastRowNum() + 1;
		return(totalRow);
	}
	public String[][] getDataFromSheet(String sheetName) {

		String dataSet[][] = null;

		try {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			int totalRow = sheet.getLastRowNum() + 1;

			int totalColumn = sheet.getRow(0).getLastCellNum();

			dataSet = new String[totalRow - 1][totalColumn];

			for (int i = 1; i < totalRow; i++) {

				XSSFRow row = sheet.getRow(i);

				for (int j = 0; j < totalColumn; j++) {

					XSSFCell cell = row.getCell(j);

					if (cell.getCellType() == cell.CELL_TYPE_STRING) {

						dataSet[i - 1][j] = cell.getStringCellValue();

					} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {

						String cellText = String.valueOf(cell.getNumericCellValue());

						dataSet[i - 1][j] = cellText;

					}

					else if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {

						String cellText = String.valueOf(cell.getBooleanCellValue());

						dataSet[i - 1][j] = cellText;

					}

				}

			}

			return dataSet;

		} catch (Exception e) {

			System.out.println("Exception in reading excel file" + e.getMessage());

			e.printStackTrace();

		}

		return dataSet;

	}

	
	
	
	
	
	public String getStringCellData(String sheetName, String colName, int rowNum) {

		try {

			int col_Num = 0;

			int index = workbook.getSheetIndex(sheetName);

			sheet = workbook.getSheetAt(index);

			XSSFRow row = sheet.getRow(0);

			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().equals(colName)) {

					col_Num = i;

					break;

				}

			}

			row = sheet.getRow(rowNum - 1);

			XSSFCell cell = row.getCell(col_Num);

			if (cell.getCellType() == cell.CELL_TYPE_STRING) {

				return cell.getStringCellValue();

			} else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {

				return "";

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}
	
	
	  public static HashMap<String, String> readExcel(String sTcValue) throws FileNotFoundException, IOException{
          int crValues[]=getRowColumnNumbers(cFileNameWithPath, cSheetName, cTcID, sTcValue, "");
          XSSFWorkbook myExcelBook = new XSSFWorkbook(cFileNameWithPath); 
           XSSFSheet myExcelSheet = myExcelBook.getSheet(cSheetName); 
           HashMap<String, String> hmap = new HashMap<String, String>();
          int columnCount = myExcelSheet.getRow(0).getLastCellNum();
          XSSFRow rowZero = null;
          String sCellValueName = null;
          for (int c=0;c <columnCount; c++ ){
                 try{

                       XSSFRow row = myExcelSheet.getRow(crValues[0]); 
                        rowZero = myExcelSheet.getRow(0);
                       // System.out.println("ROW:"+crValues[0]+"Column:"+c);
                       if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_STRING)
                       { 
                               sCellValueName = row.getCell(c).getStringCellValue();
                              // System.out.println("CELL_TYPE_STRING : " + sCellValueName); 
                        } 

                       if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                       { 
                               double birthdate = row.getCell(c).getNumericCellValue();
                              // System.out.println("CELL_TYPE_NUMERIC" + birthdate); 
                               sCellValueName=Double.toString(birthdate);
                       } 
                        if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
                       { 
                               boolean birthdate = row.getCell(c).getBooleanCellValue();
                              // System.out.println("CELL_TYPE_BOOLEAN" + birthdate); 
                               sCellValueName=String.valueOf(birthdate);
                       } 
                        if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_ERROR)
                       { 
                              // System.out.println("CELL_TYPE_ERROR"); 
                               sCellValueName="NoValueCellTypeERROR";
                       } 
                        if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_BLANK)
                       { 
                              // System.out.println("CELL_TYPE_BLANK"); 
                               sCellValueName="NoValueCellTypeBLANK";
                       } 
                        if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_FORMULA)
                       { 

                              sCellValueName = row.getCell(c).getRawValue();
                              //row.getCell(c).getCellFormula();
                              // System.out.println("DOB :" + sCellValueName); 
                        } 
                  }
                 catch(Exception e){

                 }
                 hmap.put(rowZero.getCell(c).getStringCellValue(),sCellValueName);
          }
          myExcelSheet = null;
          myExcelBook = null; 
           return hmap;


   }


	/** Description of class body 
	 * @throws IOException 
	 * @throws FileNotFoundException */
	 /*getRowColumnNumbers*/
	 public static int[] getRowColumnNumbers(String file,String sheetName,String sTCidColumnLabel,String TcidsColumnValue,String sUpdateColumanName) throws FileNotFoundException, IOException{
		FileInputStream ExcelFile = new FileInputStream(file);
		XSSFWorkbook myExcelBook= new XSSFWorkbook(file);
		//XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file)); 
		XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName); 
		int columnCount = myExcelSheet.getRow(0).getPhysicalNumberOfCells();
		int rowCount = 0;
		Iterator rowIter = myExcelSheet.rowIterator();
		while(rowIter.hasNext()){
			Row r = (Row)rowIter.next();
			rowCount = r.getRowNum();
		}

		boolean sColumnValueNumberBoolean=false;
		int sColumnValueNumber=0;
		int sCoulumnHeadingNumber=0;
		int sRowNumber=0;
		boolean sRowValueBoolean=false;
		boolean sColumnHeadinBoolean=false;
		// System.out.println("rowCount"+rowCount+"columnCount"+columnCount);
		String sCellValueName = null;
		Rcount: for (int r=0;r <rowCount+1; r++ ){
			for (int c=0;c <columnCount; c++ ){
				try{
					XSSFRow row = myExcelSheet.getRow(r); 
					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_STRING)
					{ 
						sCellValueName = row.getCell(c).getStringCellValue();
						//System.out.println("NAME : " + sCellValueName); 
					} 

					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
					{ 
						double birthdate = row.getCell(c).getNumericCellValue();
						//System.out.println("DOB :" + birthdate); 
						sCellValueName=Double.toString(birthdate);
					} 
					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
					{ 
						boolean birthdate = row.getCell(c).getBooleanCellValue();
						//System.out.println("DOB :" + birthdate); 
						sCellValueName=String.valueOf(birthdate);
					} 
					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_ERROR)
					{ 
						sCellValueName="NoValueCellTypeERROR";
					} 
					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_BLANK)
					{ 
						sCellValueName="NoValueCellTypeBLANK";
					} 
					if(row.getCell(c).getCellType() == HSSFCell.CELL_TYPE_FORMULA)
					{ 
						sCellValueName = row.getCell(c).getCellFormula();
						//System.out.println("DOB :" + sCellValueName); 
					} 
				}
				catch(Exception e){

				}

				if(sCellValueName.equals(sTCidColumnLabel)){
					//System.out.println("enterf1");
					sCoulumnHeadingNumber=c;
					sColumnHeadinBoolean=true;
				}


				String ssCoulumnHeadingNumber =Integer.toString(sCoulumnHeadingNumber);
				if(sColumnHeadinBoolean){
					if(sCellValueName.equals(TcidsColumnValue)){
						String ssColumnNumber=Integer.toString(c);
						if(ssCoulumnHeadingNumber.equals(ssColumnNumber)){
							sRowNumber=r;
							sRowValueBoolean=true;
						}
					}
					if(sUpdateColumanName != null && sUpdateColumanName.trim().length() != 0){
						if(sUpdateColumanName.equals(sCellValueName)) {
							//System.out.println("enterf34");
							sColumnValueNumber=c;
							sColumnValueNumberBoolean=true;
						}
					}
					if(sColumnHeadinBoolean&&sRowValueBoolean&&sColumnValueNumberBoolean){
						break Rcount;
					}
				}
			}
		}
		int rowColumnArray[]={sRowNumber,sColumnValueNumber};
		return rowColumnArray;

	 }


	 public static void writeExcel(String SheetName, HashMap<String, String> Data, int RowNum) throws FileNotFoundException, IOException{	

		 Iterator hmIterator = Data.entrySet().iterator(); 
		 int columnposition[] = new int[50];
		 String HashMapValue[] = new String[50];
		 int columiterator = 0;
		 while (hmIterator.hasNext()) { 
			 Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
			 String sUpdateColumanValue = (String)mapElement.getValue(); 
			 String sUpdateColumnName = (String) mapElement.getKey(); 
			 int ColumnPos = getColumnPosition(cFileNameWithPath, SheetName, sUpdateColumnName);
			 columnposition[columiterator] = ColumnPos;
			 HashMapValue[columiterator] = sUpdateColumanValue;
			 columiterator++;
		 }
		 FileInputStream fis = new FileInputStream(new File(cFileNameWithPath));
		 XSSFWorkbook workbook = new XSSFWorkbook(fis);

		 XSSFSheet sheet = workbook.getSheet(SheetName);
		 XSSFRow row1 = sheet.createRow(RowNum+1);
		 for(int coliterator =0; coliterator<columiterator;coliterator++)
		 { 
			 int j;
			 for( j=0;j<columiterator;j++)
			 {
				 if(columnposition[j] == coliterator)
					 break;

			 }
			 XSSFCell r1c1 = row1.createCell(coliterator);
			 r1c1.setCellValue(HashMapValue[j]); 
		 }
		 fis.close();
		 FileOutputStream fos =new FileOutputStream(new File(cFileNameWithPath));
		 workbook.write(fos);
		 fos.close();
	 }

	 
	 public static void writeExcelnew(String SheetName, HashMap<String, String> Data, int RowNum) throws FileNotFoundException, IOException{	

		 Iterator hmIterator = Data.entrySet().iterator(); 
		 int columnposition[] = new int[50];
		 String HashMapValue[] = new String[50];
		 int columiterator = 0;
		 while (hmIterator.hasNext()) { 
			 Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
			 String sUpdateColumanValue = (String)mapElement.getValue(); 
			 String sUpdateColumnName = (String) mapElement.getKey(); 
			 int ColumnPos = getColumnPosition(cFileNameWithPath, SheetName, sUpdateColumnName);
			 columnposition[columiterator] = ColumnPos;
			 HashMapValue[columiterator] = sUpdateColumanValue;
			 columiterator++;
		 }
		 FileInputStream fis = new FileInputStream(new File(cFileNameWithPath));
		 XSSFWorkbook workbook = new XSSFWorkbook(fis);

		 XSSFSheet sheet = workbook.getSheet(SheetName);
		 XSSFRow row1 = sheet.createRow(RowNum);
		 for(int coliterator =0; coliterator<columiterator;coliterator++)
		 { 
			 int j;
			 for( j=0;j<columiterator;j++)
			 {
				 if(columnposition[j] == coliterator)
					 break;

			 }
			 XSSFCell r1c1 = row1.createCell(coliterator);
			 r1c1.setCellValue(HashMapValue[j]); 
		 }
		 fis.close();
		 FileOutputStream fos =new FileOutputStream(new File(cFileNameWithPath));
		 workbook.write(fos);
		 fos.close();
	 } 
	 
	 
	 public static int getColumnPosition(String file, String sheetName, String colName) throws IOException {
		 int col_Num = 0;
		 XSSFWorkbook myExcelBook= new XSSFWorkbook(file);
		 //XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file)); 
		 XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName); 
		 XSSFRow row = myExcelSheet.getRow(0);

		 for (int i = 0; i < row.getLastCellNum(); i++) {

			 if (row.getCell(i).getStringCellValue().equals(colName)) {

				 col_Num = i;

				 break;

			 }

		 }
		 return(col_Num);
	 }

	 public static int getRowPosition(String file, String sheetName) throws IOException {
		 XSSFWorkbook myExcelBook= new XSSFWorkbook(file);
		 //XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file)); 
		 XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName); 
		 return(myExcelSheet.getLastRowNum());

	 }

	 public static HashMap<String, String> getPODeatils(String file, String KeyColumnName, String ColumnName, String sheetName) throws IOException {
		 HashMap<String, String> Data = new HashMap<String, String>();
		 XSSFWorkbook myExcelBook= new XSSFWorkbook(file); 
		 XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName); 
		 XSSFRow row = myExcelSheet.getRow(0);
		 int colFound=0, colValFound = 0;
		 String sCellValueName = null;
		 for (int colNum = 0; colNum< myExcelSheet.getRow(0).getLastCellNum();colNum++) {
			 {
				 String cellValue = row.getCell(colNum).getStringCellValue();
				 if(cellValue.contains(KeyColumnName))
					 colFound = colNum;
				 if(cellValue.contains(ColumnName))
					 colValFound = colNum;
			 }
		 }
		 for(int RowNum=1;RowNum<=myExcelSheet.getLastRowNum();RowNum++)
		 {
			 String KeyName = myExcelSheet.getRow(RowNum).getCell(colFound).getStringCellValue();
			 /*System.out.println(Data.containsKey(KeyName));
                    	if(Data.containsKey(KeyName))
                    		Data.remove(KeyName);*/
			 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_STRING)
			 { 
				 sCellValueName = myExcelSheet.getRow(RowNum).getCell(colValFound).getStringCellValue();
				// System.out.println("CELL_TYPE_STRING : " + sCellValueName); 
			 } 

			 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
			 { 
				 double birthdate = myExcelSheet.getRow(RowNum).getCell(colValFound).getNumericCellValue();
				// System.out.println("CELL_TYPE_NUMERIC" + birthdate); 
				 sCellValueName=Double.toString(birthdate);
			 } 
			 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
			 { 
				 boolean birthdate = myExcelSheet.getRow(RowNum).getCell(colValFound).getBooleanCellValue();
				// System.out.println("CELL_TYPE_BOOLEAN" + birthdate); 
				 sCellValueName=String.valueOf(birthdate);
			 } 
			 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_ERROR)
			 { 
				// System.out.println("CELL_TYPE_ERROR"); 
				 sCellValueName="NoValueCellTypeERROR";
			 } 
			 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_BLANK)
			 { 
				// System.out.println("CELL_TYPE_BLANK"); 
				 sCellValueName="NoValueCellTypeBLANK";
			 } 
			 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_FORMULA)
			 { 

				 sCellValueName = myExcelSheet.getRow(RowNum).getCell(colValFound).getRawValue();
				 //row.getCell(c).getCellFormula();
				// System.out.println("DOB :" + sCellValueName); 
			 } 
			 Data.put(KeyName, sCellValueName);
		 }
		 return(Data);
	 }
	
	 
	 
	 
	 public void writeToHistoryFile(String filename, String TestsheetName, String TestCaseID, String TestCaseValue, int HistoryRownumber, HashMap<String, String> HistoryData) throws FileNotFoundException, IOException
	 {
		 cFileNameWithPath = filename;
		 cSheetName = TestsheetName;
		 cTcID = TestCaseID;
		 cTcValue = TestCaseValue;
		 writeExcel(TestsheetName, HistoryData, HistoryRownumber);
	 }

	 public void writeToHistoryFilenew(String filename, String TestsheetName, String TestCaseID, String TestCaseValue, int HistoryRownumber, HashMap<String, String> HistoryData) throws FileNotFoundException, IOException
	 {
		 cFileNameWithPath = filename;
		 cSheetName = TestsheetName;
		 cTcID = TestCaseID;
		 cTcValue = TestCaseValue;
		 writeExcelnew(TestsheetName, HistoryData, HistoryRownumber);
	 }
 
	 
public static HashMap<String, String> getItemDetails(String file, String KeyColumnName, String ColumnName, String sheetName) throws IOException {
	 HashMap<String, String> Data = new HashMap<String, String>();
	 XSSFWorkbook myExcelBook= new XSSFWorkbook(file); 
	 XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName); 
	 XSSFRow row = myExcelSheet.getRow(0);
	 int colFound=0, colValFound = 0;
	 String sCellValueName = null;
	 for (int colNum = 0; colNum< myExcelSheet.getRow(0).getLastCellNum();colNum++) {
		 {
			 String cellValue = row.getCell(colNum).getStringCellValue();
			 if(cellValue.contains(KeyColumnName))
				 colFound = colNum;
			 if(cellValue.contains(ColumnName))
				 colValFound = colNum;
		 }
	 }
	 for(int RowNum=1;RowNum<=myExcelSheet.getLastRowNum();RowNum++)
	 {
		 String KeyName = myExcelSheet.getRow(RowNum).getCell(colFound).getStringCellValue();
		 /*System.out.println(Data.containsKey(KeyName));
               	if(Data.containsKey(KeyName))
               		Data.remove(KeyName);*/
		 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_STRING)
		 { 
			 sCellValueName = myExcelSheet.getRow(RowNum).getCell(colValFound).getStringCellValue();
			// System.out.println("CELL_TYPE_STRING : " + sCellValueName); 
		 } 

		 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
		 { 
			 double birthdate = myExcelSheet.getRow(RowNum).getCell(colValFound).getNumericCellValue();
			// System.out.println("CELL_TYPE_NUMERIC" + birthdate); 
			 sCellValueName=Double.toString(birthdate);
		 } 
		 if(myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
		 { 
			 boolean birthdate = myExcelSheet.getRow(RowNum).getCell(colValFound).getBooleanCellValue();
			// System.out.println("CELL_TYPE_BOOLEAN" + birthdate); 
			 sCellValueName=String.valueOf(birthdate);
		 } 
		 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_ERROR)
		 { 
			// System.out.println("CELL_TYPE_ERROR"); 
			 sCellValueName="NoValueCellTypeERROR";
		 } 
		 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_BLANK)
		 { 
			// System.out.println("CELL_TYPE_BLANK"); 
			 sCellValueName="NoValueCellTypeBLANK";
		 } 
		 if( myExcelSheet.getRow(RowNum).getCell(colValFound).getCellType() == HSSFCell.CELL_TYPE_FORMULA)
		 { 

			 sCellValueName = myExcelSheet.getRow(RowNum).getCell(colValFound).getRawValue();
			 //row.getCell(c).getCellFormula();
			// System.out.println("DOB :" + sCellValueName); 
		 } 
		 Data.put(KeyName, sCellValueName);
	 }
	 return(Data);
}

}