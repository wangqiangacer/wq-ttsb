package com.jacken.wqttsbcommon.utils;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;


import java.io.StringReader;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author pocketcoder
 */
public class SignUtils {

    /**
     * 校验签名
     *
     * @param params
     * @param key
     * @return
     */
    public static boolean checkParam(Map params, String key) {
        boolean result = false;
        if (params.containsKey("sign")) {
            String sign = (String) params.get("sign");
            params.remove("sign");
            StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
            SignUtils.buildPayParams(buf, params, false);
            String preStr = buf.toString();
            String signRecieve = MD5.sign(preStr, "&key=" + key, "utf-8");
            result = sign.equalsIgnoreCase(signRecieve);
        }
        return result;
    }

    /**
     * 过滤参数
     *
     * @param sArray
     * @return
     * @author
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }


    /**
     * 将map转成String
     *
     * @param payParams
     * @return
     */
    public static String paramsToString(Map<String, Object> payParams) {
        return paramsToString(payParams, false);
    }

    public static String paramsToString(Map<String, Object> payParams, boolean encoding) {
        return paramsToString(new StringBuilder(), payParams, encoding);
    }

    /**
     * @param payParams
     * @return
     * @author
     */
    public static String paramsToString(StringBuilder sb, Map<String, Object> payParams, boolean encoding) {
        buildPayParams(sb, payParams, encoding);
        return sb.toString();
    }

    /**
     * @param payParams
     * @return
     * @author
     */
    public static void buildPayParams(StringBuilder sb, Map<String, Object> payParams, boolean encoding) {
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            sb.append(key).append("=");
            if (encoding) {
                sb.append(urlEncode(String.valueOf(payParams.get(key))));
            } else {
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            return str;
        }
    }


    public static Element readerXml(String body, String encode) throws DocumentException {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new StringReader(body));
        source.setEncoding(encode);
        Document doc = reader.read(source);
        Element element = doc.getRootElement();
        return element;
    }


}
