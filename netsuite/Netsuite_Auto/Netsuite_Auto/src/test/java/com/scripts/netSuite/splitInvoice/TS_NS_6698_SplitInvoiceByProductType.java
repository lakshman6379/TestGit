package com.scripts.netSuite.splitInvoice;

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

import com.framework.BaseReport;
import com.framework.BaseTest;
import com.framework.Excel_Reader;
import com.framework.Generic;
import com.netsuite.common.NS_Billing_AdjustmentAndSpecialBilling;
import com.netsuite.common.NS_LoginPage;

public class TS_NS_6698_SplitInvoiceByProductType extends BaseReport{
	private BaseTest basetest;
	public static Excel_Reader excelReader;
	public static int i=6698;
	int HistoryRowNumber=0;
	int passCount=0, FailCount=0;
	public static String TestDataPath="";
	public static HashMap<String,String> XLTestData;
	public static WebDriver driver;
	
	NS_LoginPage oLoginPage=new NS_LoginPage();
	Generic oGenericUtils=new Generic();
	NS_Billing_AdjustmentAndSpecialBilling oSalesOrderNetsuite = new NS_Billing_AdjustmentAndSpecialBilling();
	Generic gen =new Generic();
	@BeforeTest(alwaysRun=true)
	public void getTest() throws IOException {
		basetest=new BaseTest();
		basetest.getTest(this.getClass().getSimpleName(),"Adjustment Tool");
	}
	@BeforeClass
	public void test() throws FileNotFoundException, IOException {
		TestDataPath = System.getProperty("user.dir") + "\\Data\\NetSuiteTestData_SplitInvoice.xlsx";
		System.out.println("Test Data Path: "+TestDataPath);
		excelReader=new Excel_Reader(TestDataPath);
		excelReader.cFileNameWithPath = TestDataPath;
		excelReader.cSheetName = "TestData";
		excelReader.cTcID = "TestCaseID";
		excelReader.cTcValue = "1";
		XLTestData = new HashMap<String, String>();
		XLTestData = excelReader.readExcel("NS-" + Integer.toString(i));		
	}
	
	@Test()
	public void TS_NS_6698_SplitInvoiceByProductType() throws InterruptedException{
		
		try {
			basetest.test = basetest.extent.createTest(XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString(), XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString());
			 basetest.test.info("<span style='font-weight:bold;color:blue'>'"+ XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString()+ " Execution started"+"'</span>");
		
			 driver = oLoginPage.LaunchNetSuiteApp(XLTestData, "", basetest);
		//Login Application
		oLoginPage.NetSuiteLogin(driver, XLTestData,basetest);
		//choosing role
		oSalesOrderNetsuite.SelectRoleFOrNetSuiteAsAdmin(driver, XLTestData, basetest);
		
		//selecting new sales order through list
		 oSalesOrderNetsuite.selectAdjSplBillingInBilling(driver, XLTestData, basetest);	
		 String scenario = XLTestData.get("Scenario").trim();
		 String invoiceNO = XLTestData.get("invoiceNumber").toString().trim();
		 String splitingType = "ProductType";
		 
		 oSalesOrderNetsuite.splitInvoiceByType(driver, scenario, invoiceNO, splitingType, basetest);
		}catch(Exception e) {
			System.out.println(" Exception in splitInvoiceSplitStationTest() :"+e);
		}
		oLoginPage.NetSuiteLogout(driver, basetest);
		basetest.test.info("<span style='font-weight:bold;color:blue'>'"+ XLTestData.get("TestCaseID")+":"+XLTestData.get("TestFlow").toString()+ " Execution completed"+"'</span>");
	}
	
	@AfterMethod(alwaysRun = true)
	public void ExtentReport() {
		basetest.extent.flush();
		if(driver != null)
		{driver.close();
		// driver.quit();
		}
	}
  
	@AfterClass(alwaysRun = true)
	public void LogsOut() throws InterruptedException, IOException {
		String ClassName = this.getClass().getSimpleName();
		LogScenario(ClassName, passCount, FailCount);
		if(driver != null) {
			{
				if(gen.isAlertPresents(driver))
				{
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}
				driver.close();
				driver.quit();
			}

		}
	}
}
