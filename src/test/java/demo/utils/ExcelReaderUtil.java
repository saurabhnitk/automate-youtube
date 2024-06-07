package demo.utils;

import java.util.*;
import java.io.*;
import

public class ExcelReaderUtil {
    
    public static Object[][] readExcelData(String fileName){
        try{
            System.out.println("Reading data");
            InputStream file = new DataInputStream(new FileInputStream(fileName));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            List<Object[]> records = new ArrayList<>();
            int rowNum = sheet.getFirstRowNum() + 1;  // skip the header row
            int totalRows = findLastNonBlankRow(sheet);

            for(int i=rowNum;i<totalRows;i++){
                Row row = sheet.getRow(i);
                List<Object> columns = new ArrayList<>();
                Cell cell = row.getCell(0);
                columns.add(getCellValue(cell));
                records.add(columns.toArray());
            }
            workbook.close();
            return records.toArray(new Object[0][]);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int findLastNonBlankRow(Sheet sheet){
        int lastNonBlankRowNum = 1;
        if(sheet != null){
            for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                if(row != null && !isRowBlank(row)){
                    lastNonBlankRowNum = i;
                }
            }
        }
        return lastNonBlankRowNum;
    }

    public static int findLastNonBlankColumn(Row row){
        int lastNonBlankColumnNum = -1;
        if(row != null){
            for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                Cell cell = row.getCell(j,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if(cell != null && cell.getCellType() != CellType.BLANK){
                    lastNonBlankColumnNum = j;
                }
            }
        }
        return lastNonBlankColumnNum;
    }

    private static boolean isRowBlank(Row row){
        for(int cellNum=row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++){
            Cell cell = row.getCell(cellNum,Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if(cell != null && cell.getCellType() != CellType.BLANK){
                return false;
            }
        }
        return true;
    } 

    private static Object getCellValue(Cell cell){
        switch(cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }



}
