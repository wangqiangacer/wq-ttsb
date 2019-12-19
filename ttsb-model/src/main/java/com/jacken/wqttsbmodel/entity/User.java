package com.jacken.wqttsbmodel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;

    private String nickName;

    private String realName;

    private String mobileNo;

    private String wechatOpenid;

    private Integer isVip;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private String avatarUrl;

    private Date lastLoginTime;

    private Date registerTime;

    private String sourceCode;

    private Date vipTime;

    private String channelCode;

    private String taobaoOpenid;

    private static final long serialVersionUID = 1L;

}