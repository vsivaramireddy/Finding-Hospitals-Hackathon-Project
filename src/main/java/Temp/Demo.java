package Temp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Demo {
	
	WebDriver driver;
	
	public void failed() throws IOException
	{
		File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir")+"\\Resources\\Snapshots\\FailureSS.png"));
	}
}
