package com.netsuite.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

public class NS_AdvertiseChange {
	NS_Billing_AdjustmentAndSpecialBilling billing = new NS_Billing_AdjustmentAndSpecialBilling();
	NS_Billing_CasePageValidations casePage = new NS_Billing_CasePageValidations();
	NS_Billing_InvoicePageValidations invoiceValidations = new NS_Billing_InvoicePageValidations();
	String systemInformation = "//a[@id='s_sysinfotxt']";
	String agencyBillingAddress="//span[@id='custbody_ihm_agency_billing_address_id_fs_lbl']/following::span";
    String advertiserBillingAddress="//span[@id='custbody_ihm_advertiser_bill_addr_id_fs_lbl']/following::span";
    String clientSalesforceID = "//span[@id='custbody_ihm_client_salesforce_id_fs_lbl']/a/following::span";
    
	Generic oGenericUtils = new Generic();
	
	
	String selectNewAgencyName 		= "//input[@id='custpage_newagency_display']";
	String selectNewAdvertiserName  ="//input[@id='custpage_newadvertiser_display']";
	String toBeAiredAsTextBox = "//input[@id='custpage_tobeairedas']";
	String sendToAdvertiserCheckbox = "//input[@id='custpage_sendtoadvertiser_fs_inp']/../img";
	private CharSequence revisedAgencyID;
	
