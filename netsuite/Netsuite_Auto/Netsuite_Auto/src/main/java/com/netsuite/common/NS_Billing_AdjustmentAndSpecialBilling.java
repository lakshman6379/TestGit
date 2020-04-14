package com.netsuite.common;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;
import com.framework.ScreenShot;

import junit.framework.Assert;

public class NS_Billing_AdjustmentAndSpecialBilling  {


	String transaction 				= 	"//li[@data-title='Transactions']";
	String adminElement 			=	"spn_cRR_d1";
	String elementSB2 				= 	"//div[@id='ns-dashboard-heading-panel']/h1";

	//Searching salesorder And Admin Locators

	String SB2Admin 				= 	"//span[contains(text(),'SB1 Dev1 - iHeartMedia  -  Administrator')]";
	String SB5Admin                 =   "//span[contains(text(),'SB5 20.1 Upgrade - iHeartmedia  -  Administrator')]";


	// Billing and Remove Agency Commission flow
	String removeAgencyCommision   =   "//div[text()='Remove Agency Commission']";
	String billing						=	"(//li[@data-title='Billing'])[1]";
	String adjustmentSpecialBilling 	= 	"//li[@data-title='Adjustment / Special Billing']";
	String adjustmentSpecialBillingPage = 	"//h4[text()='Adjustment']";
	String adjustment 					= 	"//h4[text()='Adjustment']";
	String selectAdjustmentType 		= 	"//input[@name='inpt_adjtype_display']";
	String adjustmentPage				=	"//h1[contains(text(),'Adjustments')]";
	String secondarynext                =  "//input[@id='secondarynext']";
	String addOrRemoveAgencyCommision 	= 	"(//input[@title='Add or Remove Agency Commission'])[1]";
	String adjustmentReasonGeneral 		= 	"(//div[@class='uir-select-input-container'])[10]/input";
	String adjustmentReasonGeneralField 		= "(//div[@class='uir-select-input-container'])[9]/input";
	String agencyCommision 				= 	"//div[@class='dropdownDiv']/div[text()='Agency Commission']";
	String next    						= 	"//input[@name='next']";
	String agencyCommisNextBtn 			= 	"//input[@name='secondarynext']";
	String invoiceNumber 				= 	"//input[@id='invnum_text']";
	String invoiceNumberText 			=	"//input[@name='invnum_display']";
	String searchInvoice 				=	"//td[text()='Search Invoice']";
	String agencyCommisionOptions 		= 	"//input[@name='inpt_custpage_aggcomm_select']";
	String Comments  					= 	"//textarea[@id='custpage_memo']";
	String finish  						=	"//input[@value='Finish']";
	String caseNumber 					= 	"//table[@id='tbl__innerscroll']//a";
	String goToButton 					= 	"//button[contains(text(),'Go To')]";
	String invoiceCMS 					= 	"//span[@class='inputreadonly']/a[contains(text(),'Invoice')]";
	String adjustAmount = "//input[@id='custpage_creditamount_formattedValue']";
	String lineAdjustTable = "(//div[contains(text(),'Line Adjustment')]/following::tbody[2])/tr[contains(@id,'invoicelines_row')]";
	String downloadpdflink   = "//input[@id='custpage_showpdf']";
	String selectAdjArrow = "//img[@id='inpt_adjtype_display1_arrow']";

	//String adjReasonGeneralDowArrow ="(//input[starts-with(@name,'inpt_adjcode')])[10]";
	String adjReasonGeneralDowArrow = "(//h1[text()='Adjustments']/following::a[text()='Adjustment Reason General'][11]/following::input)[1]";
	String orderEntryError = "//div[text()='Order Entry Error']";

	//Split invoice
	String splitByStation = "//input[@id='custpage_split_by_station_fs_inp']/..";
	String splitBySpotAndMisc 	="//span[@id='custpage_split_by_line_type_fs']/..";
	String splitByProductType = "//input[@id='custpage_split_by_prd_type_fs_inp']/..";
	String splitByMarketType = "//span[@id='custpage_split_by_mkt_type_fs']/..";
	String splitByISCI = "//input[@id='custpage_split_by_isci_fs_inp']/..";

	String orderNumber = "(//span[@id='custevent_ihm_case_order_no_fs_lbl_uir_label']/following::span)[1]";
	String memoOrText = "(//span[@id='custevent_adj_req_text_fs_lbl_uir_label']/following::span)[1]";
	String invoice_Remaining_Amount = "//span[@id='custevent_ihm_invremainingamount_fs_lbl']/following::span";
	String AddAgencyCommission     = "//div[text()='Add Agency Commission']";

	//Instance creation
	Generic oGenericUtils=new Generic();
	NS_Billing_CasePageValidations caseValidations = new NS_Billing_CasePageValidations();
	NS_Billing_InvoicePageValidations invoiceValidations = new NS_Billing_InvoicePageValidations();


	//Issue Credit to client
	String preference = "//input[@name='inpt_newinvoicerequired']";
	String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";
	String fullCreditcheck = "//span[@id='custpage_fullcredit_fs']";
	String invoiceAmount = "//input[@id='custpage_creditamount_formattedValue']";
	String ok_btn = "//input[@id='invoicelines_addedit']";
	String linecreditamount = "//input[@id='linecreditamount_formattedValue']";
	String autoApproval = "//span[@id='custevent_case_approval_fs_lbl_uir_label']/following::span[1]/span";
	String adjustmentAmount = "//span[@id='custevent_ihm_adjamount_fs_lbl']/following::span[1]";
	String invoiceTotalamount = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[1]";
	String approve_btn  = "(//input[@type='button' and @value='Approve'])[1]";
	//Locators for Search case And Changing Roles

	String searchboxCase                   = "//input[@id='_searchstring']";
	String showItems                       = "//a[@id='showMoreItems']";
	String   caseView                          = "//td[text()='Case']/../td/a[2]";
	String SB5iHMRefundsSpecialist       =  "//span[contains(text(),'SB5 20.1 Upgrade - iHeartmedia  -  iHM Refunds and Adjustment Specialist-SSO')]";
	String SB5iHMRefundsmanager       =     "//span[contains(text(),'SB5 20.1 Upgrade - iHeartmedia  -  iHM Refunds and Adjustment Manager-SSO')]";
	String SB5BillingManager          =     "//span[contains(text(),'SB5 20.1 Upgrade - iHeartmedia  -  iHM Billing Manager-SSO')]";
	String SB5FinanceManager				= 	"//span[contains(text(),'SB5 20.1 Upgrade - iHeartmedia  -  iHM Market Finance Manager-SSO')]";

