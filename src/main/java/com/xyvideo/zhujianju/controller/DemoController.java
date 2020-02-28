package com.xyvideo.zhujianju.controller;


import com.xyvideo.zhujianju.util.utils.ExcelUtil;
import com.xyvideo.zhujianju.util.utils.HttpContextUtils;
import com.xyvideo.zhujianju.util.utils.SpringContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xyvideo.zhujianju.util.utils.ExcelUtil.getWorkBook;

@Api("Demo")
@RestController
@RequestMapping("api/demo")
public class DemoController {

    @Autowired
    private ExcelUtil util;
    @ApiOperation(value = "新增HCItem",notes = "新增HCItem")
    @GetMapping("excel")
    public Object add(String fileName) {
        try {
            return util.readExcelInfo(fileName);
        } catch (Exception e) {
        return e.toString();
        }
    }
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String ,Object> result = new HashMap<>();
        String path = request.getSession().getServletContext().getRealPath("/");
        try{
            // 如果文件不为空，写入上传路径
            if(!file.isEmpty()){
                result = null;
            }else {
                result.put("code","1");
                result.put("message","上传文件为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @PostMapping("/uploadExcel")
    @ResponseBody
    public Object upLoadAccessory(@RequestParam("file")MultipartFile file){

        Map<String,Object> map = new HashMap<>();

        if (file.isEmpty()) {
            return null;

        }else {

            //保存时的文件名
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String dateName = df.format(calendar.getTime())+file.getOriginalFilename();

            System.out.println(dateName);
            //保存文件的绝对路径
            WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String realPath = servletContext.getRealPath("/");
            System.out.println(realPath);
            String filePath = realPath + "WEB-INF"+File.separator + "classes" + File.separator +"static" + File.separator + "resource" + File.separator+dateName;
            System.out.println("绝对路径:"+filePath);
//            String filePath="F:/data/file/"+dateName;
            File newFile = new File(filePath);
            //如果文件夹不存在则创建

            //MultipartFile的方法直接写文件
            try {
                if(!newFile.exists()  && !newFile.isDirectory()){
                    newFile.getParentFile().mkdirs();//创建父级文件路径
                    newFile.createNewFile();
                }
                //上传文件
                file.transferTo(newFile);

//                //数据库存储的相对路径
                String projectPath = servletContext.getContextPath();
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;
                String url = contextpath + "/resource/"+dateName;
                System.out.println("相对路径:"+url);
                //文件名与文件URL存入数据库表

                //linux部署用
                //赋予权限
//

                //生成文件地址
//                String url="http://XXXXXXXXX.cn"+filePath+"/"+dateName;
//                result.setCode(200+"");
//                result.setMsg("图片上传成功");
                System.out.println("上传成功 url:"+url);
               return dateName;
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }

        }

    }
    @ApiOperation(value = "导出",notes = "导出")
    @GetMapping("export")
    public void exportExcel(){
        File excelFile = null;
        try {
            excelFile = ResourceUtils.getFile("classpath:static/templates.xltx");
            InputStream is = new FileInputStream(excelFile);//创建输入流对象
            //checkExcelVaild(excelFile);
            Workbook workbook = getWorkBook(is, "xltx");
            int sheetNum = workbook.getNumberOfSheets();
            Sheet sheet = workbook.getSheetAt(0);
            //第二行写入数据
            Row row1 = sheet.getRow(1);
            row1.createCell(0).setCellValue(1);
            row1.createCell(1).setCellValue(2);
            workbook.setForceFormulaRecalculation(true);//刷新公式
            File file = new File("D:/t.xlsx");
            FileOutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            //关流
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
}
