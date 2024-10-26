package TestAutomation.Utilities.utility;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BrowserUIUtils {
	public static String gridHubUrl = null;
	public enum Driver {
		FIREFOX,
		CHROME,
		IE,
		SAFARI,
		HTMLUNIT;
	}
	public static String getGridHubUrl(){
		String gridHubUrl = null;
		if(null!=System.getProperty(Constants.GRID_HUB_URL)){//if the gridhub url is not null then enters into condition.
			gridHubUrl = System.getProperty(Constants.GRID_HUB_URL);	//storing null value in gridHubURL
		}
		//System.out.println(gridHubUrl);
		return gridHubUrl;
	}
	public static WebDriver getNewDriver(Driver driverType, String browserVersion, String platform) throws Exception{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		if (null==gridHubUrl) {
			desiredCapabilities.setAcceptInsecureCerts(true);
		}
		else {
			if(platform.equals("XP")){
				desiredCapabilities.setPlatform(Platform.XP);
			}
			else if(platform.equals("VISTA")){ 
				desiredCapabilities.setPlatform(Platform.VISTA);
			}
			else if(platform.equals("ANDROID")){ 
				desiredCapabilities.setPlatform(Platform.ANDROID);
			}
			desiredCapabilities.setVersion(browserVersion);
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);	
		}
		
		switch (driverType) {
		
		case FIREFOX:
			// Disable Native Events on Windows for Firefox Driver.
			try {
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setAcceptUntrustedCertificates(false);
				desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				
				if (null==gridHubUrl) {
					//running on local
					return new FirefoxDriver(desiredCapabilities);
				}
				else{
					//running on Selenium Grid
					//desiredCapabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					desiredCapabilities.setBrowserName("firsefox");
					desiredCapabilities.setPlatform(Platform.ANY);
					FirefoxOptions options=new FirefoxOptions();
		    		options.merge(desiredCapabilities);
					return new RemoteWebDriver(new URL(gridHubUrl), desiredCapabilities);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case CHROME:
			if (null==System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY)){
				System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,"chromedriver.exe");
			}
			try {
				if (null==gridHubUrl){
					return new ChromeDriver(desiredCapabilities);
				}
				else{
					desiredCapabilities.setBrowserName("chrome");
					desiredCapabilities.setPlatform(Platform.ANY);
					desiredCapabilities.setAcceptInsecureCerts(true);
					ChromeOptions options=new ChromeOptions();
					options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
			                  UnexpectedAlertBehaviour.IGNORE);
					options.addArguments("--start-maximized");
		    		options.merge(desiredCapabilities);
					return new RemoteWebDriver(new URL(gridHubUrl), options);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case IE:
			try {
				if (null==gridHubUrl){
					//running on local
					return new InternetExplorerDriver(desiredCapabilities);
				}
				else{
					//running on Selenium Grid
					desiredCapabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
					desiredCapabilities.setCapability("requireWindowFocus", true);
					return new RemoteWebDriver(new URL(gridHubUrl), desiredCapabilities);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case SAFARI:
			try{
				//initSeleniumRC();
				//Selenium sel = new DefaultSelenium("localhost", 4444, "*safari", UrlUtil.getDayBase());
		//		CommandExecutor executor = new SeleneseCommandExecutor(sel);
				DesiredCapabilities dc = new DesiredCapabilities();
		//		return new RemoteWebDriver(executor, dc);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		case HTMLUNIT:
			return new HtmlUnitDriver(true);
		default:
			throw new Exception("You must choose one of the defined driver types");
		}
	}

	public static WebDriver getNewDriver(String browser, String browserVersion, String platform) throws Exception{
		return getNewDriver(Driver.valueOf(browser.toUpperCase()), browserVersion, platform);
	}
	public static boolean checkElementExistsUsingWebelement(WebElement ele)
	 { 
		 try
		 {		 
		   boolean element = ele.isDisplayed();
		   return element;
		 }
		 catch(Exception e)
		 {
			 return false;
		 }
	 }
}
