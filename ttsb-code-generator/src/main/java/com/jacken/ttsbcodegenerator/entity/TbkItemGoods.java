package com.jacken.ttsbcodegenerator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbkItemGoods implements Serializable {
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl == null ? null : pictUrl.trim();
    }

    public String getSmallImages() {
        return smallImages;
    }

    public void setSmallImages(String smallImages) {
        this.smallImages = smallImages == null ? null : smallImages.trim();
    }

    public BigDecimal getZkFinalPriceWap() {
        return zkFinalPriceWap;
    }

    public void setZkFinalPriceWap(BigDecimal zkFinalPriceWap) {
        this.zkFinalPriceWap = zkFinalPriceWap;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getTkRate() {
        return tkRate;
    }

    public void setTkRate(String tkRate) {
        this.tkRate = tkRate == null ? null : tkRate.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getProvcity() {
        return provcity;
    }

    public void setProvcity(String provcity) {
        this.provcity = provcity == null ? null : provcity.trim();
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl == null ? null : itemUrl.trim();
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle == null ? null : shopTitle.trim();
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(Integer favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getCouponClickUrl() {
        return couponClickUrl;
    }

    public void setCouponClickUrl(String couponClickUrl) {
        this.couponClickUrl = couponClickUrl == null ? null : couponClickUrl.trim();
    }

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo == null ? null : couponInfo.trim();
    }

    public Integer getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Integer couponValue) {
        this.couponValue = couponValue;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getFavoritesTitle() {
        return favoritesTitle;
    }

    public void setFavoritesTitle(String favoritesTitle) {
        this.favoritesTitle = favoritesTitle == null ? null : favoritesTitle.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime == null ? null : couponEndTime.trim();
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime == null ? null : couponStartTime.trim();
    }

    public Integer getCouponTotalCount() {
        return couponTotalCount;
    }

    public void setCouponTotalCount(Integer couponTotalCount) {
        this.couponTotalCount = couponTotalCount;
    }

    public Integer getCouponRemainCount() {
        return couponRemainCount;
    }

    public void setCouponRemainCount(Integer couponRemainCount) {
        this.couponRemainCount = couponRemainCount;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", numIid=").append(numIid);
        sb.append(", title=").append(title);
        sb.append(", pictUrl=").append(pictUrl);
        sb.append(", smallImages=").append(smallImages);
        sb.append(", zkFinalPriceWap=").append(zkFinalPriceWap);
        sb.append(", nick=").append(nick);
        sb.append(", volume=").append(volume);
        sb.append(", tkRate=").append(tkRate);
        sb.append(", userType=").append(userType);
        sb.append(", provcity=").append(provcity);
        sb.append(", itemUrl=").append(itemUrl);
        sb.append(", shopTitle=").append(shopTitle);
        sb.append(", reservePrice=").append(reservePrice);
        sb.append(", sellerId=").append(sellerId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", favoritesId=").append(favoritesId);
        sb.append(", couponClickUrl=").append(couponClickUrl);
        sb.append(", couponInfo=").append(couponInfo);
        sb.append(", couponValue=").append(couponValue);
        sb.append(", commission=").append(commission);
        sb.append(", favoritesTitle=").append(favoritesTitle);
        sb.append(", source=").append(source);
        sb.append(", couponEndTime=").append(couponEndTime);
        sb.append(", couponStartTime=").append(couponStartTime);
        sb.append(", couponTotalCount=").append(couponTotalCount);
        sb.append(", couponRemainCount=").append(couponRemainCount);
        sb.append(", category=").append(category);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}