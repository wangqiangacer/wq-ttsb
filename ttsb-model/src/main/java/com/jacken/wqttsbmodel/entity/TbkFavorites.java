package com.jacken.wqttsbmodel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class TbkFavorites implements Serializable {


    private Integer favoritesId;

    private String favoritesTitle;

    private Date createTime;

    private Date updateTime;

    private Integer type;

    private Integer totalResult;

    private static final long serialVersionUID = 1L;


}