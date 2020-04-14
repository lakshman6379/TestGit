package com.scripts.netSuite.removeAgencyCommission;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.framework.BaseReport;
import com.framework.BaseTest;
import com.framework.Excel_Reader;
import com.framework.Generic;
import com.framework.PDFValidation;
import com.netsuite.common.NS_Billing_AdjustmentAndSpecialBilling;
import com.netsuite.common.NS_Billing_CasePageValidations;
import com.netsuite.common.NS_LoginPage;

public class TS_NS_6607_RemoveAgencyCommission extends BaseReport {

	private BaseTest basetest;
	public static Excel_Reader excelReader;
	public static int i = 6607;
	int HistoryRowNumber = 0;
	int passCount = 0, FailCount = 0;
	public static String TestDataPath = "";
	public static HashMap<String, String> XLTestData;
	public static WebDriver driver;

	// ========================>
	NS_LoginPage oLoginPage = new NS_LoginPage();
	Generic oGenericUtils = new Generic();
	NS_Billing_AdjustmentAndSpecialBilling oSalesOrderNetsuite = new NS_Billing_AdjustmentAndSpecialBilling();
	Generic gen = new Generic();
	NS_Billing_CasePageValidations caseValidations = new NS_Billing_CasePageValidations();
	String filePathToDownload = null;

	// ========================>
	@BeforeTest(alwaysRun = true)
	public void getTest() throws IOException {
		basetest = new BaseTest();
		basetest.getTest(this.getClass().getSimpleName(), "Adjustment Tool");
		/*
		 * Runtime rt = Runtime.getRuntime(); Process proc =
		 * rt.exec("taskkill /im chromedriver.exe /f /t");
		 */

	}

	// ==============>
	@BeforeClass
	public void test() throws FileNotFoundException, IOException {
		TestDataPath = System.getProperty("user.dir") + "\\Data\\NetSuiteTestData_RemoveAgencyCommission.xlsx";
		System.out.println("Test Data Path: " + TestDataPath);
		excelReader = new Excel_Reader(TestDataPath);
		excelReader.cFileNameWithPath = TestDataPath;
		excelReader.cSheetName = "TestData";
		excelReader.cTcID = "TestCaseID";
		excelReader.cTcValue = "1";
		XLTestData = new HashMap<String, String>();
		XLTestData = excelReader.readExcel("NS-" + Integer.toString(i));

		// Folder creation
		File folder = new File(System.getProperty("user.dir") + "\\RemoveAgencyCommissionPDFFile");
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (File file : folder.listFiles()) {
			String fileNameDeleted = "";
			fileNameDeleted = file.getName();
			if (file.delete()) {
				System.out.println("File : " + fileNameDeleted + " deleted sucessfully");
			}
		}
		filePathToDownload = folder.getAbsolutePath();
	}

	// =============>
	@Test
	public void removeAgencyCommission() throws InterruptedException, IOException, AWTException {

		/*
		 * PDFValidation PDFValidation = new PDFValidation(); String[] pdfData =
		 * PDFValidation.getPDFData();
		 * 
		 * System.out.println(pdfData[10]);
		 */

		basetest.test = basetest.extent.createTest(XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString(), XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString());
		basetest.test.info("<span style='font-weight:bold;color:blue'>'"+ XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString()+ " Execution started"+"'</span>");

		// Launch URL
		// driver=oLoginPage.LaunchNetSuiteApp(XLTestData.get("NetSuite_URL").toString(),XLTestData,basetest);
		 driver = oLoginPage.LaunchNetSuiteApp(XLTestData, "", basetest);
		// Login Application
		oLoginPage.NetSuiteLogin(driver, XLTestData, basetest);

		// choosing role
		//oSalesOrderNetsuite.SelectRoleFOrNetSuiteAsAdmin(driver, XLTestData, basetest);

		// selecting new sales order through list
		oSalesOrderNetsuite.selectAdjSplBillingInBilling(driver, XLTestData, basetest);

		//Add Agency commission Exclude Misc Lines
		String amountValidationDetails =oSalesOrderNetsuite.removeAgencyCommision(driver, XLTestData, basetest);
		
		//Add Agency commission Exclude Misc Lines
		caseValidations.agencyCommision_CasePageValidation(driver,basetest);
	

		 /*String grossAmountInUI, agencyCommissionInUI, netDueInUI; 
		 String details[]=amountValidationDetails.split(";"); grossAmountInUI =
		details[0].toString(); agencyCommissionInUI = details[1].toString();
		  netDueInUI = details[2].toString();
		  
		  oSalesOrderNetsuite.clickPDFDownloadLink(driver,basetest);
		  
		  PDFValidation PDFValidation = new PDFValidation(); 
		  String[] pdfData = PDFValidation.getPDFData(filePathToDownload,XLTestData); 
		  System.out.println(pdfData[9]);
		  if(pdfData[10].contains(XLTestData.get("invoiceNumber"))) { 
			  basetest.test.log(Status.PASS, "Gross Amount(<span style='font-weight:bold;color:blue'>"+grossAmountInUI+"</span>) in UI and in PDF document are equal");
			  } 
		  else{
		  basetest.test.log(Status.FAIL, "Gross Amount("+grossAmountInUI+") in UI and in PDF document are not equal");
		  } 
		  if(pdfData[9].contains(agencyCommissionInUI)) {
			  basetest.test.log(Status.PASS,"Agency Commission in UI and PDF document are equal");
			  } 
		  else 
		  {
	     basetest.test.log(Status.FAIL, "Agency Commission in UI and PDF document are not  equal"); 
	     }*/
		/*
		 * if(pdfData[9].contains(netDueInUI)){ basetest.test.log(Status.PASS,
		 * "Net Due("+netDueInUI+") in UI and in PDF document are equal"); } else {
		 * basetest.test.log(Status.FAIL,
		 * "Net Due("+netDueInUI+") in UI and in PDF document are not equal"); }
		 * 
		 */
		//logout from Netsuite
		oLoginPage.NetSuiteLogout(driver, basetest);
		basetest.test.info("<span style='font-weight:bold;color:blue'>'"+ XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString()+ " Execution completed"+"'</span>");
	}

	// =========================>
	@AfterMethod(alwaysRun = true)
	public void ExtentReport() {
		basetest.extent.flush();
		if (driver != null) {
			driver.close();
			// driver.quit();
		}
	}

	// =========================>
	@AfterClass(alwaysRun = true)
	public void LogsOut() throws InterruptedException, IOException {
		String ClassName = this.getClass().getSimpleName();
		LogScenario(ClassName, passCount, FailCount);
		if (driver != null) {
			{
				if (gen.isAlertPresents(driver)) {
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}
				driver.close();
				driver.quit();
			}

		}
	}
}
