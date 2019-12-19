package com.jacken.wqttsbmodel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class JdGoods implements Serializable {
    private Integer id;

    private String skuid;

    private Integer cid;

    private Integer cid2;

    private Integer cid3;

    private String goodsName;

    private String cidName;

    private String cid2Name;

    private String cid3Name;

    private String imgUrl;

    private String shortUrl;

    private String materialUrl;

    private Integer commisionRatioPc;

    private Integer commisionRatioWl;

    private BigDecimal wlUnitPrice;

    private BigDecimal unitPrice;

    private Integer goodsPackageId;

    private String goodsPackageName;

    private Date createTime;

    private Date updateTime;

    private String source;

    private BigDecimal commission;

    private Integer status;

    private static final long serialVersionUID = 1L;

}