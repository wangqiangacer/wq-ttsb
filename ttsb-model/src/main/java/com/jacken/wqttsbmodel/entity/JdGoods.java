package com.jacken.wqttsbmodel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid == null ? null : skuid.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCid2() {
        return cid2;
    }

    public void setCid2(Integer cid2) {
        this.cid2 = cid2;
    }

    public Integer getCid3() {
        return cid3;
    }

    public void setCid3(Integer cid3) {
        this.cid3 = cid3;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getCidName() {
        return cidName;
    }

    public void setCidName(String cidName) {
        this.cidName = cidName == null ? null : cidName.trim();
    }

    public String getCid2Name() {
        return cid2Name;
    }

    public void setCid2Name(String cid2Name) {
        this.cid2Name = cid2Name == null ? null : cid2Name.trim();
    }

    public String getCid3Name() {
        return cid3Name;
    }

    public void setCid3Name(String cid3Name) {
        this.cid3Name = cid3Name == null ? null : cid3Name.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl == null ? null : shortUrl.trim();
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl == null ? null : materialUrl.trim();
    }

    public Byte getCommisionRatioPc() {
        return commisionRatioPc;
    }

    public void setCommisionRatioPc(Byte commisionRatioPc) {
        this.commisionRatioPc = commisionRatioPc;
    }

    public Byte getCommisionRatioWl() {
        return commisionRatioWl;
    }

    public void setCommisionRatioWl(Byte commisionRatioWl) {
        this.commisionRatioWl = commisionRatioWl;
    }

    public BigDecimal getWlUnitPrice() {
        return wlUnitPrice;
    }

    public void setWlUnitPrice(BigDecimal wlUnitPrice) {
        this.wlUnitPrice = wlUnitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getGoodsPackageId() {
        return goodsPackageId;
    }

    public void setGoodsPackageId(Integer goodsPackageId) {
        this.goodsPackageId = goodsPackageId;
    }

    public String getGoodsPackageName() {
        return goodsPackageName;
    }

    public void setGoodsPackageName(String goodsPackageName) {
        this.goodsPackageName = goodsPackageName == null ? null : goodsPackageName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", skuid=").append(skuid);
        sb.append(", cid=").append(cid);
        sb.append(", cid2=").append(cid2);
        sb.append(", cid3=").append(cid3);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", cidName=").append(cidName);
        sb.append(", cid2Name=").append(cid2Name);
        sb.append(", cid3Name=").append(cid3Name);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", shortUrl=").append(shortUrl);
        sb.append(", materialUrl=").append(materialUrl);
        sb.append(", commisionRatioPc=").append(commisionRatioPc);
        sb.append(", commisionRatioWl=").append(commisionRatioWl);
        sb.append(", wlUnitPrice=").append(wlUnitPrice);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", goodsPackageId=").append(goodsPackageId);
        sb.append(", goodsPackageName=").append(goodsPackageName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", source=").append(source);
        sb.append(", commission=").append(commission);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}