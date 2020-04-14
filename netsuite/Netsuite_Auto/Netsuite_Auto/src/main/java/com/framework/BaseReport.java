package com.framework;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
//import Framework.DataBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.*;
import java.sql.SQLException;
import java.nio.charset.Charset;

public class BaseReport {
	public static int TestCasePassCount = 0, TestCaseFailCount = 0;
	public static String LogString = "";
	public static int ExecutionID;
	static List<String> list;
	public Date date1, date2;

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws IOException, ClassNotFoundException, SQLException {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		date1 = new Date();
		DeleteFile(System.getProperty("user.dir") + "//target//HTMLEmailableReport.html");
		File folder = new File(System.getProperty("user.dir") + "//target");
		File[] listOfFiles = folder.listFiles();
		/*
		 * for (int i = 0; i < listOfFiles.length; i++) {
		 * if(listOfFiles[i].isDirectory()) { File subfolder = new
		 * File(System.getProperty("user.dir") +"\\target\\"+ listOfFiles[i].getName());
		 * File[] FilesList = subfolder.listFiles(); for(int j=0;j< FilesList.length; j
		 * ++) { FilesList[j].delete(); } listOfFiles[i].delete(); } }
		 */
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy hh mm ss");
		String time = dateFormat.format(now);
		time = time.replace(" ", "");
		File dir = new File(System.getProperty("user.dir") + "//target//Results" + time);
		dir.mkdir();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				Path temp = Files.move(
						Paths.get(System.getProperty("user.dir") + "//target//" + listOfFiles[i].getName()),
						Paths.get(System.getProperty("user.dir") + "//target//Results" + time + "//"
								+ listOfFiles[i].getName()));
			}
		}
		// Get the String representation of this LocalDateTime
	}

	@AfterMethod(alwaysRun = true)
	public void getResult() {
		System.out.println(" Script Execution is Completed");
		TestCasePassCount = 0;
		TestCaseFailCount = 0;
		LogString = "";
		PrepareConsolidatedReport();
		prepareEmailableReport();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() throws IOException {
		try {
			TestCasePassCount = 0;
			TestCaseFailCount = 0;
			LogString = "";
			PrepareConsolidatedReport();
			prepareEmailableReport();
			replaceFileCount();
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PrepareConsolidatedReport() {
		BufferedReader reader;
		String ReportName = "";
		String StartTime = "", EndTime = "";
		try {
			System.out.println("PrepareConsolidatedReport() is called");
			File folder = new File(System.getProperty("user.dir") + "//target");
			File[] listOfFiles = folder.listFiles();
			int numberofFile = 0;
			File fout = new File(System.getProperty("user.dir") + "//target//index.html");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile() && !listOfFiles[i].getName().contains("HTMLEmailableReport") && !listOfFiles[i].getName().contains("index")) {
					String getPassCount = "";
					String getFailCount = "";
					boolean FailedStepFound = false, ScriptStarted = false;
					String failedSteps = "";
					Charset charset = Charset.defaultCharset();
					StartTime = "";
					EndTime = "";
					if (numberofFile == 0) {
						boolean StartLogging = true, CollectionStarted = false;
						List<String> allLines = Files.readAllLines(
								Paths.get(System.getProperty("user.dir") + "\\target\\" + listOfFiles[i].getName()),
								charset);
						boolean logNextStep = false;
						for (String line : allLines) {
							if (line.contains("test-collection"))
								CollectionStarted = true;
							if (CollectionStarted == true && line.contains("/ul"))
								StartLogging = false;
							if (line.contains("test(s) passed")) {
								getPassCount = StringUtils.substringBetween(line, "<span class='strong'>",
										"</span> test(s) passed");
								TestCasePassCount = TestCasePassCount + Integer.parseInt(getPassCount);
							}
							if (line.contains("test(s) failed")) {
								getFailCount = StringUtils.substring(line, line.indexOf(">") + 1,
										line.indexOf("</span>"));
								TestCaseFailCount = TestCaseFailCount + Integer.parseInt(getFailCount);
							}
							if (line.contains("report-name'")) {
								ReportName = StringUtils.substringBetween(line, "<span class='report-name'>",
										"</span>");
							}
							if (line.contains("status fail"))
								FailedStepFound = true;
							if (line.contains("passParent"))
								System.out.println(line);
							if (line.contains("test(s) passed"))
								line = "<span class='tooltipped' data-position='top' data-tooltip='50%'><span class='strong'>##testspassed</span> test(s) passed</span>";
							if (line.contains("test(s) failed"))
								line = "<span class='strong tooltipped' data-position='top' data-tooltip='50%'>##testsfailed</span> test(s) failed, <span class='strong tooltipped' data-position='top' data-tooltip='0%'>0 </span> others";
							if (line.contains("report-name'"))
								line = "<span class='report-name'>Consoidated Report</span>";
							if (line.contains("step(s) passed"))
								line = "<span class='tooltipped' data-position='top' data-tooltip='11.765%'><span class='strong'></span>";
							if (line.contains("step(s) failed"))
								line = "<span class='strong tooltipped' data-position='top' data-tooltip='1.961%'></span>";
							if (line.contains("<td class='timestamp'>") && ScriptStarted == false) {
								StartTime = StringUtils.substringBetween(line, "<td class='timestamp'>", "</td>");
								ScriptStarted = true;
							}
							if (line.contains("<td class='timestamp'>") && ScriptStarted == true) {
								EndTime = StringUtils.substringBetween(line, "<td class='timestamp'>", "</td>");
							}

							if (line.contains("</span><br>") && logNextStep == true) {
								failedSteps = failedSteps + line.substring(0, line.indexOf("</span><br>"));
								logNextStep = false;
							} else if (logNextStep == true)
								failedSteps = failedSteps + line;
							if (line.contains("step-details") && FailedStepFound == true) {
								if (!line.contains("selenium")) {
									if (line.contains("<br>"))
										failedSteps = failedSteps + StringUtils.substringBetween(line,
												"<td class='step-details'>", "<br>") + "<br>";
									else if (line.contains("</td>"))
										failedSteps = failedSteps + StringUtils.substringBetween(line,
												"<td class='step-details'>", "</td>") + "<br>";
									else if (line.contains("<td class='step-details'>"))
										failedSteps = failedSteps + line.replace("<td class='step-details'>", "") + "<br>";
									else if (line.contains("</span><span style='font-weight:bold;color:blue'>")) {
										line = line.replace("<td class='step-details'>", "");
										line = line.replace("</span><span style='font-weight:bold;color:blue'>", "");
										line = line.replace("<span style=\"font-weight:bold;color:blue\">", "");
										line = line.replace("<br>", "");
										line = line.replace("<table>", "");
										line = line.replace("<tbody>", "");
										line = line.replace("<tr>", "");
										line = line.replace("<td>", "");
										line = line.replace("</span>", "");

										failedSteps = failedSteps + line + "<br>";
										logNextStep = true;
									} else {
										failedSteps = failedSteps + line + "<br>";
									}

								}
								FailedStepFound = false;
							}
							if ((line.contains("<div class='left panel-name'>Steps</div>")
									|| line.contains("step(s) failed") || line.contains("step(s) passed")))
								System.out.println("skip statements");
							else {
								if (StartLogging == true) {
									bw.write(line);
									bw.newLine();
								}
							}
						}
					} else {
						List<String> allLines = Files.readAllLines(
								Paths.get(System.getProperty("user.dir") + "\\target\\" + listOfFiles[i].getName()),
								charset);
						boolean StartLogging = false;
						for (String line : allLines) {
							if (line.contains("status fail"))
								FailedStepFound = true;
							if (line.contains("step-details") && FailedStepFound == true) {
								if (!line.contains("selenium")) {
									if (line.contains("<br>"))
										failedSteps = failedSteps + StringUtils.substringBetween(line,
												"<td class='step-details'>", "<br>") + "<br>";
									else
										failedSteps = failedSteps + StringUtils.substringBetween(line,
												"<td class='step-details'>", "</td>") + "<br>";
								}
								FailedStepFound = false;
							}
							if (StartLogging == true) {
								bw.write(line);
								bw.newLine();
							}
							if (line.contains("test(s) passed")) {
								getPassCount = StringUtils.substringBetween(line, "<span class='strong'>",
										"</span> test(s) passed");
								TestCasePassCount = TestCasePassCount + Integer.parseInt(getPassCount);
							}
							if (line.contains("test(s) failed")) {
								getFailCount = StringUtils.substring(line, line.indexOf(">") + 1,
										line.indexOf("</span>"));
								TestCaseFailCount = TestCaseFailCount + Integer.parseInt(getFailCount);
							}
							if (line.contains("report-name'")) {
								ReportName = StringUtils.substringBetween(line, "<span class='report-name'>",
										"</span>");

							}
							if (line.contains("test-collection"))
								StartLogging = true;
							if (line.contains("</ul>"))
								StartLogging = false;
							if (line.contains("<td class='timestamp'>") && ScriptStarted == false) {
								StartTime = StringUtils.substringBetween(line, "<td class='timestamp'>", "</td>");
								ScriptStarted = true;
							}
							if (line.contains("<td class='timestamp'>") && ScriptStarted == true) {
								EndTime = StringUtils.substringBetween(line, "<td class='timestamp'>", "</td>");
							}
						}
					}
					numberofFile = numberofFile + 1;
					String fileName = listOfFiles[i].getName();
					if (failedSteps.contains("<table>"))
						failedSteps = failedSteps.replace("<table>", "");
					if (failedSteps.contains("<tr>"))
						failedSteps = failedSteps.replace("<tr>", "");
					if (failedSteps.contains("<td>"))
						failedSteps = failedSteps.replace("<td>", "");
					if (failedSteps.contains("</table>"))
						failedSteps = failedSteps.replace("</table>", "");
					if (failedSteps.contains("</tr>"))
						failedSteps = failedSteps.replace("</tr>", "");
					if (failedSteps.contains("</td>"))
						failedSteps = failedSteps.replace("</td>", "");
					if (failedSteps.contains("<b>Field</b>"))
						failedSteps = failedSteps.replace("<b>Field</b>", "");
					LogString = LogString + "<tr><td>" + fileName.substring(0, fileName.length() - 5) + "</td><td>"
							+ ReportName + "</td><td>" + getPassCount + "</b></td><td>" + getFailCount + "</td><td>"
							+ failedSteps + "</td></tr>";
				}
				
				//<td>" + StartTime + "</td><td>" + EndTime + "</td>
			}
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<!-- subview left -->");
			bw.write("<div class='subview-right left'>");
			bw.write("<div class='view-summary'>");
			bw.write("<h5 class='test-name'></h5>");
			bw.write("<div id='step-filters' class='right'>");
			bw.write(
					"<span class='blue-text' status='info' alt='info' title='info'><i class='material-icons'>info_outline</i></span>");
			bw.write(
					"<span class='green-text' status='pass' alt='pass' title='pass'><i class='material-icons'>check_circle</i></span>");
			bw.write(
					"<span class='red-text' status='fail' alt='fail' title='fail'><i class='material-icons'>cancel</i></span>");
			bw.write(
					"<span class='red-text text-darken-4' status='fatal' alt='fatal' title='fatal'><i class='material-icons'>cancel</i></span>");
			bw.write(
					"<span class='pink-text text-lighten-1' status='error' alt='error' title='error'><i class='material-icons'>error</i></span>");
			bw.write(
					"<span class='orange-text' alt='warning' status='warning' title='warning'><i class='material-icons'>warning</i></span>");
			bw.write(
					"<span class='teal-text' status='skip' alt='skip' title='skip'><i class='material-icons'>redo</i></span>");
			bw.write(
					"<span status='clear' alt='Clear filters' title='Clear filters'><i class='material-icons'>clear</i></span>");
			bw.write("</div>");
			bw.write("          </div>");
			bw.write("</div>");
			bw.write("<!-- subview right -->");
			bw.write("</div>");
			bw.write("<!-- test view -->");
			bw.write("<!-- category view -->");
			bw.write("<!-- exception view -->");
			bw.write("<div id='dashboard-view' class='view hide'>");
			bw.write("<div class='card-panel transparent np-v'>");
			bw.write("<h5>Dashboard</h5>");
			bw.write("<div class='row'>");
			bw.write("<div class='col s2'>");
			bw.write("<div class='card-panel r'>");
			bw.write("Tests");
			bw.write("<div class='panel-lead'>2</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<div class='col s2'>");
			bw.write("<div class='card-panel r'>");
			bw.write("Steps");
			bw.write("<div class='panel-lead'>2</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<div class='col s2'>");
			bw.write("          <div class='card-panel r'>");
			bw.write("Start");
			bw.write("<div class='panel-lead'>Dec 20, 2018 10:47:24 PM</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<div class='col s2'>");
			bw.write("div class='card-panel r'>");
			bw.write("End");
			bw.write("<div class='panel-lead'>Dec 20, 2018 10:47:35 PM</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<div class='col s2'>");
			bw.write("<div class='card-panel r'>");
			bw.write("Time Taken");
			bw.write("<div class='panel-lead'>0h 0m 11s+762ms</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("</div>");
			bw.write("<!-- dashboard view -->");
			bw.write("<!-- testrunner-logs view -->");
			bw.write("</div>");
			bw.write("<!-- container -->");
			bw.write("<script>");
			bw.write("var statusGroup = {");
			bw.write("passParent: ##testspassed,");
			bw.write("failParent: ##testsfailed,");
			bw.write("fatalParent: 0,");
			bw.write("errorParent: 0,");
			bw.write("warningParent: 0,");
			bw.write("skipParent: 0,");
			bw.write("exceptionsParent: 2,");
			bw.write("passChild: 0,");
			bw.write("failChild: 0,");
			bw.write("fatalChild: 0,");
			bw.write("errorChild: 0,");
			bw.write("warningChild: 0,");
			bw.write("skipChild: 0,");
			bw.write("infoChild: 0,");
			bw.write("exceptionsChild: 2,");
			bw.write("passGrandChild: 0,");
			bw.write("failGrandChild: 0,");
			bw.write("fatalGrandChild: 0,");
			bw.write("errorGrandChild: 0,");
			bw.write("warningGrandChild: 0,");
			bw.write("skipGrandChild: 0,");
			bw.write("infoGrandChild: 0,");
			bw.write("exceptionsGrandChild: 0,");
			bw.write("};");
			bw.write("</script>");
			bw.write(
					"<script src='https://cdn.rawgit.com/anshooarora/extentreports-java/fca20fb7653aade98810546ab96a2a4360e3e712/dist/js/extent.js' type='text/javascript'></script>");
			bw.write("<script type='text/javascript'>");
			bw.write("</script>");
			bw.write("</body>");
			bw.write("</html>");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DeleteFile(String FilePath) {
		File file = new File(FilePath);
		if (file.delete()) {
			System.out.println("Consolidated File deleted successfully");
		} else {
			System.out.println("Consolidated Failed to delete the file");
		}

	}

	public void LogScenario(String ScriptName, int PassCount, int FailCount) {
		// LogString = LogString + "<tr><td><b>"+ ScriptName +"</b></td><td><b>"+
		// PassCount +"</b></td><td><b>"+ FailCount +"</b></td></tr>";
	}

	public void replaceFileCount() {
		try {
			File file = new File(System.getProperty("user.dir") + "\\target\\index.html");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "", oldtext = "";
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
			reader.close();
			// To replace a line in a file
			oldtext = oldtext.replaceAll("##testspassed", Integer.toString(TestCasePassCount));
			oldtext = oldtext.replaceAll("##testsfailed", Integer.toString(TestCaseFailCount));
			FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\target\\index.html");
			writer.write(oldtext);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void prepareEmailableReport() {
		try {
			System.out.println("After Suite Called");
			DeleteFile(System.getProperty("user.dir") + "//target//HTMLEmailableReport.html");
			String HTMLContent = "<html><head><title>iHM NetSuite Automation - Test Results</title></head>";
			// append body
			HTMLContent = HTMLContent + "<body>";
			// append table
			HTMLContent = HTMLContent + "Test Automation Execution Report for Build <b># ${BUILD_NUMBER}</b>";
			// append row
			HTMLContent = HTMLContent + "<br>";
			HTMLContent = HTMLContent
					+ "Last execution report is attached in email. Please find the attached status report fore more details.";
			HTMLContent = HTMLContent + "<br><br>";
			// HTMLContent = HTMLContent + "<table border = 1>";
			// long timetaken = (date2.getTime() - date1.getTime())/1000;
			// long minutues = timetaken/60;
			// long Seconds = timetaken - (minutues * 60);
			// HTMLContent = HTMLContent + "<tr><td bgcolor='#BDC3C7'><b>Time Taken for
			// Order Creation in eComm</b></td></tr><tr><td><b>"+Long.toString(minutues)+ "
			// minutes "+Long.toString(Seconds)+ " seconds "
			// +"</b></td></tr>";
			// close html file
			// HTMLContent = HTMLContent + "</table>";
			// append row

			// append row
			HTMLContent = HTMLContent + "<table border = 1>";
			int TotalCount = TestCasePassCount + TestCaseFailCount;
			HTMLContent = HTMLContent
					+ "<tr bgcolor='#BDC3C7'><td><b>Total Count</b></td><td><b>Pass Count</b></td><td><b>Fail Count</b></td></tr>";
			HTMLContent = HTMLContent + "<tr><td>" + Integer.toString(TotalCount) + "</td><td>"
					+ Integer.toString(TestCasePassCount) + "</td><td>" + Integer.toString(TestCaseFailCount)
					+ "</td></tr>";
			// close html file
			HTMLContent = HTMLContent + "</table>";
			HTMLContent = HTMLContent + "<br>";
			HTMLContent = HTMLContent + "<br>";
			HTMLContent = HTMLContent + "<table border = 1>";
			HTMLContent = HTMLContent
					+ "<tr bgcolor='#BDC3C7' align='center'><td><b>ScriptName</b></td><td><b>Functional Area</b></td><td><b>PassCount</b></td><td><b>FailCount</b></td><td><b>Failed Steps/Comments</b></td></tr>";
			HTMLContent = HTMLContent + LogString;
			HTMLContent = HTMLContent + "</table></body></html>";
			System.out.println(HTMLContent);
			OutputStream outputStream = new FileOutputStream(
					System.getProperty("user.dir") + "//target//HTMLEmailableReport.html");
			byte b[] = HTMLContent.getBytes();
			outputStream.write(b);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
