package com.xyvideo.zhujianju.controller;

import com.xyvideo.zhujianju.model.Department;
import com.xyvideo.zhujianju.service.DepartmentService;
import com.xyvideo.zhujianju.util.utils.ExcelUtil;
import com.xyvideo.zhujianju.util.utils.ListUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.xyvideo.zhujianju.util.utils.ExcelUtil.checkExcel;

@Api("部门信息接口")
@RestController
@RequestMapping("api/Department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ExcelUtil excelUtil;
    @ApiOperation(value = "新增部门信息",notes = "新增部门信息")
    @PostMapping("add")
    public Object add(@RequestBody Department department){
        return this.departmentService.add(department);
    }
    @ApiOperation(value = "修改部门信息",notes = "修改部门信息")
    @PostMapping("update")
    public Object update(@RequestBody Department department){

        return this.departmentService.update(department);
    }
    @ApiOperation(value = "查看所有部门信息",notes = "查看所有部门信息")
    @GetMapping("getList")
    public List<Department> getList(){

        return this.departmentService.getList();
    }
    @ApiOperation(value = "查看所有部门信息（带分页）",notes = "查看所有部门信息（带分页）")
    @GetMapping("getListPage")
    public Page<Department> getListPage(@RequestParam(name = "pageNo",defaultValue = "1")int pageNo,
                                        @RequestParam(name = "pageSize",defaultValue = "10")int pageSize){

        return this.departmentService.findAllByDeletedFalse(pageNo,pageSize);
    }
    @ApiOperation(value = "查看某个部门信息",notes = "查看某个部门信息")
    @GetMapping("get")
    public Object getByID(Long id){
        if(!this.departmentService.existsById(id)){
            return null;
        }
        Department obj =this.departmentService.getByID(id);
        if(obj==null)
            return null;
        return obj;
    }
    @ApiOperation(value = "删除某个部门信息",notes = "删除某个部门信息")
    @GetMapping("delete")
    public int  delete(Long id){
        Department department =this.departmentService.getByID(id);
        department.setDeleted(true);
        Department update=this.departmentService.update(department);
        return update.isDeleted()?0:1;
    }
    @ApiOperation(value = "按id判断部门信息是否存在",notes = "按id判断部门信息是否存在")
    @GetMapping("existsById")
    public boolean existsById(Long id){
        return this.departmentService.existsById(id);
    }
    @ApiOperation(value = "按名称查询部门信息",notes = "按名称查询部门信息")
    @GetMapping("findByName")
    public Department     findByName(String name){
        return this.departmentService.findByName(name);
    }

    @ApiOperation(value = "按分部门及主部门名称查询部门信息",notes = "按分部门及主部门名称查询部门信息")
    @GetMapping("query")
    public List<Department>     findByNameAndMainDepartment(String name,String mainDepartment ){
        if(name==null)
            name="";
        if(mainDepartment==null)
            mainDepartment="";
        return this.departmentService.findByNameAndMainDepartment(name,mainDepartment);
    }
    @ApiOperation(value = "导入部门信息",notes = "导入部门信息")
    @PostMapping("import")
    @ResponseBody
    public Object importExcel(@RequestParam("file") MultipartFile file)throws Exception{
        List<List<String>> object= null;
        String fileName=file.getOriginalFilename();
        String fileType=fileName.substring(fileName.indexOf("."), fileName.length());
        if(!checkExcel(fileType))
            return "文件不是Excel！";
        object=excelUtil.readExcel(file);
        List<String> titleName=object.get(0);
        List<String> buTitle= Arrays.asList("Sub Department","Department","Local Section","Attribution","Finance Cost Type-IDL","Finance Cost Type-DL",
                "ERP Sales Offices Branch Name","工时类型","BPM组织机构定义类型-分部门","BPM组织机构定义类型-部门","BPM分部门编码","BPM部门编码");
        if(!ListUtils.equals(titleName,buTitle))
            return "上传文档与模板不符！";
        List<List<String>> data=object.subList(1,object.size());
        int i=0;
        for (List<String> list:data) {
            Department update=this.findByName(list.get(0));
            if(update==null||update.getId()==0){
                Department department=new Department();
                Department result=this.setDepartment(department,list);
                result=this.departmentService.add(result);
                if(result.getId()>0){
                    i++;
                }
            }else{
                Department result=this.setDepartment(update,list);
                result=this.departmentService.update(result);
                if(result.getId()>0){
                    i++;
                }
            }
        }
        return i==data.size()?"成功导入"+i+"条数据！":"数据导入不全！";
    }
    public Department setDepartment(Department department,List<String> list){
        department.setName(list.get(0));
        department.setMainDepartment(list.get(1));
        department.setLocalSection(list.get(2));
        department.setAscription(list.get(3));
        department.setFINCostTypeIDL(list.get(4));
        department.setFINCostTypeDL(list.get(5));
        department.setErpName(list.get(6));
        department.setFunctionType(list.get(7));
        department.setBpmTypeChild(list.get(8));
        department.setBpmType(list.get(9));
        department.setBpmCodeChild(list.get(10));
        department.setBpmCode(list.get(11));
        return department;
    }
    @GetMapping("export")
    @ApiOperation(value = "导出",notes = "导出")
    public void Export(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("部门表");
        List<Department> classmateList = this.departmentService.getList();        // 设置要导出的文件的名字
        String date= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName =  date + "Department"  +".xlsx";
        // 新增数据行，并且设置单元格数据
        int rowNum = 1;
        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = { "NO","Sub Department","Department","Local Section","Attribution","Finance Cost Type-IDL","Finance Cost Type-DL",
                "ERP Sales Offices Branch Name","工时类型","BPM组织机构定义类型-分部门","BPM组织机构定义类型-部门","BPM分部门编码","BPM部门编码"};
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i=0;i<classmateList.size();i++) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(i+1);
            row1.createCell(1).setCellValue(classmateList.get(i).getName());
            row1.createCell(2).setCellValue(classmateList.get(i).getMainDepartment());
            row1.createCell(3).setCellValue(classmateList.get(i).getLocalSection());
            row1.createCell(4).setCellValue(classmateList.get(i).getAscription());
            row1.createCell(5).setCellValue(classmateList.get(i).getFINCostTypeIDL());
            row1.createCell(6).setCellValue(classmateList.get(i).getFINCostTypeDL());
            row1.createCell(7).setCellValue(classmateList.get(i).getErpName());
            row1.createCell(8).setCellValue(classmateList.get(i).getFunctionType());
            row1.createCell(9).setCellValue(classmateList.get(i).getBpmTypeChild());
            row1.createCell(10).setCellValue(classmateList.get(i).getBpmType());
            row1.createCell(11).setCellValue(classmateList.get(i).getBpmCodeChild());
            row1.createCell(12).setCellValue(classmateList.get(i).getBpmCode());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }



}
