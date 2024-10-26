package TestAutomation.Utilities.Reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.GenericFunctions;

public class WritetoHTML {
	public static final String resultsFolder = Constants.resultsFolderHTML;//C:\Users\Home\output\HtmlFiles
	 
	 public static void writeLog(ArrayList<Map<String, String>> status) throws IOException, Exception
		{ 		  File folder=null;	
				try
				{
		        folder = new File(resultsFolder);//stores folder path	
				if(!folder.exists()){
					folder.mkdirs();	//if the folder is not exist then it creates folder
				}
				}
				catch(Exception e)
				{
					throw new Exception("Unable to Create Output folder");//if exception occurs it is throwing custom exception with error msg as "Unable to Create Output folder"
				}
				File file = new File(folder+"\\StudentDetails.html"); //adding html file name to the folder path
				StringBuilder buf = new StringBuilder();
				StringBuilder buf1 = new StringBuilder();
				buf.append("<html>" +
				           "<body style=\"background-color:powderblue;\">" +
						   "<p>Hi,\n</p>" +
				           "<p> Students Validation Test Results\n</p>" +  
						   "<p>Check detailed Validation report O/p Results Path - {user}/OutputResults/</P>" +
				           "<p style = \"color:blue; font-weight: bold;\"><u>Student Validation Results</u></p>" +
				           "<table border=\"1\">" +
				           "<tr>" +
				           "<th>  mobileNumber  </th>" +
				           "<th>  email  </th>" +
				           "<th>  URL   </th>" +
				           "<th>  LOGIN_VALIDATIONS   </th>" +
				           "<th>  ProfileSetting_Validations</th>"+
				           "<th>  EditContactDetails_Validations   </th>"+
				           "<th>  EditedContactDetails   </th>"
				           );//html file code for testresult storing in one table format and these are column names
					for(int i=0;i<status.size();i++) {
						if(status.get(i).containsKey("ApplyForDrives")) {
							buf.append("<th>  ApplyForDrives</th>");
							break;
						}	
					}
					
				buf.append("<th>  OverAllResult   </th>" +				          
				           "<th>  Comments </th>" +
				           "</tr>");
				 for(int i=0;i<status.size();i++) {
				    buf1.append("<tr>" +
				    		   ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"mobileNumber"))+"</td>" +
				    		   ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"email"))+"</td>" +                            
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"URL"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"Login"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"ProfileSetting_Validations"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"EditContactDetails_Validations"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"EditedContactDetails"))+"</td>");					   
					 
					    	if(buf.indexOf("ApplyForDrives")>=0) {
					    		if(GenericFunctions.getData(status.get(i),"ApplyForDrives")!="Key Not Available") {
					    			buf1.append(""+resultsStatusColor(GenericFunctions.getData(status.get(i),"ApplyForDrives"))+"</td>");
					    		}
					    		else if(GenericFunctions.getData(status.get(i),"ApplyForDrives")=="Key Not Available") {
							    	buf1.append("<td> </td>");
							    }
					    	}
					    
						buf1.append(""+resultsStatusColor(GenericFunctions.getData(status.get(i),"TestResult"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(status.get(i),"Comments"))+"</td>" +
                              "</tr>");//writing html code for insert values into table
				    }
				buf1.append("</table>" +
						   "<p style = \"color:blue; font-weight: bold;\"> For Failed results,Check the detailed report in the output results sheet</P>" +
						   "</body>" +
				           "</html>");//ending tags
					buf.append(buf1);		
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
				bufferedWriter.write(buf.toString()); 
				bufferedWriter.close(); 
			}
	 
	 public static String resultsStatusColor(String Results)
		{
			String val = Results.toUpperCase();
			if(Results.toUpperCase().contains("PASS"))
			{
				Results = "Pass";
				val = "<td style = \"background: #FFF; color: green; font-weight: bold;\">"+Results;
			}
			else if(Results.toUpperCase().contains("FAIL"))
			{
				Results = "Fail";
				val = "<td style = \"background: #FFF; color: red; font-weight: bold;\">"+Results;
			}
			else if(Results.contains("Key Not Available") || Results.isEmpty() || Results.equalsIgnoreCase("null"))
			{
				val = "<td>"+" ";
			}	
			else if((!(Results.isEmpty())))
			{
				val = "<td>"+Results;
			}
			
			return val;
		}
		
}
