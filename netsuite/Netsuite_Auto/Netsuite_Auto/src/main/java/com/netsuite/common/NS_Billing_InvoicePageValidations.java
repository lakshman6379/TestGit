package com.netsuite.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

public class NS_Billing_InvoicePageValidations {
	Generic gen=new Generic();
	
	String invoicePageHeading = "//div[@class='uir-page-title-secondline']/div[3]";
	String subTotalInInvoicePage = "(//span[@id='subtotal_fs_lbl_uir_label']/following::span)[1]";
	String tottalInInvoicePage = "(//span[@id='total_fs_lbl_uir_label']/following::span)[1]";
	
	String suppressRate_InvoicePage = "//span[@id='custbody_ihm_suppress_rate_fs']/img";
	String supressMiscRate_InvoicePage  = "//span[@id='custbody_ihm_suppress_misc_rate_fs_lbl']/../../span/span/img";
	String amountDue = "(//span[@id='amountremainingtotalbox_fs_lbl']/following::span)[1]";
	String sendInvoiceToAgency = "//span[@id='custbody_ihm_send_inv_to_agency_fs']/img";
	String clientIDAndName = "(//span[@id='entity_fs_lbl']/a/../../following::span/span/a[starts-with(@id,'qsTarget_')])[1]";
	String agencyID = "//span[@id='custbody_ihm_agency_id_fs_lbl_uir_label']/following-sibling::span";
	String agencyName = "//span[@id='custbody_ihm_agency_name_fs_lbl_uir_label']/following-sibling::span";
	String billTo = "//span[@id='billaddress_fs_lbl_uir_label']/following-sibling::span";
	

	String toBePrinted = "//span[@id='tobeprinted_fs']/img";
	String toBeSentEDI = "//span[@id='custbody_ihm_edi_fs']/img";
	String toBeSentEmail = "//span[@id='custbody_tobeemailed_custom_fs']/img";
	
	String clientName = " //span[@id='entity_fs_lbl']/a/../../following::span";
	String advertiserName = "//span[@id='custbody_tran_advertiser_id_list_fs_lbl']/following::span";
	String advertiseID = "//span[@id='custbody_ihm_advertiser_fs_lbl']/following::span";
	
	String sendInvoiceToAdvertiserCheckbox = "//span[@id='custbody_ihm_send_inv_to_advertiser_fs' ]/img";
	
	public void invoiceValidations(WebDriver driver,BaseTest basetest) {
		try {

			/*
			 * String subTotalTxt = gen.getTextOfElement(driver,subTotalInInvoicePage);
			 * String totalTxt = gen.getTextOfElement(driver,tottalInInvoicePage);
			 * if(subTotalTxt.contains(totalTxt)) { basetest.test.log(Status.PASS,
			 * "Sub total("+subTotalTxt+") and Total("+totalTxt+") are equal"); }else {
			 * basetest.test.log(Status.FAIL,
			 * "Sub total("+subTotalTxt+") and Total("+totalTxt+") are not equal"); }
			 */
			String invoicePagHeadingTxt = gen.getTextOfElement(driver,invoicePageHeading);
			if(invoicePagHeadingTxt.contains("PAID IN FULL")) {
				basetest.test.log(Status.PASS, "Invoice page heading is displayed as :<span style='font-weight:bold;color:green'>"+invoicePagHeadingTxt+"</span>");
			}else {
				basetest.test.log(Status.FAIL, "Invoice page heading: <span style='font-weight:bold;color:red'>"+invoicePagHeadingTxt+" </span> is displayed ");
			}
			
			String amtDue = gen.getTextOfElement(driver, amountDue).trim();
			if(amtDue.contains("0.00")) {
				basetest.test.log(Status.PASS, "Amount Due is displayed as :<span style='font-weight:bold;color:green'>"+amtDue+"</span>");
			}else {
				basetest.test.log(Status.FAIL, "Amount Due is displyed as :"+amtDue);
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void suppressRateValidations(WebDriver driver,BaseTest basetest){
			

		//oGenericUtils.scrollToElement( driver,suppressRate_InvoicePage);

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");

		WebElement suppressRate_Invoice = driver.findElement(By.xpath(suppressRate_InvoicePage));
		WebElement supressMiscRate_Invoice = driver.findElement(By.xpath(supressMiscRate_InvoicePage));

		String Suppress_Misc = supressMiscRate_Invoice.getAttribute("alt");
		String Suppress = suppressRate_Invoice.getAttribute("alt");

		if(Suppress.contains("Checked"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page SuppressRate Is Selected"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page SuppressRate Is not selected"+"'</span>");
		}

		if(Suppress_Misc.contains("Checked"))
		{
			basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page SuppressMiscllanious Rate Is Selected"+"'</span>");
		}else {
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+"Invoice  Page SuppressMiscllanious Rate Is not selected"+"'</span>");
		}

	}
	
	public boolean isInvoiceToAgencyChecked(WebDriver driver, BaseTest basetest) {
		try {
			WebElement invoiceToAgencyElement = driver.findElement(By.xpath(sendInvoiceToAgency));
			String checkStatus = invoiceToAgencyElement.getAttribute("alt");
			if (checkStatus.contains("Checked")) {
				basetest.test.log(Status.PASS, " Send Invoice to Agency is checked");
			} else if (checkStatus.contains("Unchecked")) {
				basetest.test.log(Status.FAIL, " Send Invoice to Agency is not checked");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}
	

	public boolean isCheckBoxChecked(WebDriver driver, String checkBoxPath, BaseTest basetest) {
		boolean isChecked = false;
		try {
			if (driver.findElements(By.xpath(checkBoxPath)).size() > 0) {
				WebElement checkBoxEle = driver.findElement(By.xpath(checkBoxPath));
				String checkBoxStatus = checkBoxEle.getAttribute("alt");
				if (checkBoxStatus.contains("Checked")) {
					isChecked = true;
				}
			} else {
				basetest.test.log(Status.FAIL, "The checkbox is not present ");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return isChecked;
	}

}
