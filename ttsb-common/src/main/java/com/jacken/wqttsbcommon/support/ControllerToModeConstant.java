package com.jacken.wqttsbcommon.support;

import com.alibaba.fastjson.JSON;

/**
 * Json转 成对象 并且 检查对象属性
 */
public class ControllerToModeConstant {

    public static <T> T getModel(String json, Class<T> clazz) {

        Validator.validate(JSON.parseObject(json, clazz));
        return JSON.parseObject(json, clazz);
    }

    public static <T> T getModel(String json, Class<T> clazz, Class<?>... groups) {

        Validator.validate(JSON.parseObject(json, clazz), groups);
        return JSON.parseObject(json, clazz);
    }

}