	String SB1FinanceManager				= 	"//span[contains(text(),'SB1 Dev1 - iHeartMedia  -  iHM Market Finance Manager-SSO')]";
	String SB1iHMRefundsSpecialist       =  "//span[contains(text(),'SB1 Dev1 - iHeartMedia  -  iHM Refunds and Adjustment Specialist-SSO')]";
	String SB1iHMRefundsmanager       =     "//span[contains(text(),'SB1 Dev1 - iHeartMedia  -  iHM Refunds and Adjustment Manager-SSO')]";
	String SB1BillingManager          =     "//span[contains(text(),'SB1 Dev1 - iHeartmedia  -  iHM Billing Manager-SSO')]";
	//Locators for Credit_Revise_Invoice_Under_10k
	String ratechange        = "//a[text()='Rate Change']";
	String revisedAmount       = "(//input[@id='rate_formattedValue'])[1]";
	//Locators for Adjustment Request
	String adjustmentRequest = "//li[@data-title='Adjustment Request']";
	String fullcredit_checkbox ="//input[@id='invoicelines_linefullcredit_fs_inp']/following::img[1]";
	String processStatus = "(//span[@id='custevent_process_status_fs_lbl_uir_label']/following::span)[1]";
	String CreditAmount  = "//input[@id='custpage_creditamount']";
	String invoice_Total  = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[1]";
	String invoice_Adjsutment = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[4]";
	String invoice_remaining = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[7]";
	String Lists = "//li[@data-title='Lists']";
	String Search = "(//li[@data-title='Search'])[4]";
	String SavedSearches = "(//li[@data-title='Saved Searches'])[1]";
	String New  = "//li[@data-title='New']";
	String search_box = "//input[@id='_searchstring']";
	String click_Invoice_View = "//a[@class='uir-item-view']/span";
	String credit_Memo = "//input[@type ='button' and @id='credit']";
	String unapplied_amount = "//input[@id='unapplied']";
	String save = "//input[@type ='submit' and  @id='btn_secondarymultibutton_submitter']";
	String credit_Memo_text = "//div[@class='uir-page-title-firstline']/h1";
	String actions = "//span[contains(@id,'spn_ACTIONMENU_d1')]";
	String rebill_makecopy = "//span[text()='Rebill - Make Copy']";
	String save_invoice = "//input[@id='btn_multibutton_submitter']";
	String invoice_txt = "//div[@class='uir-page-title-firstline']/h1";
	String apply = "//a[@id='applytxt']";
	String Apply_text_table = "//table[@id='apply_splits']/tbody/tr";
	String apply_table_header = "//table[@id='apply_splits']/tbody/tr[1]/td";
	/*
		 @Descriptions 		---	Choosing Role As Admin
		 @param driver 		---	WebDriver parameter
		 @param XLTestData 	---	Test Data Parameter
		 @param basetest 	---	Report Parameter
	 */
	public String SelectRoleFOrNetSuiteAsAdmin(WebDriver driver, HashMap<String, String> XLTestData, BaseTest basetest)
			throws InterruptedException {
		String homepageurl = null;
		try {
			FileInputStream fis = new FileInputStream( System.getProperty("user.dir") + "\\resources\\object.properties");
			Properties p = new Properties();
			p.load(fis);

			String adminRole = "(//span[text()='"+p.getProperty("environment")+" - iHeartmedia  -  Administrator'])[1]";
			System.out.println(adminRole);
			//Move to Admin Role
			oGenericUtils.navigateMouseToElement(driver, By.id(adminElement),"Admin Button in NetSuite Home page",basetest);

			//Administartor 
			oGenericUtils.clickButton(driver, By.xpath(adminRole),"Administrator", basetest);

			//SB5 20.1 Upgrade - iHeartmedia  -  Administrator

			//Checking for Home Page	
			if(driver.findElements(By.xpath(elementSB2)).size()>0) {

				homepageurl = driver.getCurrentUrl();
				basetest.test.log(Status.PASS,"User Role is  selected as "+p.getProperty("roleAdmin").toString());
			}
			else 	
			{
				basetest.test.log(Status.FAIL,"User Role is Not selected as "+p.getProperty("roleAdmin").toString()+"/May be NewPopup Occurs In Netsuite HomePage");
			}

			oGenericUtils.waiForPageLoad(3);
		} catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
		}
		return homepageurl;
	}

	public void select_Search(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) throws Exception
	{

		//webdriver waiting
		oGenericUtils.waiForPageLoad(5);

		//Move to Lists
		oGenericUtils.navigateMouseToElement(driver, By.xpath(Lists),"Lists",basetest);

		oGenericUtils.waiForPageLoad(3);

		//Move to Search
		oGenericUtils.navigateMouseToElement(driver, By.xpath(Search),"Search",basetest);
		oGenericUtils.waiForPageLoad(2);

		//Move to SavedSearches
		oGenericUtils.navigateMouseToElement(driver, By.xpath(SavedSearches),"Search",basetest);
		oGenericUtils.waiForPageLoad(2);

		//click on New
		oGenericUtils.clickButton(driver, By.xpath(New),"New",basetest);
		oGenericUtils.waiForPageLoad(6);



	}

	public void Search_criteria(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) throws Exception
	{
		String transactions = "//a[text()='Transaction']";
		WebElement element = driver.findElement(By.xpath(transactions));
		JavascriptExecutor js =((JavascriptExecutor)driver);
		js.executeScript("arguments[0].scrollIntoView(true)", element);
		oGenericUtils.waiForPageLoad(6);
		//click on New
		oGenericUtils.clickButton(driver, By.xpath(transactions),"Transactions",basetest);

		String search_title = "//input[@id='searchtitle']";



	}



	/*##############################################################
	 @Descriptions 		---	Select Adjustment and Special Billing in Billing via Admin role
	 @param driver 		---	WebDriver parameter
	 @param XLTestData 	---	Test Data Parameter
	 @param basetest 	---	Report Parameter
	 ##############################################################*/
	public void selectAdjSplBillingInBilling(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) {

		try {	

			//webdriver waiting
			oGenericUtils.waiForPageLoad(5);

			//Move to Transactions
			oGenericUtils.navigateMouseToElement(driver, By.xpath(transaction),"Transactions",basetest);

			oGenericUtils.waiForPageLoad(3);
			//Move to Billing
			oGenericUtils.navigateMouseToElement(driver, By.xpath(billing),"Billing",basetest);

			oGenericUtils.waiForPageLoad(5);

			//Move to Adjustment / Special Billing
			oGenericUtils.navigateMouseToElement(driver, By.xpath(adjustmentSpecialBilling),"Adjustment / Special Billing",basetest);

			oGenericUtils.waiForPageLoad(1);
			//Adjustment / Special Billing
			oGenericUtils.clickButton(driver, By.xpath(adjustmentSpecialBilling),"Adjustment / Special Billing",basetest);

			oGenericUtils.waiForPageLoad(5);
			//Page validation
			/*if(driver.findElements(By.xpath(adjustment)).size()>0)
			{	
				basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'>'"+"Adjustments Page Loded Succesfully"+"'</span>");
			}
			else
			{
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+"Adjustments Page Not Loded Succesfully"+"'</span>");
			}*/

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
		}	
	}	

	public void supressRate(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait untaile page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(5);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),"Type of Invoice Adjustment Slection",basetest);
		oGenericUtils.waiForPageLoad(3);	

		//	oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(3);

		//Agencyselection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneral),"Adjustment Reason General",basetest);
				String typeofagency = "//div[contains(text(),'"+XLTestData.get("Agencyselection")+"')]";
				oGenericUtils.clickButton(driver, By.xpath(typeofagency),"Adjustment Reason General",basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(10);
		String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(18);

		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(10);

		String suppressRate = "//input[@id='custpage_suppress_spotrate_fs_inp']/following::img[1]";

		String suppressMiscRate = "//input[@id='custpage_suppress_miscrate_fs_inp']/following::img[1]";

		//Suppress Spot Rates
		oGenericUtils.clickButton(driver, By.xpath(suppressRate),"suppressrate",basetest);

		//click onSuppress Misc Rates

		oGenericUtils.clickButton(driver, By.xpath(suppressMiscRate),"suppressMiscRate",basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments

		oGenericUtils.setText(driver, Comments, "SupressRate");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);

		oGenericUtils.waiForPageLoad(10);

		//Case number

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(6);




	}


	/**
	 * @Descriptions 		---	Select Adjustment and Special Billing in Billing via Admin role
	 * @param driver 		---	WebDriver parameter
	 * @param XLTestData 	---	Test Data Parameter
	 * @param basetest 	---	Report Parameter
	 */


	public String clickAdjustments(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) 
	{
		String invoiceNumberTextValue="";
		float grossAmount =0;
		float netDue =0;
		String adjmentAmtValue =  "";
		String grossValueFormated="";
		String netValueFormated="";

		try {
			float totalRevsiedAmt;
			//Adjustment
			oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

			//wait untaile page load
			oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

			oGenericUtils.waiForPageLoad(5);
			//select Adjustment Type
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

			//Add or remove agency commision
			oGenericUtils.waiForPageLoad(5);
			String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),"Type of Invoice Adjustment Slection",basetest);

			//tabing 
			oGenericUtils.waiForPageLoad(6);
			//clicking on OK Alert
			oGenericUtils.isAlertPresent(driver);
			oGenericUtils.waiForPageLoad(6);
			//Agencyselection
			oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
			String typeofagency = "//div[contains(text(),'"+XLTestData.get("Agencyselection")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofagency),"Adjustment Reason General",basetest);

			//next
			oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

			oGenericUtils.waiForPageLoad(6);
			String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";

			if(driver.findElements(By.xpath(invoicePage)).size()>0){
				basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
			}else {
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page Not verifeid"+"'</span>");
			}


			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);



			//Search Invoice
			oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

			oGenericUtils.waiForPageLoad(30);

			WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

			invoiceNumberTextValue = invoiceElement.getAttribute("previousvalue");

			if(invoiceNumberTextValue.contains("#"))

				Assert.assertTrue("Invoice Verified ", true);
			else
				Assert.assertFalse("InvoiceNotVerified", false);


			//next
			oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);


			oGenericUtils.waiForPageLoad(20);
			if(driver.findElements(By.xpath(agencyCommisionOptions)).size()>0){
				basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
			}else {
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
			}
			List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			int size =lineAdjustTableRowCount.size();
			ArrayList<Float> beforeRevisedNetAmt =  new ArrayList<Float>();
			ArrayList<Float> aftereRevisedNetAmt =new ArrayList<Float>();
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED NET AMOUNT")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						beforeRevisedNetAmt.add(Float.parseFloat(text2));
						break;
					}
				}

			}

			//Adjustment Reason General   agencyCommision
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisionOptions),"Agency Commision",basetest);

			//Remove agency commision
			oGenericUtils.clickButton(driver, By.xpath(removeAgencyCommision),"Remove Agency Commision",basetest);

			oGenericUtils.waiForPageLoad(18);
			lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			size =lineAdjustTableRowCount.size();
			String revisedcommisionRate="";
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED AG COMM AMOUNT")){
						revisedcommisionRate = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						break;
					}
				}
				if(revisedcommisionRate.contains("0.0")){
					basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'>Revised commission rate is zero"+"'</span>");

				}else{
					basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>Revised commission rate not changed"+"'</span>");

				}	
			}


			WebElement adjAmtElement = driver.findElement(By.xpath(adjustAmount));
			adjmentAmtValue = adjAmtElement.getAttribute("value").trim();
			if(adjmentAmtValue.contains("-")){
				adjmentAmtValue = adjmentAmtValue.replace("-", "");
			}else if(adjmentAmtValue.contains("+")){
				adjmentAmtValue = adjmentAmtValue.replace("+", "");
			}
			adjmentAmtValue = adjmentAmtValue.replace(",", "");
			float adjmentAmtValueInDecimal = Float.parseFloat(adjmentAmtValue);

			ArrayList<Float> netCreditAmtDecimal = new ArrayList<Float>();
			float totalNetCreditAmount=0;
			for(int i=1; i<=size ; i++){
				String netCreditAmtText ="";
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("NET CREDIT AMOUNT")){
						netCreditAmtText = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"); 
						break;
					}
				}				
				if(netCreditAmtText.contains("-")){
					netCreditAmtText = netCreditAmtText.replace("-", "");
				}else if(netCreditAmtText.contains("+")){
					netCreditAmtText = netCreditAmtText.replace("+", "");
				}
				netCreditAmtText = netCreditAmtText.replace(",", "");
				float netCreditAmount=Float.parseFloat(netCreditAmtText);
				netCreditAmtDecimal.add(netCreditAmount);
				totalNetCreditAmount=totalNetCreditAmount+netCreditAmount;
			}

			if(adjmentAmtValueInDecimal==totalNetCreditAmount){
				basetest.test.log(Status.PASS, "Adjustment Amount("+adjmentAmtValueInDecimal+")= Total Net Credit Amount("+totalNetCreditAmount+") are equal");
			}else{
				basetest.test.log(Status.FAIL, "Adjustment Amount("+adjmentAmtValueInDecimal+")= Total Net Credit Amount("+totalNetCreditAmount+") are equal not equal");
			}

			lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			size =lineAdjustTableRowCount.size();


			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED NET AMOUNT")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");
						String text2 = text.replace(",", "");
						aftereRevisedNetAmt.add(Float.parseFloat(text2));
						break;
					}
				}
				totalRevsiedAmt	=netCreditAmtDecimal.get(i-1)+beforeRevisedNetAmt.get(i-1);
				if(aftereRevisedNetAmt.get((i-1))==(totalRevsiedAmt)){
					basetest.test.log(Status.PASS, "After Revised Net Amount : "+aftereRevisedNetAmt.get((i-1))+" Before Revised Net Amount("+beforeRevisedNetAmt.get(i-1)+")+ Net Credit Amount("+netCreditAmtDecimal.get(i-1)+") are equal");
				}else{
					basetest.test.log(Status.FAIL, "After Revised Net Amount : "+aftereRevisedNetAmt.get((i-1))+" Before Revised Net Amount("+beforeRevisedNetAmt.get(i-1)+")+ Net Credit Amount("+netCreditAmtDecimal.get(i-1)+") are not equal");
				}

			}


			//next
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisNextBtn),"next",basetest);


			oGenericUtils.waiForPageLoad(6);
			//Enter Comments

			oGenericUtils.setText(driver, Comments, "QA");

			//finish
			oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);


			//Casenumber

			String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number <span style='font-weight:bold;color:blue':"+caseNumberText+"</span>");
			}
			oGenericUtils.waiForPageLoad(3);
			oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);



			oGenericUtils.waiForPageLoad(10);

			caseValidations.getStatus(driver, basetest);
			caseValidations.getprocessStatusText(driver, basetest);

			double  invTotAmt = caseValidations.getInvoiceTotalAmt(driver, basetest);
			double adjustmentAmt = caseValidations.getAdjustmentAmtt(driver, basetest);
			double invoiceRemAmt = caseValidations.getInvoiceRemaningAmtt(driver, basetest);

			double invTotalAndAdjustAmt =invTotAmt-adjustmentAmt;

			if(invTotalAndAdjustAmt==invoiceRemAmt){
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}else{
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}


			//page scroll
			/*JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,600)");
			 */
			oGenericUtils.clickButton(driver, By.xpath(invoiceCMS), "invoice CMS", basetest);
			oGenericUtils.waiForPageLoad(8);


			for(float tempGrossAmt: aftereRevisedNetAmt){
				grossAmount = grossAmount+tempGrossAmt;
			}

			for(float tempNetDue :beforeRevisedNetAmt){
				netDue = netDue+tempNetDue;
			}

			grossValueFormated = String.valueOf(grossAmount);
			grossValueFormated = "$"+grossValueFormated.substring(0,1)+","+grossValueFormated.substring(1,grossValueFormated.length());

			netValueFormated = String.valueOf(netDue);
			netValueFormated = "$"+netValueFormated.substring(0,1)+","+netValueFormated.substring(1,netValueFormated.length());


			//Return [1]Gross Amount[2]Net Due[3]invoicenumber

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
		}
		return grossValueFormated+";"+adjmentAmtValue+";"+netValueFormated;	
	}
	public void enableAutomaticPDFDownload(WebDriver driver) throws AWTException, InterruptedException{

		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabList = new ArrayList<String>(driver.getWindowHandles());
		int numberOfWindows = tabList.size();
		System.out.println(numberOfWindows);
		driver.switchTo().window(tabList.get(numberOfWindows-1));
		driver.get("chrome://settings/content/pdfDocuments");
		WebElement settingsUIRoot = driver.findElement(By.xpath("//settings-ui"));
		WebElement shadowRoot1 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsUIRoot);
		WebElement settingsMainRoot = shadowRoot1.findElement(By.cssSelector("settings-main"));
		WebElement shadowRoot2 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsMainRoot);
		WebElement settingsBasicPageRoot = shadowRoot2.findElement(By.cssSelector("settings-basic-page"));
		WebElement shadowRoot3 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsBasicPageRoot);
		WebElement settingsPrivacyPageRoot = shadowRoot3.findElement(By.cssSelector("settings-privacy-page"));
		WebElement shadowRoot4 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsPrivacyPageRoot);
		WebElement settingsPDFDocumentsRoot = shadowRoot4.findElement(By.cssSelector("settings-pdf-documents"));
		WebElement shadowRoot5 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsPDFDocumentsRoot);
		WebElement settingsToggleButtonRoot = shadowRoot5.findElement(By.cssSelector("settings-toggle-button"));
		WebElement shadowRoot6 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",settingsToggleButtonRoot);    
		WebElement crToggleRoot = shadowRoot6.findElement(By.cssSelector("cr-toggle"));
		WebElement shadowRoot7 = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot",crToggleRoot);
		WebElement enableDownloadButton = shadowRoot7.findElement(By.cssSelector("#knob"));
		boolean button= enableDownloadButton.isSelected();
		if(button==false){
			enableDownloadButton.click();
		}
		//driver.findElement(By.id("knob")).click();

		//keyboard operation is working fine for selecting automatic download option
		/*Robot robot = new Robot();  // Robot class throws AWT Exception    
        Thread.sleep(2000); 
        robot.keyPress(KeyEvent.VK_TAB);  
        Thread.sleep(2000);  
        robot.keyPress(KeyEvent.VK_TAB);  
        Thread.sleep(2000);  
        robot.keyPress(KeyEvent.VK_ENTER);*/
		Thread.sleep(18000);

		driver.switchTo().window(tabList.get(numberOfWindows-2));
	}


	public void clickPDFDownloadLink(WebDriver driver,BaseTest basetest) throws InterruptedException, AWTException{

		oGenericUtils.waiForPageLoad(15);
		enableAutomaticPDFDownload(driver);

		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(downloadpdflink),"Print",basetest);

		oGenericUtils.waiForPageLoad(30);
	}


	public void ChangeinvoiceAddress(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) throws InterruptedException {

		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait untaile page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(5);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath("//img[@id='inpt_adjtype_display1_arrow']"),"Select Adjustment Type",basetest);

		//Adrress/Est/NoteChange
		oGenericUtils.waiForPageLoad(5);
		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),"Type of Invoice Adjustment Slection",basetest);

		//tabing 
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		//Agencyselection
		//oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("Agencyselection")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),"Adjustment Reason General",basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(10);
		String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}


		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(18);

		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);


		oGenericUtils.waiForPageLoad(9);

		/*if(driver.findElements(By.xpath(agencyCommisionOptions)).size()>0){
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
		}*/


		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath("//img[@id='inpt_custpage_billto1_arrow']"),"Select Adjustment Type",basetest);

		String typeofBilltoSelect = "//div[contains(text(),'"+XLTestData.get("Bill TO Select")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofBilltoSelect),"Type of Invoice Adjustment Slection",basetest);

		String revisedESTIOCPE ="//textarea[@id='custpage_cpe']";
		oGenericUtils.SetVal(driver, By.xpath(revisedESTIOCPE), XLTestData.get("RevisedEST/IO/CPE").toString(),"RevisedEST/CPE",basetest);

		String newNote1 = "//textarea[@id='custpage_note1']";
		oGenericUtils.SetVal(driver, By.xpath(newNote1), XLTestData.get("NewNote1").toString(),"NewNote1",basetest);

		String newNote2 = "//textarea[@id='custpage_note2']";
		oGenericUtils.SetVal(driver, By.xpath(newNote2), XLTestData.get("NewNote2").toString(),"NewNote2",basetest);		
	}
	/**
	 * 
	 * @param driver
	 * @param basetest
	 */
	public void clickOnAdjustment(WebDriver driver,BaseTest basetest) {
		try {
			oGenericUtils.clickButton(driver, By.xpath(adjustment), "Adjustment", basetest);
		} catch (Exception e) {
			System.out.println(e);
		}
	}



	public void verifyInvalidInvoiceMessage(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest){
		try{
			oGenericUtils.waiForPageLoad(3);
			clickOnAdjustment(driver, basetest);
			oGenericUtils.waiForPageLoad(11);
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);		
			String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
			oGenericUtils.waiForPageLoad(5);
			//click on select Adjustment Type
			//oGenericUtils.clickButton(driver, By.xpath(adjReasonGeneralDowArrow),"Click Adjustment Reason General",basetest);
			oGenericUtils.waiForPageLoad(2);
			oGenericUtils.clickButton(driver, By.xpath(orderEntryError),"orderEntryError",basetest);
			oGenericUtils.clickButton(driver, By.xpath(next)," next ",basetest);
			oGenericUtils.waiForPageLoad(6);
			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);
			//click on Search Invoice
			oGenericUtils.clickButton(driver, By.xpath(searchInvoice)," Tab Out",basetest);						
			oGenericUtils.waiForPageLoad(20);
			//clicking on OK Alert
			String text =oGenericUtils.getAlertText(driver, "Click OK on Alert",basetest);	
			
			basetest.test.log(Status.INFO, text);
			
			
			

		}catch(Exception e){
			System.out.println(e);
		}
	}







	public void splitInvoicePartiallyPaid(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest){
		try{
			oGenericUtils.waiForPageLoad(3);
			clickOnAdjustment(driver, basetest);
			oGenericUtils.waiForPageLoad(11);
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);		
			String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),"Type of Invoice Adjustment Slection",basetest);
			oGenericUtils.waiForPageLoad(5);

			//select Adjustment Type
			//oGenericUtils.clickButton(driver, By.xpath(adjReasonGeneralDowArrow),"Select Adjustment Type",basetest);
			oGenericUtils.waiForPageLoad(2);
			oGenericUtils.clickButton(driver, By.xpath(orderEntryError),"orderEntryError",basetest);
			oGenericUtils.clickButton(driver, By.xpath(next),"next button",basetest);
			oGenericUtils.waiForPageLoad(6);
			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);
			//Search Invoice
			oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab out",basetest);						
			oGenericUtils.waiForPageLoad(20500);

			//clicking on OK Alert
			oGenericUtils.getAlertText(driver, "You are not allowed to request adjustment for this Invoice. Please use the",basetest);	


		}catch(Exception e){
			System.out.println(e);
		}
	}



	/**
	 * 
	 * @param driver - WebDriver obj
	 * @param scenario - split Invoice
	 * @param invoiceNO - pass invoice
	 * @param splitingType - type of spliting
	 * @param basetest - report obj
	 */
	public void splitInvoiceByType(WebDriver driver,String scenario,String invoiceNO,String splitingType,BaseTest basetest) {
		String caseNumberText="";
		String selectSplitTypeCheckBox="";
		if (splitingType.contains("ISCI")) {
			selectSplitTypeCheckBox = splitByISCI;

		} else if (splitingType.contains("MarketType")) {
			selectSplitTypeCheckBox = splitByMarketType;			

		} else if (splitingType.contains("ProductType")) {
			selectSplitTypeCheckBox = splitByProductType;			

		} else if (splitingType.contains("SplitStation")) {
			selectSplitTypeCheckBox = splitByStation;			

		} else if (splitingType.contains("SpotAndMisc")) {
			selectSplitTypeCheckBox = splitBySpotAndMisc;

		}

		try {
			oGenericUtils.waiForPageLoad(3);
			clickOnAdjustment(driver, basetest);
			oGenericUtils.waiForPageLoad(15);
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);		
			String typeofAdjustment = "//div[contains(text(),'"+scenario+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),"Type of Invoice Adjustment Slection",basetest);
			oGenericUtils.waiForPageLoad(5);

			//click on select Adjustment Type
				//oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField)," Select Adjustment Type",basetest);
			oGenericUtils.waiForPageLoad(2);
			oGenericUtils.clickButton(driver, By.xpath(orderEntryError),"orderEntryError",basetest);
			oGenericUtils.waiForPageLoad(4);
			oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);
			oGenericUtils.waiForPageLoad(9);
			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), invoiceNO,"Invoice Text Box",basetest);
			//click on Search Invoice
			//oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Click on Invoice Search",basetest);
			driver.findElement(By.xpath(invoiceNumber)).sendKeys(Keys.TAB);
			oGenericUtils.waiForPageLoad(29);

			oGenericUtils.clickButton(driver, By.xpath(next),"next ",basetest);
			oGenericUtils.waiForPageLoad(11);

			//Select split by station checkbox 
			oGenericUtils.clickButton(driver, By.xpath(selectSplitTypeCheckBox)," Split By "+splitingType,basetest);
			//click next
			oGenericUtils.waiForPageLoad(3);
			oGenericUtils.clickButton(driver, By.xpath(next),"next ",basetest);
			//Click on alert
			oGenericUtils.isAlertPresent(driver);

			oGenericUtils.waiForPageLoad(16);
			oGenericUtils.setText(driver, Comments, "Split Invoice.");
			oGenericUtils.clickButton(driver, By.xpath(finish)," finish",basetest);
			oGenericUtils.waiForPageLoad(13);
			caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber).trim();
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number :<span style='font-weight:bold;color:blue'>"+caseNumberText+"</span> is genersted.");
			}
			oGenericUtils.waiForPageLoad(7);
			oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);
			oGenericUtils.waiForPageLoad(12);

			String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
			String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
			basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

			String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
			String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
			basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

			//invoice_Total
			String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
			basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

			//invoice_Adjsutment
			String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
			basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
			//invoice_remaining
			String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
			basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);
			//Details under Case page
			//Added Status statement
			//Validation statements	
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)");
			oGenericUtils.waiForPageLoad(8);


			String communications = "//a[@id='interactionstxt']";
			oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
			oGenericUtils.waiForPageLoad(3);

			String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
			String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
			basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);
			caseValidations.casePageValidations(driver, splitingType, invoiceNO, caseNumberText, basetest);			
			invoiceValidations.invoiceValidations(driver, basetest);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public void approvalWorkFlow_below5k(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait until page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(25);


		//full credit
		oGenericUtils.clickButton(driver, By.xpath(fullCreditcheck),"Full Credit checkbox",basetest);



		String invoiceamt = driver.findElement(By.xpath(invoiceAmount)).getAttribute("value");

		String invoiceamt_replace = invoiceamt.replace(",","");

		Float invoice_float = Float.parseFloat(invoiceamt_replace);

		/*List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("NET CREDIT AMOUNT")){
					//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]")," Net Credit Amount field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);

					oGenericUtils.SetVal(driver, By.xpath(linecreditamount), XLTestData.get("linecreditamount").toString()," Adjustment amount entered in ‘NET CREDIT AMOUNT’ field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);
					oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;

		}*/

		//String linecreditamount = "//input[@id='linecreditamount_formattedValue']";


		//oGenericUtils.SetVal(driver, By.xpath(linecreditamount), XLTestData.get("linecreditamount").toString(),"Change Line Credit Amount",basetest);
		oGenericUtils.waiForPageLoad(2);

		/*//Ok button
		oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);

		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab Out",basetest);
		 */		oGenericUtils.waiForPageLoad(2);
		 //next
		 oGenericUtils.clickButton(driver, By.xpath(secondarynext),"next",basetest);

		 oGenericUtils.waiForPageLoad(10);


		 //Enter Comments

		 oGenericUtils.setText(driver, Comments, " Adjustment Amount is Greater than $200, but less than $5,000");

		 //finish
		 oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);

		 oGenericUtils.waiForPageLoad(12);

		 String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		 if(caseNumberText.length()>0){
			 basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		 }
		 oGenericUtils.waiForPageLoad(6);
		 oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		 caseValidations.autoapprovalWorkFlowValidations( driver, XLTestData, basetest);
		 oGenericUtils.waiForPageLoad(2);
		 caseValidations.getprocessStatusText(driver,basetest);
		 oGenericUtils.waiForPageLoad(2);

		 String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		 String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text+ " and verified");

		 String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		 String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text+ " and verified");

		 //invoice_Total
		 String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		 basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		 //invoice_Adjsutment
		 String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		 basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		 //invoice_remaining
		 String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		 basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		 oGenericUtils.waiForPageLoad(8);
		 float amount=caseValidations.verifyStatusInInvoicesAndCMs( driver, basetest,caseNumberText);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 String adjustment = driver.findElement(By.xpath(adjustmentAmount)).getText();

		 String adjustment_replace = adjustment.replace(",", "");

		 Float adjustment_float = Float.parseFloat(adjustment_replace);

		 if(Float.compare(amount,adjustment_float)==0)
		 {
			 basetest.test.log(Status.PASS, "AMOUNT:  "+ amount+ " in Invoice CMs and Adjustment amount:  " + adjustment_float+" are equal" );
		 } else {
			 basetest.test.log(Status.FAIL, "AMOUNT:  " + amount+ " in Invoice CMs and Adjustment amount: " + adjustment_float+" are Not equal" );
		 }

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,1000)");

		 oGenericUtils.waiForPageLoad(8);
		 String communications = "//a[@id='interactionstxt']";
		 oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		 oGenericUtils.waiForPageLoad(3);

		 String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		 String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		 basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);
		 oGenericUtils.waiForPageLoad(12);

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 oGenericUtils.waiForPageLoad(3);
		 //WebElement  invoice_number = driver.findElement(By.xpath(invoiceCMS)); 
		 oGenericUtils.clickButton(driver, By.xpath(invoiceCMS),"Invoice number : "+XLTestData.get("invoiceNumber").toString(),basetest);
		 oGenericUtils.waiForPageLoad(12);
		 String status ="//div[@class='uir-record-status']";
		 String status_ui = driver.findElement(By.xpath(status)).getText();
		 System.out.println(status_ui);
		 if(status_ui.equals("PAID IN FULL"))
		 {
			 basetest.test.log(Status.PASS, "Invoice status is displayed as: <span style='font-weight:bold;color:green'> "+status_ui+"</span>");

		 }
		 else{
			 basetest.test.log(Status.FAIL, "Invoice status is  not displayed as:'Paid in Full'");
		 }


	}

	public void approvalWorkFlow_above5k(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait until page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);



		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(25);


		//full credit
		oGenericUtils.clickButton(driver, By.xpath(fullCreditcheck),"Full Credit Button",basetest);

		oGenericUtils.waiForPageLoad(5);
		/*
		String invoiceamt = driver.findElement(By.xpath(invoiceAmount)).getAttribute("value");

		String invoiceamt_replace = invoiceamt.replace(",","");

		Float invoice_float = Float.parseFloat(invoiceamt_replace);

		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("NET CREDIT AMOUNT")){
					//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Revised Rate",basetest);
					oGenericUtils.waiForPageLoad(2);

					oGenericUtils.SetVal(driver, By.xpath(linecreditamount), XLTestData.get("linecreditamount").toString(),"Change Line Credit Amount",basetest);
					oGenericUtils.waiForPageLoad(2);
					oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;

		}

		//String linecreditamount = "//input[@id='linecreditamount_formattedValue']";


		//oGenericUtils.SetVal(driver, By.xpath(linecreditamount), XLTestData.get("linecreditamount").toString(),"Change Line Credit Amount",basetest);
		oGenericUtils.waiForPageLoad(2);

		//Ok button
		oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);

		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab Out",basetest);
		oGenericUtils.waiForPageLoad(2);*/
		//next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"next",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments

		oGenericUtils.setText(driver, Comments, "Adjustments over 5K and Less Than 10K");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);

		/*WebElement Element = driver.findElement(By.xpath(autoApproval));

		if(Element.getText().contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"Case STATUS (APPROVAL) showing "+Element.getText());
		}else {
			basetest.test.log(Status.FAIL,"Case STATUS (APPROVAL) showing "+Element.getText() );
		}

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText.contains("Not Started")){
			basetest.test.log(Status.PASS, "Process status displayed as : "+processStatusText);

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}*/

		//choose role to iHM Market Finance Manager-SSO
		changeRole(driver, XLTestData, basetest,SB5FinanceManager,"iHM Market Finance Manager");
		oGenericUtils.waiForPageLoad(8);

		//case number View
		searchCase(driver,caseNumberText,basetest);
		oGenericUtils.waiForPageLoad(8);
		//WebElement approve = driver.findElement(By.xpath(approve_btn));
		oGenericUtils.waiForPageLoad(8);


		String Approval = "";

		//Approval=driver.findElement(By.xpath(autoApproval)).getText();



		/*if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");

		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}


*/
	/*	if(approve.isDisplayed())
		{
			caseValidations.click_Approve(driver, basetest);


		}
*/
		oGenericUtils.waiForPageLoad(8);

		/*Approval=driver.findElement(By.xpath(autoApproval)).getText();

		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}
		String processStatusText2 = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText2.contains("Not Started")){
			basetest.test.log(Status.PASS, "Process status displayed as : <span style='font-weight:bold;color:green'> "+processStatusText2+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}*/


		oGenericUtils.waiForPageLoad(8);

		//choose role to iHM SB1iHM Refunds Specialist-SSO
		changeRole(driver, XLTestData, basetest,SB5iHMRefundsSpecialist,"iHM Refunds and Adjustment Specialist");
		oGenericUtils.waiForPageLoad(8);
		//case number View
		searchCase(driver,caseNumberText,basetest);

		/*oGenericUtils.waiForPageLoad(8);
		Approval=driver.findElement(By.xpath(autoApproval)).getText();
		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}*/

		oGenericUtils.waiForPageLoad(2);
		caseValidations.click_Approve( driver, basetest);
		oGenericUtils.waiForPageLoad(8);
		caseValidations.autoapprovalWorkFlowValidations( driver, XLTestData, basetest);
		 oGenericUtils.waiForPageLoad(2);
		 caseValidations.getprocessStatusText(driver,basetest);
		 oGenericUtils.waiForPageLoad(2);

		 String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		 String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text+ " and verified");

		 String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		 String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text+ " and verified");

		 //invoice_Total
		 String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		 basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		 //invoice_Adjsutment
		 String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		 basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		 //invoice_remaining
		 String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		 basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		 oGenericUtils.waiForPageLoad(8);
		 float amount=caseValidations.verifyStatusInInvoicesAndCMs( driver, basetest,caseNumberText);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 String adjustment = driver.findElement(By.xpath(adjustmentAmount)).getText();

		 String adjustment_replace = adjustment.replace(",", "");

		 Float adjustment_float = Float.parseFloat(adjustment_replace);

		 if(Float.compare(amount,adjustment_float)==0)
		 {
			 basetest.test.log(Status.PASS, "AMOUNT:  "+ amount+ " in Invoice CMs and Adjustment amount:  " + adjustment_float+" are equal" );
		 } else {
			 basetest.test.log(Status.FAIL, "AMOUNT:  " + amount+ " in Invoice CMs and Adjustment amount: " + adjustment_float+" are Not equal" );
		 }

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,1000)");

		 oGenericUtils.waiForPageLoad(8);
		 String communications = "//a[@id='interactionstxt']";
		 oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		 oGenericUtils.waiForPageLoad(3);

		 String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		 String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		 basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);
		 oGenericUtils.waiForPageLoad(12);

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 oGenericUtils.waiForPageLoad(3);
		 //WebElement  invoice_number = driver.findElement(By.xpath(invoiceCMS)); 
		 oGenericUtils.clickButton(driver, By.xpath(invoiceCMS),"Invoice number : "+XLTestData.get("invoiceNumber").toString(),basetest);
		 oGenericUtils.waiForPageLoad(12);
		 String status ="//div[@class='uir-record-status']";
		 String status_ui = driver.findElement(By.xpath(status)).getText();
		 System.out.println(status_ui);
		 if(status_ui.equals("PAID IN FULL"))
		 {
			 basetest.test.log(Status.PASS, "Invoice status is displayed as: <span style='font-weight:bold;color:green'> "+status_ui+"</span>");

		 }
		 else{
			 basetest.test.log(Status.FAIL, "Invoice status is  not displayed as:'Paid in Full'");
		 }



	}

	/*##############################################################
	@Descriptions 		---	Changing the Role
	@param driver 		---	WebDriver parameter
	@param XLTestData 	---	Test Data Parameter
	@param basetest 	---	Report Parameter
	@param role        --- Role Parameter
	##############################################################*/


	public String changeRole(WebDriver driver, HashMap<String, String> XLTestData, BaseTest basetest, String role, String roleName)
	{
		String homepageurl = null;
		try {

			//Move to Admin Role
			oGenericUtils.navigateMouseToElement(driver, By.id(adminElement),"Change Role in Netsuite Home page",basetest);

			oGenericUtils.waiForPageLoad(6);

			//Role 
			oGenericUtils.clickButton(driver, By.xpath(role), roleName , basetest);

			if(driver.getTitle().contains("Administrator Message - NetSuite"))
			{
				basetest.test.log(Status.INFO,"Administrator Message - NetSuite/Netsuite Under Maintance");
				driver.findElement(By.xpath("//*[@id='agree_fs_fs']/img")).click();
				oGenericUtils.waiForPageLoad(1);
				driver.findElement(By.xpath("//*[@id='submitter']")).click();
			}

			//Checking for Home Page	
			if(driver.findElements(By.xpath(elementSB2)).size()>0) {

				homepageurl = driver.getCurrentUrl();
				basetest.test.log(Status.PASS,"User Role is  selected as '"+roleName+"'");
			}
			else 	
			{
				basetest.test.log(Status.FAIL,"User Role is Not selected as '"+roleName+"'");
			}


		} catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
		}
		return homepageurl;
	}
	public void searchCase(WebDriver driver,String caseNumber,BaseTest basetest) throws InterruptedException
	{
		oGenericUtils.SetVal(driver, By.xpath(searchboxCase),  caseNumber.toString(),"Search",basetest);
		oGenericUtils.waiForPageLoad(7);
		//ShowMore Items
		oGenericUtils.clickButton(driver, By.xpath(showItems),"Show More Items",basetest);
		oGenericUtils.waiForPageLoad(7);
		//ShowMore Items
		oGenericUtils.clickButton(driver, By.xpath(caseView ),"Case View",basetest);

	}

	public void approvalWorkFlowover10k(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{

		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait untaile page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);

		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency Selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"Next button",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		/*if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid "+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page Not verifeid"+"'</span>");
		}
		 */
		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Searching Invoive Number",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))
		{
			Assert.assertTrue("Invoice Verified ", true);
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'>'"+ XLTestData.get("invoiceNumber")+ " has been prefixed with 'Invoice #'"+"'</span>");

		}else
		{
			Assert.assertFalse("Invoice Not Verified", false);

		}
		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"Next button",basetest);

		oGenericUtils.waiForPageLoad(25);


		//full credit
		oGenericUtils.clickButton(driver, By.xpath(fullCreditcheck),"Full Credit Checkbox",basetest);

		oGenericUtils.waiForPageLoad(5);

		WebElement creditamount = driver.findElement(By.xpath(CreditAmount));

		String creditamount_ui = creditamount.getAttribute("value");

		basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:blue'>'"+" Credit amount is displayed as :"+creditamount_ui+"'</span>");

		String creditamount_rep = creditamount_ui.replace(",","");

		Float Creditamount_Float = Float.parseFloat(creditamount_rep);

		//next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"Next button",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments
		oGenericUtils.setText(driver, Comments, "QA testing");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"Finish button",basetest);


		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){

			basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:blue'>'"+"Case number generated as :"+caseNumberText+"'</span>");
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);

		WebElement Element = driver.findElement(By.xpath(autoApproval));

		if(Element.getText().contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Element.getText() + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Element.getText() + "'</span>");
		}

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText.contains("Not Started")){
			basetest.test.log(Status.PASS, "Process status displayed as : <span style='font-weight:bold;color:green'> "+processStatusText+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}

		//choose role to iHM Market Finance Manager-SSO
		changeRole(driver, XLTestData, basetest,SB5FinanceManager,"iHM Market Finance Manager");
		oGenericUtils.waiForPageLoad(8);

		//case number View
		searchCase(driver,caseNumberText,basetest);
		oGenericUtils.waiForPageLoad(8);
		WebElement approve = driver.findElement(By.xpath(approve_btn));
		oGenericUtils.waiForPageLoad(8);


		String Approval = "";

		Approval=driver.findElement(By.xpath(autoApproval)).getText();



		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");

		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}

		caseValidations.before_approval_amount( driver, basetest);

		if(approve.isDisplayed())
		{
			caseValidations.click_Approve(driver, basetest);


		}

		oGenericUtils.waiForPageLoad(8);

		Approval=driver.findElement(By.xpath(autoApproval)).getText();

		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}
		String processStatusText2 = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText2.contains("Not Started")){
			basetest.test.log(Status.PASS, "Process status displayed as : <span style='font-weight:bold;color:green'> "+processStatusText2+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}

		caseValidations.clickInvoiceNumber(driver,basetest,XLTestData);
		caseValidations.validateAmount_due(driver,basetest,Creditamount_Float);
		oGenericUtils.waiForPageLoad(8);

		//choose role to iHM SB1iHM Refunds Specialist-SSO
		changeRole(driver, XLTestData, basetest,SB5iHMRefundsSpecialist,"iHM Refunds and Adjustment Specialist");
		oGenericUtils.waiForPageLoad(8);
		//case number View
		searchCase(driver,caseNumberText,basetest);

		oGenericUtils.waiForPageLoad(8);
		Approval=driver.findElement(By.xpath(autoApproval)).getText();
		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}

		caseValidations.before_approval_amount( driver, basetest);

		oGenericUtils.waiForPageLoad(2);
		caseValidations.click_Approve( driver, basetest);


		oGenericUtils.waiForPageLoad(6);
		Approval=driver.findElement(By.xpath(autoApproval)).getText();
		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}

		String processStatusText3 = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText3.contains("Not Started")){
			basetest.test.log(Status.PASS, "Process status displayed as : <span style='font-weight:bold;color:green'> "+processStatusText3+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}

		caseValidations.clickInvoiceNumber(driver,basetest,XLTestData);
		caseValidations.validateAmount_due(driver,basetest,Creditamount_Float);
		oGenericUtils.waiForPageLoad(8);

		//choose role to iHM SB1iHM Refunds Manager-SSO
		changeRole(driver, XLTestData, basetest,SB5iHMRefundsmanager,"iHM Refunds and Adjustment Manager");
		oGenericUtils.waiForPageLoad(8);
		//case number View
		searchCase(driver,caseNumberText,basetest);
		oGenericUtils.waiForPageLoad(8);
		Approval=driver.findElement(By.xpath(autoApproval)).getText();
		if(Approval.contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}

		caseValidations.before_approval_amount( driver, basetest);

		oGenericUtils.waiForPageLoad(2);

		caseValidations.click_Approve(driver, basetest);

		oGenericUtils.waiForPageLoad(20);
		caseValidations.autoapprovalWorkFlowValidations( driver, XLTestData, basetest);
		 oGenericUtils.waiForPageLoad(2);
		 caseValidations.getprocessStatusText(driver,basetest);
		 oGenericUtils.waiForPageLoad(2);

		 String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		 String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text+ " and verified");

		 String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		 String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		 basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text+ " and verified");

		 //invoice_Total
		 String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		 basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		 //invoice_Adjsutment
		 String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		 basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		 //invoice_remaining
		 String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		 basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		 oGenericUtils.waiForPageLoad(8);
		 float amount=caseValidations.verifyStatusInInvoicesAndCMs( driver, basetest,caseNumberText);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 String adjustment = driver.findElement(By.xpath(adjustmentAmount)).getText();

		 String adjustment_replace = adjustment.replace(",", "");

		 Float adjustment_float = Float.parseFloat(adjustment_replace);

		 if(Float.compare(amount,adjustment_float)==0)
		 {
			 basetest.test.log(Status.PASS, "AMOUNT:  "+ amount+ " in Invoice CMs and Adjustment amount:  " + adjustment_float+" are equal" );
		 } else {
			 basetest.test.log(Status.FAIL, "AMOUNT:  " + amount+ " in Invoice CMs and Adjustment amount: " + adjustment_float+" are Not equal" );
		 }

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,1000)");

		 oGenericUtils.waiForPageLoad(8);
		 String communications = "//a[@id='interactionstxt']";
		 oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		 oGenericUtils.waiForPageLoad(3);

		 String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		 String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		 basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);
		 oGenericUtils.waiForPageLoad(12);

		 js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,-1500)");

		 oGenericUtils.waiForPageLoad(3);
		 //WebElement  invoice_number = driver.findElement(By.xpath(invoiceCMS)); 
		 oGenericUtils.clickButton(driver, By.xpath(invoiceCMS),"Invoice number : "+XLTestData.get("invoiceNumber").toString(),basetest);
		 oGenericUtils.waiForPageLoad(12);
		 String status ="//div[@class='uir-record-status']";
		 String status_ui = driver.findElement(By.xpath(status)).getText();
		 System.out.println(status_ui);
		 if(status_ui.equals("PAID IN FULL"))
		 {
			 basetest.test.log(Status.PASS, "Invoice status is displayed as: <span style='font-weight:bold;color:green'> "+status_ui+"</span>");

		 }
		 else{
			 basetest.test.log(Status.FAIL, "Invoice status is  not displayed as:'Paid in Full'");
		 }


	}

	public void approvalWorkFlow_CreditRevise_invoice_Under5k(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait until page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		//oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"Next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);



		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab out",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"Next",basetest);

		oGenericUtils.waiForPageLoad(30);


		//full credit
		oGenericUtils.clickButton(driver, By.xpath(ratechange),"Rate Change",basetest);

		oGenericUtils.waiForPageLoad(25);

		String invoiceamt = driver.findElement(By.xpath(invoiceAmount)).getAttribute("value");

		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("REVISED RATE")){
					//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Net Credit Amount field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);

					String RevisedRate = driver.findElement(By.xpath(revisedAmount)).getAttribute("value").trim();
					String Rate = RevisedRate.replace(",", "");
					Float Revised = Float.parseFloat(Rate);
					String adjustamount = XLTestData.get("AdjustmentAmount");
					String Adjamt = adjustamount.replace(",", "");
					Float adjsutedamount = Float.parseFloat(Adjamt);
					Float RevisedRateNet = (float) (Revised - adjsutedamount);
					driver.findElement(By.xpath(revisedAmount)).clear();
					oGenericUtils.waiForPageLoad(2);
					oGenericUtils.SetVal(driver, By.xpath(revisedAmount), RevisedRateNet.toString(),"Adjustment amount entered in ‘NET CREDIT AMOUNT’ field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath(ok_btn),"OK Button",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;

		}







		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab Out",basetest);
		oGenericUtils.waiForPageLoad(2);
		//next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"Next",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments

		oGenericUtils.setText(driver, Comments, "Adjustment Amount is Greater than $200, but less than $5,000");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"Finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);



		WebElement Element = driver.findElement(By.xpath(autoApproval));
		String status = Element.getText();
		basetest.test.log(Status.INFO, "STATUS (APPROVAL) is displayed as: "+status);

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		basetest.test.log(Status.INFO, "PROCESS STATUS is displayed as:  "+processStatusText);

		String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

		String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		oGenericUtils.waiForPageLoad(8);



		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		String communications = "//a[@id='interactionstxt']";
		oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		oGenericUtils.waiForPageLoad(3);

		String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);


	}

	public void approvalWorkFlow_CreditRevise_invoice_over10k(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Adjustment
		oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

		//wait until page load
		oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		//oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"Next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab Out",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified", true);
		else
			Assert.assertFalse("Invoice Not Verified", false);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(30);


		//full credit
		oGenericUtils.clickButton(driver, By.xpath(ratechange),"RateChange",basetest);

		oGenericUtils.waiForPageLoad(25);

		String invoiceamt = driver.findElement(By.xpath(invoiceAmount)).getAttribute("value");

		if(XLTestData.get("linetype").contains("national"))
		{
			List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
			lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			int  size =lineAdjustTableRowCount.size();
			boolean flag=false;
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED RATE")){
						//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						//Ok button
						oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Net Credit Amount field in Line Adjustment",basetest);
						oGenericUtils.waiForPageLoad(2);

						String RevisedRate = driver.findElement(By.xpath(revisedAmount)).getAttribute("value").trim();
						String Rate = RevisedRate.replace(",", "");
						Float Revised = Float.parseFloat(Rate);
						String adjustamount = XLTestData.get("AdjustmentAmount");
						String Adjamt = adjustamount.replace(",", "");
						Float adjsutedamount = Float.parseFloat(Adjamt);
						Float RevisedRateNet = (float) (Revised - adjsutedamount);
						driver.findElement(By.xpath(revisedAmount)).clear();
						oGenericUtils.waiForPageLoad(2);
						oGenericUtils.SetVal(driver, By.xpath(revisedAmount), RevisedRateNet.toString(),"Adjustment amount entered in ‘NET CREDIT AMOUNT’ field in Line Adjustment",basetest);
						oGenericUtils.waiForPageLoad(2);
						//Ok button
						oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);
						flag = true;
						break;
					}
				}
				if(flag==true)
					break;

			}

			oGenericUtils.waiForPageLoad(2);
			oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
			oGenericUtils.waiForPageLoad(2);

		}
		else
		{
			//full credit
			oGenericUtils.clickButton(driver, By.xpath(fullCreditcheck),"Full Credit Button",basetest);

			oGenericUtils.waiForPageLoad(5);
		}
		//next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"next",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments

		oGenericUtils.setText(driver, Comments, "Adjustment Amount is Greater than $200,");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);
		/*WebElement Element = driver.findElement(By.xpath(autoApproval));

		if(Element.getText().contains("Pending Approval From Finance"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Partially Approved"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Auto Approved"+"'</span>");
		}

		//choosing role to iHM Market Finance Manager-SSO
		changeRole(driver, XLTestData, basetest,SB5FinanceManager, "iHM Finance Manager");
		oGenericUtils.waiForPageLoad(8);

		searchCase( driver, caseNumberText, basetest);

		caseValidations.click_Approve(driver, basetest);

		WebElement Element2 = driver.findElement(By.xpath(autoApproval));

		if(Element2.getText().contains("Pending Approval"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Partially Approved"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Auto Approved"+"'</span>");
		}

		//choosing role to iHM Billing Manager-SSO
		changeRole(driver, XLTestData, basetest,SB5BillingManager, "iHM Billing  Manager");
		oGenericUtils.waiForPageLoad(8);
		searchCase( driver, caseNumberText, basetest);

		caseValidations.click_Approve(driver, basetest);
		String Approval = "";
		oGenericUtils.waiForPageLoad(20);*/


		WebElement Element3 = driver.findElement(By.xpath(autoApproval));
		String status = Element3.getText();
		basetest.test.log(Status.INFO, "STATUS (APPROVAL) is displayed as: "+status);

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		basetest.test.log(Status.INFO, "PROCESS STATUS is displayed as:  "+processStatusText);

		String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

		String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		oGenericUtils.waiForPageLoad(8);



		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		String communications = "//a[@id='interactionstxt']";
		oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		oGenericUtils.waiForPageLoad(3);

		String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);



	}

	/*##############################################################
	 @Descriptions 		---	Select Adjustment Request in Billing via Admin role
	 @param driver 		---	WebDriver parameter
	 @param XLTestData 	---	Test Data Parameter
	 @param basetest 	---	Report Parameter
	 ##############################################################*/
	public void selectAdjRequestInBilling(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) {

		try {	

			//webdriver waiting
			oGenericUtils.waiForPageLoad(5);

			//Move to Transactions
			oGenericUtils.navigateMouseToElement(driver, By.xpath(transaction),"Transactions",basetest);

			//Move to Billing
			oGenericUtils.navigateMouseToElement(driver, By.xpath(billing),"Billing",basetest);



			//Adjustment / Special Billing
			oGenericUtils.clickButton(driver, By.xpath(adjustmentRequest),"Adjustment Request",basetest);

			//Page validation
			/*if(driver.findElements(By.xpath(adjustment)).size()>0)
			{	
				basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'>'"+"Differnt Billing Request Page Loded Succesfully"+"'</span>");
			}
			else
			{
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+"Differnt Billing Request Page Loded Succesfully"+"'</span>");
			}*/

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
		}	
	}	

	public void issueCreditToClient_PartialPaid_Digital(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		oGenericUtils.waiForPageLoad(7);
		//select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified", true);
		else
			Assert.assertFalse("Invoice Not Verified", false);


		//next
		oGenericUtils.clickButton(driver, By.xpath(next),"next",basetest);

		oGenericUtils.waiForPageLoad(30);





		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("FULL CREDIT")){

					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Net Credit Amount field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);
					//Full Credit Check box
					//oGenericUtils.clickButton(driver, By.xpath(fullcredit_checkbox),"Full credit Check BOx",basetest);
					driver.findElement(By.xpath(fullcredit_checkbox)).click();
					oGenericUtils.waiForPageLoad(2);
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;


		}

		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
		oGenericUtils.waiForPageLoad(2);



		//next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"next",basetest);

		oGenericUtils.waiForPageLoad(10);


		//Enter Comments

		oGenericUtils.setText(driver, Comments, "Adjustment Amount is Greater than $200, but less than $5,000");

		//finish
		oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);
		caseValidations.autoapprovalWorkFlowValidations( driver, XLTestData, basetest);
		oGenericUtils.waiForPageLoad(2);
		caseValidations.getprocessStatusText(driver,basetest);
		oGenericUtils.waiForPageLoad(2);

		String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

		String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		oGenericUtils.waiForPageLoad(8);

		Float amount = caseValidations.verifyStatusInInvoicesAndCMs(driver, basetest, caseNumberText);

		oGenericUtils.waiForPageLoad(3);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-1500)");

		String adjustment = driver.findElement(By.xpath(adjustmentAmount)).getText();

		String adjustment_replace = adjustment.replace(",", "");

		Float adjustment_float = Float.parseFloat(adjustment_replace);

		if(Float.compare(amount,adjustment_float)==0)
		{
			basetest.test.log(Status.PASS, "AMOUNT:  "+ amount+ " in Invoice CMs and Adjustment amount:  " + adjustment_float+" are equal" );
		} else {
			basetest.test.log(Status.FAIL, "AMOUNT:  " + amount+ " in Invoice CMs and Adjustment amount: " + adjustment_float+" are Not equal" );
		}

		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		oGenericUtils.waiForPageLoad(8);

		String autoApproved = "//span[@id='custevent_case_auto_approved_fs']/img";
		String checkboxAutoApp=driver.findElement(By.xpath(autoApproved)).getAttribute("alt").trim();
		if (checkboxAutoApp.contains("Checked")) {
			basetest.test.log(Status.PASS, "Case is auto approved and auto approved checkbox is selected");
		} else {
			basetest.test.log(Status.FAIL, "Case is  not auto approved and auto approved checkbox is Not selected");
		}

		String communications = "//a[@id='interactionstxt']";
		oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		oGenericUtils.waiForPageLoad(3);

		String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);



	}	 

	public void issue_credit_Fullamount(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest)throws InterruptedException
	{
		oGenericUtils.waiForPageLoad(7);
		//click on select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);



		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);
		oGenericUtils.waiForPageLoad(5);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," Next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page verifeid successfully"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page Not verifeid"+"'</span>");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//click on Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice)," Tab out",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified Successfully", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," Next",basetest);

		oGenericUtils.waiForPageLoad(30);


		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("FULL CREDIT")){

					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"FULL CREDIT LINE",basetest);
					oGenericUtils.waiForPageLoad(2);
					//Full Credit Check box
					oGenericUtils.clickButton(driver, By.xpath(fullcredit_checkbox),"FULL CREDIT CHECK BOX IN LINE ITEM",basetest);
					oGenericUtils.waiForPageLoad(2);
					//Ok button
					oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;


		}
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
		oGenericUtils.waiForPageLoad(2);

		WebElement creditamount = driver.findElement(By.xpath(CreditAmount));

		String creditamount_ui = creditamount.getAttribute("value");



		String creditamount_rep = creditamount_ui.replace(",","");

		Float Creditamount_Float = Float.parseFloat(creditamount_rep);

		oGenericUtils.waiForPageLoad(5);
		String text = "//span[@id='invoiceinfo_val']/table/tbody/tr/td/div/ul/li[text()]";
		String ivoice_msg = driver.findElement(By.xpath(text)).getText();
		int totalindex = ivoice_msg.indexOf("$");
		int OpenIndex = ivoice_msg.indexOf("$", totalindex + 1);



		String OpenAmount_string = ivoice_msg.substring(OpenIndex+1);
		String OpenAmount_replace = OpenAmount_string.replace(",","");
		Float openamount = Float.parseFloat(OpenAmount_string);

		if(openamount>Creditamount_Float)	
		{
			//click on next
			oGenericUtils.clickButton(driver, By.xpath(secondarynext)," next",basetest);

			oGenericUtils.waiForPageLoad(10);

		}




		oGenericUtils.waiForPageLoad(10);
		//Enter Comments

		oGenericUtils.setText(driver, Comments, "QA");

		//click on finish
		oGenericUtils.clickButton(driver, By.xpath(finish)," Finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);

		String Approval = "";
		Approval=driver.findElement(By.xpath(autoApproval)).getText();
		if(Approval.contains("Approved"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Case STATUS (APPROVAL) showing "+Approval + "'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Case STATUS (APPROVAL) showing "+Approval+ "'</span>");
		}

		String processStatusText4 = oGenericUtils.getTextOfElement(driver, processStatus);
		if(processStatusText4.contains("Complete")){
			basetest.test.log(Status.PASS, "Process status displayed as : <span style='font-weight:bold;color:green'> "+processStatusText4+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Process status is not displayed as 'Not started'");
		}


		Float amount = caseValidations.verifyStatusInInvoicesAndCMs( driver, basetest,caseNumberText);


		if(Creditamount_Float.equals(amount))
		{
			basetest.test.log(Status.PASS, "Adjustment amount is matched with the amount showing in Credit Memo: <span style='font-weight:bold;color:green'> "+amount+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Adjustment amount is matched with the amount showing in Credit Memo 'Not matched'");
		}


		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-1500)");

		oGenericUtils.waiForPageLoad(3);
		String invoice_remining_amount_ui = driver.findElement(By.xpath(invoice_Remaining_Amount)).getText();
		String invoice_remaining_amount_replace =  invoice_remining_amount_ui.replace(",", "");
		Float invoice_remaining_amount_float = Float.parseFloat(invoice_remaining_amount_replace);

		Float amount_Due = invoice_remaining_amount_float-Creditamount_Float;

		oGenericUtils.waiForPageLoad(3);
		//WebElement  invoice_number = driver.findElement(By.xpath(invoiceCMS)); 
		oGenericUtils.clickButton(driver, By.xpath(invoiceCMS),"Invoice number",basetest);
		oGenericUtils.waiForPageLoad(12);

		//Validating Amount Due in invoice page

		String amount_Due_invoice ="//span[@id='amountremainingtotalbox_fs_lbl']/following::span";

		String amount_Due_invoice_ui = driver.findElement(By.xpath(amount_Due_invoice)).getText();

		String amount_due_replace = amount_Due_invoice_ui.replace(",", "");

		Float amount_due_invoice_float = Float.parseFloat(amount_due_replace);

		if(amount_due_invoice_float.equals(amount_Due))
		{
			basetest.test.log(Status.PASS, "Adjustment amount is matched with the amount showing in Credit Memo: <span style='font-weight:bold;color:green'> "+amount+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Adjustment amount is matched with the amount showing in Credit Memo 'Not matched'");
		}



	}
	public void issueCreditToClient_Validate_Amount_Greater_PartialAmount(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest)
	{
		oGenericUtils.waiForPageLoad(7);
		//click on select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS,"Invoice Page verified");
		}else {
			basetest.test.log(Status.FAIL,"Invoice Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//click on Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab out",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(30);

		if(XLTestData.get("creditReviseInvoice").equalsIgnoreCase("yes"))
		{
			//click on full credit
			oGenericUtils.clickButton(driver, By.xpath(ratechange)," RateChange",basetest);

		}
		oGenericUtils.waiForPageLoad(3);
		//click on full credit
		oGenericUtils.clickButton(driver, By.xpath(fullCreditcheck)," Full Credit checkbox",basetest);
		//driver.findElement(By.xpath(fullCreditcheck)).click();
		oGenericUtils.waiForPageLoad(5);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext)," next",basetest);

		oGenericUtils.waiForPageLoad(2);
		//String AlertText = oGenericUtils.getAlertText( driver,  "No Match",  basetest);
		String AlertText = driver.switchTo().alert().getText();

		if(AlertText.contains(XLTestData.get("AlertText")))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+AlertText+"'</span>");
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '" +"Verified that adjustment cannot be created for amount greater than partial amount"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Not Verified that adjustment cannot be created for amount greater than partial amount"+"'</span>");
		}

		oGenericUtils.waiForPageLoad(2);

		driver.switchTo().alert().accept();

	}

	public void issue_Credit_to_Client_PartialPaid_CreditToReviseInvoice_PaymentApplied(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		oGenericUtils.waiForPageLoad(7);
		//click on select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		//oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField)," Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS, "Invoice  Page verified ");
		}else {
			basetest.test.log(Status.FAIL, "Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//click on Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab out",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified ", true);
		else
			Assert.assertFalse("Invoice NotVerified", false);


		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(30);

		//click on full credit
		oGenericUtils.clickButton(driver, By.xpath(ratechange)," RateChange",basetest);

		String text = "//span[@id='invoiceinfo_val']/table/tbody/tr/td/div/ul/li[text()]";
		String ivoice_msg = driver.findElement(By.xpath(text)).getText();
		int totalindex = ivoice_msg.indexOf("$");
		String t = ivoice_msg.substring(totalindex+1);
		String arr[] = t.split(" ", 2) ;
		System.out.println(arr[0]);


		String paidAmount_replace = arr[0].replace(",","");
		Float paidAmount = Float.parseFloat(arr[0]);

		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.contains("REVISED RATE")){
					//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						

					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]")," Net Credit Amount field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);


					driver.findElement(By.xpath(revisedAmount)).clear();
					oGenericUtils.waiForPageLoad(2);
					oGenericUtils.SetVal(driver, By.xpath(revisedAmount), arr[0].toString(),"Adjustment amount entered in ‘NET CREDIT AMOUNT’ field in Line Adjustment",basetest);
					oGenericUtils.waiForPageLoad(2);
					//click on Ok button
					oGenericUtils.clickButton(driver, By.xpath(ok_btn)," Ok Button",basetest);
					flag = true;
					break;
				}
			}
			if(flag==true)
				break;

		}
		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
		oGenericUtils.waiForPageLoad(2);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(secondarynext),"next",basetest);

		oGenericUtils.waiForPageLoad(10);
		//Enter Comments

		oGenericUtils.setText(driver, Comments, "Adjustment Amount is Greater than $200, but less than $5,000");

		//click on finish
		oGenericUtils.clickButton(driver, By.xpath(finish)," finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);

		WebElement Element = driver.findElement(By.xpath(autoApproval));
		String status = Element.getText();
		basetest.test.log(Status.INFO, "STATUS (APPROVAL) is displayed as: "+status);

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		basetest.test.log(Status.INFO, "PROCESS STATUS is displayed as:  "+processStatusText);

		String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

		String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		oGenericUtils.waiForPageLoad(8);



		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		String communications = "//a[@id='interactionstxt']";
		oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		oGenericUtils.waiForPageLoad(3);

		String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		basetest.test.log(Status.INFO,"Under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);


	}
	public void issue_Credit_to_Client_PartialPaid_CreditToReviseInvoice_CreditToInvoice(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException{
		//      issue_Credit_to_Client_PartialPaid_CreditToReviseInvoice_CreditToInvoice
		oGenericUtils.waiForPageLoad(7);
		//click on select Adjustment Type
		oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);

		String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);
		oGenericUtils.waiForPageLoad(5);


		oGenericUtils.clickButton(driver, By.xpath(preference),"Type of Preference",basetest);
		String typeofPreference = "//div[contains(text(),'"+XLTestData.get("preference")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofPreference),XLTestData.get("preference"),basetest);
		oGenericUtils.waiForPageLoad(6);
		//clicking on OK Alert
		//oGenericUtils.isAlertPresent(driver);

		oGenericUtils.waiForPageLoad(5);

		//Agency selection
		oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneralField),"Adjustment Reason General",basetest);
		String typeofagency = "//div[contains(text(),'"+XLTestData.get("AdjustmentReasonGeneral")+"')]";
		oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("AdjustmentReasonGeneral"),basetest);

		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(9);

		oGenericUtils.waiForPageLoad(10);


		if(driver.findElements(By.xpath(invoicePage)).size()>0){
			basetest.test.log(Status.PASS,"Invoice  Page verified successfully");
		}else {
			basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
		}

		oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

		//click on Search Invoice
		oGenericUtils.clickButton(driver, By.xpath(searchInvoice)," Invoice Search",basetest);

		oGenericUtils.waiForPageLoad(50);
		WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

		String invoiceNumberText = invoiceElement.getAttribute("previousvalue");

		if(invoiceNumberText.contains("#"))

			Assert.assertTrue("Invoice Verified Successfully", true);
		else
			Assert.assertFalse("InvoiceNotVerified", false);


		//click on next
		oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

		oGenericUtils.waiForPageLoad(30);

		//click on full credit
		oGenericUtils.clickButton(driver, By.xpath(ratechange)," RateChange",basetest);

		String text = "//span[@id='invoiceinfo_val']/table/tbody/tr/td/div/ul/li[text()]";
		String ivoice_msg = driver.findElement(By.xpath(text)).getText();
		int totalindex = ivoice_msg.indexOf("$");
		int OpenIndex = ivoice_msg.indexOf("$", totalindex + 1);

		System.out.println(text.substring(OpenIndex+1));

		String OpenAmount_string = ivoice_msg.substring(OpenIndex+1);
		String OpenAmount_replace = OpenAmount_string.replace(",","");
		Float openamount = Float.parseFloat(OpenAmount_string);


		List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));
		lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));


		int  size =lineAdjustTableRowCount.size();
		boolean flag=false;
		Float Rate =0.0f;
		Float NetCreditAmount=0.0f;
		for(int i=1; i<=size ; i++){
			List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
			int headSize = tableHead.size();
			for(int j=1;j<=headSize;j++){
				String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
				if(headText.equals("RATE"))
				{
					String Rate_UI= oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");
					//oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Click on Rate",basetest);
					oGenericUtils.waiForPageLoad(2);
					//String Rate_value = driver.findElement(By.xpath(revisedAmount)).getAttribute("value").trim();
					String Rate_replace = Rate_UI.replace(",", "");
					Rate = Float.parseFloat(Rate_replace);
				}
				if(headText.equals("REVISED RATE")){
					//String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						


					//String RevisedRate_String = driver.findElement(By.xpath(revisedAmount)).getAttribute("value").trim();
					//String RevisedRate_replace = RevisedRate_String.replace(",", "");
					//Float Revised = Float.parseFloat(RevisedRate_replace);
					String RevisedRate_str = XLTestData.get("RevisedRate");
					String RevisedRate_replace = RevisedRate_str.replace(",", "");
					Float Revisedamount = Float.parseFloat(RevisedRate_replace);
					//Float RevisedRateNet = (float) (Revised - adjsutedamount);
					oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]")," Revised Rate",basetest);
					oGenericUtils.waiForPageLoad(2);
					driver.findElement(By.xpath(revisedAmount)).clear();
					oGenericUtils.waiForPageLoad(2);
					if(Rate>=Revisedamount)
					{
						oGenericUtils.SetVal(driver, By.xpath(revisedAmount), Revisedamount.toString(),"Change Revised Rate Amount",basetest);
					}

					oGenericUtils.waiForPageLoad(2);
					//click on Ok button
					oGenericUtils.clickButton(driver, By.xpath(ok_btn),"Ok Button",basetest);

				}

				if(headText.equals("NET CREDIT AMOUNT"))
				{
					String NET_CREDITAmount_UI= oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");
					//oGenericUtils.clickButton(driver, By.xpath("//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]"),"Click on Rate",basetest);
					oGenericUtils.waiForPageLoad(2);
					//String Rate_value = driver.findElement(By.xpath(revisedAmount)).getAttribute("value").trim();
					String NetCreditAmt_replace = NET_CREDITAmount_UI.replace(",", "");
					NetCreditAmount = Float.parseFloat(NetCreditAmt_replace);
					flag = true;
					break;
				}


			}
			if(flag==true)
				break;

		}



		oGenericUtils.waiForPageLoad(2);
		oGenericUtils.clickButton(driver, By.xpath(adjustmentPage),"Tab out",basetest);
		oGenericUtils.waiForPageLoad(2);


		if(openamount>NetCreditAmount)	
		{
			//click on next
			oGenericUtils.clickButton(driver, By.xpath(secondarynext)," next",basetest);

			oGenericUtils.waiForPageLoad(10);

		}




		//Enter Comments

		oGenericUtils.setText(driver, Comments, "QA");

		//click on finish
		oGenericUtils.clickButton(driver, By.xpath(finish)," finish",basetest);

		oGenericUtils.waiForPageLoad(12);

		String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
		if(caseNumberText.length()>0){
			basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
		}
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);

		oGenericUtils.waiForPageLoad(8);

		WebElement Element = driver.findElement(By.xpath(autoApproval));
		String status = Element.getText();
		basetest.test.log(Status.INFO, "STATUS (APPROVAL) is displayed as: "+status);

		String processStatusText = oGenericUtils.getTextOfElement(driver, processStatus);
		basetest.test.log(Status.INFO, "PROCESS STATUS is displayed as:  "+processStatusText);

		String adjustment_Type = "//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span[1]/span/a";
		String adjustment_Type_text = driver.findElement(By.xpath(adjustment_Type)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT TYPE   is displayed as: "+adjustment_Type_text);

		String adjustment_reason_code = "//span[@id='custevent_ihm_adjreason_fs_lbl']/following::span[1]/span";
		String adjustment_reason_code_text = driver.findElement(By.xpath(adjustment_reason_code)).getText();
		basetest.test.log(Status.INFO,"ADJUSTMENT REASON CODE  is displayed as: "+adjustment_reason_code_text);

		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO," INVOICE TOTAL AMOUNT :  "+invoice_tot);

		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO, "ADJUSTMENT AMOUNT:  "+invoice_adjust);
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO, "INVOICE REMAINING AMOUNT:  "+invoice_remain);

		oGenericUtils.waiForPageLoad(8);



		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		String communications = "//a[@id='interactionstxt']";
		oGenericUtils.clickButton(driver, By.xpath(communications)," communication",basetest);
		oGenericUtils.waiForPageLoad(3);

		String email_Sent = "//table[@id='messages__tab']/tbody/tr/td[6]";
		String email_sent_txt = driver.findElement(By.xpath(email_Sent)).getText();
		basetest.test.log(Status.INFO,"under Messages EMAIL SENT  is displayed as :  "+email_sent_txt);



	}

	public void Creditmemo_Generation(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest)
	{
		//enter invoice in search box
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.SetVal(driver, By.xpath(search_box), XLTestData.get("invoiceNumber").toString(),"Search Invoice",basetest);

		oGenericUtils.waiForPageLoad(8);
		//click on Invoice number
		oGenericUtils.clickButton(driver, By.xpath(click_Invoice_View)," Invoice number to view:"+XLTestData.get("invoiceNumber"),basetest);

		oGenericUtils.waiForPageLoad(10);//credit_Memo
		//click on credit memo
		oGenericUtils.clickButton(driver, By.xpath(credit_Memo),"Credit memo",basetest);

		oGenericUtils.waiForPageLoad(30);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1900)");

		String unapplied_amount_ui = driver.findElement(By.xpath(unapplied_amount)).getAttribute("value"); 
		String unapplied_amount_ui_replace = unapplied_amount_ui.replace(",", "");
		Float unapplied_amount_float = Float.parseFloat(unapplied_amount_ui_replace);
		oGenericUtils.waiForPageLoad(3);

		oGenericUtils.clickButton(driver, By.xpath(save)," save",basetest);

		oGenericUtils.waiForPageLoad(3);

		if(unapplied_amount_float>0.0)
		{
			driver.switchTo().alert().sendKeys(unapplied_amount_ui_replace);
			oGenericUtils.waiForPageLoad(3);
			driver.switchTo().alert().accept();
		}



		oGenericUtils.waiForPageLoad(10);

		//validate credit memo is generated or not

		String credit_memo_text = driver.findElement(By.xpath(credit_Memo_text)).getText();

		if(credit_memo_text.equals("Credit Memo"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Credit Memo generated "+"'</span>");
		}
		else
		{
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Credit Memo not Generated"+"'</span>");	
		}

	}

	public void market_invoice_rebill(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws Exception
	{
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.SetVal(driver, By.xpath(search_box), XLTestData.get("invoiceNumber").toString(),"Search Invoice",basetest);

		oGenericUtils.waiForPageLoad(6);

		oGenericUtils.clickButton(driver, By.xpath(click_Invoice_View)," Invoice number to view:"+XLTestData.get("invoiceNumber"),basetest);

		oGenericUtils.waiForPageLoad(10);

		//mouse hover on Actions

		oGenericUtils.navigateMouseToElement(driver, By.xpath(actions),"Actions",basetest);

		oGenericUtils.waiForPageLoad(2);
		//click on rebill-makecopy

		oGenericUtils.clickButton(driver, By.xpath(rebill_makecopy)," rebill - makecopy",basetest);

		oGenericUtils.waiForPageLoad(10);

		//click on save

		oGenericUtils.clickButton(driver, By.xpath(save_invoice)," Save",basetest);

		oGenericUtils.waiForPageLoad(15);

		//validate new invoice is created or not.

		String invoice_text = driver.findElement(By.xpath(invoice_txt)).getText();

		if(invoice_text.equals("Invoice"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"New Invoice Created Successfully"+"'</span>");
		}
		else
		{
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"New Invoice is not created"+"'</span>");	
		}


		//Search for the old invoice
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.SetVal(driver, By.xpath(search_box), XLTestData.get("invoiceNumber").toString(),"Search Invoice",basetest);

		oGenericUtils.waiForPageLoad(10);

		oGenericUtils.clickButton(driver, By.xpath(click_Invoice_View)," Invoice number to view:"+XLTestData.get("invoiceNumber"),basetest);

		oGenericUtils.waiForPageLoad(10);

		//click on credit memo
		oGenericUtils.clickButton(driver, By.xpath(credit_Memo),"Credit memo",basetest);

		oGenericUtils.waiForPageLoad(15);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1800)");

		oGenericUtils.waiForPageLoad(10);
		////a[@id='applytxt']




		//click on Apply text
		oGenericUtils.clickButton(driver, By.xpath(apply),"Apply text",basetest);


		//Apply table  Apply_text_table

		String orinal_amount="";
		List<WebElement>applytable = driver.findElements(By.xpath(Apply_text_table));

		for(int i=2;i<=applytable.size();i++)
		{
			List<WebElement>applytable_header = driver.findElements(By.xpath(apply_table_header));
			for(int j=1;j<=applytable_header.size();j++)
			{
				String header_text = driver.findElement(By.xpath("//table[@id='apply_splits']/tbody/tr[1]/td["+j+"]")).getText();
				if(header_text.contains("ORIG. AMT."))
				{
					orinal_amount = driver.findElement(By.xpath("//table[@id='apply_splits']/tbody/tr["+i+"]/td["+j+"]")).getText();
				}
			}
		}


		String unapplied_amount_ui = driver.findElement(By.xpath(unapplied_amount)).getAttribute("value"); 
		String unapplied_amount_ui_replace = unapplied_amount_ui.replace(",", "");
		Float unapplied_amount_float = Float.parseFloat(unapplied_amount_ui_replace);
		oGenericUtils.waiForPageLoad(3);

		oGenericUtils.clickButton(driver, By.xpath(save)," save",basetest);

		oGenericUtils.waiForPageLoad(15);

		if(unapplied_amount_float>0.0)
		{
			driver.switchTo().alert().sendKeys(unapplied_amount_ui_replace);
			oGenericUtils.waiForPageLoad(3);
			driver.switchTo().alert().accept();
		}

		String credit_memo_text = driver.findElement(By.xpath(credit_Memo_text)).getText();

		if(credit_memo_text.equals("Credit Memo"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:green'> '"+"Credit Memo generated Successfully"+"'</span>");
		}
		else
		{
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:red'> '"+"Credit Memo not Generated"+"'</span>");	
		}

		//Search for the old invoice
		oGenericUtils.waiForPageLoad(6);
		oGenericUtils.SetVal(driver, By.xpath(search_box), XLTestData.get("invoiceNumber").toString(),"Search Invoice",basetest);

		oGenericUtils.waiForPageLoad(10);

		oGenericUtils.clickButton(driver, By.xpath(click_Invoice_View)," Invoice number to view:"+XLTestData.get("invoiceNumber"),basetest);

		oGenericUtils.waiForPageLoad(10);

		String status = driver.findElement(By.xpath("//div[@class='uir-record-status']")).getText();
		System.out.println(status);
		if(status.equals("PAID IN FULL"))
		{
			basetest.test.log(Status.PASS, "Invoice status is displayed as: <span style='font-weight:bold;color:green'> "+status+"</span>");
		}
		else{
			basetest.test.log(Status.FAIL, "Invoice status is not displayed as:'Paid in Full'");
		}


	}

	public String removeAgencyCommision(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) {




		String invoiceNumberTextValue="";
		float grossAmount =0;
		float netDue =0;
		String adjmentAmtValue =  "";
		String grossValueFormated="";
		String netValueFormated="";

		try {
			oGenericUtils.clickButton(driver, By.xpath(adjustment),"Adjustment",basetest);

			//wait untaile page load
			oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

			oGenericUtils.waiForPageLoad(5);
			//click on select Adjustment Type
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow),"Select Adjustment Type",basetest);

			//click on Add or remove agency commision
			oGenericUtils.waiForPageLoad(5);
			String typeofAdjustment = "//div[contains(text(),'Add or Remove Agency Commission')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);

			//tabing 
			oGenericUtils.waiForPageLoad(6);
			//clicking on OK Alert
			oGenericUtils.isAlertPresent(driver);
			oGenericUtils.waiForPageLoad(6);
			//Agencyselection
			//oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneral),"Adjustment Reason General",basetest);
			String typeofagency = "//div[contains(text(),'Agency Commission')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("Agencyselection"),basetest);

			//click on next
			oGenericUtils.clickButton(driver, By.xpath(next),"Next",basetest);

			oGenericUtils.waiForPageLoad(6);
			String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";

			if(driver.findElements(By.xpath(invoicePage)).size()>0){
				basetest.test.log(Status.PASS,"Invoice  Page verified ");
			}else {
				basetest.test.log(Status.FAIL,"Invoice  Page Not verified");
			}

