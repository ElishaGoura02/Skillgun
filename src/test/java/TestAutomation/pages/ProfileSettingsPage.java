package TestAutomation.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;

public class ProfileSettingsPage {
	private WebDriver driver;
	@FindBy(id="ContentPlaceHolder1_hlEditContact")WebElement editContactDetailsBtn;
	public ProfileSettingsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void clickOnEditContactDetailsBtn() {
		editContactDetailsBtn.click();
	}
	public void validateEditContactDetailsBtn(Map<String,String>dataMap,WebDriver driver) throws Exception {
		
		try {
			clickOnEditContactDetailsBtn();
			GenericFunctions.waitInSeconds(5);
		}
		catch(Exception e){
			dataMap.put("TestResult", "Fail");    	
	    	dataMap.put("EditContactDetails_Validations", "Fail");
		    dataMap.put("Comments", "Issue while click on editContactDetails button in profile settings page");
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
	    	throw new Exception("Not navigated to edit contact details page");
		}
	}

}
