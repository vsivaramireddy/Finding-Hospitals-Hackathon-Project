package setup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Pageclasses.DisplayHospitalNames;
import utils.DateUtils;
import utils.ExtentReportManager;


public class Base {

	public static WebDriver driver;
	WebDriverWait wait;
	String filePath=System.getProperty("user.dir") + "\\Resources\\properties\\config.properties";
	Properties prop;
	public ExtentReports report =ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	//Creating softAssert object
	 SoftAssert softAssert=new SoftAssert();
		//Invoking Practo website
	@Test
	public WebDriver openBrowser(String browserName)
	{
		try {
		//To Invoke the browser
		driver=DriverSetup.getWebDriver(browserName);
		driver.manage().window().maximize();
		prop=ReadProperties.readFile(filePath);
		wait=new WebDriverWait(driver, 120);
		reportPass(browserName+" is opened successfully");
		openUrl();
		
		} catch(Exception e) {
			reportFail(e.getMessage());
		}
		return driver;
	}
	
	
	public String getPropertyValue(String key)
	{
		return prop.getProperty(key);
	}
	
	//Implementation code of invoking url
	public void openUrl()
	{
		try {
		//To open the Url 
		driver.get(getPropertyValue("baseUrl"));
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		reportPass(getPropertyValue("baseUrl")+" opened successfully");
		} catch(Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	
	public static DisplayHospitalNames nextPage1()
	{
		//sending driver to next page
		return PageFactory.initElements(driver, DisplayHospitalNames.class);
	}
	
	
	public void closeBrowser()
	{
		try {
		//close the browser
		driver.quit();
		reportPass("Browser closed successfully");
	}catch(Exception e) {
		reportFail(e.getMessage());
	}
	}
		
	
	// locating the element
			public WebElement getElement(String path) {
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
				return driver.findElement(By.xpath(path));
			}

			public List<WebElement> getElements(String path) {
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
				return driver.findElements(By.xpath(path));
			}
			//Assertion Functions
			public void assertTrue(boolean flag) {
			
					softAssert.assertTrue(flag);
			}
			
			public void assertFalse(boolean flag) {
				
				softAssert.assertFalse(flag);
			}
			
			public void assertEqual(String actual, String expected) {
				
				softAssert.assertEquals(actual,expected);
			}	
			//Reporting functions
			
			public static void reportFail(String reportString) {
				logger.log(Status.FAIL, reportString);
				takeScreenShotOnFailure();
				Assert.fail(reportString);
			}
			public static void reportPass(String reportString) {
			
				logger.log(Status.PASS,reportString);
			}
			
			public static void takeScreenShotOnFailure() {
				TakesScreenshot takeScreenshot=(TakesScreenshot) driver;
				File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
				
				File destFile = new File(System.getProperty("user.dir")+"//Screenshots//"+DateUtils.getTimeStamp()+".png");
				try {
					FileUtils.copyFile(sourceFile, destFile);
					logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"//Screenshots//"+DateUtils.getTimeStamp()+".png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
}
