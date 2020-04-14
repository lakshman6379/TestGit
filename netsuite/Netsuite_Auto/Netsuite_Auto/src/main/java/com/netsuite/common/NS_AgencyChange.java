package com.netsuite.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

public class NS_AgencyChange {
	NS_Billing_AdjustmentAndSpecialBilling billing = new NS_Billing_AdjustmentAndSpecialBilling();
	NS_Billing_CasePageValidations casePage = new NS_Billing_CasePageValidations();
	NS_Billing_InvoicePageValidations invoiceValidations = new NS_Billing_InvoicePageValidations();
	Generic oGenericUtils = new Generic();
	
	
	String selectNewAgencyName 		= "//input[@id='custpage_newagency_display']";
	String toBeAiredAsTextBox = "//input[@id='custpage_tobeairedas']";
	String sendToAgencyCheckbox = "//img[@class='checkboximage']";
	
	
	public void agencyChange(WebDriver driver,String scenario,String adjustType,String invoiceNO,BaseTest basetest) {
		String caseNumberText="";
		String oldAgencyID="";
		String oldAgencyName= "";
		String oldBillToAddress = "";
		String revisedAgencyID = "";
		String revisedAgencyName = "";
		String revisedBillTo = "";
		
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
			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next ",basetest);
			oGenericUtils.waiForPageLoad(9);
			oGenericUtils.SetVal(driver, By.xpath(billing.invoiceNumber), invoiceNO,"Invoice Text Box",basetest);
			//click on Search Invoice
			//oGenericUtils.clickButton(driver, By.xpath(searchInvoice),"Click on Invoice Search",basetest);
			driver.findElement(By.xpath(billing.invoiceNumber)).sendKeys(Keys.TAB);
			oGenericUtils.waiForPageLoad(29);

			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next ",basetest);
			oGenericUtils.waiForPageLoad(15);
			
			
			//select new agency name == 10000000 UNK-20
			oGenericUtils.clickButton(driver, By.xpath(selectNewAgencyName),"select new agency name",basetest);
			oGenericUtils.setText(driver, selectNewAgencyName, "121351 OMD.");
			oGenericUtils.waiForPageLoad(2);
			driver.findElement(By.xpath(selectNewAgencyName)).sendKeys(Keys.TAB);
			//oGenericUtils.waiForPageLoad(2);
	
			//oGenericUtils.navigateMouseToElement(driver, By.xpath(sendToAgencyCheckbox),"Agency check box is checked ",basetest);
			oGenericUtils.setText(driver, toBeAiredAsTextBox, "Radio");
			
			oGenericUtils.clickButton(driver, By.xpath(billing.next),"Next",basetest);
			
			oGenericUtils.waiForPageLoad(16);
			oGenericUtils.setText(driver, billing.Comments, "QA");
			oGenericUtils.clickButton(driver, By.xpath(billing.finish),"Finish",basetest);
			oGenericUtils.waiForPageLoad(13);
			
			
			caseNumberText =  oGenericUtils.getTextOfElement(driver, billing.caseNumber).trim();
			if(caseNumberText.length()>0){
				basetest.test.log(Status.INFO, "Case Number :<span style='font-weight:bold;color:blue'>"+caseNumberText+"</span> is generated.");
			}
			oGenericUtils.waiForPageLoad(7);
			oGenericUtils.clickButton(driver, By.xpath(billing.goToButton), "Go To Case", basetest);
			oGenericUtils.waiForPageLoad(12);
			
			
			casePage.casePageValidations_agencychange(driver, "", adjustType, invoiceNO, caseNumberText, basetest);
		
			/*js.executeScript("window.scrollBy(0,1500)");
			Thread.sleep(3000);

			oGenericUtils.clickButton(driver, By.xpath(casePage.invoiceNoLink),"invoice number",basetest);
			Thread.sleep(9000);*/
			
			invoiceValidations.invoiceValidations(driver, basetest);
			if(invoiceValidations.isInvoiceToAgencyChecked(driver, basetest)) {
				String clientIDandNameText = oGenericUtils.getTextOfElement(driver, invoiceValidations.clientIDAndName);
				 oldAgencyID = oGenericUtils.getTextOfElement(driver, invoiceValidations.agencyID);
				 oldAgencyName= oGenericUtils.getTextOfElement(driver, invoiceValidations.agencyName);
				 oldBillToAddress = oGenericUtils.getTextOfElement(driver, invoiceValidations.billTo);
				
				basetest.test.log(Status.PASS, "Client : "+clientIDandNameText+" <br>"
											  + "Agency ID : "+oldAgencyID+"<br>"
											  + "Agency Name : "+oldAgencyName+"<br>"
											  + "Bill  To : "+oldBillToAddress);
				
			}
						
			
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
				
				oGenericUtils.clickButton(driver, By.xpath(revisedInvoice), " Revised invoice is clicked ", basetest);
				oGenericUtils.waiForPageLoad(9);
				revisedAgencyID = oGenericUtils.getTextOfElement(driver, invoiceValidations.agencyID);
				revisedAgencyName = oGenericUtils.getTextOfElement(driver, invoiceValidations.agencyName);
				revisedBillTo = oGenericUtils.getTextOfElement(driver, invoiceValidations.billTo);
				
				jsa.executeScript("window.scrollBy(0,-1500)");
				
				isRevInvoiceToBePrinted = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBePrinted, basetest);
				isRevInvoiceSentEDI = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEDI, basetest);
				isRevInoiceEmail = invoiceValidations.isCheckBoxChecked(driver, invoiceValidations.toBeSentEmail, basetest);
				
				
			}else {
				basetest.test.log(Status.FAIL, " Revised invoice is not generated.");
			}
			if(oldAgencyName.length()>1 && revisedAgencyName.length()>1) {
				if(!oldAgencyName.contains(revisedAgencyName)) {
					basetest.test.log(Status.PASS, "Old Agency Name : "+oldAgencyName+" <br>"
												 + "Revised Agency Name :"+revisedAgencyName +" are not same");
				}else {
					basetest.test.log(Status.FAIL, "Old Agency Name : "+oldAgencyName+" <br>"
							 						+ "Revised Agency Name :"+revisedAgencyName +" are same");
				}
			}else if(oldAgencyName.length()<1||revisedAgencyName.length()<1) {
				if(oldAgencyName.length()<1&&revisedAgencyName.length()>1) {
					basetest.test.log(Status.FAIL, " Old Agency Name is not displayed <br>"
							+ "Revised Agency Name is displayed as "+revisedAgencyName);
				}else if(oldAgencyName.length()<1 && revisedAgencyName.length()<1) {
					basetest.test.log(Status.FAIL, "Old Agency Name is not displayed <br>"
							+ "Revised Agency Name is not displayed");
				}else if(oldAgencyName.length()>1 && revisedAgencyName.length()<1) {
					basetest.test.log(Status.FAIL, " Old Agency Name is  displayed "+oldAgencyName+"<br>"
							+ "Revised Agency Name is not displayed ");
				}
			}
			
			if(oldAgencyID.length()>1 && revisedAgencyID.length()>1) {
				if(!oldAgencyName.contains(revisedAgencyName)) {
					basetest.test.log(Status.PASS, "Old Agency ID : "+oldAgencyID+" <br>"
												 + "Revised Agency ID :"+revisedAgencyID +" are not same.");
				}else {
					basetest.test.log(Status.FAIL, "Old Agency ID : "+oldAgencyID+" <br>"
							 						+ "Revised Agency ID :"+revisedAgencyID +" are same.");
				}
			}else if(oldAgencyID.length()<1||revisedAgencyID.length()<1) {
				if(oldAgencyID.length()<1&&revisedAgencyID.length()>1) {
					basetest.test.log(Status.FAIL, " Old Agency ID is not displayed <br>"
							+ "Revised Agency ID is displayed as "+revisedAgencyID);
				}else if(oldAgencyID.length()<1 && revisedAgencyID.length()<1) {
					basetest.test.log(Status.FAIL, "Old Agency ID is not displayed <br>"
							+ "Revised Agency ID is not displayed");
				}else if(oldAgencyID.length()>1 && revisedAgencyID.length()<1) {
					basetest.test.log(Status.FAIL, " Old Agency ID is  displayed "+oldAgencyID+"<br>"
							+ "Revised Agency ID is not displayed ");
				}
			}
			if(oldBillToAddress.length()>1 && revisedBillTo.length()>1) {
				if(!oldAgencyName.contains(revisedBillTo)) {
					basetest.test.log(Status.PASS, "Old Agency Bill : "+oldBillToAddress+" <br>"
												 + "Revised Agency Bill :"+revisedBillTo +" are not same.");
				}else {
					basetest.test.log(Status.FAIL, "Old Agency ID : "+oldBillToAddress+" <br>"
							 						+ "Revised Agency Bill :"+revisedBillTo +" are same.");
				}
			}else if(oldBillToAddress.length()<1||revisedBillTo.length()<1) {
				if(oldBillToAddress.length()<1&&revisedBillTo.length()>1) {
					basetest.test.log(Status.FAIL, " Old Agency Bill is not displayed <br>"
							+ "Revised Agency Bill is displayed as "+revisedBillTo);
				}else if(oldBillToAddress.length()<1 && revisedBillTo.length()<1) {
					basetest.test.log(Status.FAIL, "Old Agency Bill is not displayed <br>"
							+ "Revised Agency Bill is not displayed");
				}else if(oldBillToAddress.length()>1 && revisedBillTo.length()<1) {
					basetest.test.log(Status.FAIL, " Old Agency Bill is  displayed "+oldBillToAddress+"<br>"
							+ "Revised Agency Bill is not displayed ");
				}
			}
			
			
			/*if(!oldBillToAddress.contains(revisedBillTo)) {
				basetest.test.log(Status.PASS, "Old Bill To : "+oldBillToAddress+" <br>"
 						+ "Revised Bill To :"+revisedBillTo +" are different.");
			}else {
				basetest.test.log(Status.FAIL, "Old Bill To : "+oldBillToAddress+" <br>"
 						+ "Revised Bill To :"+revisedBillTo +" are same.");
			}*/
			
			if(isRevInvoiceToBePrinted == isOrgInvoiceToBePrinted) {
				basetest.test.log(Status.PASS, "To Be Printed check box is checked in both Original and Revised invoice");
			}else {
				basetest.test.log(Status.FAIL, "To Be Printed check box is not checked Either in Original or Revised invoice");
			}
			if(isRevInvoiceSentEDI == isOrgInvoiceSentEDI) {
				basetest.test.log(Status.PASS, "To Be Sent via EDI check box is checked in both Original and Revised invoice");
			}else {
				basetest.test.log(Status.FAIL, "To Be Sent via EDI check box is not checked Either in Original or Revised invoice");
			}
			if(isRevInoiceEmail == isOrgInvoiceEmail) {
				basetest.test.log(Status.PASS, "To Be Sent Email check box is checked in both Original and Revised invoice");
			}else {
				basetest.test.log(Status.FAIL, "To Be Sent Email in check box is not checked Either in Original or Revised invoice");
			}
	
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
