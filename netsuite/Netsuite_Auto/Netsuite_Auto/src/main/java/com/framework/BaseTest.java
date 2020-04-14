package com.framework;
import org.testng.ITestResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileOutputStream;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
public class BaseTest{
	public static int TestCasePassCount = 0, TestCaseFailCount = 0;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test; 
	@BeforeTest
	public void getTest(String ClassName, String FunctionalName) throws IOException
	{
		BaseReport B1 = new BaseReport();

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"//target//"+ClassName+".html");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("iHeartMedia_"+FunctionalName);
		//htmlReporter.config().setDocumentTitle("Automation Report");
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
	}
	@AfterMethod
	public void getResult(ITestResult result)
	{
		System.out.println(result.getName() + " Script Execution is Completed");
		if(result.getStatus() == ITestResult.FAILURE)
		{

			// test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
			//test.fail(result.getThrowable());
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			TestCasePassCount = TestCasePassCount + 1;
			//  test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
		}
		else
		{
			TestCaseFailCount = TestCaseFailCount + 1;
			//test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
			//test.skip(result.getThrowable());
		}
	}
	@AfterSuite
	public void tearDown() throws IOException{
		try {
			String HTMLContent = "<html><head><title>API Automation - Test Results</title></head>";
			//append body
			HTMLContent = HTMLContent + "<body>";
			//append table
			HTMLContent = HTMLContent + "Test Automation Execution Report for Build <b># ${BUILD_NUMBER}</b>";
			//append row
			HTMLContent = HTMLContent +  "<br>";
			//append row
			HTMLContent = HTMLContent + "Last execution report is attached in email. Please find the attached status report fore more details.";
			//append row
			HTMLContent = HTMLContent + "<table border = 1>";
			HTMLContent = HTMLContent + "<tr><td><b>Pass Count</b></td><td><b>Fail Count</b></td></tr>";
			HTMLContent = HTMLContent + "<tr><td>"+TestCasePassCount+"</td><td>"+TestCaseFailCount+"</td></tr>";
			//close html file
			HTMLContent = HTMLContent + "</table></body></html>";

			OutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") +"//target//HTMLEmailableReport.html");
			byte b[]=HTMLContent.getBytes();
			outputStream.write(b);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
