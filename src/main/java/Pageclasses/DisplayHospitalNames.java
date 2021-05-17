package Pageclasses;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import setup.ReadProperties;
import utils.ExcelData;

public class DisplayHospitalNames extends setup.Base {

	static WebDriver driver;
	Properties prop;
	WebDriverWait wait;
	String filePath = System.getProperty("user.dir") + "\\Resources\\properties\\config.properties";
	String path = System.getProperty("user.dir") + ".\\resources\\ExcelData\\FindingHospital.xlsx";
	
	public DisplayHospitalNames() {

	}

	// Receiving the driver instance using constructor
	public DisplayHospitalNames(WebDriver driver) {
		DisplayHospitalNames.driver = driver;
		prop = ReadProperties.readFile(filePath);
		wait = new WebDriverWait(driver, 5000);

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

	// for city
	public void selectLocation() {
		try {

			logger.log(Status.INFO, "Selecting location as Bangalore");
			WebElement location = getElement(ExcelData.readExcelXpath(path, 1, 1));
			location.click();

			driver.findElement(By.xpath("//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/span[2]/span/i")).click();

			// Enter location
			location.sendKeys(ExcelData
					.readExcel(System.getProperty("user.dir") + "\\resources\\ExcelData\\FindingHospital.xlsx", 1, 0));

			getElement(ExcelData.readExcelXpath(path, 2, 1)).click();
			reportPass("Bangalore selected successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

//Locate Hospital
	public void selectHospital() {
		try {
			logger.log(Status.INFO, "Searching Hospital");

			WebElement type = getElement(ExcelData.readExcelXpath(path, 3, 1));
			type.sendKeys("Hospital");
			getElement(ExcelData.readExcelXpath(path, 4, 1)).click();
			reportPass("Hospital selected successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// Applying filters
	// open 24X7 hospitals
	public void applyFilters() {

		try {
			logger.log(Status.INFO, "clicking 24X7 checkbox");
			getElement(ExcelData.readExcelXpath(path, 5, 1)).click();
			reportPass("24X7 checkbox clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		// Has parking
		boolean staleElement = true;

		while (staleElement) {
			try {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				
				driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
				getElement(ExcelData.readExcelXpath(path, 6, 1)).click();

				staleElement = false;
			} catch (StaleElementReferenceException e) {
				staleElement = true;
			}
		}

		try {
			logger.log(Status.INFO, "Clicking has parking checkbox");
			WebElement parking = getElement(ExcelData.readExcelXpath(path, 7, 1));
			parking.click();
			reportPass("Successfully clicked has parking checkbox");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void hospitals() {

		try {
			logger.log(Status.INFO, "Selecting hospitals with above 3.5 rating");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			System.out.println("***********HospitalNames****************");

			List<WebElement> ratings = getElements(ExcelData.readExcelXpath(path, 8, 1));
			int n = ratings.size();
			int count = 0;
			String[] hospitals = new String[30];
			List<WebElement> name = getElements(ExcelData.readExcelXpath(path, 9, 1));
			for (int i = 0; i < n; i++) {
				String[] r = new String[n];
				driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
				r[i] = ratings.get(i).getText();
				double value = Double.parseDouble(r[i].replaceAll("[^0-9]", ""));
				if (value > 3.5) {

					System.out.println(name.get(i).getText());

					hospitals[count] = name.get(i).getText();
					count++;
				}
			}
			try {
				// Writing hospital names in the Output.xlsx file
				ExcelData.writeExcelData(System.getProperty("user.dir") + "\\Output\\Output.xlsx", hospitals);
			} catch (IOException e) {
				e.printStackTrace();
			}
			reportPass("Successfully selected hospitals with above 3.5 rating");
			reportPass("Successfully hospital names are printed");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void Back() {
		// navigate to home
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

	}

	public static DisplayTopCities nextPage() {
		// sending driver to next page
		return PageFactory.initElements(driver, DisplayTopCities.class);
	}
}
