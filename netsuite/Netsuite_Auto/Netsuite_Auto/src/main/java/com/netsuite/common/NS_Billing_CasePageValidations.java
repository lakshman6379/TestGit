package com.netsuite.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Driver;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

public class NS_Billing_CasePageValidations {
	Generic gen=new Generic();

	//Case page locators
	String processStatus = "(//span[@id='custevent_process_status_fs_lbl_uir_label']/following::span)[1]";
	String statusText = "(//span[@id='custevent_case_approval_fs_lbl_uir_label']/following::span/span)[1]";
	String adjustmentType = "(//span[@id='custevent_ihm_adjustmenttype_fs_lbl']/following::span)[1]";
	String customer = "(//span[@id='company_fs_lbl_uir_label']/following::span)[1]";
	String eMail = "(//span[@id='email_fs_lbl_uir_label']/following::span)[1]";
	String invoiceTotalAmt = "//span[@id='custevent_inv_total_amt_fs_lbl_uir_label']/../span[2]";
	String invoiceRemaningAmt = "//span[@id='custevent_ihm_invremainingamount_fs_lbl_uir_label']/../span[2]";
	String adjstAmt = "//span[@id='custevent_ihm_adjamount_fs_lbl']/../span[2]";
	String invoicesOrCMS = "(//a[text()='I'])[1]";
	String creditMemoType = "//td[ starts-with(@class,'listtext') and text()='Credit Memo']"; 
	String creditButn = "//input[@id='credit']";
	String totalApplyAmt = "//a[@id='itemtxt']/span[2]";
	String statusInCasePage = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']";

	String amountRemaining = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']/preceding::td[1]";
	String amount = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']/preceding::td[2]";
	String name = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']/preceding::td[3]";
	String type = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']/preceding::td[5]";
	String docnumber = "//td[ starts-with(@class,'listtext') and text()='Fully Applied']/preceding::td[4]";
	//checkBox
	String autoApprovedCheckBox = "//span[@id='custevent_case_auto_approved_fs_lbl']/../../span/span/img";
	String splitByStationCheckBox = "//span[@id='custevent_spit_by_station_fs_lbl_uir_label']/../span/span/img";
	String splitByProductTypeCheckBox ="//span[@id='custevent_split_by_product_fs']/img";
	String splitByLineTypeCheckBox  ="//span[@id='custevent_split_by_line_type_fs']/img";
	String splitByMarketTypeCheckBox = "//span[@id='custevent_split_by_market_fs']/img";
	String splitByISCICheckBox = "//span[@id='custevent_split_by_isci_fs']/img";

	String caseAdjID = "//input[@name='CUSTBODY_IHM_CASEADJID_display']";
	String invoiceCMsTable = "//table[starts-with(@id,'customsublist')]/tbody/tr";
	String invoiceNoLink ="(//span[@id='custevent_ihm_case_inv_no_fs_lbl_uir_label']/following::span)[1]";
	String invoiceCMS 	= 	"//span[@class='inputreadonly']/a[contains(text(),'Invoice')]";
	String approve_btn  = "(//input[@type='button' and @value='Approve'])[1]";

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

	//Issue credit to client case page validations
	String invoiceRemainingamount= "//span[@id='custevent_ihm_adjamount_fs_lbl']/following::span[4]";
	String amountdue_InSummary   = "//span[@id='amountremainingtotalbox_fs_lbl_uir_label']/following::span[1]";
	String amount_Due_invoice ="//span[@id='amountremainingtotalbox_fs_lbl']/following::span[1]";
	String status ="//div[@class='uir-record-status']";
	String invoice_Total  = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[1]";
    String invoice_Adjsutment = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[4]";
	String invoice_remaining = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[7]";
	
	String InvoiceType = "//td[ starts-with(@class,'listtext') and text()='Invoice']";
	String StatusInCasePage = "//td[ starts-with(@class,'listtext') and text()='Open']";
	String CaseNumber = "//span[@class='uir-field inputreadonly'][1]";


