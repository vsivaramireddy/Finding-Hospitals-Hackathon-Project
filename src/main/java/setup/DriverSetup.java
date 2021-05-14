package setup;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DriverSetup {
	
	//Returning WebDriver of specified Browser
	public static WebDriver getWebDriver(String browserName)
	{
		WebDriver driver = null;
		try {
		if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",".\\Resources\\Drivers\\chromedriver.exe");
	         driver=new ChromeDriver();
		}else if(browserName.equalsIgnoreCase("edge"))
		{	
			System.setProperty("webdriver.edge.driver", ".\\Resources\\Drivers\\msedgedriver.exe");
			driver=new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			return driver;
		}else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", ".\\Resources\\Drivers\\geckodriver.exe");
			FirefoxProfile myProfile= new FirefoxProfile();
			myProfile.setAcceptUntrustedCertificates(true);	
			driver=new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			return driver;
		}
		} catch(Exception e) {
			Base.reportFail(e.getMessage());
		}
		
		
		return driver;
	}
}
