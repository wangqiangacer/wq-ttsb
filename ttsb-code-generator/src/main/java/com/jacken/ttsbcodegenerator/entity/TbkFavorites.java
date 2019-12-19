package com.jacken.ttsbcodegenerator.entity;

import java.io.Serializable;
import java.util.Date;

public class TbkFavorites implements Serializable {
    private Integer id;

    private Integer favoritesId;

    private String favoritesTitle;

    private Date createTime;

    private Date updateTime;

    private Integer type;

    private Integer totalResult;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(Integer favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getFavoritesTitle() {
        return favoritesTitle;
    }

    public void setFavoritesTitle(String favoritesTitle) {
        this.favoritesTitle = favoritesTitle == null ? null : favoritesTitle.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", favoritesId=").append(favoritesId);
        sb.append(", favoritesTitle=").append(favoritesTitle);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", type=").append(type);
        sb.append(", totalResult=").append(totalResult);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}