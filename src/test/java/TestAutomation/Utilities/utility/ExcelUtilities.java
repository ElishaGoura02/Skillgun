package TestAutomation.Utilities.utility;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.SimpleDateFormat;



public class ExcelUtilities {
	public static List<Map<String, String>> createData(String fileName , String sheetName) {
		final String NULLROW = "NULLROW";
		List<Map<String, String>> excelDataList = null;
		List<String> keyList = null;
		Map<String, String> dataMap = null;
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = null;
		InputStream xlFile = DatabaseUtil.getExcelFile(fileName);//it will get excelsheet location with filename

		try 
		{
			keyList = new ArrayList<String>();
			OPCPackage opc = OPCPackage.open(xlFile);//opensExcelfile

			Workbook wb1 = WorkbookFactory.create(opc);//interacts with excel file
			Sheet sheet = wb1.getSheet(sheetName);//getting excel sheet
			XSSFFormulaEvaluator formulaEv = new XSSFFormulaEvaluator( (XSSFWorkbook) wb1);
			excelDataList = new ArrayList<Map<String, String>>();

			for (Row row : sheet) {

				if(row.getRowNum() == sheet.getFirstRowNum()) {
					for (int fr = 0; fr < row.getLastCellNum(); fr++) {
						if(null != row.getCell(fr)){
							keyList.add(row.getCell(fr).toString());
						} else {
							keyList.add(NULLROW);
						}          				
					}

				} else if (row.getRowNum() > sheet.getFirstRowNum() ){
					dataMap = new LinkedHashMap<String, String>();
					for (int cn = 0 ; cn < keyList.size();cn++ ) {

						if(!keyList.get(cn).equals(NULLROW)) {

							if(null == row.getCell(cn)){

								dataMap.put(keyList.get(cn).toString(), "");    

							} else {

                                Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
								if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
									dataMap.put(keyList.get(cn).toString(),"");
								} 
								else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									DataFormatter formater = new DataFormatter();
						            String mobileNumberAsString = formater.formatCellValue(cell);
									dataMap.put(keyList.get(cn).toString(),mobileNumberAsString);
								}
								else if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
									dataMap.put(keyList.get(cn).toString(),(row.getCell(cn).toString()));
									
								} 
								else if (HSSFDateUtil.isCellDateFormatted(cell))
								{   
									CellValue cValue = formulaEv.evaluate(cell);
									double dv = cValue.getNumberValue();
									date = HSSFDateUtil.getJavaDate(dv);
									formattedDate  = formatter.format(date);
									dataMap.put(keyList.get(cn).toString(),formattedDate);
								}
								else {
									dataMap.put(keyList.get(cn).toString(),getValueAsString(row.getCell(cn).toString()));
								}
							}
						}
					}
					excelDataList.add(dataMap);
				}
			}
			opc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelDataList;
	}
	private static String getValueAsString(String value){
		BigDecimal decimal = null;
		Double checkForDbl = Double.valueOf(value);

		if( (checkForDbl - (checkForDbl.intValue())) > 0 ) {
			decimal = new BigDecimal(value);
		} else {
			decimal = new BigDecimal(Double.valueOf(value));
		}
		return decimal.toString();
	}
	public static void getstudentDetails(Map<String,String>dataMap1)
    {
        ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
        data =	(ArrayList<Map<String, String>>) ExcelUtilities.createData("StudentTestData.xlsx","Data");
        for(int j=0;j<data.size();j++)			
         {
	         Map<String,String> map2 = new HashMap<String,String>();	
	         map2.putAll(data.get(j));	         
	         if(dataMap1.get("mobileNumber").equalsIgnoreCase(map2.get("mobileNumber")))
	          {
	        	 dataMap1.putAll(map2);
		         break;
	          }		
	
          }	
    }
	public static ArrayList<Map<String,String>> getstudentCompleteDetailsTestData()
    {
        ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
        data =	(ArrayList<Map<String, String>>) ExcelUtilities.createData("StudentTestData.xlsx","Data");
        return data;
    }
	public static String outputToSharedFolder(String path) throws Exception {
		
		File requestLogFolder = new File(path);
		if(!requestLogFolder.exists()){
			requestLogFolder.mkdir();
		}
		

		String timestamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(Calendar.getInstance().getTime());
		
		String var = "Student_Validations";

		String date = timestamp.split("_")[0];//(timestamp.replaceAll(timestamp.split("_")[1],"")).replaceAll("_", "");

		File folder = new File(path+"/"+date);
		if(!folder.exists()){
			folder.mkdir();//if folder is not exists then one directory will be created
		}
		File requestLogFile = new File(folder+"/"+var+"_"+"_["+timestamp+"].xls");
		if(!requestLogFile.exists())
		{
			requestLogFile.createNewFile();
		}
		
		return ""+requestLogFile; //returning file name with path
	}
}
