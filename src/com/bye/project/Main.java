package com.bye.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

  public static void main(String[] args) throws Exception {

	  try
		{
			FileInputStream file = new FileInputStream(new File("/Users/webster/Documents/Workspace_Fire/Java-ReadExcel-And-WriteTo/assetsFolder/example_user_email.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			List<String> users = new ArrayList<String>();
			
			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) 
				{
					Cell cell = cellIterator.next();
					//Check the cell type and format accordingly
					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_NUMERIC:
							//System.out.print(cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_STRING:
							//System.out.print(cell.getStringCellValue() + "\t");
							if(cell.getStringCellValue().contains("@")){
								users.add(cell.getStringCellValue());
							}
							break;
					}
				}
				System.out.println("");
			}
			file.close();
			SendGmail.sendToMail(users);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
  }
  
  public static void writeDataToFile(List<String> data) throws Exception {
	  
			XSSFWorkbook workbook = new XSSFWorkbook(); 
			
			//Create a blank sheet
			XSSFSheet sheet = workbook.createSheet("Employee Data");
			 
			int i = 0;
			int rownum = 0;
			while (i < data.size()) {
				Row row = sheet.createRow(rownum++);
			    Object [] objArr = new Object[] {data.get(i)};
			    int cellnum = 0;
			    for (Object obj : objArr)
			    {
			       Cell cell = row.createCell(cellnum++);
			       if(obj instanceof String)
			            cell.setCellValue((String)obj);
			        else if(obj instanceof Integer)
			            cell.setCellValue((Integer)obj);
			    }
				i++;
			}
			
			
			try 
			{
				//Write the workbook in file system
			    FileOutputStream out = new FileOutputStream(new File("/Users/webster/Documents/Workspace_Fire/Java-ReadExcel-And-WriteTo/assetsFolder/new_tiwi_yahoo_contacts.xlsx"));
			    workbook.write(out);
			    out.close();
			    
			    System.out.println("new_tiwi_yahoo_contacts.xlsx written successfully on disk.");
			     
			} 
			catch (Exception e) 
			{
			    e.printStackTrace();
			}
	  
  }

}