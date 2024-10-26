package TestAutomation.Utilities.Reports;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Reporter;

import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.Constants;



public class ScreenshotUtilities {
	public static String takeScreenShot(WebDriver driver,Map<String,String> dataMap) throws IOException{
	WebDriver augmentedDriver = null;
	String returnUrl = null;
	try{
		String userReadableName ="studentflow";		
		String completeUrl = null;
		String fileNameWithPath = null;
		String fileName = null;
		String filePath = null;
		String urlPath = null;
		String currentUrl = null;
		String htmlUrl = null;
		String iFrameUrl = null;

		if(driver!=null){
			if (null==BrowserUIUtils.getGridHubUrl()){
				augmentedDriver = driver;//storing Chrome on windows
			}
			else
			{
					augmentedDriver = new Augmenter().augment(driver);
			}
			if (null==BrowserUIUtils.getGridHubUrl()){
				filePath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;//C:Users/home/outputsheet/selenium/Execution_sreenshots
				urlPath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
			}
			else{
				String os = System.getProperty("os.name").toLowerCase();//windows
				if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {//if os is linux then enters into condition
					filePath = Constants.LINUX_SCREENSHOT_FILE_PATH;
					urlPath = Constants.LINUX_SCREENSHOT_URL_PATH;
				}
				else if (os.indexOf("win") >= 0) {//if os is windows then enters into this condition 
					filePath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;//C:Users/home/outputsheet/selenium/Execution_sreenshots
					urlPath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
				}
			}
			String timestamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(Calendar.getInstance().getTime());
			//System.out.println(timestamp);

			String date =timestamp.split("_")[0];//(timestamp.replaceAll(timestamp.split("_")[1],"")).replaceAll("_", "");
			File folder = new File(filePath+"/"+date);//adding date as foldername to the filepath url
			if(!folder.exists()){
				folder.mkdir();
			}


			File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);//captures screenshot
			//System.out.println("Snapshot scrFile :"+scrFile);
			augmentedDriver.manage().window().maximize();
			fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
					+".png";//filename
			
			fileNameWithPath = folder.getAbsolutePath() + "/" + userReadableName+"_"+fileName;	//adding filename as "Studentflow_date" to folder path as a string			
			FileUtils.copyFile(scrFile, new File(fileNameWithPath));
		
			completeUrl = fileNameWithPath;

			currentUrl = augmentedDriver.getCurrentUrl();//storing current url
			Reporter.log("<h4 style='color:red'> <b>Screenshot URL:</b> <a href='"+currentUrl+"' target='_blank'>"+currentUrl+"</a> </h4>");
			scrFile.delete();

			
			htmlUrl = "<h3 style='color:red'><b>Analyze Exceptions with below Screenshot</b> &nbsp; &nbsp; &nbsp; OR &nbsp; &nbsp; &nbsp; <a href='"+completeUrl+"' target='_blank'>Click Here to open Screenshot in New Tab/Window</a></h3>";
			Reporter.log(htmlUrl);

			iFrameUrl = "<iframe name='inlineframe' src='"+completeUrl+"' frameborder='0' scrolling='auto' width='1024' height='180' marginwidth='5' marginheight='5'></iframe>";
			Reporter.log(iFrameUrl);
			returnUrl = completeUrl;
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
	}
	return returnUrl;
}
}
