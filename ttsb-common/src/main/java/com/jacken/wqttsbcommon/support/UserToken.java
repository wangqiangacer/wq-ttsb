package com.jacken.wqttsbcommon.support;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.Base64Utils;

import java.io.Serializable;

/**
 * @author pocketcoder
 */
@Data
public class UserToken implements Serializable {

    private Long userId;
    private String userPhone;
    private Long time;

    public UserToken() {
    }

    public UserToken(Long userId, String userPhone, Long time) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.time = time;
    }

    public String buildToken() {
        return new String(Base64Utils.encode(JSONObject.toJSONString(this).getBytes()));
    }
}
