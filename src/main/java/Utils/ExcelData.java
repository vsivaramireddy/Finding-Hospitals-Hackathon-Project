package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {

	public static File file;
	public static FileInputStream work_file;
	public static XSSFWorkbook workbook;
	public static XSSFSheet worksheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileOutputStream result_file;

	
	public static String readExcel(String filepath, int rowNum, int colNum) {

		String data = null;
		try {
			file = new File(filepath);
			work_file = new FileInputStream(file);
			workbook = new XSSFWorkbook(work_file);
			worksheet = workbook.getSheet("FieldsData");
			row = worksheet.getRow(rowNum);

			cell = row.getCell(colNum);
			data = cell.getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(data);
		return data;
	}

	public static String readExcelNumeric(String filepath, int rowNum, int colNum) {

		long data = 0;
		try {
			file = new File(filepath);
			work_file = new FileInputStream(file);
			workbook = new XSSFWorkbook(work_file);
			worksheet = workbook.getSheet("FieldsData");
			row = worksheet.getRow(rowNum);

			cell = row.getCell(colNum);
			data = (long) cell.getNumericCellValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(data);
		String s = Long.toString(data);
		return s;
	}

	public static String readExcelXpath(String filepath, int rowNum, int colNum) {

		String data = null;
		try {
			file = new File(filepath);
			work_file = new FileInputStream(file);
			workbook = new XSSFWorkbook(work_file);
			worksheet = workbook.getSheet("Xpaths");
			row = worksheet.getRow(rowNum);

			cell = row.getCell(colNum);
			data = cell.getStringCellValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
	//	System.out.println(data);
		return data;

	}

	public static void writeExcelData(String filePath, String[] hospitalNames) throws IOException {

		try {
			file = new File(filePath);
			work_file = new FileInputStream(file);
			workbook = new XSSFWorkbook(work_file);
			worksheet = workbook.getSheet("SearchedHospitals");
			row = worksheet.getRow(worksheet.getLastRowNum());

			int rowCount = hospitalNames.length;

			for (int i = 0; i < rowCount; i++) {

				row = worksheet.createRow(i + 1);

				row.createCell(0).setCellValue(hospitalNames[i]);
			}
			work_file.close();
			result_file = new FileOutputStream(file);
			workbook.write(result_file);
			result_file.close();

		} catch (FileNotFoundException e) {
			System.out.println("The required file is not available");
			e.printStackTrace();
		}
	}

	public static void writeExcelTopCitiesData(String filePath, String[] topCities) throws IOException {

		try {
			file = new File(filePath);
			work_file = new FileInputStream(file);
			workbook = new XSSFWorkbook(work_file);
			worksheet = workbook.getSheet("TopCities");
			row = worksheet.getRow(worksheet.getLastRowNum());

			int rowCount = topCities.length;

			for (int i = 0; i < rowCount; i++) {

				row = worksheet.createRow(i + 1);

				row.createCell(0).setCellValue(topCities[i]);
			}
			work_file.close();
			result_file = new FileOutputStream(file);
			workbook.write(result_file);
			result_file.close();

		} catch (FileNotFoundException e) {
			System.out.println("The required file is not available");
			e.printStackTrace();
		}
	}
}
