package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	private static final String TEST_DATA_SHEET_PATH = "C:/eclipse-workspace/OpenCartProject/src/main/resources/testdata/OpenCartData.xlsx";
	private static Workbook book;
	private static Sheet sheet; // import ss always


	public static Object[][] getTestData(String sheetName) {
		System.out.println("Reading the data from sheet : " + sheetName);
		Object data[][] = null;

		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);    // coming from apche
			sheet = book.getSheet(sheetName);		
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];   // here row column is empty
			for (int i = 0; i < sheet.getLastRowNum(); i++) {            
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {         //0,1,2,3,4,5<5
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString(); // this will fill the data //Excel cell value need to convert to the string 
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;


	}

}
/**
 * *******Always prefer the dataprovider approach instead oh the Excel sheet approach*******
 * 1R > if you have more and more row then may be it will crapted while reading the code
 * 2R > Excel sheet we can not monitor if someone change the data, but we can monitor the code 
 * 3R > we have to depend upon the third party library(apache poi api)
 */
