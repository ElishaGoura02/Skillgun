package TestAutomation.Utilities.utility;

import java.io.InputStream;

public class DatabaseUtil {
	public static InputStream getExcelFile(String xlFileName){
		
		InputStream xlFile = ClassLoader.getSystemResourceAsStream(getBaseFilePath()+xlFileName);
		
		if (null==xlFile){
			xlFile = ClassLoader.getSystemResourceAsStream(xlFileName);
		}
		
		return xlFile;
	}
	public static String getBaseFilePath(){
		String environment = System.getProperty("environment");
		if (null==environment){
			environment = "";
		} else {
			if (environment.equals("production")){}

			environment = environment + "/";
		}
		if(null==System.getProperty("test_data_location")){
		
			return environment;
		}
		else{
			
			return System.getProperty("test_data_location")+environment;
		}
	}	

}
