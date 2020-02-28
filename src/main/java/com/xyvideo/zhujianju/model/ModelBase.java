package com.xyvideo.zhujianju.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public class ModelBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="身份标识",dataType="Long",name="id",example="1")
    private long id;
    @ApiModelProperty(value="创建时间",dataType="Date",name="createTime",example="2019-05-12 08:00")
    private Date createTime;
    @ApiModelProperty(value="创建者",dataType="String",name="creator",example="魏XX")
    private String creator;
    @ApiModelProperty(value="更新时间",dataType="Date",name="updateTime",example="2019-05-12 08:00")
    private Date updateTime;
    @ApiModelProperty(value="更新者",dataType="String",name="updater",example="李XX")
    private String updater;
    @ApiModelProperty(value="删除",dataType="boolean",name="false",example="")
    private boolean deleted;

    public ModelBase(){
        Date now=new Date();
        this.createTime=now;
        this.updateTime=now;
        this.deleted=false;
    }


}
