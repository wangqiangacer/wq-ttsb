package com.jacken.wqttsbcommon.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.jacken.wqttsbcommon.utils.CheckUtils;
import com.jacken.wqttsbcommon.utils.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * @author pocketcoder
 */
public class ApiRequestSupport {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestSupport.class);

    private final static Gson GSON = new Gson();

    private static final String SALT = "SUGER.APP";

    /**
     * 请求参数权限校验
     *
     * @param data
     * @throws
     */
    public static void checkData(String data) {
        if (CheckUtils.isNull(data) || data.length() == 0) {
            throw new ParamException(ExceptionEnum.PARAMEMPTYERROR);
        }
    }

    /**
     * 获取请求参数实体
     *
     * @param data 请求参数字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static JSONObject getRequestData(String data) throws UnsupportedEncodingException {

        logger.info("input data:{}", data);
        if (CheckUtils.isNull(data)) {
            throw new ParamException(ExceptionEnum.PARAMEMPTYERROR);
        }
        String value;
        try {
            value = new String(Base64.getDecoder().decode(data), "UTF-8");
        } catch (Exception e) {
            logger.info("解析出错=={}", e);
            value = new String(org.apache.commons.codec.binary.Base64.decodeBase64(URLDecoder.decode(data, "UTF-8")));
            logger.info("errorvalue=={}", value);
        }
        return JSON.parseObject(value);
    }

    public static void invokeExceptionWrapper(HttpServletResponse response, ExceptionEnum exceptionEnum)
            throws IOException {
        Result json = new Result();
        json.setCode(exceptionEnum.getCode());
        json.setMessage(exceptionEnum.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(GSON.toJson(json));
    }
}
