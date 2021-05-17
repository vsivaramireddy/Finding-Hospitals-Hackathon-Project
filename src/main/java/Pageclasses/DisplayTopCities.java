package Pageclasses;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import setup.ReadProperties;
import utils.ExcelData;

public class DisplayTopCities extends setup.Base {

	static WebDriver driver;
	Properties prop;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\Resources\\properties\\config.properties";
	String Excelpath = System.getProperty("user.dir") + ".\\resources\\ExcelData\\FindingHospital.xlsx";

	public DisplayTopCities() {

	}

	// Receiving the driver instance using constructor
	public DisplayTopCities(WebDriver driver) {
		DisplayTopCities.driver = driver;
		prop = ReadProperties.readFile(filePath);
		wait = new WebDriverWait(driver, 10000);

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

	public List<WebElement> getElements(String path) {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(path))));
		return driver.findElements(By.xpath(path));
	}

	public void Back() {
		// navigate to home
		driver.navigate().back();
	}

	// click on diagnostics
	public void clickOnDiagnostics() {
		try {

			logger.log(Status.INFO, "Clicking on Diagnostics");

			WebElement diag = wait
					.until(ExpectedConditions.visibilityOf(getElement(ExcelData.readExcelXpath(Excelpath, 10, 1))));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", diag);

			reportPass("Successfully clicked on Diagnostics");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void topcities() {
		try {
			// display top cities
			System.out.println("***********Top cities****************");

			driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);

			try {
				List<WebElement> topcities = getElements(ExcelData.readExcelXpath(Excelpath, 11, 1));
				printingCities(topcities);
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				List<WebElement> topcities = getElements(ExcelData.readExcelXpath(Excelpath, 11, 1));
				printingCities(topcities);
			}
			reportPass("Successfully printed top cities");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	private void printingCities(List<WebElement> topcities) {

		int count = 0;
		String[] topCitiesNames = new String[30];
		for (WebElement tc : topcities) {

			System.out.println(tc.getText());
			topCitiesNames[count] = tc.getText();
			count++;
		}
		try {
			// writing Topcities in Output.xlsx
			ExcelData.writeExcelTopCitiesData(System.getProperty("user.dir") + "\\Output\\Output.xlsx", topCitiesNames);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static FillDetailsAndCaptureMessage nextPage() {
		// sending driver to next page
		return PageFactory.initElements(driver, FillDetailsAndCaptureMessage.class);
	}

}
