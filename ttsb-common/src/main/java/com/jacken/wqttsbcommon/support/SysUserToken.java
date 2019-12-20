package com.jacken.wqttsbcommon.support;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.Base64Utils;

import java.io.Serializable;

@Data
public class SysUserToken implements Serializable {

    private Long sysUserId;
    private String sysUserName;
    private Long time;

    public SysUserToken() {
    }

    public SysUserToken(Long sysUserId, String sysUserName, Long time) {
        this.sysUserId = sysUserId;
        this.sysUserName = sysUserName;
        this.time = time;
    }

    public  String buildToken() {
        return new String(Base64Utils.encode(JSONObject.toJSONString(this).getBytes()));
    }

    public static void main(String[] args) {
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setSysUserId(12345L);
        sysUserToken.setSysUserName("wangqiang");
        sysUserToken.setTime(System.currentTimeMillis());
        String s = new String(Base64Utils.encode(JSONObject.toJSONString(sysUserToken).getBytes()));
        System.out.println(s);

    }
}
