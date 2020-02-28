package com.xyvideo.zhujianju.util.utils;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
@Component
public class ExcelUtil {
//        private Logger logger = LoggerFactory.getLogger(this.getClass());
        private static final String EXCEL_XLS = ".xls";
        private static final String EXCEL_XLSX = ".xlsx";
        private static final String EXCEL_XLTX=".xltx";

        /**
         *读取excel数据
         * @throws Exception
         *
         */
        public List<List<String>> readExcelInfo(String fileName) throws Exception{
            /*
             * workbook:工作簿,就是整个Excel文档
             * sheet:工作表
             * row:行
             * cell:单元格
             */

//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
//        支持excel2003、2007
        InputStream is=null;
            // 部署用
            if(!fileName.equals("payroll.xlsx")){

                WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
                ServletContext servletContext = webApplicationContext.getServletContext();
                String realPath = servletContext.getRealPath("/");
                String filePath = realPath + "WEB-INF"+File.separator + "classes" + File.separator +"static" + File.separator + "resource" + File.separator+fileName;
                File excelFile = new File(filePath);
//            File excelFile = ResourceUtils.getFile("classpath:static/"+fileName);
                 is= new FileInputStream(excelFile);//创建输入流对象
            }else{
                ClassPathResource cpr = new ClassPathResource("static/"+fileName);
                is = cpr.getInputStream();
            }

//            String file=fileName.substring(fileName.indexOf("."), fileName.length());
//            checkExcel(file);
//            Workbook workbook = getWorkBook(is, fileName);

////        Workbook workbook = WorkbookFactory.create(is);//同时支持2003、2007、2010
////        获取Sheet数量
//            int sheetNum = workbook.getNumberOfSheets();
////      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
//            List<List<String>> dataList = new ArrayList<List<String>>();
////        FormulaEvaluator formulaEvaluator = null;
////        遍历工作簿中的sheet,第一层循环所有sheet表
//            for(int index = 0;index<sheetNum;index++){
//                Sheet sheet = workbook.getSheetAt(index);
//                if(sheet==null){
//                    continue;
//                }
//                System.out.println("表单行数："+sheet.getLastRowNum());
////            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
//                for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++){
//                    Row row = sheet.getRow(rowIndex);
////                根据文件头可以控制从哪一行读取，在下面if中进行控制
//                    if(row==null){
//                        continue;
//                    }
////                遍历每一行的每一列，第三层循环行中所有单元格
//                    List<String> cellList = new ArrayList<String>();
//                    for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
//                        Cell cell = row.getCell(cellIndex);
//                        System.out.println("遍历行中cell数据:"+getCellValue(cell));
//                        cellList.add(getCellValue(cell));
//                        System.out.println("第"+cellIndex+"个:     cell个数："+cellList.size());
//                    }
//                    dataList.add(cellList);
//                    System.out.println("第"+rowIndex+"行:     共几行："+dataList.size());
//                }
//
//            }
//            is.close();
            return this.readWorkbook(is,fileName);
        }
    /**
     *读取excel数据
     * @throws Exception
     *
     */
    public List<List<String>> readExcel(MultipartFile file)throws Exception{
        InputStream is=file.getInputStream();
        String fileName=file.getOriginalFilename();
        return this.readWorkbook(is,fileName);
    }
    public List<List<String>> readWorkbook(InputStream is,String fileName) throws Exception{
        String fileType=fileName.substring(fileName.indexOf("."), fileName.length());
        checkExcel(fileType);
        Workbook workbook = getWorkBook(is, fileName);
        int sheetNum = workbook.getNumberOfSheets();
//      创建二维数组保存所有读取到的行列数据，外层存行数据，内层存单元格数据
        List<List<String>> dataList = new ArrayList<List<String>>();
//        FormulaEvaluator formulaEvaluator = null;
//        遍历工作簿中的sheet,第一层循环所有sheet表
        for(int index = 0;index<sheetNum;index++){
            Sheet sheet = workbook.getSheetAt(index);
            if(sheet==null){
                continue;
            }
            System.out.println("表单行数："+sheet.getLastRowNum());
//            如果当前行没有数据跳出循环，第二层循环单sheet表中所有行
            for(int rowIndex=0;rowIndex<=sheet.getLastRowNum();rowIndex++){
                Row row = sheet.getRow(rowIndex);
//                根据文件头可以控制从哪一行读取，在下面if中进行控制
                if(row==null){
                    continue;
                }
//                遍历每一行的每一列，第三层循环行中所有单元格
                List<String> cellList = new ArrayList<String>();
                for(int cellIndex=0;cellIndex<row.getLastCellNum();cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    System.out.println("遍历行中cell数据:"+getCellValue(cell));
                    cellList.add(getCellValue(cell));
                    System.out.println("第"+cellIndex+"个:     cell个数："+cellList.size());
                }
                dataList.add(cellList);
                System.out.println("第"+rowIndex+"行:     共几行："+dataList.size());
            }

        }
        is.close();
        return dataList;
    }
        /**
         *获取单元格的数据,暂时不支持公式
         *
         *
         */
        public static String getCellValue(Cell cell){
            if(cell==null || StringUtils.isEmpty(cell.toString())){
                return "";
            }
            CellType cellType = cell.getCellTypeEnum();
            String cellValue = "";
            if(cellType==CellType.STRING){
                cellValue = cell.getStringCellValue().trim();
                return cellValue =(cellValue).equals("")?"":cellValue;
            }
            if(cellType==CellType.NUMERIC){
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    cellValue = "";
//                            DateFormat.formatDurationYMD(cell.getDateCellValue().getTime());
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                return cellValue;
            }
            if(cellType==CellType.BOOLEAN){
                cellValue = String.valueOf(cell.getBooleanCellValue());
                return cellValue;
            }
            return null;

        }
        /**
         *判断excel的版本，并根据文件流数据获取workbook
         * @throws IOException
         *
         */
        public static Workbook getWorkBook(InputStream is,String file) throws Exception{

            Workbook workbook = null;

            if(file.endsWith(EXCEL_XLS)){
                workbook = new HSSFWorkbook(is);
            }else if(file.endsWith(EXCEL_XLSX)||file.endsWith(EXCEL_XLTX)){
                workbook = new XSSFWorkbook(is);
            }

            return workbook;
        }
        /**
         *校验文件是否为excel
         * @throws Exception
         *
         *
         */
        public static void checkExcelVaild(File file) throws Exception {
            String message = "该文件是EXCEL文件！";
            if(!file.exists()){
                message = "文件不存在！";
                throw new Exception(message);
            }
            if(!file.isFile()||((!file.getName().endsWith(EXCEL_XLS)&&!file.getName().endsWith(EXCEL_XLSX)&&!file.getName().endsWith(EXCEL_XLTX)))){
                System.out.println(file.isFile()+"==="+file.getName().endsWith(EXCEL_XLS)+"==="+file.getName().endsWith(EXCEL_XLSX)+"==="+file.getName().endsWith(EXCEL_XLTX));
                System.out.println(file.getName());
                message = "文件不是Excel";
                throw new Exception(message);
            }
        }
    public static boolean checkExcel(String  file) {
        if(!file.equals(EXCEL_XLS)&&!file.equals(EXCEL_XLSX)&&!file.equals(EXCEL_XLTX)){
            return false;
        }
        return true;
    }

}
