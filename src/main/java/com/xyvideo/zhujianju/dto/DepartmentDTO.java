package com.xyvideo.zhujianju.dto;


import com.xyvideo.zhujianju.model.ModelBase;
import io.swagger.annotations.ApiModelProperty;

public class DepartmentDTO extends ModelBase {
    @ApiModelProperty(value="主部门名称",dataType="String",name="mainDepartment",example="IT")
    private String mainDepartment;
    @ApiModelProperty(value="分部门名称",dataType="String",name="name",example="GST")
    private String name;
    @ApiModelProperty(value="LocalSection",dataType="String",name="LocalSection",example="GST")
    private String localSection;
    @ApiModelProperty(value="归属",dataType="String",name="ascription",example="")
    private String ascription;//归属
    @ApiModelProperty(value="",dataType="String",name="functionType",example="")
    private String functionType;
    @ApiModelProperty(value="",dataType="String",name="fINCostTypeIDL",example="")
    private String fINCostTypeIDL;
    @ApiModelProperty(value="",dataType="String",name="fINCostTypeDL",example="")
    private String fINCostTypeDL;
    @ApiModelProperty(value="工时类型",dataType="String",name="manhourtType",example="")
    private String manhourtType;//工时类型
    @ApiModelProperty(value=" BPM组织机构定义类型-分部门",dataType="String",name="bpmTypeChild",example="")
    private String bpmTypeChild;
    @ApiModelProperty(value=" BPM组织机构定义类型-部门",dataType="String",name="bpmType",example="")
    private String bpmType;
    @ApiModelProperty(value=" BPM分部门编码",dataType="String",name="bpmCodeChild",example="")
    private String bpmCodeChild;
    @ApiModelProperty(value=" BPM部门编码",dataType="String",name="bpmCode",example="")
    private String bpmCode;
    @ApiModelProperty(value=" ERP分支机构名称",dataType="String",name="erpName",example="")
    private String erpName;
}
