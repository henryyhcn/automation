package testpackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.Test;

public class Practice001
{
	@Test
	public void test001()
	{
		
		ProfilesIni allProfiles = new ProfilesIni();  
		// "Selenium" is the new profile just created  
		FirefoxProfile profile = allProfiles.getProfile("wd");  
		WebDriver driver = new FirefoxDriver(profile);  		
		driver.manage().window().maximize();
		driver.get("http://www.qbao.com");
	}
}
