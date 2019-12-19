package com.jacken.wqttsbmodel.request;

import com.jacken.wqttsbmodel.baseentity.BasePageRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class JdGoodsRequest extends BasePageRequest {
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

    private Byte commisionRatioPc;

    private Byte commisionRatioWl;

    private BigDecimal wlUnitPrice;

    private BigDecimal unitPrice;

    private Integer goodsPackageId;

    private String goodsPackageName;

    private Date createTime;

    private Date updateTime;

    private String source;

    private BigDecimal commission;

    private Integer status;
}
