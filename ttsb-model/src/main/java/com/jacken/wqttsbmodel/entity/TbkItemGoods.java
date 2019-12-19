package com.jacken.wqttsbmodel.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class TbkItemGoods implements Serializable {


    private Long numIid;

    private String title;

    private String pictUrl;

    private String smallImages;

    private BigDecimal zkFinalPriceWap;

    private String nick;

    private Integer volume;

    private String tkRate;

    private Integer userType;

    private String provcity;

    private String itemUrl;

    private String shopTitle;

    private BigDecimal reservePrice;

    private Integer sellerId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer favoritesId;

    private String couponClickUrl;

    private String couponInfo;

    private Integer couponValue;

    private BigDecimal commission;

    private String favoritesTitle;

    private String source;

    private String couponEndTime;

    private String couponStartTime;

    private Integer couponTotalCount;

    private Integer couponRemainCount;

    private Long category;

    private static final long serialVersionUID = 1L;


}