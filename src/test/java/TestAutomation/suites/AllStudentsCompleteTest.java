package TestAutomation.suites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.Reports.WriteToExcel;
import TestAutomation.Utilities.Reports.WritetoHTML;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.ExcelUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;
import TestAutomation.pages.EditContactDetailsPage;
import TestAutomation.pages.NewPlacementDrivePage;
import TestAutomation.pages.ProfileSettingsPage;
import TestAutomation.pages.StudentHomePage;
import TestAutomation.pages.StudentLoginPage;

public class AllStudentsCompleteTest {
	ArrayList<Map<String, String>> validationresults = new ArrayList<Map<String, String>>();
	String URL = "http://skillgun.com";
	WebDriver driver = null;
	Map<String, String> dataMap;
	@Test(groups = { "E2EFlow" })
	public void verifystudentDetailworkFlows() throws Exception {
		ArrayList<Map<String, String>> Studentdetails = new ArrayList<Map<String, String>>();
		Studentdetails = ExcelUtilities.getstudentCompleteDetailsTestData();
		try {
			for (int j = 0; j < Studentdetails.size(); j++) {
				dataMap = new HashMap<String, String>();
				dataMap.putAll(Studentdetails.get(j));
				dataMap.put("URL", URL);
				dataMap.put("TestResult", "Pass");
				dataMap.put("Comments", "");
				try {
					driver = BrowserUIUtils.getNewDriver("CHROME", "ANY", "Windows 7");
					driver.manage().window().maximize();
					driver.get(URL);
					GenericFunctions.waitInSeconds(3);
					StudentLoginPage st_login = new StudentLoginPage(driver);
					st_login.loginWorkFlow(dataMap, driver);
					String exp_url = "skillgun.com/student/home.aspx";
					String act_url = driver.getCurrentUrl().toLowerCase();
					if (act_url.contains(exp_url)) {
						dataMap.put("Login", "Pass");
					}
					else {
						dataMap.put("TestResult", "Fail");
						dataMap.put("Login", "Fail");
						dataMap.put("Comments", "Login is success but user not navigated to home page");
						ScreenshotUtilities.takeScreenShot(driver, dataMap);
						throw new Exception("Login is not Successful");
					}
					StudentHomePage stud_home = new StudentHomePage(driver);
					stud_home.validateProfileSettingsBtn(dataMap, driver);
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
				    verifyStudentPlacement();
				}
				catch(Exception e) {
					validationresults.add(dataMap);
				}
				finally
				{
					 if(driver != null)
				     {
				       driver.quit();
				     }	
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally
		{
			WritetoHTML.writeLog(validationresults);
			WriteToExcel.ValidationOutputResultsSheet(validationresults);
		}	
	}
	public void verifyStudentPlacement() throws IOException, Exception {
		Map<String, String> dataMap1=new HashMap<String,String>();
		dataMap1.putAll(dataMap);
		try {
				if(driver.getCurrentUrl().contains("Student/Home.aspx")) {
				dataMap1.put("TestResult", "Pass");	
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
		
	}
}