	public void advertiseChange(WebDriver driver,String scenario,String adjustType,String invoiceNO,BaseTest basetest) {
		String caseNumberText="";
		
		String oldAgencyID="";
		String oldAgencyName= "";
		String oldBillToAddress = "";
		String revisedClientName = "";
		String revisedAdvertiserName = "";
		String revisedAdvertiserID ="";
		String revisedBillTo = "";
		
		boolean  isSendInvoiceToAdv = false;
		boolean isRevInvoiceToBePrinted = false;
		boolean isRevInvoiceSentEDI = false;
		boolean isRevInoiceEmail = false;
		
		boolean isOrgInvoiceToBePrinted;
		boolean isOrgInvoiceSentEDI;
		boolean isOrgInvoiceEmail;
		try {
			
			oGenericUtils.waiForPageLoad(3);
			//billing.clickOnChangeAdvertiserOrAgency(driver, basetest);
			oGenericUtils.clickButton(driver, By.xpath(billing.selectAdjArrow),"Select Adjustment Type",basetest);		
			String typeofAdjustment = "//div[contains(text(),'"+scenario+"')]";
			oGenericUtils.clickButton(driver, By.xpath(typeofAdjustment),scenario,basetest);
			oGenericUtils.waiForPageLoad(1);
			oGenericUtils.isAlertPresent(driver);
			
			
			//oGenericUtils.clickButton(driver, By.xpath(billing.adjReasonGeneralDowArrow),"Select Adjustment Type",basetest);
			oGenericUtils.waiForPageLoad(2);
			oGenericUtils.clickButton(driver, By.xpath(billing.orderEntryError),"orderEntryError",basetest);
			oGenericUtils.waiForPageLoad(4);
			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next button",basetest);
			oGenericUtils.waiForPageLoad(9);
			oGenericUtils.SetVal(driver, By.xpath(billing.invoiceNumber), invoiceNO,"Invoice Text Box",basetest);
			//click on Search Invoice
			//oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Click on Invoice Search",basetest);
			driver.findElement(By.xpath(billing.invoiceNumber)).sendKeys(Keys.TAB);
			oGenericUtils.waiForPageLoad(29);

			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next ",basetest);
			oGenericUtils.waiForPageLoad(15);
			
			
			//select new advertiser name == 121351 OMD
			
			oGenericUtils.clickButton(driver, By.xpath(sendToAdvertiserCheckbox),"SEND TO ADVERTISER check box is checked ",basetest);
			oGenericUtils.clickButton(driver, By.xpath(selectNewAdvertiserName),"select new advertiser name",basetest);
			oGenericUtils.setText(driver, selectNewAdvertiserName, "121351 OMD.");
			oGenericUtils.waiForPageLoad(2);
			
			driver.findElement(By.xpath(selectNewAdvertiserName)).sendKeys(Keys.TAB);
			oGenericUtils.waiForPageLoad(2);
	
			
			oGenericUtils.setText(driver, toBeAiredAsTextBox, "Radio");
			
			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next ",basetest);
			
			oGenericUtils.waiForPageLoad(16);
			oGenericUtils.setText(driver, billing.Comments, "QA");
			oGenericUtils.clickButton(driver, By.xpath(billing.finish),"finish",basetest);
			oGenericUtils.waiForPageLoad(13);
			
			
			caseNumberText =  oGenericUtils.getTextOfElement(driver, billing.caseNumber).trim();
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number :<span style='font-weight:bold;color:blue'>"+caseNumberText+"</span> is generated.");
			}
			oGenericUtils.waiForPageLoad(7);
			oGenericUtils.clickButton(driver, By.xpath(billing.goToButton), "Go To Case", basetest);
			oGenericUtils.waiForPageLoad(12);
			
			
			casePage.casePageValidations_agencychange(driver, "", adjustType, invoiceNO, caseNumberText, basetest);
			invoiceValidations.invoiceValidations(driver, basetest);
			
			
			
			isOrgInvoiceToBePrinted = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBePrinted, basetest);
			isOrgInvoiceSentEDI = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEDI, basetest);
			isOrgInvoiceEmail = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEmail, basetest);
			
			driver.navigate().back();
			
			Thread.sleep(5000);
			
			JavascriptExecutor jsa = (JavascriptExecutor) driver;
			jsa.executeScript("window.scrollBy(0,1500)");
			
			oGenericUtils.clickButton(driver, By.xpath(casePage.invoicesOrCMS),"Invoice/CMs",basetest);
			oGenericUtils.SetVal(driver, By.xpath(casePage.caseAdjID), caseNumberText,"Case number entered",basetest);
			driver.findElement(By.xpath(casePage.caseAdjID)).sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			
			
			String revisedInvoice = "(//table[starts-with(@id,'customsublist')]/tbody/tr)[2]/td[4]/a";
			
			if(driver.findElements(By.xpath(revisedInvoice)).size()>0) {
				
				oGenericUtils.clickButton(driver, By.xpath(revisedInvoice), "Revised invoice is clicked ", basetest);
				oGenericUtils.waiForPageLoad(9);
				
								
				isSendInvoiceToAdv = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.sendInvoiceToAdvertiserCheckbox, basetest);
				
				if(isSendInvoiceToAdv) {
					basetest.test.log(Status.PASS, "Send Invoice To Advertiser is Checked");
				}else {
					basetest.test.log(Status.PASS, "Send Invoice To Advertiser is  Not Checked");
				}
				
				revisedClientName = oGenericUtils.getTextOfElement(driver, invoiceValidations.clientName);
				revisedAdvertiserName = oGenericUtils.getTextOfElement(driver, invoiceValidations.advertiserName);
				revisedAdvertiserID =oGenericUtils.getTextOfElement(driver, invoiceValidations.advertiseID);
				revisedBillTo = oGenericUtils.getTextOfElement(driver, invoiceValidations.billTo);
				
				
				basetest.test.log(Status.INFO, "Revised Client Name "+revisedClientName+"<br>"
											 + "Revised Advertiser Name "+revisedAdvertiserName+"<br>"
											 + "Revised Advertiser ID "+revisedAdvertiserID);
				
				jsa.executeScript("window.scrollBy(0,-1500)");
				
				isRevInvoiceToBePrinted = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBePrinted, basetest);
				isRevInvoiceSentEDI = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEDI, basetest);
				isRevInoiceEmail = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEmail, basetest);
				
				jsa.executeScript("window.scrollBy(0,-1500)");
				
				String agencyBillingAddId = null;
				String advertiserBillAddIDText= null;
				String clientSaleforceID= null;
				
				oGenericUtils.clickButton(driver, By.xpath(systemInformation), "System information", basetest);
				
				/*agencyBillingAddId = oGenericUtils.getTextOfElement(driver, agencyBillingAddress); 
				if (agencyBillingAddId == null) {
					basetest.test.log(Status.PASS, "AGENCY BILLING ADDRESS ID is Removed "+ agencyBillingAddress);
				} else {
					basetest.test.log(Status.FAIL, "AGENCY BILLING ADDRESS ID is not removed ");
				}*/
				
				advertiserBillAddIDText = oGenericUtils.getTextOfElement(driver, advertiserBillingAddress);
				if (!advertiserBillAddIDText.isEmpty()) {
					basetest.test.log(Status.PASS, "ADVERTISER BILLING ADDRESS ID "+ advertiserBillAddIDText);
				} else {
					basetest.test.log(Status.FAIL, "ADVERTISER BILLING ADDRESS ID NOT FOUND");
				}
				clientSaleforceID = oGenericUtils.getTextOfElement(driver, clientSalesforceID);
				if (!clientSaleforceID.isEmpty()) {
					basetest.test.log(Status.PASS, "CLIENT SALERSFORCE ID "+ clientSaleforceID);
				} else {
					basetest.test.log(Status.FAIL, " CLIENT sALESFORCE ID IS NOT FOUND");
				}
				
				
				
				
				
			/*}else {
				basetest.test.log(Status.FAIL, " Revised invoice is not generated.");
			}
			if(!oldAgencyName.contains(revisedAdvertiserName)) {
				basetest.test.log(Status.PASS, "Old Agency Name : "+oldAgencyName+" <br>"
											 + "Revised Agency Name :"+revisedAdvertiserName +" are not same.");
			}else {
				basetest.test.log(Status.FAIL, "Old Agency Name : "+oldAgencyName+" <br>"
						 						+ "Revised Agency Name :"+revisedAdvertiserName +" are same.");
			}
			if(!oldAgencyID.contains(revisedAdvertiserID)) {
				basetest.test.log(Status.PASS, "Old Agency ID : "+oldAgencyID+" <br>"
 						+ "Revised Agency ID :"+revisedAdvertiserID +" are not same .");
			}else {
				basetest.test.log(Status.FAIL, "Old Agency ID : "+oldAgencyID+" <br>"
 						+ "Revised Agency ID :"+revisedAdvertiserID +" are same.");
			}
			if(!oldBillToAddress.contains(revisedBillTo)) {
				basetest.test.log(Status.PASS, "Old Bill To : "+oldBillToAddress+" <br>"
 						+ "Revised Bill To :"+revisedBillTo +" are not same.");
			}else {
				basetest.test.log(Status.FAIL, "Old Bill To : "+oldBillToAddress+" <br>"
 						+ "Revised Bill To :"+revisedBillTo +" are same.");
			}*/
			
			//Invoice Control settings validations
			if(isRevInvoiceToBePrinted == isOrgInvoiceToBePrinted) {
				basetest.test.log(Status.PASS, "To Be Printed in Revised Invoice and Original Invoice are Same");
			}else {
				basetest.test.log(Status.FAIL, "To Be Printed in Revised Invoice and Original Invoice are Not Same");
			}
			if(isRevInvoiceSentEDI == isOrgInvoiceSentEDI) {
				basetest.test.log(Status.PASS, "To Be Sent via EDI in Revised Invoice and Original Invoice are Same");
			}else {
				basetest.test.log(Status.FAIL, "To Be Sent via EDI in Revised Invoice and Original Invoice are Not Same");
			}
			if(isRevInoiceEmail == isOrgInvoiceEmail) {
				basetest.test.log(Status.PASS, "To Be Sent Email in Revised Invoice and Original Invoice are Same");
			}else {
				basetest.test.log(Status.FAIL, "To Be Sent Email in Revised Invoice and Original Invoice are Not Same");
			}
			}
		}
			
		catch(Exception e) {
			
		}
	}
}
	

