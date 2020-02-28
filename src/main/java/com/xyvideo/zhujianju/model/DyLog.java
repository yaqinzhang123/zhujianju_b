package com.xyvideo.zhujianju.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;
@Entity
@Data
@ApiModel(value="DyLog", description="操作日志")
public class DyLog extends ModelBase {
    private Date time;//时间
    private String resourceType;//页面
    private Long resourceID;//资源id
    private String name;//名字
    private String operationMemo;//操作备注
    private String operationType;//操作类型，例如Add，update等
    private String memo;//备注
}
