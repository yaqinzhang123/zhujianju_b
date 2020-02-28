package com.xyvideo.zhujianju.controller;

import com.xyvideo.zhujianju.model.DyLog;
import com.xyvideo.zhujianju.service.DyLogService;
import com.xyvideo.zhujianju.util.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@Api("操作日志信息")
@RequestMapping("api/DyLog")
public class DyLogController {
    @Autowired
    private DyLogService dyLogService;
    @Autowired
    private ExcelUtil excelUtil;
    @ApiOperation(value = "操作日志信息录入",notes = "操作日志信息录入")
    @PostMapping("add")
    public Object add(@RequestBody DyLog dyLog){
        return this.dyLogService.add(dyLog);
    }
    @ApiOperation(value = "修改操作日志信息",notes = "修改操作日志信息")
    @PostMapping("update")
    public Object update(@RequestBody DyLog dyLog){

        return this.dyLogService.update(dyLog);
    }
    @ApiOperation(value = "查看所有操作日志信息",notes = "查看所有操作日志信息")
    @GetMapping("getList")
    public List<DyLog> getList(){

        return this.dyLogService.getList();
    }
    @ApiOperation(value = "查看所有操作日志信息（带分页）",notes = "查看所有操作日志信息（带分页）")
    @GetMapping("getListPage")
    public Page<DyLog> getListPage(@RequestParam(name = "pageNo",defaultValue = "1")int pageNo,
                                   @RequestParam(name = "pageSize",defaultValue = "10")int pageSize){

        return this.dyLogService.findAllByDeletedFalse(pageNo,pageSize);
    }
    @ApiOperation(value = "查看某个操作日志信息",notes = "查看某个操作日志信息")
    @GetMapping("get")
    public Object getByID(Long id){
        if(!this.dyLogService.existsById(id)){
            return null;
        }
        DyLog obj =this.dyLogService.getByID(id);
        if(obj==null)
            return null;
        return obj;
    }
    @ApiOperation(value = "删除某个操作日志信息",notes = "删除某个操作日志信息")
    @GetMapping("delete")
    public int  delete(Long id){
        DyLog dyLog=this.dyLogService.getByID(id);
        dyLog.setDeleted(true);
        DyLog update=this.dyLogService.update(dyLog);
        return update.isDeleted()?0:1;
    }
}
