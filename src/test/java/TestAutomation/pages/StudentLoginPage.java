package TestAutomation.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;



public class StudentLoginPage {
	private WebDriver driver;
	@FindBy(id="ContentPlaceHolder1_tbPhoneNumber")WebElement mobileNumber;
	@FindBy(id="ContentPlaceHolder1_tbEmail")WebElement emailid;
	@FindBy(id="ContentPlaceHolder1_tbPassword")WebElement password;
	@FindBy(id="ckbkPolicyAgreement")WebElement checkbox;
	@FindBy(id="ContentPlaceHolder1_btnLogin")WebElement loginBtn;
	public StudentLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void enterMobileNo(String phno) {
		mobileNumber.sendKeys(phno);
	}
	public void enterEmailid(String email) {
		emailid.sendKeys(email);
	}
	public void enterPassword(String pswd) {
		password.sendKeys(pswd);
	}
	public void agreeForPlacementPolicy() {
		checkbox.click();
	}
	public void clickOnLoginbutton() {
		loginBtn.click();
	}
	public void loginWorkFlow(Map<String,String>dataMap,WebDriver driver) throws Exception {
		try{
			enterMobileNo(dataMap.get("mobileNumber"));
			GenericFunctions.waitInSeconds(3);
			enterEmailid(dataMap.get("email"));
			enterPassword(dataMap.get("password"));
			agreeForPlacementPolicy();
			GenericFunctions.waitInSeconds(5);
			clickOnLoginbutton();
		}
		catch(Exception e){
			dataMap.put("TestResult", "Fail");    	
	    	dataMap.put("Login", "Fail");
		    dataMap.put("Comments", "Issue while Logging into Application");
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
	    	throw new Exception("Login is not Successful");
		}
	}

}
