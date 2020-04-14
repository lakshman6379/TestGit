package com.netsuite.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.framework.BaseTest;
import com.framework.Generic;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class NS_LoginPage {
	BaseTest basetest;

	//Locators for Login Page
	String img_NetSuite					=	"//img[@class='uir-logo']";

	//Locators for Login
	String username 					= 	"//input[@id='userName']";
	String pass 						= 	"//input[@id='password']";

	//logout NetSuite application Locators
	String move							=	"//*[@id='spn_cRR_d1']/a";
	String logout 						=	"//span[contains(text(),'Log Out')]";
	String elementhomepage 				= 	"//div[@id='ns-dashboard-heading-panel']/h1";

	//choosing role Locators
	public	String adminElement 		= 	"spn_cRR_d1";
	public	String elementSB2 			= 	"//*[@id='ns-header-menu-main-item2']";

	//Authentication Locators
	String SQ1					 		= 	"What is your maternal grandmother's maiden name?";
	String SQ2 							= 	"What was your childhood nickname?";
	String SQ3 							= 	"In what city did you meet your spouse/significant other?";
	String securityAnswerX 				= 	"//input[@type='password']";
	String secAnsSubmitX 				= 	"//input[@type='submit']";

	//Locators for OTP
	String newmessage 					= 	"//ul[@id='navBarTabs']/li[1]/a";
	String otp 							= 	"//span[contains(text(),'Your NetSuite verification code is')]";
	String otptextbox 					= 	"//input[@placeholder= '6-digit code']";
	String submitbtn 					= 	"//div[@n-login-id='button-login-next']";
	String gmailusername 				= 	"//input[@name='identifier']";
	String gmailnext 					= 	"//div[@id='identifierNext']";
	String passwordinput 				= 	"//input[@name='password']";
	String passwordnext 				= 	"//div[@id='passwordNext']";
	String popupcancel 					= 	"//button[@id='custom-alert-and-confirm-modal-cancel-button']";

	//Instance creation
	Generic oGenericUtils=new Generic();
	
	
	//===========================Launch NetSuite===========================================================>
	public WebDriver LaunchNetSuiteApp(HashMap<String, String> XLTestData,String filePathInString,BaseTest basetest) {
		WebDriver driver = null;
		try {

			FileInputStream fis = new FileInputStream( System.getProperty("user.dir") + "\\resources\\object.properties");
			Properties p = new Properties();
			p.load(fis);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();   

			Map<String,Object> prefs = new HashMap<String,Object>();
			if(!filePathInString.isEmpty()){
				prefs.put("download.default_directory", filePathInString);
			}
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("user-data-dir="+System.getProperty("user.home")+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			options.addArguments("--disable-features=InfiniteSessionRestore");
			options.addArguments("--disable-notifications");
			options.addArguments("--start-maximized");
			options.addArguments("--disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			driver = new ChromeDriver(options);
			driver.get(p.getProperty("url"));

			//Page Maximize
			//driver.manage().window().maximize();				  
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			basetest.test.log(Status.PASS, p.getProperty("environment").toString() + " Environment URL "+p.getProperty("url").toString() + "  is Launched ");

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(), "FAILED",basetest);
			basetest.test.log(Status.FAIL,"NetSuite Application is not Launched");
		}
		return driver;
	}

	/*##############################################################
	 @Descriptions 		---	Login to NetSuite Page
	 @param driver 		---	WebDriver parameter
	 @param XLTestData 	---	Test Data Parameter
	 @param basetest 	---	Report Parameter
	 ##############################################################*/
	public void NetSuiteLogin(WebDriver driver,HashMap<String, String> XLTestData,BaseTest basetest) {
		try {
			FileInputStream fis = new FileInputStream( System.getProperty("user.dir") + "\\resources\\object.properties");
			Properties p = new Properties();
			p.load(fis);
			
			//Enter Username
			oGenericUtils.SetVal(driver, By.xpath(username), p.getProperty("email").toString(),"Username textbox",basetest);

			//Enter Password
			driver.findElement(By.xpath(pass)).sendKeys(p.getProperty("password").toString());
			basetest.test.log(Status.INFO,"******* is Entered  in Password Text Box");

			//Click Login Button
			oGenericUtils.clickButton(driver, By.id("submitButton"),"Submit Button",basetest);


			if(driver.findElements(By.xpath(elementhomepage)).size() > 0) 
			{
				basetest.test.log(Status.INFO, "User is  logged in to NetSuite");

			}
			else {

				if(driver.getTitle().contains("Administrator Message - NetSuite"))
				{
					basetest.test.log(Status.INFO, "Administrator Message - NetSuite/Under Maintainace ");
					driver.findElement(By.xpath("//*[@id='agree_fs_fs']/img")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//*[@id='submitter']")).click();
				}
				else 
				{
					if(driver.getTitle().contains("Two-factor login challenge")) {

						basetest.test.log(Status.FAIL, "User is not  logged in to NetSuite/Pass Code is Expired");
						driver.close();
					}

					basetest.test.log(Status.FAIL, "User is not  logged in to NetSuite/May NewPop Occurs in Netsuite HomePage");
					Assert.assertTrue(false);
				}
			}

		} catch (Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(),"FAILED",basetest);
			basetest.test.log(Status.FAIL, "test failed");
		}
	}


	/*##############################################################
	 @Descriptions 		---	Logout from NetSuite Page
	 @param driver 		---	WebDriver parameter
	 @param basetest 	---	Report Parameter
	 ##############################################################*/
	public void NetSuiteLogout(WebDriver driver,BaseTest basetest) {
		try {

			//Click on Logout Page
			oGenericUtils.navigateMouseToElement(driver, By.xpath(move),"Logout button",basetest);
			oGenericUtils.clickButton(driver, By.xpath(logout),"Logout button",basetest);
			basetest.test.log(Status.PASS,"Logout from NetSuite Application ");

		}catch(Exception e) {
			oGenericUtils.Verify("Object not found:="+e.getMessage(), "FAILED",basetest);
			basetest.test.log(Status.FAIL,"Issue in Logout from NetSuite application");
		}
	
	}



	/*##############################################################
	 @Descriptions 		---	extracting digits from url
	 @param src 		---	String value
	 ##############################################################*/
	public String extractDigits(String src) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}