System.out.println(XLTestData.get("invoiceNumber"));
			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

			//click on Search Invoice
			oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab Out",basetest);

			oGenericUtils.waiForPageLoad(30);

			WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

			invoiceNumberTextValue = invoiceElement.getAttribute("previousvalue");

			if(invoiceNumberTextValue.contains("#"))

				Assert.assertTrue("Invoice Verified ", true);
			else
				Assert.assertFalse("InvoiceNotVerified", false);


			//click on next
			oGenericUtils.clickButton(driver, By.xpath(next),"Next",basetest);


			oGenericUtils.waiForPageLoad(20);
			if(driver.findElements(By.xpath(agencyCommisionOptions)).size()>0){
				basetest.test.log(Status.PASS,"Invoice  Page Verified");
			}else {
				basetest.test.log(Status.FAIL,"Invoice  Page Not Verified ");
			}


			List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			int size =lineAdjustTableRowCount.size();
			ArrayList<Float> beforeRevisedAGComm =  new ArrayList<Float>();
			ArrayList<Float> afterRevisedAGComm =new ArrayList<Float>();
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED AG COMM")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						beforeRevisedAGComm.add(Float.parseFloat(text2));
						break;
					}
				}

				if(beforeRevisedAGComm.get((i-1))==15.0){
					basetest.test.log(Status.PASS, "before Remove commission Revised AGComm is: "+beforeRevisedAGComm.get((i-1)));
				}else{
					basetest.test.log(Status.FAIL, "before Remove  commission Revised AGComm is : "+beforeRevisedAGComm.get((i-1)));
				}


			}

			lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			size =lineAdjustTableRowCount.size();
			ArrayList<Float> beforeRevisedNetAmt =  new ArrayList<Float>();
			ArrayList<Float> aftereRevisedNetAmt =new ArrayList<Float>();
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED NET AMOUNT")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						beforeRevisedNetAmt.add(Float.parseFloat(text2));
						break;
					}
				}

			}



			//click on Adjustment Reason General   agencyCommision
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisionOptions),"Agency Commision",basetest);
			// click on Add agency Commission Exclude Misc lines
			oGenericUtils.clickButton(driver, By.xpath(removeAgencyCommision),"removeAgency Commission ",basetest);

			oGenericUtils.waiForPageLoad(18);

			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED AG COMM")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						afterRevisedAGComm.add(Float.parseFloat(text2));
						break;
					}
				}

				if(afterRevisedAGComm.get((i-1))==0.0){
					basetest.test.log(Status.PASS, "after Remove commission Revised AGComm is: "+afterRevisedAGComm.get((i-1)));
				}else{
					basetest.test.log(Status.FAIL, "after Remove commission Revised AGComm is : "+afterRevisedAGComm.get((i-1)));
				}


			}

			lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			size =lineAdjustTableRowCount.size();


			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED NET AMOUNT")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");
						String text2 = text.replace(",", "");
						aftereRevisedNetAmt.add(Float.parseFloat(text2));
						break;

					}
				}
			}

			//click on next
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisNextBtn),"Next",basetest);


			oGenericUtils.waiForPageLoad(6);
			//Enter Comments

			oGenericUtils.setText(driver, Comments, "QA");

			//click on finish
			oGenericUtils.clickButton(driver, By.xpath(finish),"finish",basetest);


			//click on Casenumber

			String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
			}
			oGenericUtils.waiForPageLoad(3);
			oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);
			oGenericUtils.waiForPageLoad(8);
			caseValidations.getStatus(driver, basetest);
			caseValidations.getprocessStatusText(driver, basetest);

			double invTotAmt = caseValidations.getInvoiceTotalAmt(driver, basetest);
			double adjustmentAmt = caseValidations.getAdjustmentAmtt(driver, basetest);
			double invoiceRemAmt = caseValidations.getInvoiceRemaningAmtt(driver, basetest);

			double invTotalAndAdjustAmt =invTotAmt-adjustmentAmt;

			if(invTotalAndAdjustAmt==invoiceRemAmt){
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}else{
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}

			//page scroll
			/*JavascriptExecutor js = (JavascriptExecutor) driver;
		 		js.executeScript("window.scrollBy(0,600)");
			 */
			//oGenericUtils.clickButton(driver, By.xpath(invoiceCMS), "invoice CMS", basetest);
			oGenericUtils.waiForPageLoad(8);

			for(float tempGrossAmt: aftereRevisedNetAmt){
				grossAmount = grossAmount+tempGrossAmt;
			}

			for(float tempNetDue :beforeRevisedNetAmt){
				netDue = netDue+tempNetDue;
			}

			grossValueFormated = String.valueOf(grossAmount);
			grossValueFormated = "$"+grossValueFormated.substring(0,1)+","+grossValueFormated.substring(1,grossValueFormated.length());

			netValueFormated = String.valueOf(netDue);
			netValueFormated = "$"+netValueFormated.substring(0,1)+","+netValueFormated.substring(1,netValueFormated.length());

		}catch (Exception e) {
			System.out.println(e);
		}

		return grossValueFormated+";"+adjmentAmtValue+";"+netValueFormated;	
	}

	public void Radio_Click(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) {

		String invoiceNumberTextValue="";
		float grossAmount =0;
		float netDue =0;
		String adjmentAmtValue =  "";
		String grossValueFormated="";
		String netValueFormated="";

		try {
			oGenericUtils.clickButton(driver, By.xpath(adjustment)," Adjustment",basetest);

			//wait untaile page load
			oGenericUtils.WaitUntilElement(driver, By.xpath(adjustmentPage), "Adjustment Page", basetest);

			oGenericUtils.waiForPageLoad(5);
			//click on select Adjustment Type
			oGenericUtils.clickButton(driver, By.xpath(selectAdjArrow)," Select Adjustment Type",basetest);

			//click on Add or remove agency commision
			oGenericUtils.waiForPageLoad(5);
			String typeofAdjustment = "//div[contains(text(),'"+XLTestData.get("Scenario")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),XLTestData.get("Scenario"),basetest);

			//tabing 
			oGenericUtils.waiForPageLoad(6);
			//clicking on OK Alert
			oGenericUtils.isAlertPresent(driver);
			oGenericUtils.waiForPageLoad(6);
			//Agencyselection
			//oGenericUtils.clickButton(driver, By.xpath(adjustmentReasonGeneral),"Adjustment Reason General",basetest);
			String typeofagency = "//div[contains(text(),'"+XLTestData.get("Agencyselection")+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofagency),XLTestData.get("Agencyselection"),basetest);

			//click on next
			oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);

			oGenericUtils.waiForPageLoad(6);
			String invoicePage = "//span[@id='invnum_text_fs_lbl']/a[contains(text(),'Invoice Number')]";

			if(driver.findElements(By.xpath(invoicePage)).size()>0){
				basetest.test.log(Status.PASS,"Invoice  Page Verified ");
			}else {
				basetest.test.log(Status.FAIL,"Invoice  Page Not Verified");
			}


			oGenericUtils.SetVal(driver, By.xpath(invoiceNumber), XLTestData.get("invoiceNumber").toString(),"Invoice Text Box",basetest);

			//click on Search Invoice
			oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Tab out",basetest);

			oGenericUtils.waiForPageLoad(30);

			WebElement invoiceElement = driver.findElement(By.xpath(invoiceNumberText));

			invoiceNumberTextValue = invoiceElement.getAttribute("previousvalue");

			if(invoiceNumberTextValue.contains("#"))

				Assert.assertTrue("Invoice Verified ", true);
			else
				Assert.assertFalse("InvoiceNotVerified", false);


			//click on next
			oGenericUtils.clickButton(driver, By.xpath(next)," next",basetest);


			oGenericUtils.waiForPageLoad(20);
			if(driver.findElements(By.xpath(agencyCommisionOptions)).size()>0){
				basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page Verified successfully"+"'</span>");
			}else {
			}

			List<WebElement> lineAdjustTableRowCount=driver.findElements(By.xpath(lineAdjustTable));

			int size =lineAdjustTableRowCount.size();
			ArrayList<Float> beforeRevisedAGComm =  new ArrayList<Float>();
			ArrayList<Float> afterRevisedAGComm =new ArrayList<Float>();
			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED AG COMM")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						beforeRevisedAGComm.add(Float.parseFloat(text2));
						break;
					}
				}

				if(beforeRevisedAGComm.get((i-1))==0.0){
					basetest.test.log(Status.PASS, "before Add commission Revised AGComm is: "+beforeRevisedAGComm.get((i-1)));
				}else{
					basetest.test.log(Status.FAIL, "before Add commission Revised AGComm is : "+beforeRevisedAGComm.get((i-1)));
				}


			}



			//click on Adjustment Reason General   agencyCommision
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisionOptions)," Agency Commission",basetest);
			// click on Add agency Commission Exclude Misc lines
			oGenericUtils.clickButton(driver, By.xpath(AddAgencyCommission)," AddAgency Commission ",basetest);

			oGenericUtils.waiForPageLoad(18);

			for(int i=1; i<=size ; i++){
				List<WebElement> tableHead = driver.findElements(By.xpath("//table[@id='invoicelines_splits']/tbody/tr/td/div"));
				int headSize = tableHead.size();
				for(int j=1;j<=headSize;j++){
					String headText = driver.findElement(By.xpath("(//table[@id='invoicelines_splits']/tbody/tr/td/div)["+j+"]")).getText().trim();
					if(headText.contains("REVISED AG COMM")){
						String text = oGenericUtils.getTextOfElement(driver, "//table[@id='invoicelines_splits']/tbody/tr[contains(@id,'invoicelines_row_"+i+"')]/td["+j+"]");						
						String text2 = text.replace(",","");
						afterRevisedAGComm.add(Float.parseFloat(text2));
						break;
					}
				}

				if(afterRevisedAGComm.get((i-1))==15.0){
					basetest.test.log(Status.PASS, "after Add commission Revised AGComm is: "+afterRevisedAGComm.get((i-1)));
				}else{
					basetest.test.log(Status.FAIL, "after add commission Revised AGComm is : "+afterRevisedAGComm.get((i-1)));
				}


			}


			//click on next
			oGenericUtils.clickButton(driver, By.xpath(agencyCommisNextBtn)," next",basetest);


			oGenericUtils.waiForPageLoad(6);
			//Enter Comments

			oGenericUtils.setText(driver, Comments, "agency Commision");

			//click on finish
			oGenericUtils.clickButton(driver, By.xpath(finish)," finish",basetest);


			//click on Casenumber

			String caseNumberText =  oGenericUtils.getTextOfElement(driver, caseNumber);
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number :"+caseNumberText);
			}
			oGenericUtils.waiForPageLoad(3);
			oGenericUtils.clickButton(driver, By.xpath(goToButton), "Go To Case", basetest);
			oGenericUtils.waiForPageLoad(8);
			caseValidations.getStatus(driver, basetest);
			caseValidations.getprocessStatusText(driver, basetest);

			double invTotAmt = caseValidations.getInvoiceTotalAmt(driver, basetest);
			double adjustmentAmt = caseValidations.getAdjustmentAmtt(driver, basetest);
			double invoiceRemAmt = caseValidations.getInvoiceRemaningAmtt(driver, basetest);

			double invTotalAndAdjustAmt =invTotAmt-adjustmentAmt;

			if(invTotalAndAdjustAmt==invoiceRemAmt){
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}else{
				basetest.test.log(Status.INFO, "Invoice total amount("+invTotAmt+") - Adjustment amount("+adjustmentAmt+") equal to Invoice remaining amount("+invoiceRemAmt+")");
			}

			//page scroll
			/*JavascriptExecutor js = (JavascriptExecutor) driver;
		 		js.executeScript("window.scrollBy(0,600)");
			 */
			//oGenericUtils.clickButton(driver, By.xpath(invoiceCMS), "invoice CMS", basetest);
			oGenericUtils.waiForPageLoad(8);

		}catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

	public void selectMenu(WebDriver driver, String firstElement,String secondElement,String thirdElement, BaseTest basetest) {
		try {	

			//webdriver waiting
			oGenericUtils.waiForPageLoad(5);

			//Move to Transactions
			oGenericUtils.navigateMouseToElement(driver, By.xpath("//li[@data-title='"+firstElement+"']"),firstElement,basetest);

			//Move to Billing
			oGenericUtils.navigateMouseToElement(driver, By.xpath("(//li[@data-title='"+secondElement+"'])[1]"),secondElement,basetest);

			//Move to Adjustment / Special Billing
			oGenericUtils.navigateMouseToElement(driver, By.xpath("//li[@data-title='"+thirdElement+"']"),thirdElement,basetest);

			//click on Adjustment / Special Billing
			oGenericUtils.clickButton(driver, By.xpath("//li[@data-title='"+thirdElement+"']"),thirdElement,basetest);

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
		}

	}

}
