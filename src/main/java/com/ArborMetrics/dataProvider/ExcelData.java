package com.ArborMetrics.dataProvider;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ExcelData {

    FileInputStream file;
    XSSFWorkbook workBook;
    XSSFSheet sheet;

    /**
     * @param indexOfSheet - this is sheet order in the work book index starts with 0.
     */

    private void openExcel(int indexOfSheet) {

        try {
            file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/ArborMetrics/dataProvider/ArborMatrixTestData.xlsx");
            workBook = new XSSFWorkbook(file);
            sheet = workBook.getSheetAt(indexOfSheet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is for the Object of one dimensional array from the excel sheet
     * @param sheetIndex - give the index of the sheet in the work book starts from zero index
     * @param startingRow - starting from which row you want to get the data
     * @param noOfRows - how many no of rows of data you want to receive as it is size of the one dimensional array declared in the method
     *                 this method could give you nulls if you use noOfRows more than what you want or
     *                 indexOutOfBound exception
     * @return - it returns the one dimensional array
     */
    private Object[] readData(int sheetIndex, int startingRow, int noOfRows) {
        openExcel(sheetIndex);
        Object[] data = new Object[noOfRows];
        int row = sheet.getPhysicalNumberOfRows();
        // int cells = rows.getLastCellNum();
        for(int i = startingRow, j = 0; i < row; i++) {
            XSSFCell cell = sheet.getRow(i).getCell(1);
            data[j++] = cell.getStringCellValue();
        }
        return data;
    }

    /**
     * This method returns the two d object array
     * @param indexOfSheet - order of the sheet in work book index starts from zero
     * @param startingRow - from which row you want data give that row no starts from 1
     * @param startingCell - from which cell you want data as per the columns starts  from 1.
     * @param endingCell - no of last column you want to get starts from 1.
     * @param noOfRows - this is for the object array for the no of rows we want to print expect null if you give bigger than rows
     *                 outOfBound exception if you give less index than needed
     * @param noOfColumns - this is for the object array for the no of columns you want to print exceptions are same as above
     * @return - two d array
     */

    private Object[][] readData(int indexOfSheet, int startingRow, int startingCell, int endingCell, int noOfRows, int noOfColumns) {
        openExcel(indexOfSheet);
        Object[][] data = new Object[noOfRows][noOfColumns + 1];
        int row = sheet.getPhysicalNumberOfRows();
        for(int i = startingRow, j = 0; i < row; i++) {
            for(int k = startingCell, l = 0; k <= endingCell; k++) {
                XSSFCell cell = sheet.getRow(i).getCell(k);
                data[j][l++] = cell.getStringCellValue();

            }
            j++;
        }
        return data;
    }


    @DataProvider(name="NameFieldsData")
    public Object[][] browserAndUrl() {
        return readData(1, 1, 0,3,3,3);
    }

    /**
     *this is to verify the data calling from the excel sheet
     * @param args
     */
    public static void main(String[] args) {
        ExcelData read = new ExcelData();
        for(int i = 0; i < read.browserAndUrl().length; i++) {
            System.out.println(Arrays.toString(read.browserAndUrl()[i]));

            }
        }
    }
