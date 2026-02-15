package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener

{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String reportName;

	public void onStart(ITestContext context)

	{

		/*
		 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
		 * LocalDateTime now = LocalDateTime.now(); reportName = "Test-Report-" +
		 * dtf.format(now) + ".html";
		 */

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt=
		 * new Date(); String currentdatetimestamp=df.format(dt);
		 */

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\Reports\\" + reportName);

		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Computer Name", "Local Host");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester", "Akash");
		extent.setSystemInfo("OS", "Windows 11");
		extent.setSystemInfo("Browser name", "Chrome");
		extent.setSystemInfo("User name", System.getProperty("user.name"));

		// OS from XML
		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating system", os);

		// Browser from XML
		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		// group from the XMl

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result)

	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());  //to display groups in report
		test.log(Status.PASS, result.getName()+"got successfully executed");

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+  "Got failed"); 
		test.log(Status.FAIL, result.getThrowable().getMessage());
		
		try 
		{
			String imgpath = new BaseClass().CaptureScreen(result.getName());
			test.addScreenCaptureFromPath(imgpath);
			System.out.println("Screen shot path: "+imgpath);
		}catch(IOException e1)
		{
			e1.printStackTrace();
		}
		
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+"got Skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

/*	public void onFinish(ITestContext context) {
		extent.flush();
		
		
		String pathofExtentReport= System.getProperty("user.dir")+"\\reports\\"+reportName;
		File extentReport= new File(pathofExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/	
	public void onFinish(ITestContext context) {

	    extent.flush();

	    String path = System.getProperty("user.dir") + "\\reports\\" + reportName;
	    File reportFile = new File(path);

	    try {

	        int retry = 0;

	        while (!reportFile.exists() && retry < 10) {
	            Thread.sleep(1000);
	            retry++;
	        }

	        if (reportFile.exists()) {
	            Desktop.getDesktop().browse(reportFile.toURI());
	        } else {
	            System.out.println("Report not found even after waiting");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
		
	/*	//Send report by mail
		try {

		    String reportPath = System.getProperty("user.dir")+ File.separator + "reports"  + File.separator + reportName;

		    URL url = new URL("file:///" + reportPath);

		    ImageHtmlEmail email = new ImageHtmlEmail();
		    email.setDataSourceResolver(new DataSourceUrlResolver(url));

		    email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(465);

		    email.setAuthenticator(new DefaultAuthenticator(
		            "sender@gmail.com",
		            "gmail_app_password"));

		    email.setSSLOnConnect(true);

		    email.setFrom("sender@gmail.com");
		    email.setSubject("Automation Test Results");

		    email.setMsg("Hi, Please find attached Extent Report.");

		    email.addTo("receiver@gmail.com");

		    email.attach(url, "Extent Report", "Automation Execution Report");

		    email.send();

		    System.out.println("Email Sent Successfully");

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		*/
	}