	public void casePageValidations(WebDriver driver, String splitingType, String invoiceNo, String caseNumberText,
			BaseTest basetest) {
		String splitTypeCheckBox = "";
		if (splitingType.contains("ISCI")) {
			splitTypeCheckBox = splitByISCICheckBox;

		} else if (splitingType.contains("MarketType")) {
			splitTypeCheckBox = splitByMarketTypeCheckBox;

		} else if (splitingType.contains("ProductType")) {
			splitTypeCheckBox = splitByProductTypeCheckBox;

		} else if (splitingType.contains("SplitStation")) {
			splitTypeCheckBox = splitByStationCheckBox;

		} else if (splitingType.contains("SpotAndMisc")) {
			splitTypeCheckBox = splitByLineTypeCheckBox;
		}
		try {
			String processStatusText = gen.getTextOfElement(driver, processStatus);
			if (processStatusText.contains("Complete")) {
				basetest.test.log(Status.PASS, "Process status is : <span style='font-weight:bold;color:blue'> "
						+ processStatusText + "</span>");
			} else {
				basetest.test.log(Status.FAIL, "Process status is : <span style='font-weight:bold;color:blue'> "
						+ processStatusText + "</span>");
			}

			String statusTextInUI = gen.getTextOfElement(driver, statusText);
			if (statusTextInUI.contains("Approved")) {
				basetest.test.log(Status.PASS, "Status displayed as : <span style='font-weight:bold;color:blue'> "
						+ statusTextInUI + "</span>");
			} else {
				basetest.test.log(Status.FAIL, "Status displayed as :<span style='font-weight:bold;color:blue'> "
						+ statusTextInUI + "</span>");
			}

			//
			String adjustmentTypeText = gen.getTextOfElement(driver, adjustmentType);
			if (adjustmentTypeText.contains("Split Invoice")) {
				basetest.test.log(Status.PASS,
						"Adjustment Type displayed as : <span style='font-weight:bold;color:blue'>" + adjustmentTypeText
								+ "</span>");
			} else {
				basetest.test.log(Status.FAIL,
						"Adjustment Type displayed as : <span style='font-weight:bold;color:blue'>" + adjustmentTypeText
								+ "</span>");
			}

			String invoiceNumber = gen.getTextOfElement(driver, invoiceNoLink);
			if (invoiceNumber.length() > 0) {
				basetest.test.log(Status.PASS,
						"Invoice number displayed as : <span style='font-weight:bold;color:blue'>" + invoiceNumber
								+ "</span>");
			} else {
				basetest.test.log(Status.FAIL,
						"Invoice number displayed as : <span style='font-weight:bold;color:blue'>" + invoiceNumber
								+ "</span>");
			}

			String customerText = gen.getTextOfElement(driver, customer);
			if (customerText.length() > 0) {
				basetest.test.log(Status.PASS, "Customer displayed as : <span style='font-weight:bold;color:blue'>"
						+ customerText + "</span>");
			} else {
				basetest.test.log(Status.FAIL, "Customer is not displayed");
			}

			String emailText = gen.getTextOfElement(driver, eMail);
			if (emailText.length() > 0) {
				basetest.test.log(Status.PASS,
						"Email :<span style='font-weight:bold;color:blue'>" + emailText + "</span> is displayed");
			} else {
				basetest.test.log(Status.FAIL, "Email is not displayed");
			}

			String invoiceTotalAmtText = gen.getTextOfElement(driver, invoiceTotalAmt);

			String invoiceRemainingAmtText = gen.getTextOfElement(driver, invoiceRemaningAmt);
			if (invoiceTotalAmtText.contains(invoiceRemainingAmtText)) {
				basetest.test.log(Status.PASS, " Invoice total amount(" + invoiceTotalAmtText
						+ ") and Invoice remaining amount(" + invoiceRemainingAmtText + ") are same ");
			} else {
				basetest.test.log(Status.FAIL, " Invoice total amount(" + invoiceTotalAmtText
						+ ") and Invoice remaining amount(" + invoiceRemainingAmtText + ") are not same ");
			}
			WebElement autoApproviedElement = driver.findElement(By.xpath(autoApprovedCheckBox));
			WebElement splitByISCIElement = driver.findElement(By.xpath(splitTypeCheckBox));

			String checkboxAutoApp = autoApproviedElement.getAttribute("alt").trim();

			String checkboxSplitByStation = splitByISCIElement.getAttribute("alt").trim();

			if (checkboxAutoApp.contains("Checked")) {
				basetest.test.log(Status.PASS, "Auto Approved check Box is selected");
			} else {
				basetest.test.log(Status.FAIL, "Auto Approved check Box is not selected");
			}
			if (checkboxSplitByStation.contains("Checked")) {
				basetest.test.log(Status.PASS, "Split By Product Type check Box is selected");
			} else {
				basetest.test.log(Status.FAIL, "Split By Product Type Box is not selected");
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1500)");

			gen.clickButton(driver, By.xpath(invoicesOrCMS), " Invoice/CMs", basetest);

			Thread.sleep(4000);
			// enter case ID click on
			gen.SetVal(driver, By.xpath(caseAdjID), caseNumberText, "Case number entered", basetest);
			driver.findElement(By.xpath(caseAdjID)).sendKeys(Keys.ENTER);
			Thread.sleep(3000);

			if (!(driver
					.findElements(
							By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']"))
					.size() > 0)) {
				String creditMemoTypeTxt = gen.getTextOfElement(driver, creditMemoType);
				if (creditMemoTypeTxt.contains("Credit Memo")) {
					basetest.test.log(Status.PASS, "Type :<span style='font-weight:bold;color:blue'>"
							+ creditMemoTypeTxt + "</span> is displayed");
				} else {
					basetest.test.log(Status.FAIL, "Type :<span style='font-weight:bold;color:blue'>"
							+ creditMemoTypeTxt + "</span> is displayed");
				}

				String statusTxt = gen.getTextOfElement(driver, statusInCasePage);
				if (statusTxt.contains("Fully Applied")) {
					basetest.test.log(Status.PASS,
							"Status :  <span style='font-weight:bold;color:blue'>" + statusTxt + "</span> is displayed");
				} else {
					basetest.test.log(Status.FAIL,
							"Status :  <span style='font-weight:bold;color:blue'>" + statusTxt + " </span>is displayed");
				}

				List<WebElement> tableInvoiceCMS = driver.findElements(By.xpath(invoiceCMsTable));

				String creditAmountStr1 = "";
				String creditAmountStr2 = "";
				for (int i = 1; i <= tableInvoiceCMS.size(); i++) {
					if (i == 1) {

						String type = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[3]");
						String docNumber = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[4]");
						String name = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[5]");
						String amount = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
						String amountRemaining = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[7]");
						String status = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[8]");
						;
						if ((type.length() > 0) && (docNumber.length() > 0) && (name.length() > 0)
								&& (amount.length() > 0) && (amountRemaining.length() > 0) && (status.length() > 0)) {
							if (type.contains("Credit Memo")) {
								basetest.test.log(Status.PASS,
										"Type is displayed as :<span style='font-weight:bold;color:green'>  " + type
												+ "</span>");
							} else {
								basetest.test.log(Status.FAIL,
										"Type is displayed as :<span style='font-weight:bold;color:red'>  " + type
												+ "</span>");
							}
							basetest.test.log(Status.PASS, "Document number is displayed as :" + docNumber);
							basetest.test.log(Status.PASS, "Name is displayed as :" + name);
							

						} else {
							basetest.test.log(Status.INFO, "Type is not displayed in Case page[Invoices/CMs] ");
						}
						creditAmountStr1 = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
						if (creditAmountStr1.length() > 0) {

						}
					}
					if (i > 1) {
						creditAmountStr2 = gen.getTextOfElement(driver,
								"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");

					}
				}

			} else {
				String text = driver
						.findElement(
								By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']"))
						.getText();
				basetest.test.log(Status.FAIL, "After entering the case adjustment ID :" + text);
			}
			// click on invoice number

			js.executeScript("window.scrollBy(0,-1500)");
			Thread.sleep(3000);

			gen.clickButton(driver, By.xpath(invoiceNoLink), "invoice number", basetest);
			Thread.sleep(6000);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void clickInvoiceNumber(WebDriver driver,BaseTest basetest,HashMap<String,String> XLTestData) throws InterruptedException{

		gen.clickButton(driver, By.xpath(invoiceCMS), "Invoice number - "+XLTestData.get("invoiceNumber").toString(), basetest);
		gen.waiForPageLoad(8);
	}

	public void validateAmount_due(WebDriver driver,BaseTest basetest,float Creditamount_Float)
	{
		//Validating Amount Due in invoice page
		String amount_Due_invoice_ui = driver.findElement(By.xpath(amount_Due_invoice)).getText();

		String amount_due_replace = amount_Due_invoice_ui.replace(",", "");

		Float amount_due_invoice_float = Float.parseFloat(amount_due_replace);

		if(amount_due_invoice_float.equals(Creditamount_Float))
		{
			basetest.test.log(Status.PASS, "Adjustment amount: <span style='font-weight:bold;color:green'> "+Creditamount_Float+" is matched with Invoice Amount Due: <span style='font-weight:bold;color:green'> "+amount_due_invoice_float+"</span>");

		}
		else{
			basetest.test.log(Status.FAIL, "Adjustment amount is matched with Invoice Amount Due 'Not matched'");
		}
		
		
		String status_ui = driver.findElement(By.xpath(status)).getText();
		System.out.println(status_ui);
		if(status_ui.equals("OPEN"))
		{
			basetest.test.log(Status.PASS, "Invoice status is displayed as: <span style='font-weight:bold;color:green'> "+status_ui+"</span>");
			
		}
		else{
			basetest.test.log(Status.FAIL, "Invoice status is  not displayed as:'OPEN'");
		}
		
	}
	
	public void before_approval_amount(WebDriver driver,BaseTest basetest)
	{
		//invoice_Total
		String invoice_tot = driver.findElement(By.xpath(invoice_Total)).getText();
		basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:green'>'"+" Before approval  INVOICE TOTAL AMOUNT :  "+invoice_tot+"'</span>");
		//invoice_Adjsutment
		String invoice_adjust = driver.findElement(By.xpath(invoice_Adjsutment)).getText();
		basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:green'>'"+" Before approval  ADJUSTMENT AMOUNT :  "+invoice_adjust+"'</span>");
		//invoice_remaining
		String invoice_remain = driver.findElement(By.xpath(invoice_remaining)).getText();
		basetest.test.log(Status.INFO,"<span style='font-weight:bold;color:green'>'"+" Before approval : INVOICE REMAINING AMOUNT :  "+invoice_remain+"'</span>");
	}

	public void approvalWorkFlowValidations(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException{
		WebElement Element = driver.findElement(By.xpath(autoApproval));

		if(Element.getText().contains("Approved"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Auto Approved"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  is Not Auto Approved"+"'</span>");
		}


		WebElement adjustmentamount = driver.findElement(By.xpath(adjustmentAmount));

		Thread.sleep(3000);

		if(adjustmentamount.getText().equals(XLTestData.get("linecreditamount").toString()))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Adjustment Amount is Displayed Cottectly"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Adjustment Amount is Displayed Incorrectly"+"'</span>");
		}

		String invoiceTotalamount = "//span[@id='custevent_inv_total_amt_fs_lbl']/following::span[1]";
		WebElement invoiceamount_in_page = driver.findElement(By.xpath(invoiceTotalamount));

		String invoiceamt = driver.findElement(By.xpath(invoiceAmount)).getAttribute("value");
		if(invoiceamount_in_page.getText().equals(invoiceamt))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice Total  Amount is Displayed Cottectly"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice Total  Amount is Displayed Incorrectly"+"'</span>");
		}

	}

	public void autoapprovalWorkFlowValidations(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{

		WebElement Element = driver.findElement(By.xpath(autoApproval));
		String status = Element.getText();

		if(Element.getText().contains("Approved"))
		{
			basetest.test.log(Status.PASS, "STATUS (APPROVAL) is displayed as: "+status);
		}else {
			basetest.test.log(Status.FAIL,"STATUS (APPROVAL) is displayed as: "+status);
		}
	}

	public void click_Approve(WebDriver driver,BaseTest basetest){
		gen.clickButton(driver, By.xpath(approve_btn), "'Approve' button", basetest);
		gen.waiForPageLoad(5);
	}

	public float verifyStatusInInvoicesAndCMs(WebDriver driver,BaseTest basetest,String caseNumberText)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1500)");
		gen.clickButton(driver, By.xpath(invoicesOrCMS),"Click on Invoice/CMs",basetest);
		gen.waiForPageLoad(3);
		gen.SetVal(driver, By.xpath(caseAdjID), caseNumberText,"Case number entered",basetest);
		driver.findElement(By.xpath(caseAdjID)).sendKeys(Keys.ENTER);
		gen.waiForPageLoad(8);
		
		

		String statusTxt = gen.getTextOfElement(driver, statusInCasePage);
		if (statusTxt.contains("Fully Applied")) {
			basetest.test.log(Status.PASS, "STATUS is displayed as :  <span style='font-weight:bold;color:green'>" + statusTxt + "</span>");
		} else {
			basetest.test.log(Status.FAIL, "STATUS  is displayed as :  <span style='font-weight:bold;color:red'>" + statusTxt + " </span>");
		}

		String amountremainingTxt = gen.getTextOfElement(driver, amountRemaining);

		if(amountremainingTxt.contains("0.00"))
		{
			basetest.test.log(Status.PASS, "AMOUNT REMAINING displayed as  :<span style='font-weight:bold;color:green'>" + amountremainingTxt + "</span>");
		} else {
			basetest.test.log(Status.FAIL, "AMOUNT REMAINING displayed as  :<span style='font-weight:bold;color:red'>" + statusTxt + " </span>");
		}

		String  amount_ui = gen.getTextOfElement(driver, amount);
		basetest.test.log(Status.PASS, "Amount  displayed as :<span style='font-weight:bold;color:green'>" + amount_ui + "</span>");

		String amount_str = amount_ui.substring(1,amount_ui.length());

		String amount_replace = amount_str.replace(",", "");

		Float amount_float = Float.parseFloat(amount_replace);


		String name_ui = gen.getTextOfElement(driver, name);
		basetest.test.log(Status.PASS, "NAME  displayed as :<span style='font-weight:bold;color:green'>" + name_ui + "</span>");

		String credit_type = gen.getTextOfElement(driver, type);
		basetest.test.log(Status.PASS, "TYPE  displayed as :<span style='font-weight:bold;color:green'>" + credit_type + "</span>");

		String docnumber_ui = gen.getTextOfElement(driver, docnumber);
		basetest.test.log(Status.PASS, "Document number  displayed as :<span style='font-weight:bold;color:green'>" +docnumber_ui + "</span>");

		return amount_float;

	}
	public void validate_Amountdue(WebDriver driver,HashMap<String,String> XLTestData,BaseTest basetest) throws InterruptedException
	{
		//Get Adjustment amount
		WebElement adjustmentamount = driver.findElement(By.xpath(adjustmentAmount));
		String adjustmentamount_text = adjustmentamount.getText();
		String adjustmentamount_replace = adjustmentamount_text.replace(",", "");
		Float adjustmentamount_float = Float.parseFloat(adjustmentamount_replace);

		// Get invoiceRemainingamount
		WebElement invoiceRemainingAmt = driver.findElement(By.xpath(invoiceRemainingamount));
		String invoiceRemainingAmt_text = invoiceRemainingAmt.getText();
		String invoiceRemainingAmt_replace = invoiceRemainingAmt_text.replace(",", "");
		Float invoiceremainingAmount_float = Float.parseFloat(invoiceRemainingAmt_replace);
		//click on Invoice Number
		clickInvoiceNumber( driver, basetest,XLTestData); 
		gen.waiForPageLoad(5);

		//Get Amount due
		WebElement amountdue_insummary = driver.findElement(By.xpath(amountdue_InSummary));
		String amountdue_insummary_text = amountdue_insummary.getText();
		String amountdue_insummary_replace = amountdue_insummary_text.replace(",", "");
		Float amountdue_insummary_float = Float.parseFloat(amountdue_insummary_replace);

		//truncating last decimal value in Amountdue_insummary
		BigDecimal fd = new BigDecimal(amountdue_insummary_float);
		BigDecimal cutted = fd.setScale(1, RoundingMode.DOWN);
		amountdue_insummary_float = cutted.floatValue();

		//calculating Remaing amount after Adjusting amount
		Float remainigamount = invoiceremainingAmount_float-adjustmentamount_float;

		BigDecimal fd2 = new BigDecimal(remainigamount);
		BigDecimal cutted2 = fd.setScale(1, RoundingMode.DOWN);
		remainigamount = cutted.floatValue();


		if(amountdue_insummary_float.equals(remainigamount))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Amount due and Invoice amount after Adjustment matched"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Amount due and Invoice amount after Adjustment Not matched"+"'</span>");
		}


	}


	public void getStatus(WebDriver driver,BaseTest basetest){
		String statusTextInUI = gen.getTextOfElement(driver, statusText);
		if(statusTextInUI.contains("Approved")){
			basetest.test.log(Status.PASS, "Status displayed as : <span style='font-weight:bold;color:blue'> "+statusTextInUI+"</span>");
		}else{
			basetest.test.log(Status.FAIL, "Status displayed as :<span style='font-weight:bold;color:blue'> "+statusTextInUI+"</span>");
		}
	}

	public void getprocessStatusText(WebDriver driver,BaseTest basetest){
		String processStatusText = gen.getTextOfElement(driver, processStatus);
		if(processStatusText.contains("Complete")){
			basetest.test.log(Status.PASS, "PROCESS STATUS is displayed as:  "+processStatusText);
		}else if(processStatusText.length()>0){
			basetest.test.log(Status.FAIL, "PROCESS STATUS is displayed as:  "+processStatusText);
		}else{
			basetest.test.log(Status.FAIL, "Process status is not displayed");
		}
	}

	public double getInvoiceTotalAmt(WebDriver driver,BaseTest basetest){
		double  invoiceTotalAmtTextDouble=0.0;
		try{

			if(driver.findElements(By.xpath(invoiceTotalAmt)).size()>0){
				String invoiceTotalAmtText = gen.getTextOfElement(driver,invoiceTotalAmt).trim();
				if(invoiceTotalAmtText.length()>0){
					Double.valueOf(invoiceTotalAmtText);
					basetest.test.log(Status.INFO, "Invoice Total Amount :"+invoiceTotalAmtText);
				}else{
					basetest.test.log(Status.INFO, "Invoice Total Amount is not displayed");
				}
			}else{
				basetest.test.log(Status.FAIL, "Invoice total amount is not present");
			}
		}catch(Exception e){
			System.out.println("Exception :"+e);
		}
		return invoiceTotalAmtTextDouble;
	}

	public double getInvoiceRemaningAmtt(WebDriver driver, BaseTest basetest) {
		double invoiceRemAmtTextDouble = 0.0;
		try {

			if (driver.findElements(By.xpath(invoiceRemaningAmt)).size() > 0) {
				String invoiceRemAmtText = gen.getTextOfElement(driver, invoiceRemaningAmt).trim();
				if(invoiceRemAmtText.length()>0){
					Double.valueOf(invoiceRemAmtText);
					basetest.test.log(Status.INFO, "Invoice  Remaining Amount : "+invoiceRemAmtText);
				}else{
					basetest.test.log(Status.INFO, "Invoice  Remaining Amount is not displayed");
				}

			} else {
				basetest.test.log(Status.FAIL, "Invoice total amount is not present");
			}
		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return invoiceRemAmtTextDouble;
	}
	public double getAdjustmentAmtt(WebDriver driver, BaseTest basetest) {
		double adjustmentDouble = 0.0;
		try {

			if (driver.findElements(By.xpath(adjstAmt)).size() > 0) {
				String adjAmtText = gen.getTextOfElement(driver, adjstAmt).trim();
				if(adjAmtText.length()>0){
					Double.valueOf(adjAmtText);
					basetest.test.log(Status.INFO, "Adjustment Amount :"+adjAmtText);
				}else{
					basetest.test.log(Status.INFO, "Adjustment Amount is not displayed");
				}

			} else {
				basetest.test.log(Status.FAIL, "Invoice total amount is not present");
			}
		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return adjustmentDouble;
	}

	// =====================================================================================================================================
		public void agencyCommision_CasePageValidation(WebDriver driver, BaseTest basetest)
				throws InterruptedException {

			try {
				String caseNumber = gen.getTextOfElement(driver, CaseNumber).trim();
				/*
				 * String processStatusText = gen.getTextOfElement(driver, processStatus); if
				 * (processStatusText.contains("Complete")) { basetest.test.log(Status.PASS,
				 * "Process status is : <span style='font-weight:bold;color:blue'> " +
				 * processStatusText + "</span>"); } else { basetest.test.log(Status.FAIL,
				 * "Process status is : <span style='font-weight:bold;color:blue'> " +
				 * processStatusText + "</span>"); }
				 * 
				 * String statusTextInUI = gen.getTextOfElement(driver, statusText); if
				 * (statusTextInUI.contains("Approved")) { basetest.test.log(Status.PASS,
				 * "Status displayed as : <span style='font-weight:bold;color:blue'> " +
				 * statusTextInUI + "</span>"); } else { basetest.test.log(Status.FAIL,
				 * "Status displayed as :<span style='font-weight:bold;color:blue'> " +
				 * statusTextInUI + "</span>"); }
				 */
				//

				String invoiceNumber = gen.getTextOfElement(driver, invoiceNoLink);
				if (invoiceNumber.length() > 0) {
					basetest.test.log(Status.PASS,
							"Invoice number displayed as : <span style='font-weight:bold;color:blue'>" + invoiceNumber
									+ "</span>");
				} else {
					basetest.test.log(Status.FAIL,
							"Invoice number displayed as : <span style='font-weight:bold;color:blue'>" + invoiceNumber
									+ "</span>");
				}

				String customerText = gen.getTextOfElement(driver, customer);
				if (customerText.length() > 0) {
					basetest.test.log(Status.PASS, "Customer displayed as : <span style='font-weight:bold;color:blue'>"
							+ customerText + "</span>");
				} else {
					basetest.test.log(Status.FAIL, "Customer is not displayed");
				}

				String emailText = gen.getTextOfElement(driver, eMail);
				if (emailText.length() > 0) {
					basetest.test.log(Status.PASS,
							"Email is displayed as:  <span style='font-weight:bold;color:blue'>" + emailText + "</span> ");
				} else {
					basetest.test.log(Status.FAIL, "Email is not displayed");
				}

				String invoiceTotalAmtText = gen.getTextOfElement(driver, invoiceTotalAmt);

				String invoiceRemainingAmtText = gen.getTextOfElement(driver, invoiceRemaningAmt);
				if (invoiceTotalAmtText.contains(invoiceRemainingAmtText)) {
					basetest.test.log(Status.PASS, " Invoice total amount(" + invoiceTotalAmtText
							+ ") and Invoice remaining amount(" + invoiceRemainingAmtText + ") are equal ");
				} else {
					basetest.test.log(Status.FAIL, " Invoice total amount(" + invoiceTotalAmtText
							+ ") and Invoice remaining amount(" + invoiceRemainingAmtText + ") are not equal ");
				}
				WebElement autoApproviedElement = driver.findElement(By.xpath(autoApprovedCheckBox));

				String checkboxAutoApp = autoApproviedElement.getAttribute("alt").trim();

				if (checkboxAutoApp.contains("Checked")) {
					basetest.test.log(Status.PASS, "Auto Approved check Box is selected");
				} else {
					basetest.test.log(Status.FAIL, "Auto Approved check Box is not selected");
				}

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,1500)");

				gen.waiForPageLoad(4);

				gen.clickButton(driver, By.xpath(invoicesOrCMS), " Invoice/CMs", basetest);

				Thread.sleep(4000);
				// enter case ID click on
				gen.SetVal(driver, By.xpath(caseAdjID), caseNumber, "Case number entered", basetest);
				driver.findElement(By.xpath(caseAdjID)).sendKeys(Keys.ENTER);
				Thread.sleep(3000);

				if (!(driver
						.findElements(
								By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']"))
						.size() > 0)) {
					/*
					 * String creditMemoTypeTxt = gen.getTextOfElement(driver, creditMemoType); if
					 * (creditMemoTypeTxt.contains("Credit Memo")) { basetest.test.log(Status.PASS,
					 * "Type is displayed as:<span style='font-weight:bold;color:blue'>" +
					 * creditMemoTypeTxt + "</span>"); } else { basetest.test.log(Status.FAIL,
					 * "Type is  displayed as :<span style='font-weight:bold;color:blue'>" +
					 * creditMemoTypeTxt + "</span> "); }
					 */

					String statusTxt = gen.getTextOfElement(driver, statusInCasePage);
					if (statusTxt.contains("Fully Applied")) {
						basetest.test.log(Status.PASS, "Status is displayed as:  <span style='font-weight:bold;color:blue'>"
								+ statusTxt + "</span> ");
					} else {
						basetest.test.log(Status.FAIL, "Status is displayed as :  <span style='font-weight:bold;color:blue'>"
								+ statusTxt + " </span>");
					}
					String invoiceTypeTxt = gen.getTextOfElement(driver, InvoiceType);
					if (invoiceTypeTxt.contains("Invoice")) {
						basetest.test.log(Status.PASS, "Type is displayed as:  <span style='font-weight:bold;color:blue'>"
								+ invoiceTypeTxt + "</span> ");
					} else {
						basetest.test.log(Status.FAIL, "Type is displayed as:  <span style='font-weight:bold;color:blue'>"
								+ invoiceTypeTxt + "</span> ");
					}

					String statusTxt1 = gen.getTextOfElement(driver, StatusInCasePage);
					if (statusTxt1.contains("Open")) {
						basetest.test.log(Status.PASS, "Status is displayed as:  <span style='font-weight:bold;color:blue'>"
								+ statusTxt1 + "</span> ");
					} else {
						basetest.test.log(Status.FAIL, "Status is displayed as:  <span style='font-weight:bold;color:blue'>"
								+ statusTxt1 + " </span>");
					}

					List<WebElement> tableInvoiceCMS = driver.findElements(By.xpath(invoiceCMsTable));

					String creditAmountStr1 = "";
					String creditAmountStr2 = "";
					for (int i = 1; i <= tableInvoiceCMS.size(); i++) {
						if (i == 1) {

							String type = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[3]");
							String docNumber = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[4]");
							String name = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[5]");
							String amount = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
							String amountRemaining = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[7]");
							String status = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[8]");
							;
							if ((type.length() > 0) && (docNumber.length() > 0) && (name.length() > 0)
									&& (amount.length() > 0) && (amountRemaining.length() > 0) && (status.length() > 0)) {
								if (type.contains("Credit Memo")) {
									basetest.test.log(Status.PASS,
											"Type is displayed as : <span style='font-weight:bold;color:green'>  " + type
													+ "</span>");
								} else {
									basetest.test.log(Status.FAIL,
											"Type is displayed as : <span style='font-weight:bold;color:red'>  " + type
													+ "</span>");
								}

								basetest.test.log(Status.PASS, "Status is displayed as: " + status);
								basetest.test.log(Status.PASS, "Document number is displayed as: " + docNumber);
								basetest.test.log(Status.PASS, "Name is displayed as: " + name);
								basetest.test.log(Status.PASS, "Amount Remaining is displayed as: " + amountRemaining);

							} else {
								basetest.test.log(Status.INFO, "Type is not displayed in Case page[Invoices/CMs] ");
							}
							creditAmountStr1 = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
							if (creditAmountStr1.length() > 0) {

							}
						}

						/*
						 * String statusTxt1 = gen.getTextOfElement(driver, StatusInCasePage); if
						 * (statusTxt1.contains("Open")) { basetest.test.log(Status.PASS,
						 * "Status :<span style='font-weight:bold;color:blue'>" + statusTxt1 +
						 * "</span> is displayed"); } else { basetest.test.log(Status.FAIL,
						 * "Status :<span style='font-weight:bold;color:blue'>" + statusTxt1 +
						 * " </span>is displayed"); }
						 */
						if (i > 1) {
							String type = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[3]");
							String docNumber = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[4]");
							String name = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[5]");
							String amount = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
							String amountRemaining = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[7]");
							String status = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[8]");
							;
							if ((type.length() > 0) && (docNumber.length() > 0) && (name.length() > 0)
									&& (amount.length() > 0) && (amountRemaining.length() > 0) && (status.length() > 0)) {
								if (type.contains("Invoice")) {
									basetest.test.log(Status.PASS,
											"Type is displayed as :  <span style='font-weight:bold;color:green'>  " + type
													+ "</span>");
								} else {
									basetest.test.log(Status.FAIL,
											"Type is displayed as :  <span style='font-weight:bold;color:red'>  " + type
													+ "</span>");
								}
								basetest.test.log(Status.PASS, "Status is displayed as :" + status);
								basetest.test.log(Status.PASS, "Document number is displayed as :" + docNumber);
								basetest.test.log(Status.PASS, "Name is displayed as :" + name);
								basetest.test.log(Status.PASS, "Amount Remaining is displayed as :" + amountRemaining);

							} else {
								basetest.test.log(Status.INFO, "Type is not displayed in Case page[Invoices/CMs] ");
							}
							creditAmountStr2 = gen.getTextOfElement(driver,
									"(//table[starts-with(@id,'customsublist')]/tbody/tr)[" + (i) + "]/td[6]");
							if (creditAmountStr2.length() > 0) {

							}

							// creditAmountStr2 = gen.getTextOfElement(driver,
							// "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[6]");

						}
					}

				} else {
					String text = driver
							.findElement(
									By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']"))
							.getText();
					basetest.test.log(Status.FAIL, "After entering the case adjustment ID :" + text);
				}
				// click on invoice number in invoices/cms
				//// table[starts-with(@id,'customsublist')]/tbody/tr[2]/td[4]
				String invoicelink_in_cms = "//table[starts-with(@id,'customsublist')]/tbody/tr[2]/td[4]/a";
				gen.clickButton(driver, By.xpath(invoicelink_in_cms), "Newly generated invoice number", basetest);

				Thread.sleep(4000);
				String tobeprinted = "//span[@id='tobeprinted_fs']/img";
				String verifytobeprinted = driver.findElement(By.xpath(tobeprinted)).getAttribute("alt");
				Thread.sleep(2000);
				 String print_mg_original =  "//span[@id='custbody_ihm_print_mg_original_date_fs']/img"; 
				  String print_mg_original_date =  driver.findElement(By.xpath(print_mg_original)).getAttribute("alt");
				/*
				 * if(verifytobeprinted.equalsIgnoreCase("checked")) {
				 * basetest.test.log(Status.PASS,
				 * " <span style='font-weight:bold;color:green'>To be Printed checkbox under Invoice control is checked</span>"
				 * ); } else { basetest.test.log(Status.FAIL,
				 * " <span style='font-weight:bold;color:red'>To be Printed checkbox under Invoice control is not checked</span>"
				 * ); }
				 */
				String status = driver.findElement(By.xpath("//div[@class='uir-record-status']")).getText();
				System.out.println(status);
				Thread.sleep(4000);

				if (status.equalsIgnoreCase("Open")) {
					basetest.test.log(Status.PASS,
							"Invoice status is displayed as:  <span style='font-weight:bold;color:green'> " + status
									+ "</span>");
				} else {
					basetest.test.log(Status.FAIL, "Invoice status is not displayed as:  'Open'");
				}
				// Ag comm%
				String agencycomm = driver
						.findElement(By.xpath("//span[@id='custbody_ihm_agency_comm_pct_fs_lbl']/following::span[1]"))
						.getText();
				if (agencycomm.contains("15")) {
					basetest.test.log(Status.PASS,
							"Agency Commission is displayed as: <span style='font-weight:bold;color:green'> " + agencycomm
									+ "</span>");
				} else {
					basetest.test.log(Status.FAIL,
							"Agency Commission is  displayed as: <span style='font-weight:bold;color:red'> " + agencycomm
									+ "</span>");
				}

				Thread.sleep(5000);

				// Navigate to previous page
				driver.navigate().back();
				// click on invoice number

				js.executeScript("window.scrollBy(0,-1500)");
				Thread.sleep(3000);

				gen.clickButton(driver, By.xpath(invoiceNoLink), "old invoice number", basetest);
				Thread.sleep(6000);
				String status1 = driver.findElement(By.xpath("//div[@class='uir-record-status']")).getText();
				System.out.println(status1);
				if (status1.equals("PAID IN FULL")) {
					basetest.test.log(Status.PASS,
							"Invoice status is displayed as:  <span style='font-weight:bold;color:green'> " + status1
									+ "</span>");
				} else {
					basetest.test.log(Status.FAIL, "Invoice status is not displayed as: 'Paid in Full'");
				}

				Thread.sleep(3000);
				js.executeScript("window.scrollBy(0,1000)");

				Thread.sleep(6000);
				tobeprinted = "//span[@id='tobeprinted_fs']/img";
				String verifytobeprinted2 = driver.findElement(By.xpath(tobeprinted)).getAttribute("alt");
				if (verifytobeprinted.equalsIgnoreCase(verifytobeprinted2)) {
					basetest.test.log(Status.PASS,
							" <span style='font-weight:bold;color:green'>To be Printed checkbox  Selection  is Matched in the Both Invoices</span>");
				} else {
					basetest.test.log(Status.FAIL,
							" <span style='font-weight:bold;color:red'>To be Printed checkbox  Selection  is  Not Matched in the Both Invoices</span>");
				}

				
				
			      print_mg_original =  "//span[@id='custbody_ihm_print_mg_original_date_fs']/img"; 
				  String print_mg_original_date2 =  driver.findElement(By.xpath(print_mg_original)).getAttribute("alt");
				  if(print_mg_original_date.equalsIgnoreCase(print_mg_original_date2)) {
				  basetest.test.log(Status.PASS, " <span style='font-weight:bold;color:green'>Print MG Original Date Selection is Matched in the Both Invoices</span>"  ); 
				  } 
				  else 
				  { 
					  basetest.test.log(Status.FAIL, " <span style='font-weight:bold;color:red'>Print MG Original Date Selection is not  Matched in the Both Invoices</span>" ); 
					  }
				  Thread.sleep(3000);
					js.executeScript("window.scrollBy(0,-1000)");


			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/**
		 * @Description - case page validation which support for split invoice,Advertise change and Agency change
		 * @param driver
		 * @param splitingType
		 * @param adjustType
		 * @param invoiceNo
		 * @param caseNumberText
		 * @param basetest
		 */
		public void casePageValidations_agencychange(WebDriver driver,String splitingType,String adjustType,String invoiceNo,String caseNumberText,BaseTest basetest) {
			String splitTypeCheckBox="";
			if (splitingType.contains("ISCI")) {			
				splitTypeCheckBox = splitByISCICheckBox;

			} else if (splitingType.contains("MarketType")) {			
				splitTypeCheckBox = splitByMarketTypeCheckBox;

			} else if (splitingType.contains("ProductType")) {			
				splitTypeCheckBox = splitByProductTypeCheckBox;

			} else if (splitingType.contains("SplitStation")) {			
				splitTypeCheckBox = splitByStationCheckBox;

			} else if (splitingType.contains("SpotAndMisc")) {			
				splitTypeCheckBox = splitByLineTypeCheckBox;
			}
			try {
				String processStatusText = gen.getTextOfElement(driver, processStatus);
				if(processStatusText.contains("Complete")){
					basetest.test.log(Status.PASS, "Process status is : <span style='font-weight:bold;color:blue'> "+processStatusText+"</span>");
				}else{
					basetest.test.log(Status.FAIL, "Process status is : <span style='font-weight:bold;color:blue'> "+processStatusText+"</span>");
				}

				String statusTextInUI = gen.getTextOfElement(driver, statusText);
				if(statusTextInUI.contains("Approved")){
					basetest.test.log(Status.PASS, "Status displayed as : <span style='font-weight:bold;color:blue'> "+statusTextInUI+"</span>");
				}else{
					basetest.test.log(Status.FAIL, "Status displayed as :<span style='font-weight:bold;color:blue'> "+statusTextInUI+"</span>");
				}

				//
				String adjustmentTypeText = gen.getTextOfElement(driver, adjustmentType);
				if(adjustmentTypeText.contains(adjustType)){
					basetest.test.log(Status.PASS, "Adjustment Type displayed as : <span style='font-weight:bold;color:blue'>"+adjustmentTypeText+"</span>");
				}else{
					basetest.test.log(Status.FAIL, "Adjustment Type displayed as : <span style='font-weight:bold;color:blue'>"+adjustmentTypeText+"</span>");
				}

				String invoiceNumber = gen.getTextOfElement(driver, invoiceNoLink);
				if(invoiceNumber.length()>0) {
					basetest.test.log(Status.PASS, "Invoice number displayed as : <span style='font-weight:bold;color:blue'>"+invoiceNumber+"</span>");
				}else {
					basetest.test.log(Status.FAIL, "Invoice number displayed as : <span style='font-weight:bold;color:blue'>"+invoiceNumber+"</span>");
				}

				String customerText = gen.getTextOfElement(driver, customer);
				if(customerText.length()>0){
					basetest.test.log(Status.PASS, "Customer displayed as : <span style='font-weight:bold;color:blue'>"+customerText+"</span>");
				}else{
					basetest.test.log(Status.FAIL, "Customer is not displayed");
				}

				String emailText = gen.getTextOfElement(driver, eMail);
				if(emailText.length()>0) {
					basetest.test.log(Status.PASS, "Email :<span style='font-weight:bold;color:blue'>"+emailText+"</span> is displayed");
				}else {
					basetest.test.log(Status.FAIL, "Email is not displayed");
				}

				String invoiceTotalAmtText = gen.getTextOfElement(driver,invoiceTotalAmt);

				String invoiceRemainingAmtText = gen.getTextOfElement(driver,invoiceRemaningAmt);
				if(invoiceTotalAmtText.contains(invoiceRemainingAmtText)) {
					basetest.test.log(Status.PASS, " Invoice total amount("+invoiceTotalAmtText+") and Invoice remaining amount("+invoiceRemainingAmtText+") are same ");
				}else {
					basetest.test.log(Status.FAIL, " Invoice total amount("+invoiceTotalAmtText+") and Invoice remaining amount("+invoiceRemainingAmtText+") are not same ");
				}
				WebElement autoApproviedElement = driver.findElement(By.xpath(autoApprovedCheckBox));
				
				WebElement splitByISCIElement = null;
				if(!splitTypeCheckBox.isEmpty()) {
				 splitByISCIElement = driver.findElement(By.xpath(splitTypeCheckBox));
				 String checkboxSplitByStation = splitByISCIElement.getAttribute("alt").trim();			 
				 if(checkboxSplitByStation.contains("Checked")){
						basetest.test.log(Status.PASS, "Split By Product Type check Box is selected");
					}else{
						basetest.test.log(Status.FAIL, "Split By Product Type Box is not selected");
					}
				}

				String checkboxAutoApp = autoApproviedElement.getAttribute("alt").trim();

				if(checkboxAutoApp.contains("Checked")){
					basetest.test.log(Status.PASS, "Auto Approved check Box is selected");
				}else{
					basetest.test.log(Status.FAIL, "Auto Approved check Box is not selected");
				}
				

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,1500)");

				gen.clickButton(driver, By.xpath(invoicesOrCMS),"Click on Invoice/CMs",basetest);

				Thread.sleep(4000);	
				//enter case ID click on 
				gen.SetVal(driver, By.xpath(caseAdjID), caseNumberText,"Case number entered",basetest);
				driver.findElement(By.xpath(caseAdjID)).sendKeys(Keys.ENTER);
				Thread.sleep(3000);

				if(!(driver.findElements(By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']")).size()>0)) {			
					String creditMemoTypeTxt = gen.getTextOfElement(driver, creditMemoType);
					if (creditMemoTypeTxt.contains("Credit Memo")) {
						basetest.test.log(Status.PASS, "Type :<span style='font-weight:bold;color:blue'>" + creditMemoTypeTxt + "</span> is displayed");
					} else {
						basetest.test.log(Status.FAIL, "Type :<span style='font-weight:bold;color:blue'>" + creditMemoTypeTxt + "</span> is displayed");
					}
					
					if(driver.findElements(By.xpath(statusInCasePage)).isEmpty()) {
						statusInCasePage = "//td[ starts-with(@class,'listtext') and text()='Open']";
					}
					String statusTxt = gen.getTextOfElement(driver, statusInCasePage);
					if (statusTxt.contains("Fully Applied")) {
						basetest.test.log(Status.PASS, "Status :  <span style='font-weight:bold;color:blue'>" + statusTxt + "</span> is displayed");
					} else {
						basetest.test.log(Status.FAIL, "Status :  <span style='font-weight:bold;color:blue'>" + statusTxt + " </span>is displayed");
					}

					List<WebElement> tableInvoiceCMS  = driver.findElements(By.xpath(invoiceCMsTable));
					double creditAmount1=0.00;
					double creditAmount2=0.00;
					String creditAmountStr1="";
					String creditAmountStr2="";
					
					String documentNumber = "";
					String name ="";
					int i;
					for( i =1;i<=tableInvoiceCMS.size();i++) {
						if(i==1) {
							documentNumber =gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[4]");
							basetest.test.log(Status.INFO, "Credit Memo - DOCUMENT NUMBER is displayed as "+documentNumber);
							
							name=gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[5]");
							basetest.test.log(Status.INFO, "Credit Memo - Name is displayed as "+name);
							
							creditAmountStr1 = gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[6]");
							basetest.test.log(Status.INFO, "Credit Memo - Amount is displayed as "+creditAmountStr1);
							if(creditAmountStr1.length()>0) {
								creditAmountStr1=creditAmountStr1.replaceAll("-","");
								creditAmountStr1=creditAmountStr1.replaceAll(",","");
								creditAmount1 =Double.valueOf(creditAmountStr1);
							}
						}
						if(i>1) {
							documentNumber =gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[4]");
							basetest.test.log(Status.INFO, "Invoice - DOCUMENT NUMBER is displayed as "+documentNumber);
							
							name=gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[5]");
							basetest.test.log(Status.INFO, "Invoice - Name is displayed as "+name);
							
							creditAmountStr2 = gen.getTextOfElement(driver, "(//table[starts-with(@id,'customsublist')]/tbody/tr)["+(i)+"]/td[6]");	
							basetest.test.log(Status.INFO, "Invoice - Amount is displayed as "+creditAmountStr2);
							creditAmountStr2 = creditAmountStr2.replaceAll("-","");
							creditAmountStr2 = creditAmountStr2.replaceAll(",","");
							creditAmount2 = creditAmount2+Double.valueOf(creditAmountStr2);
						}
					}
					if(!(i==1)) {
						if(creditAmount1==creditAmount2) {
							basetest.test.log(Status.PASS, "Credit amount(<span style='font-weight:bold;color:blue'>"+creditAmount1+"</span>) and invoice total amount("+creditAmount2+") are equal");
						}else {
							basetest.test.log(Status.FAIL, "Credit amount(<span style='font-weight:bold;color:blue'>"+creditAmount1+"</span>) and invoice total amount("+creditAmount2+") are not equal");
						}
					}else {
						basetest.test.log(Status.FAIL, "Revised Invoice is not generated");
					}
				}else {
					String text = driver.findElement(By.xpath("//table[starts-with(@id,'customsublist')]//td[text()='No records to show.']")).getText();
					basetest.test.log(Status.FAIL, "After entering the case adjustment ID :" + text);
				}
				//click on invoice number

				js.executeScript("window.scrollBy(0,-1500)");
				Thread.sleep(3000);

				gen.clickButton(driver, By.xpath(invoiceNoLink),"invoice number",basetest);
				Thread.sleep(6000);
			}catch(Exception e) {
				System.out.println(e);
			}
		}

	
}
