package Pageclasses;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
//import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import setup.ReadProperties;
import utils.ExcelData;

public class FillDetailsAndCaptureMessage extends setup.Base {
	static WebDriver driver;
	Properties prop;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\Resources\\properties\\config.properties";
	String Excelpath = System.getProperty("user.dir") + ".\\resources\\ExcelData\\FindingHospital.xlsx";

	public FillDetailsAndCaptureMessage() {

	}

	// Receiving the driver instance using constructor
	public FillDetailsAndCaptureMessage(WebDriver driver) {
		FillDetailsAndCaptureMessage.driver = driver;
		prop = ReadProperties.readFile(filePath);
		wait = new WebDriverWait(driver, 2000);

	}

	// Getting the values for given key from property file
	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	// locating the element
	public WebElement getElement(String path) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
		return driver.findElement(By.xpath(path));
	}

	public void Back() {
		// navigate to home
		driver.navigate().back();
	}

	public void selectCorporateWellness() {
		try {
			logger.log(Status.INFO, "Clicking on CoorporateWellness");

			// select corporate wellness from providers drop down
			getElement(ExcelData.readExcelXpath(Excelpath, 12, 1)).click();

			getElement(ExcelData.readExcelXpath(Excelpath, 13, 1)).click();
			reportPass("Successfully clicked on CoorporateWellness");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void windows() {
		// Handling windows
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String mainpage = it.next();
		String childpage = it.next();

		// move to new window
		driver.switchTo().window(childpage);
	}

	public void fillform() {
		// enter the details
		logger.log(Status.INFO, "Entering name");
		driver.findElement(By.id("name")).sendKeys(ExcelData
				.readExcel(System.getProperty("user.dir") + "\\resources\\ExcelData\\FindingHospital.xlsx", 1, 1));
		logger.log(Status.INFO, "Entering Organization name");
		driver.findElement(By.id("organization_name")).sendKeys(ExcelData
				.readExcel(System.getProperty("user.dir") + "\\resources\\ExcelData\\FindingHospital.xlsx", 1, 2));
		logger.log(Status.INFO, "Entering invalid email id");
		driver.findElement(By.id("official_email_id")).sendKeys(ExcelData
				.readExcel(System.getProperty("user.dir") + "\\resources\\ExcelData\\FindingHospital.xlsx", 1, 3));
		logger.log(Status.INFO, "Entering phone number");
		driver.findElement(By.id("official_phone_no")).sendKeys(ExcelData.readExcelNumeric(
				System.getProperty("user.dir") + "\\resources\\ExcelData\\FindingHospital.xlsx", 1, 4));
		logger.log(Status.INFO, "Clicking on Schedule a Demo button");
		driver.findElement(By.id("button-style")).click();

	}

	public void handleAlert() {
		try {

			logger.log(Status.INFO, "Capturing alert message");
			// move to alert and display the message
			Alert alt = driver.switchTo().alert();

			/*
			 * Robot rb=new Robot(); rb.keyPress(KeyEvent.VK_WINDOWS);
			 * rb.keyPress(KeyEvent.VK_PRINTSCREEN); rb.keyRelease(KeyEvent.VK_PRINTSCREEN);
			 * rb.keyRelease(KeyEvent.VK_WINDOWS);
			 */

			System.out.println("Alert message: " + alt.getText());
			alt.accept();

			reportPass("Successfully captured alert message");
		} catch (Exception e) {
			logger.log(Status.INFO, "Getting captcha instead of Alert");
			reportFail(e.getMessage());
		}
	}

	public void imageCapture() {
		// capturing the image
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileHandler.copy(screenshot, new File(".\\Output\\screenshot.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
