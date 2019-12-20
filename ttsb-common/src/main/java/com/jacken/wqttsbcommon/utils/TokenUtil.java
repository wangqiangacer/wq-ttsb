package com.jacken.wqttsbcommon.utils;

import com.alibaba.fastjson.JSONObject;

import com.jacken.wqttsbcommon.support.JedisKey;
import com.jacken.wqttsbcommon.support.SysUserToken;
import com.jacken.wqttsbcommon.support.TokenException;
import com.jacken.wqttsbcommon.support.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author pocketcoder
 */
@Configuration
public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    @Autowired
    private RedisBaseUtils redisUtils;


    public boolean checkToken(UserToken userToken) {
        String key = JedisKey.userTokenKey(userToken.getUserId());
        String token = redisUtils.get(key);
        return StringUtils.isNotEmpty(token);
    }


    public UserToken getUserToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new TokenException(ExceptionEnum.TOKENRERROR);
        }
        String tokenJson = null;
        try {
            tokenJson = new String(Base64.getDecoder().decode(token), "UTF-8");
            return JSONObject.parseObject(tokenJson, UserToken.class);
        } catch (UnsupportedEncodingException e) {
            throw new TokenException(ExceptionEnum.TOKENRERROR);
        }
    }

    public UserToken getUserTokenNotLogin(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String tokenJson = null;
        try {
            tokenJson = new String(Base64.getDecoder().decode(token), "UTF-8");
            return JSONObject.parseObject(tokenJson, UserToken.class);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    public SysUserToken getSysUserToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new TokenException(ExceptionEnum.TOKENRERROR);
        }
        String tokenJson = null;
        try {
            tokenJson = new String(Base64.getDecoder().decode(token), "UTF-8");
            return JSONObject.parseObject(tokenJson, SysUserToken.class);
        } catch (UnsupportedEncodingException e) {
            throw new TokenException(ExceptionEnum.TOKENRERROR);
        }
    }

    public boolean checkAdminToken(SysUserToken sysUserToken, String token) {
        String key = JedisKey.sysUserTokenKey(sysUserToken.getSysUserId());
        String sysToken = redisUtils.get(key);
        log.info("key:" + key);
        log.info("sysUserToken:" + sysToken + "   token:" + token);
        return StringUtils.isNotEmpty(sysToken) && sysToken.equals(token);
    }
}
