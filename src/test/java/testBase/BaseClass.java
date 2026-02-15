package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "sanity", "Master", "Regression" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) throws IOException, InterruptedException {

		// loading config.properties file
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);

		//System.out.println("URL from property = " + p.getProperty("appURL"));
		logger = LogManager.getLogger(this.getClass()); // log4j2

		// OS
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			String huburl = "http://localhost:4444/wd/hub";

			DesiredCapabilities cap = new DesiredCapabilities();

			if (os.equalsIgnoreCase("windows"))
			{
				cap.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) 
			{
				cap.setPlatform(Platform.MAC);
			} 
			else if (os.equalsIgnoreCase("linux"))
			{
				cap.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("No matching os");
				return;
			}

			// Browser
			switch (br.toLowerCase()) {
			case "chrome":
				cap.setBrowserName("chrome");
				break;
			case "edge":
				cap.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				cap.setBrowserName("firefox");
				break;
			default:
				System.out.println("Invalid browser name..");
				return;
			}
             System.out.println(huburl);
			 driver = new RemoteWebDriver(URI.create(huburl).toURL(), cap);
		}

		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid browser name..");
				return;
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
		Thread.sleep(2000);
		// driver.get("http://localhost/StoreName/index.php?route=common/home");
		driver.get(p.getProperty("appURL")); // reading url from properties file
		System.out.println("URL from property = " + p.getProperty("appURL"));
		driver.manage().window().maximize();
	}

	@AfterClass(groups = { "sanity", "Master", "Regression" })
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		String generateString = RandomStringUtils.randomAlphabetic(5);
		return generateString;
	}

	public String randomNumber() {
		String generateNumber = RandomStringUtils.randomNumeric(10);
		return generateNumber;
	}

	public String randomAlphaNumeric() {
		String generateString = RandomStringUtils.randomAlphabetic(3);
		String generateNumber = RandomStringUtils.randomNumeric(3);
		return (generateString + "@" + generateNumber);
	}

	public String CaptureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		// sourceFile.renameTo(targetFile);
		FileUtils.copyFile(sourceFile, targetFile);

		return targetFilePath;

	}

}
