package TestCases;

import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Tasks.DisplayHospitalNames;
import Tasks.DisplayTopCities;
import Tasks.FillDetailsAndCaptureMessage;
import setup.Base;
import setup.ReadProperties;

public class RunTestCases extends setup.Base {
	//Opening Browser
	@BeforeSuite//(groups= {"smoke"})
	public void openBrowserTab()
	{
		logger= report.createTest("Invoking Practo Website on browser");
		String filePath=System.getProperty("user.dir") + "\\Resources\\properties\\config.properties";
		Properties prop;
		prop=ReadProperties.readFile(filePath);
		String browser=prop.getProperty("browserName");
		openBrowser(browser);
	}
	//closing Browser
	@AfterSuite//(groups= {"smoke"})
	public void closeBrowserWindow()
	{
		logger= report.createTest("Closing Browser");
		closeBrowser();
		report.flush();
	}
	//Printing hospitals as per requirements
	@Test(priority = 0, groups= {"regression,smoke"})
	public void PrintHospitals()
	{
		logger= report.createTest("Printing Hospitals as per requirement");
		DisplayHospitalNames hp=Base.nextPage1();
		hp.selectLocation();
		hp.selectHospital();
		hp.applyFilters();
		hp.hospitals();
		hp.Back();
	}
	//Retrieving and printing TopCities
	@Test(priority = 1 , dependsOnMethods = {"PrintHospitals"},groups= {"regression,smoke"})
	public void PrintTopCities()
	{
		logger= report.createTest("Printing Top Cities");
		DisplayTopCities top=DisplayHospitalNames.nextPage();
		top.Back();
		top.clickOnDiagnostics();
		top.topcities();
	}
	//Capturing an Alert message
	@Test(priority = 2, dependsOnMethods = {"PrintTopCities"},groups= {"regression,smoke"})
	public void CaptureMessage() {
		logger= report.createTest("Capturing Warning message");
		FillDetailsAndCaptureMessage msg=DisplayTopCities.nextPage();
		msg.Back();
		msg.selectCorporateWellness();
		msg.windows();
		msg.fillform();
		//msg.handleAlert();
		
		msg.imageCapture();
	}
	//Flushing the report
	@AfterTest
	public void endReport() {
		report.flush();
	}
}
