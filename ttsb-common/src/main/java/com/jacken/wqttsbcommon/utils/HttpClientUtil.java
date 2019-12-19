package com.jacken.wqttsbcommon.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;


/**
 * http请求工具类
 *
 * @author chenjie
 */
@Slf4j
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class); // 日志记录

    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    private static int socketTimeout = 30000;// 单位(毫秒)

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            // 整个连接池最大连接数
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(100);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
            // 设置超时时间
            cm.setDefaultSocketConfig(socketConfig);
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * json方式httpPost请求
     *
     * @param url
     * @param jsonParam
     * @return
     */
    public static String httpPost(String url, String jsonParam) {
        logger.info("HTTPPOST请求 url = {}", url);
        logger.info("HTTPPOST请求 jsonData = {}", jsonParam);
        try {
            HttpPost method = new HttpPost(url);
            if (StringUtils.isNotEmpty(jsonParam)) {

                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            return getResult(method);
        } catch (Exception e) {
//			throw new InteractionException("httpClient请求异常");
            return null;
        }
    }

    /**
     * 普通GET请求
     *
     * @param url
     * @return
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    /**
     * 不设置包头POST传输
     *
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String httpGetRequest(String url, Map<String, Object> params) {
        try {
            URIBuilder ub = new URIBuilder();
            ub.setPath(url);

            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);

            HttpGet httpGet = new HttpGet(ub.build());
            return getResult(httpGet);
        } catch (Exception e) {
//			throw new InteractionException(e);
            return null;
        }
    }

    /**
     * 设置包头GET传输
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            URIBuilder ub = new URIBuilder();
            ub.setPath(url);
            if (null != params) {
                ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
                ub.setParameters(pairs);
            }

            HttpGet httpGet = new HttpGet(ub.build());
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
            return getResult(httpGet);
        } catch (Exception e) {
//			throw new InteractionException(e);
            return null;
        }
    }

    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    /**
     * 不设置包头POST传输
     *
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Map<String, Object> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
            return getResult(httpPost);
        } catch (Exception e) {
//			throw new InteractionException(e);
            return null;
        }
    }

    /**
     * 设置包头POST传输
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            HttpPost httpPost = new HttpPost(url);

            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }

            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
            return getResult(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
//			throw new InteractionException(e);
            return null;
        }
    }

    /**
     * 设置包头POST传输
     *
     * @param url
     * @param headers
     * @param jsonParam
     * @return
     * @author mxf
     * @date 2018年5月22日
     */
    public static String httpPostJsonRequest(String url, Map<String, Object> headers, String jsonParam) {
        try {
            HttpPost httpPost = new HttpPost(url);
            if (null != headers) {
                for (Map.Entry<String, Object> param : headers.entrySet()) {
                    httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
                }
            }
            if (StringUtils.isNotEmpty(jsonParam)) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            return getResult(httpPost);
        } catch (Exception e) {
//			throw new InteractionException(e);
            return null;
        }
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }

        return pairs;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            logger.error("Http请求系统异常 ClientProtocolException" + e);
            return null;
        } catch (IOException e) {
            logger.error("Http请求系统异常 IOException" + e);
            return null;
        } finally {

        }
        return EMPTY_STR;
    }

    /**
     * 设置包头POST传输
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpPostRequest1(String url, Map<String, Object> headers, Map<String, Object> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
            return getResult(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
//			throw new InteractionException(e);
            return null;
        }
    }
}
