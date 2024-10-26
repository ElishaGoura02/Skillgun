package TestAutomation.Utilities.Reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.ExcelUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class WriteToExcel {
	public static final String resultsFolder = Constants.resultsFolderExcel;
	public static String pouiFile = null;
	 
	 public static void ValidationOutputResultsSheet(ArrayList<Map<String, String>> validationDetails) throws WriteException
	 {		
  		ArrayList<String> ColoumnNamesKeyListSheet1 = new ArrayList<String>();
  		ColoumnNamesKeyListSheet1.add("mobileNumber"); 	
  		ColoumnNamesKeyListSheet1.add("email");
  		ColoumnNamesKeyListSheet1.add("URL");
  		ColoumnNamesKeyListSheet1.add("Login");
  		for(int i=0;i<validationDetails.size();i++) {
  			Map<String,String> data=new HashMap<String, String>();
  			data.putAll(validationDetails.get(i));
	  		if(data.containsKey("EditedContactDetails")) {
	  			ColoumnNamesKeyListSheet1.add("ProfileSetting_Validations");
	  	  		ColoumnNamesKeyListSheet1.add("EditContactDetails_Validations");
	  	  		ColoumnNamesKeyListSheet1.add("EditedContactDetails");
	  		}
	  		if(data.containsKey("ApplyForDrives")) {
	  			ColoumnNamesKeyListSheet1.add("ApplyForDrives");
	  		}
  		}
  		ColoumnNamesKeyListSheet1.add("TestResult");
  		ColoumnNamesKeyListSheet1.add("Comments");
  		  		   		
		WritableCellFormat w = new WritableCellFormat();
		w.setBackground(Colour.RED);
		String os = System.getProperty("os.name").toLowerCase();//returns operating system name as windows
		String filepath="";
		if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			filepath=resultsFolder;//if os is linux then stores home folderPath
		}
		else if (os.indexOf("win") >= 0) {//if os is windows
			filepath=resultsFolder;//stores file path like C:/Users/Home/OutputSheet/ExcelResults
		}
		try
		{
				pouiFile = ExcelUtilities.outputToSharedFolder(filepath);//xls file path storing into pouifile variable
				WritableWorkbook workbook = Workbook.createWorkbook(new File(pouiFile));//creating workbook
				WritableSheet sheet1 = workbook.createSheet(
						"StudentValidation_Results", 0);//creating excel sheet name as StudentValidation_Results
	            for(int i=0;i<ColoumnNamesKeyListSheet1.size();i++) {
	            	sheet1.addCell(new Label(i, 0, ColoumnNamesKeyListSheet1.get(i))); //storing keys as column names in excelsheet
	            }
	            
	           
	            for(int i=0;i<validationDetails.size();i++) {
	            	int colIndex = 0;
	            	 Map<String,String> result=new HashMap<String,String>();
	            	 result.putAll(validationDetails.get(i));  
		            for(int j=0;j<ColoumnNamesKeyListSheet1.size();j++){ 
		            	sheet1.addCell(new Label(colIndex, i+1,result.get(ColoumnNamesKeyListSheet1.get(j)))); //storing values related to column names in excelsheet
		            	colIndex++;
		            }
	            }
				workbook.write();
				workbook.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	 }
	 
}
