package TestAutomation.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.GenericFunctions;

public class EditContactDetailsPage {
	private WebDriver driver;
	@FindBy(id="ContentPlaceHolder1_tbCALine1")WebElement addressLane1;
	@FindBy(id="ContentPlaceHolder1_tbCACity")WebElement city;
	@FindBy(id="ContentPlaceHolder1_ddlCAState")WebElement state;
	@FindBy(id="ContentPlaceHolder1_btnSubmit")WebElement saveBtn;
	@FindBy(id="ContentPlaceHolder1_pmsg")WebElement updateMsg;//
	@FindBy(id="ContentPlaceHolder1_hlback")WebElement gotoHomeBtn;
	public EditContactDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void putCurrentAddress(String address) {
		addressLane1.clear();
		 addressLane1.sendKeys(address);
	}
	public void putCurrentCityName(String cityName) {
		city.clear();
		city.sendKeys(cityName);
	}
	public void selectCurrentStateName(String stateName) {
		 state.click();
		 GenericFunctions.waitInSeconds(2); 
		 Select select_state = new Select (state);
		 select_state.selectByVisibleText(stateName);
	}
	public void clickOnSaveBtn() {
		saveBtn.click();
	}
	public void clickOnGotoHomeBtn() {
		gotoHomeBtn.click();
	}
	public void editContactDetails(Map<String,String>dataMap,WebDriver driver) throws Exception {
		try {
			Actions actions = new Actions(driver);
		   	actions.moveToElement(addressLane1).perform();
		   	GenericFunctions.waitInSeconds(5);
			putCurrentAddress(dataMap.get("cur_addresslane1"));
			GenericFunctions.waitInSeconds(5);
			actions.moveToElement(city).perform();
			putCurrentCityName(dataMap.get("cur_city"));
			GenericFunctions.waitInSeconds(5);
			selectCurrentStateName(dataMap.get("cur_state"));
			GenericFunctions.waitInSeconds(5);
			actions.moveToElement(saveBtn).perform();
			clickOnSaveBtn();
			GenericFunctions.waitInSeconds(5); 
			String  editmsg="";
			actions.moveToElement(updateMsg).perform();
			 boolean updateMsgelement =BrowserUIUtils.checkElementExistsUsingWebelement(updateMsg);
			 if(updateMsgelement) {
				 editmsg=updateMsg.getText(); 
				 if(editmsg.contains("Updated")) {
					 dataMap.put("EditedContactDetails", "Pass");
				 }
				 else {
					 dataMap.put("TestResult", "Fail");
					 dataMap.put("EditedContactDetails", "Fail");
					 dataMap.put("Comments", "Contacts not saved");
					 ScreenshotUtilities.takeScreenShot(driver,dataMap); 
				 }
			 }
			 else {
				 dataMap.put("TestResult", "Fail");
				 dataMap.put("EditedContactDetails", "Fail");
				 dataMap.put("Comments", "Contacts not saved");
				 ScreenshotUtilities.takeScreenShot(driver,dataMap);
			 }
		}
		catch(Exception e) {
			dataMap.put("TestResult", "Fail");    	
	    	dataMap.put("EditContactDetails", "Fail");
	    	dataMap.put("EditedContactDetails", "Fail");
		    dataMap.put("Comments", "Issue while editing contact details in editContactDetails page");
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
	    	throw new Exception("Not saved contact details");
		}
	}

}
