package TestCases;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Pageclasses.DisplayHospitalNames;
import Pageclasses.DisplayTopCities;
import Pageclasses.FillDetailsAndCaptureMessage;
import setup.Base;
import setup.ReadProperties;
import listeners.Customlistener;
@Listeners(Customlistener.class)


public class RunTestCases extends setup.Base {
	public RemoteWebDriver driver;
	//GRID IMPLEMENTATION
	
	/*@BeforeSuite(groups= {"regression","smoke"})
	public void setUp() throws MalformedURLException
	{
	
		DesiredCapabilities capabilities=DesiredCapabilities.chrome();
		driver=new RemoteWebDriver(new URL(" http://192.168.43.241:4444/wd/hub"), capabilities);
	driver.manage().window().maximize();
	}*/
	//Opening Browser
	@Test(priority = 0, groups= {"regression","smoke"})
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
	@AfterSuite(groups= {"regression","smoke"})
	public void closeBrowserWindow()
	{
		logger= report.createTest("Closing Browser");
		closeBrowser();
		report.flush();
	}
	//Printing hospitals as per requirements
	@Test(priority = 1, groups= {"regression","smoke"})
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
	@Test(priority = 2, dependsOnMethods = {"PrintHospitals"},groups= {"regression"})
	public void PrintTopCities()
	{
		logger= report.createTest("Printing Top Cities");
		DisplayTopCities top=DisplayHospitalNames.nextPage();
		top.Back();
		top.clickOnDiagnostics();
		top.topcities();
	}
	//Capturing an Alert message
	@Test(priority = 3, dependsOnMethods = {"PrintTopCities"},groups= {"regression"})
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
