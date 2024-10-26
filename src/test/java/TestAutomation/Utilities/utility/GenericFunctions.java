package TestAutomation.Utilities.utility;

import java.util.Map;

public class GenericFunctions {
	public static void waitInSeconds(int seconds) {
		try {
			
			Thread.sleep(seconds * 1000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		
	}
	public static String GetHomeDir(){
	       String homeDir="";
		    if (isWinOS()){
		        homeDir = System.getProperty("user.home");
		    }
		    else{
		    	  homeDir = System.getProperty("user.home");
		    }
		    return homeDir;
		}
	public static boolean isWinOS(){
		 String oSName = System.getProperty("os.name");
		 if ( oSName.indexOf("WIn") > -1 ){
		        return true;
		    }
		    return false;
		 
	 }
	public static String getData(Map<String,String> data,String key)		
	{
		String value = "NULL";
		try
		{
			value = data.get(key);
			if(value.isEmpty() || value.equalsIgnoreCase("null"))
			{
				value = "Null";
			}
		}
		catch(Exception e)
		{
			value = "Key Not Available";
		}
		return value;
		
	}	
}
