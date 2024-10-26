package TestAutomation.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;

public class NewPlacementDrivePage {
	private WebDriver driver;
	@FindBy(partialLinkText="Show Details")WebElement showDetailsBtn;
	@FindBy(id="ContentPlaceHolder1_cbAcceptCondtions")WebElement checkbox;//
	@FindBy(id="ContentPlaceHolder1_chkConfirm")WebElement subCheckbox;
	@FindBy(id="ContentPlaceHolder1_btnApply")WebElement applyBtn;
	@FindBy(xpath="//div//h1")WebElement msg;
	public NewPlacementDrivePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void clickOnShowDetailsBtn() {
		showDetailsBtn.click();
	}
	public void checkTheCheckbox() {
		checkbox.click();
	}
	public void checkTheSubCheckbox() {
		subCheckbox.click();
	}
	public void clickOnApplyBtn() {
		applyBtn.click();
	}
	public void validateForApplyingDrive(Map<String,String>dataMap,WebDriver driver) throws Exception {
		try {
				clickOnShowDetailsBtn();
				GenericFunctions.waitInSeconds(5);			
		}
	   catch(Exception e) {
		   dataMap.put("TestResult", "Fail");    	
		    dataMap.put("ApplyForDrives", "Fail");
			dataMap.put("Comments", dataMap.get("Comments")+" Drives are not available");
			ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			throw new Exception("Drives are not available");
		}
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(checkbox).perform();
			GenericFunctions.waitInSeconds(3);
			checkTheCheckbox();
			GenericFunctions.waitInSeconds(3);
			checkTheSubCheckbox();
			GenericFunctions.waitInSeconds(3);
			clickOnApplyBtn();
			GenericFunctions.waitInSeconds(3);
			if(msg.getText().contains("You can not apply for this Drive")) {
				dataMap.put("TestResult", "Fail");    	
			    dataMap.put("ApplyForDrives", "Fail");
				dataMap.put("Comments", "Not eligible to apply drives");
				ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			}
			else {
				dataMap.put("TestResult", "Pass");    	
			    dataMap.put("ApplyForDrives", "Pass");
			}
		}
		catch(Exception e) {
			   dataMap.put("TestResult", "Fail");    	
			    dataMap.put("ApplyForDrives", "Fail");
				dataMap.put("Comments", "Issue while applying for drive");
				ScreenshotUtilities.takeScreenShot(driver,dataMap); 
				throw new Exception("Issue while applying for drives");
			}
		
		}

}
