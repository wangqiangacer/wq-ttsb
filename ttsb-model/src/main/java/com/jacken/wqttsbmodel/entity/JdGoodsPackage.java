package com.jacken.wqttsbmodel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JdGoodsPackage implements Serializable {

    private  Integer id;

    private String name;

    private Integer skuNum;

    private Date createTime;

    private Date updateTime;

    private String desc;

    private static final long serialVersionUID = 1L;

}