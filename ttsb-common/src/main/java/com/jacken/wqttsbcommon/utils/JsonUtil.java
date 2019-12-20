package com.jacken.wqttsbcommon.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * json utils
 *
 * @author pocketcoder
 */
public final class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper jsonMapper = null;

    static {
        if (jsonMapper == null) {
            jsonMapper = new ObjectMapper();
            SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonMapper.setDateFormat(myDateFormat);

            jsonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            jsonMapper.registerModule(simpleModule);
        }
    }

    private JsonUtil() {
    }

    public static String writeValueAsString(Object o) {
        String result = StringUtils.EMPTY;
        try {
            result = jsonMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return jsonMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            logger.error("readJson error:{}", jsonStr);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readJson(String jsonStr, Class<T> javaType) {
        try {
            return jsonMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            logger.error("readJson error:{}", jsonStr);
            e.printStackTrace();
            return null;
        }
    }

    protected static String format(String str) {
        str = str.replaceAll("\"", "");
        str = str.substring(0, str.length() - 1);
        return str;
    }

}
