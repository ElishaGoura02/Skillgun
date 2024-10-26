package TestAutomation.pages;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;

public class StudentHomePage {
	private WebDriver driver;
	@FindBy(partialLinkText="profile settings")WebElement profileSettingsBtn;
	@FindBy(id="ContentPlaceHolder1_ahrefnewdrive")WebElement newPlacementDrivesBtn;
	public StudentHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void clickOnProfileSettingsBtn() {
		profileSettingsBtn.click();
	}
	public void clickOnNewPlacementDrivesBtn() {
		newPlacementDrivesBtn.click();
	}
	public void validateProfileSettingsBtn(Map<String,String>dataMap,WebDriver driver) throws Exception {
		try {
			clickOnProfileSettingsBtn();
			GenericFunctions.waitInSeconds(5);
		}
		catch(Exception e){
			dataMap.put("TestResult", "Fail");    	
	    	dataMap.put("ProfileSetting_Validations", "Fail");
		    dataMap.put("Comments", "Issue while click on profile setting button in home page");
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
		    throw new Exception("Not navigated to Profile Settings page");
		} 
	}
	public void validateNewPlacementDrivesBtn(Map<String,String>dataMap,WebDriver driver)throws Exception {
		try {
			
			clickOnNewPlacementDrivesBtn();
			GenericFunctions.waitInSeconds(5);
		}
		catch(Exception e){
			dataMap.put("TestResult", "Fail");    	
		    dataMap.put("Comments", "Issue while click on new placement drives button in home page");
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
		    throw new Exception("Not navigated to new placement drives page");
		}
	}
}
