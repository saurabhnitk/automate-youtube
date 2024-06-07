package demo.utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name="excelData")
    public static Object[][] excelData(){
        String fileLocation = System.getProperty("user.dir")+"/src/test/resources/qa_codeathon_week3.xlsx";
        System.out.println("Fetchong excel file from "+fileLocation);
        return ExcelReaderUtil.readExcelData(fileLocation);
    }
}
