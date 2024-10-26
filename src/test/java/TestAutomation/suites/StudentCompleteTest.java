package TestAutomation.suites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.Reports.WriteToExcel;
import TestAutomation.Utilities.Reports.WritetoHTML;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.ExcelUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;
import TestAutomation.pages.EditContactDetailsPage;
import TestAutomation.pages.NewPlacementDrivePage;
import TestAutomation.pages.ProfileSettingsPage;
import TestAutomation.pages.StudentHomePage;
import TestAutomation.pages.StudentLoginPage;

public class StudentCompleteTest {
	ArrayList<Map<String, String>> validationresults = new ArrayList<Map<String, String>>();
	String mobileNumber = "9886133936";
	String URL = "https://skillgun.com";
	WebDriver driver = null;
	Map<String,String> dataMap = new HashMap<String,String>();
	
	@Test(groups = { "E2E","Regression" })
	public void verifyStudentLogin() throws IOException, Exception {
		dataMap.put("mobileNumber", mobileNumber);
		dataMap.put("URL", URL);
		ExcelUtilities.getstudentDetails(dataMap);
		try {
			dataMap.put("TestResult", "Pass");	
		    dataMap.put("Comments", "");
			   driver = BrowserUIUtils.getNewDriver("CHROME","ANY","Windows 10");
			   driver.manage().window().maximize();
			   driver.get(URL);
			   GenericFunctions.waitInSeconds(3);
				StudentLoginPage st_login = new StudentLoginPage(driver); 
				st_login.loginWorkFlow(dataMap, driver);
				GenericFunctions.waitInSeconds(3);
				String exp_url ="https://skillgun.com/student/home.aspx";
			    String act_url = driver.getCurrentUrl().toLowerCase();
			    if(act_url.equals(exp_url)) {
			    	dataMap.put("Login", "Pass");
			    }
			    else {
			    	dataMap.put("TestResult","Fail");
			    	dataMap.put("Login","Fail");
			    	dataMap.put("Comments", "Issue while Logging into Application");
			    	ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			    	throw new Exception("Login is not Successful");
			    }
			    StudentHomePage st_home=new StudentHomePage(driver);
			    st_home.validateProfileSettingsBtn(dataMap, driver);
			    String curnt_url1 = driver.getCurrentUrl();
				String exp_url1 = "Student/ProfileSetting.aspx";
				if(curnt_url1.contains(exp_url1)) {
			    	dataMap.put("ProfileSetting_Validations", "Pass");
			    }
			    else {
			    	dataMap.put("TestResult","Fail");
			    	dataMap.put("ProfileSetting_Validations","Fail");
			    	dataMap.put("Comments", "Login is success but not navigated to profilesettings page");
			    	ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			    	throw new Exception("Not navigated to Profile Settings page");
			    }
				ProfileSettingsPage profile=new ProfileSettingsPage(driver);
			    profile.validateEditContactDetailsBtn(dataMap, driver);
			    String curnt_url2 = driver.getCurrentUrl();
				String exp_url2 = "Student/EditContactDetails.aspx";
				if(curnt_url2.contains(exp_url2)) {
			    	dataMap.put("EditContactDetails_Validations", "Pass");
			    }
			    else {
			    	dataMap.put("TestResult","Fail");
			    	dataMap.put("EditContactDetails_Validations","Fail");
			    	dataMap.put("Comments", "student not navigated to edit contact details page");
			    	ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			    	throw new Exception("Not navigated to edit contact details page");
			    }
				EditContactDetailsPage edit=new EditContactDetailsPage(driver);
				edit.editContactDetails(dataMap, driver);
				//validationresults.add(dataMap);
			    edit.clickOnGotoHomeBtn();
			    GenericFunctions.waitInSeconds(5);
		   }
		   catch(Exception e) {
			  //validationresults.add(dataMap);
			   throw new Exception("");
		   }	
	}
	
	@Test(groups = { "E2E","Regression" })
	public void verifyStudentPlacement() throws IOException, Exception {
		Map<String, String> dataMap1=new HashMap<String,String>();
		dataMap1.putAll(dataMap);
			try {
				if(driver.getCurrentUrl().contains("Student/Home.aspx")) {
				dataMap1.put("TestResult", "Pass");	
			    //dataMap1.put("Comments", "");
				    StudentHomePage st_home=new StudentHomePage(driver);
				    st_home.validateNewPlacementDrivesBtn(dataMap1, driver);
				    String exp_url1 ="https://skillgun.com/Student/NewPlacementDrive.aspx";
				    String act_url1 = driver.getCurrentUrl().toLowerCase();
				    if(exp_url1.toLowerCase().equals(act_url1)) {
				    	NewPlacementDrivePage nd=new NewPlacementDrivePage(driver);
					    nd.validateForApplyingDrive(dataMap1, driver);
					    
				    }
				    else {
				    	dataMap1.put("TestResult","Fail");
				    	dataMap1.put("Comments", "Unable to navigate to new placement drives page");
				    	ScreenshotUtilities.takeScreenShot(driver,dataMap1); 
				    	throw new Exception("Unable to apply for drive");
				    }
				    validationresults.add(dataMap1);
				}
				else {
					throw new Exception("");
				}
			}
			catch(Exception e) {
				 validationresults.add(dataMap1);
			}
			finally
			{
				 if(driver != null)
			     {
			       driver.quit();
			     }	
				WritetoHTML.writeLog(validationresults);
				WriteToExcel.ValidationOutputResultsSheet(validationresults);
			}	
		
	}
}
