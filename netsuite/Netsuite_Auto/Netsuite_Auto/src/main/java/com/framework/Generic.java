package com.framework;



import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import com.aventstack.extentreports.Status;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import junit.framework.Assert;

public class Generic

{	
	ScreenShot screen =new ScreenShot();
	String msgDialog = "//*[@id='msgDialog']";
	BaseTest basetest;

	/*##############################################################
	 @Descriptions ---Clicks on the Web Element
	 @param driver -- WebDriver parameter
	 @param xpathExpress  -- xpath of the Web Element
	 ##############################################################*/
	public void clickElement(WebDriver driver, String xpathExpress) throws InterruptedException
	{
		try {
			driver.findElement(By.xpath(xpathExpress)).click();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}



	/*##############################################################
	 @Descriptions ---Enters the text in a field
	 @param driver -- WebDriver parameter
	 @param xpathExpress  -- xpath of the Web Element
	 ##############################################################*/
	public void setText(WebDriver driver, String xpathExpress,String Val2Set)
	{
		driver.findElement(By.xpath(xpathExpress)).clear();
		driver.findElement(By.xpath(xpathExpress)).sendKeys(Val2Set);


	}


	/*##############################################################
    @Descriptions           --     Navigate To Element With Actions
    @param xpathloc         --     Locator of the WebElement
    @param driver           --     Webdriver parameter
    @param repStatus        --     Report value parameter
    @param report           ---    Report Steps  
    ##############################################################*/
	public void navigateToElementWithActions(String xpathloc, WebDriver driver, String repStatus, BaseTest basetest) throws InterruptedException
	{
		WebElement webElement	= driver.findElement(By.xpath(xpathloc));
		Thread.sleep(2000);
		Actions builder = new Actions(driver);
		builder = new Actions(driver);
		builder.moveToElement(webElement).perform();
		basetest.test.log(Status.PASS,"Clicked on "+repStatus);
		Thread.sleep(2000);

	}


	/*##############################################################
	 @Descriptions --- Gets the text of the Web Element
	 @param driver -- WebDriver parameter
	 @param xpathExpress  -- xpath of the Web Element
	 ##############################################################*/
	public String getTextOfElement(WebDriver driver, String xpathExpress)
	{
		return(driver.findElement(By.xpath(xpathExpress)).getText()).trim();
	}


	/*##############################################################
	 @Descriptions ---Gets the value displayed on the UI for the Web Element
	 @param driver -- WebDriver parameter
	 @param xpathExpress  -- xpath of the Web Element
	 ##############################################################*/
	public String getValueOfElement(WebDriver driver, String xpathExpress)
	{
		return(driver.findElement(By.xpath(xpathExpress)).getAttribute("getValue"));
	}


	/*##############################################################
	 @Descriptions --- Select the value from the Dropdown 
	 @param driver -- WebDriver parameter
	 @param xpathExpress  -- xpath of the Web Element
	 ##############################################################*/
	public void selectElementValFromDropDown(WebDriver driver, String xpathExpress, String Val2Select)
	{

		Select dropdown = new Select(driver.findElement(By.xpath(xpathExpress)));
		dropdown.selectByVisibleText(Val2Select);
	}


	/*##############################################################
	 @Descriptions --- Launch the Ecom URL
	 @param XLTestData- URl from the excel sheet
	 @param basetest- Report parameter
	 ##############################################################*/
	public WebDriver LaunchURL(HashMap<String, String> XLTestData, BaseTest basetest) throws Exception
	{    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
			+ "\\resources\\chromedriver.exe"); WebDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS) ;
			driver.get(XLTestData.get("Sales_ Order_URL").toString());
			driver.manage().window().maximize();
			return driver;
	}



	/*##############################################################
	 @Descriptions --- Verifies the Launch of Ecom URL
	 @param driver-- webdriver
	 @param XLTestData- URl from the excel sheet
	 @param basetest- Report parameter
	 ##############################################################*/
	public boolean verifyLaunchURL(WebDriver driver,HashMap<String, String>XLTestData,BaseTest basetest) throws Exception{

		ScreenShot screen =new ScreenShot();
		String EcomTitle=driver.getTitle();
		if (EcomTitle.contains("Home")) {
			basetest.test.log(Status.PASS,XLTestData.get("Environment").toString() + " Environment URL "+XLTestData.get("Sales_ Order_URL").toString() + "  is Launched ");
			return true;
		}
		else {
			String FileName = screen .getScreenshot(driver);
			basetest.test.log(Status.FAIL,XLTestData.get("Environment").toString() + " Environment URL "+XLTestData.get("Sales_ Order_URL").toString() + "  is not  Launched <br><a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			return false;
		}

	}


	/*##############################################################
	 @Descriptions --- Launch the NetSuite URL
	 @param XLTestData- URl from the excel sheet
	 @param basetest- Report parameter
	 ##############################################################*/
	public WebDriver launchNetsuitURL(HashMap<String, String> XLTestData, BaseTest basetest)
	{
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir="+System.getProperty("user.home")+"\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
		options.addArguments("--disable-features=InfiniteSessionRestore");
		options.addArguments("--disable-notifications");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--start-maximized");
		WebDriver driver;
		driver = new ChromeDriver(options);
		//WebDriver driver = new ChromeDriver();
		driver.get(XLTestData.get("NetSuite_URL").toString());
		basetest.test.log(Status.PASS, XLTestData.get("Environment").toString() + " Environment URL " + XLTestData.get("NetSuite_URL").toString() + " is launched");

		return(driver);
	}





	/*##############################################################
	 @Descriptions --- Launch the Boomi URL
	 @param XLTestData- URl from the excel sheet
	 @param basetest- Report parameter
	 ##############################################################*/
	public WebDriver launchBoomiURL(HashMap<String, String> XLTestData, BaseTest basetest) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(XLTestData.get("BOOMIURL").toString());
		basetest.test.log(Status.INFO,  " BOOMI URL is launched");
		driver.manage().window().maximize();
		Thread.sleep(19000);
		return(driver);
	}



	/*##############################################################
	 @Descriptions --- scroll to Element
	 @param driver--WebDriver
	 @param xpathExpress- xpath expression of the element
	 ##############################################################*/
	public void scrollToElement(WebDriver driver,String xpathExpress) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(xpathExpress)));
		Thread.sleep(2000);


	}

	/*##############################################################
	 @Descriptions --- adds day to the current date
	 @param days- no of days to add
	 ##############################################################*/
	public  String addDaysToCurrentDate(int days)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		return sdf.format(c.getTime());

	}

	//========================================window handling==============================================================================>	
	public void switchToNewWindow( WebDriver driver,int windowNumber) {
		Set < String > s = driver.getWindowHandles();   
		Iterator < String > ite = s.iterator();
		int i = 1;
		while (ite.hasNext() && i < 10) {
			String popupHandle = ite.next().toString();
			driver.switchTo().window(popupHandle);
			//    System.out.println("Window title is : "+driver.getTitle());
			int windowCount = 0;
			if (i == windowCount) break;
			i++;
		}
	}




	//=======================================================Genericutils=============================================================>


	public  void Verify(String StepDetails,String sStatus) {

		if(sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			//basetest.test.log(Status.PASS,  StepDetails);
			Assert.assertTrue(StepDetails, true);
		}else {
			System.out.println(StepDetails);
			//basetest.test.log(Status.FAIL,  StepDetails);
			Assert.assertTrue(StepDetails, false);			
		}
	}

	public  void Verify(String StepDetails,String sStatus,BaseTest basetest) {

		if(sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			basetest.test.log(Status.PASS, StepDetails);			
			Assert.assertTrue(StepDetails, true);
		}else {
			System.out.println(StepDetails);
			basetest.test.log(Status.FAIL, StepDetails);		
			Assert.assertTrue(StepDetails, false);			
		}
	}

	//********************************CLICK BUTTON***********************************************************
	public  boolean clickButton(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							sList.get(i).click();

							blnResult=true;
							Verify("Clicked on : " +sText,"PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult){
					Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=30));

			//Flag returns false

			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is not Edited/Clicked  <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+"  is not found","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Edited/Clicked "+"'</span>");
			driver.quit();
		}
		return blnResult;
	}


	//********************************SET Value***********************************************************
	public  boolean SetVal(WebDriver driver,By sObjectType,String sInputVal,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							sList.get(i).clear();
							sList.get(i).sendKeys(sInputVal);
							blnResult=true;
							Verify(sInputVal  +" is Entered  in "+  sText,"PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult) {
					Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=60));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sInputVal+" is not Entered/Selected  <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+"  is not found","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Entered/Selected "+"'</span>");
			driver.quit();
		}
		return blnResult;
	}


	//**********************************isElementExist*********************************************************

	public boolean isElementExist(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							blnResult=true;
							Verify(sText+" is displayed ","PASSED",basetest);
							basetest.test.log(Status.PASS,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
							break;
						}
					}
				}
				if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
			}while ((blnResult!=true) && (iTimer!=10));
			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is not found <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");

			}
		}catch(Exception e) {
			Verify(sText+" is not displayed on "+sText,"FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'> '"+sText+"'</span>");
			driver.quit();
		}
		return blnResult;
	}

	//********************************MoveToElement***********************************************************
	public void navigateMouseToElement(WebDriver driver,By sObjectType,String sInputval,BaseTest basetest) throws Exception
	{
		try {

			WebElement webElement = driver.findElement(sObjectType);
			Actions builder = new Actions(driver);
			//builder = new Actions(driver);
			builder.moveToElement(webElement).perform();
			Thread.sleep(5000);
			System.out.println(" Mouse hovered to "+sInputval);
			Verify(" Mouse hovered to "+sInputval,"PASSED",basetest);
		}catch(Exception e) {
			Verify(sInputval+" is not found","FAILED",basetest);
			String FileName = screen .getScreenshot(driver);
			basetest.test.log(Status.FAIL,""+sInputval+" is not found <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			driver.quit();
		}

	}



	//**********************************isElementExistTime*********************************************************
	public boolean WaitUntilElement(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {

							blnResult=true;
							System.out.println("Loading Popup:="+i);
							Verify(sText+" is Displayed ","PASSED");
							break;

						}
					}


				}
				if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;
				}else {
					System.out.println(sText);
				}
			}while ((blnResult!=true) && (iTimer!=30));
			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is Not Displayed ","FAILED");
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sText+" is Not Displayed  <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			}
		}catch(Exception e) {
			Verify(sText+" is not found","FAILED");
			driver.quit();
		}
		return blnResult;
	}


	//=============================================SetvalueEnter=========================================================>
	public  boolean SetValEnter(WebDriver driver,By sObjectType,String sInputVal,String sText,BaseTest basetest) {
		boolean blnResult=false;
		int iTimer=0;
		try {
			do {
				List<WebElement> sList=driver.findElements(sObjectType);
				if(sList.size()>0) {
					for(int i=0;i<=sList.size();i++) 
					{
						if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
							sList.get(i).clear();
							sList.get(i).sendKeys(sInputVal);
							Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);
							robot.delay(200);
							blnResult=true;
							Verify(sInputVal+"is Entered  in "+sText,"PASSED",basetest);
							break;
						}
					}
				}
				if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;
				}else
					System.out.println(sText);
			}while ((blnResult!=true) && (iTimer!=30));

			//Flag returns false
			if(blnResult!=true) {
				Verify(sText+" is not found","FAILED",basetest);
				String FileName = screen .getScreenshot(driver);
				basetest.test.log(Status.FAIL,""+sInputVal+" is not Entered/Selected  <br> <a href='..\\target\\Screenshots\\"+FileName+".png'>Screenshot</a>");
			}
		}catch(Exception e) {
			Verify(sText+"  is not found","FAILED",basetest);
			basetest.test.log(Status.FAIL,"<span style='font-weight:bold;color:blue'>'"+sText+"' is not Entered/Selected "+"'</span>");
			driver.quit();
		}
		return blnResult;
	}

	//===============================================is Alert Present===============================================================================>
	public boolean isAlertPresent(WebDriver driver) {

		boolean presentFlag = false;

		try {

			// Check the presence of alert
			Alert alert = driver.switchTo().alert();
			// Alert present; set the flag
			presentFlag = true;
			// if present consume the alert
			alert.accept();
			//( Now, click on ok or cancel button )

		} catch (NoAlertPresentException ex) {
			// Alert not present
			//  ex.printStackTrace();
			System.out.println("Alert not found");
		}

		return presentFlag;
	}


	//===============================================is Alert Present===============================================================================>
	public String getAlertText(WebDriver driver, String StepDetails, BaseTest basetest) {

		boolean presentFlag = false;
		String text = null;

		try {

			// Check the presence of alert
			Alert alert = driver.switchTo().alert();
			text=alert.getText();

			System.out.println(text);
			
			// Alert present; set the flag
			presentFlag = true;
			// if present consume the alert
			alert.accept();
			//( Now, click on ok or cancel button )
			basetest.test.log(Status.PASS, StepDetails);

		} catch (NoAlertPresentException ex) {
			// Alert not present
			//  ex.printStackTrace();
			System.out.println("Alert not found");
			basetest.test.log(Status.FAIL, StepDetails);
		}

		return text;
	}


	//=================================================get specfic number in a string=======================================>
	public  String extractNumber(String text,String sText) {
		String ID = "null";
		String s = text;
		Pattern p = Pattern.compile("(?<="+sText+"=)\\d+");
		java.util.regex.Matcher m = p.matcher(s);
		if (m.find()) {
			ID = ((MatchResult) m).group();
			System.out.println(ID);

		}
		return ID;
	}


	//========================================================timezone================================================================>

	public CharSequence timezone() {
		CharSequence time = "null";
		Instant instant = Instant.now();
		ZoneId zoneId = ZoneId.of( "UTC-08:00" );
		ZonedDateTime zdt = ZonedDateTime.ofInstant( instant, zoneId );

		String timeone = zdt.toString();
		System.out.println(timeone);
		time = timeone.subSequence(0, ((String) timeone).lastIndexOf("["));
		System.out.println(time);


		return time;


	}

	//======================================================get date and time==================================================================>

	public String getDateAndTime() {
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);

		date =sdf.format(c.getTime());

		return date;
	}

	//==========================================ClickElementJs=======================================================================>
	public void ClickElementJs(WebDriver driver, String xpathExpress,BaseTest basetest)
	{

		WebElement Object = driver.findElement(By.xpath(xpathExpress));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", Object);


	}


	//================================================================================================>	
	/*##############################################################
	 @Descriptions --- encryptXOR(String message, String key)
	 @param driver--WebDriver
	 @param - Uses provided key to encrypt provided message using simple XOR
	 ##############################################################*/

	public static String encryptXOR(String message, String key){

		try {
			if (message==null || key==null ) return null;

			char[] keys=key.toCharArray();
			char[] mesg=message.toCharArray();

			int ml=mesg.length;
			int kl=keys.length;
			char[] newmsg=new char[ml];

			for (int i=0; i<ml; i++){
				newmsg[i]=(char)(mesg[i]^keys[i%kl]);
			}
			mesg=null; 
			keys=null;
			return new String(new BASE64Encoder().encodeBuffer(new String(newmsg).getBytes()));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}	

	/*##############################################################
	 @Descriptions --- decryptXOR(String message, String key)
	 @param driver--WebDriver
	 @param -Uses provided key to decrypt provided message encrypted using the same key
	 ##############################################################*/

	public static String decryptXOR(String message, String key){
		try {
			if (message==null || key==null ) return null;
			BASE64Decoder decoder = new BASE64Decoder();
			char[] keys=key.toCharArray();
			char[] mesg=new String(decoder.decodeBuffer(message)).toCharArray();

			int ml=mesg.length;
			int kl=keys.length;
			char[] newmsg=new char[ml];

			for (int i=0; i<ml; i++){
				newmsg[i]=(char)(mesg[i]^keys[i%kl]);
			}
			mesg=null; keys=null;
			return new String(newmsg);
		}
		catch ( Exception e ) {
			return null;
		}  
	}

	//==========================================================================================>
	public class GenericUtils {
		BaseTest basetest;

		public  void Verify(String StepDetails,String sStatus) {

			if(sStatus.equalsIgnoreCase("PASSED")) {
				System.out.println(StepDetails);
				//basetest.test.log(Status.PASS,  StepDetails);
				Assert.assertTrue(StepDetails, true);
			}else {
				System.out.println(StepDetails);
				//basetest.test.log(Status.FAIL,  StepDetails);
				Assert.assertTrue(StepDetails, false);			
			}
		}

		public  void Verify(String StepDetails,String sStatus,BaseTest basetest) {

			if(sStatus.equalsIgnoreCase("PASSED")) {
				System.out.println(StepDetails);
				basetest.test.log(Status.PASS, StepDetails);			
				Assert.assertTrue(StepDetails, true);
			}else {
				System.out.println(StepDetails);
				basetest.test.log(Status.FAIL, StepDetails);		
				Assert.assertTrue(StepDetails, false);			
			}
		}

		//********************************CLICK BUTTON***********************************************************
		public  boolean clickButton(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
			boolean blnResult=false;
			int iTimer=0;
			try {
				do {
					List<WebElement> sList=driver.findElements(sObjectType);
					if(sList.size()>0) {
						for(int i=0;i<=sList.size();i++) 
						{
							if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
								sList.get(i).click();
								blnResult=true;

								Verify(sText+":=  Element clicked ","PASSED",basetest);
								break;

							}
						}


					}
					if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
				}while ((blnResult!=true) && (iTimer!=30));
				//Flag returns false
				if(blnResult!=true) {
					Verify(sText+":=  Element not found","FAILED",basetest);
				}
			}catch(Exception e) {
				Verify(sText+":=  Element not found","FAILED",basetest);
				driver.quit();
			}
			return blnResult;
		}


		//********************************SET Value***********************************************************
		public  boolean SetVal(WebDriver driver,By sObjectType,String sInputVal,BaseTest basetest) {
			boolean blnResult=false;
			int iTimer=0;
			try {
				do {
					List<WebElement> sList=driver.findElements(sObjectType);
					if(sList.size()>0) {
						for(int i=0;i<=sList.size();i++) 
						{
							if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
								sList.get(i).clear();
								sList.get(i).sendKeys(sInputVal);
								blnResult=true;
								Verify("Value entered :="+sInputVal,"PASSED",basetest);
								break;

							}
						}


					}
					if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
				}while ((blnResult!=true) && (iTimer!=30));
				//Flag returns false
				if(blnResult!=true) {
					Verify("Element not found:="+sObjectType,"FAILED",basetest);
				}	
			}catch(Exception e) {
				Verify("Element not found:="+sObjectType,"FAILED",basetest);
				driver.quit();
			}
			return blnResult;
		}
		//**********************************isElementExist*********************************************************
		public boolean isElementExist(WebDriver driver,By sObjectType,String sText,BaseTest basetest) {
			boolean blnResult=false;
			int iTimer=0;
			try {
				do {
					List<WebElement> sList=driver.findElements(sObjectType);
					if(sList.size()>0) {
						for(int i=0;i<=sList.size();i++) 
						{
							if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {

								blnResult=true;
								Verify(sText+":=  Element displayed ","PASSED",basetest);
								break;

							}
						}


					}
					if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
				}while ((blnResult!=true) && (iTimer!=30));
				//Flag returns false
				if(blnResult!=true) {
					Verify(sText+":=  Element not found","FAILED",basetest);
				}
			}catch(Exception e) {
				Verify(sText+":=  Element not found","FAILED",basetest);
				driver.quit();
			}
			return blnResult;
		}
		//======================================================================================================>

		//********************************MoveToElement***********************************************************
		public void moveToElement(WebDriver driver,By sObjectType,BaseTest basetest)
		{
			try {

				WebElement webElement = driver.findElement(sObjectType);
				Actions builder = new Actions(driver);
				//builder = new Actions(driver);
				builder.moveToElement(webElement).perform();
				Thread.sleep(1000);
				System.out.println(" Mouse hovered:="+sObjectType);
			}catch(Exception e) {
				Verify("Element not found:="+sObjectType,"FAILED",basetest);
				driver.quit();
			}

		}



		//******************************************window handling*************************************************************

		public void switchToNewWindow( WebDriver driver,int windowNumber) {
			Set < String > s = driver.getWindowHandles();   
			Iterator < String > ite = s.iterator();
			int i = 1;
			while (ite.hasNext() && i < 10) {
				String popupHandle = ite.next().toString();
				driver.switchTo().window(popupHandle);
				//    System.out.println("Window title is : "+driver.getTitle());
				int windowCount = 0;
				if (i == windowCount) break;
				i++;
			}
		}

		//**********************************isElementExistTime*********************************************************
		public boolean WaitUntilElement(WebDriver driver,By sObjectType,String sText) {
			boolean blnResult=false;
			int iTimer=0;
			try {
				do {
					List<WebElement> sList=driver.findElements(sObjectType);
					if(sList.size()>0) {
						for(int i=0;i<=sList.size();i++) 
						{
							if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {

								blnResult=true;
								System.out.println("Loading Popup:="+i);
								Verify(sText+":=  Element displayed ","PASSED");
								break;

							}
						}


					}
					if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
				}while ((blnResult!=true) && (iTimer!=60));
				//Flag returns false
				if(blnResult!=true) {
					//Verify(sText+":=  Element not found","FAILED");
				}
			}catch(Exception e) {
				Verify(sText+":=  Element not found","FAILED");
				driver.quit();
			}
			return blnResult;
		}
		//======================================================================================================>
		public  boolean SetValEnter(WebDriver driver,By sObjectType,String sInputVal,BaseTest basetest) {
			boolean blnResult=false;
			int iTimer=0;
			try {
				do {
					List<WebElement> sList=driver.findElements(sObjectType);
					if(sList.size()>0) {
						for(int i=0;i<=sList.size();i++) 
						{
							if(sList.get(i).isDisplayed() && sList.get(i)!=null && sList.get(i).isEnabled()) {
								sList.get(i).clear();
								sList.get(i).sendKeys(sInputVal);
								Robot robot = new Robot();
								robot.keyPress(KeyEvent.VK_ENTER);
								robot.keyRelease(KeyEvent.VK_ENTER);

								robot.delay(200);
								blnResult=true;
								Verify("Value entered :="+sInputVal,"PASSED",basetest);
								break;

							}
						}


					}
					if(!blnResult) {Thread.sleep(1000);iTimer=iTimer+1;}
				}while ((blnResult!=true) && (iTimer!=30));
				//Flag returns false
				if(blnResult!=true) {
					Verify("Element not found:="+sObjectType,"FAILED",basetest);
				}	
			}catch(Exception e) {
				Verify("Element not found:="+sObjectType,"FAILED",basetest);
				driver.quit();
			}
			return blnResult;
		}
		//==============================================================================================================================>
	}	
	public boolean isAlertPresents(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		}// try
		catch (Exception e) {
			return false;
		}// catch
	}

	//==============================================================================================================================>
	public void waiForPageLoad(int seconds) {
		try {
			Thread.sleep(1000*seconds);

		}// try
		catch (Exception e) {

		}// catch
	}

}







