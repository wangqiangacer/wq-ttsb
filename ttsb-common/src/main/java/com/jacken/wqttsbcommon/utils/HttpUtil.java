package com.jacken.wqttsbcommon.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * httputil
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String CHARSET = "UTF-8";

    /**
     * http get请求
     *
     * @param url 请求地址
     * @param
     * @return
     */
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String str = EntityUtils.toString(entity, CHARSET);
                return str;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    // 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
    public static InputStream getInputStream(String imgurl) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(imgurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();

            }

        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return inputStream;

    }

    /**
     * http get请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            url = url + "?";
            for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                String temp = key + "=" + params.get(key) + "&";
                url = url + temp;
            }
            url = url.substring(0, url.length() - 1);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * get请求，使用代理
     *
     * @param url
     * @param userAgent
     * @return
     */
    public static String get(String url, String userAgent) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", userAgent);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String str = EntityUtils.toString(entity, CHARSET);
                return str;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    /**
     * http post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, CHARSET);
            uefEntity.setContentEncoding(CHARSET);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(uefEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * http post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static String post(String url, String params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity sEntity = new StringEntity(params, CHARSET);
            httpPost.setEntity(sEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, CHARSET);
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 获取用户真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info("Proxy-Client-IP:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.info("WL-Proxy-Client-IP:" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("RemoteAddr:" + ip);
        }
        logger.info("获取到的用户ip：{}", ip);
        return ip;
    }

    /**
     * 获取os
     *
     * @param osVersion
     * @return
     */
    public static String getOs(int osVersion) {
        return osVersion == 1 ? "IPhone" : osVersion == 2 ? "Android" : "Web";
    }

    /**
     * 方法名:getBrowser
     * 功能描述:获取浏览器
     * 创建者:''
     * 创建时间: Dec 8, 2017 10:08:42 AM
     * 更新者:''
     * 更新时间: Dec 8, 2017 10:08:42 AM
     */
    public static String getBrowser(HttpServletRequest request) {
        String userAgent = (request.getHeader("user-Agent") == null ? "undefined" : request.getHeader("user-Agent")).toLowerCase();
        if (userAgent.contains("edge")) {
            return (userAgent.substring(userAgent.indexOf("edge")).split(" ")[0]).replace("/", "-");
        } else if (userAgent.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("msie")).split(";")[0];
            return substring.split(" ")[0].replace("msie", "ie") + "-" + substring.split(" ")[1];
        } else if (userAgent.contains("safari") && userAgent.contains("version")) {
            return (userAgent.substring(userAgent.indexOf("safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("version")).split(" ")[0]).split("/")[1];
        } else if (userAgent.contains("opr") || userAgent.contains("opera")) {
            if (userAgent.contains("opera")) {
                return (userAgent.substring(userAgent.indexOf("opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("version")).split(" ")[0]).split("/")[1];
            } else if (userAgent.contains("opr")) {
                return ((userAgent.substring(userAgent.indexOf("opr")).split(" ")[0]).replace("/", "-")).replace("opr", "opera");
            }
        } else if (userAgent.contains("chrome")) {
            return (userAgent.substring(userAgent.indexOf("chrome")).split(" ")[0]).replace("/", "-");
        } else if ((userAgent.contains("mozilla/7.0")) || (userAgent.contains("netscape6")) || (userAgent.contains("mozilla/4.7")) || (userAgent.contains("mozilla/4.78")) || (userAgent.contains("mozilla/4.08")) || (userAgent.contains("mozilla/3"))) {
            return "netscape-?";
        } else if (userAgent.contains("firefox")) {
            return (userAgent.substring(userAgent.indexOf("firefox")).split(" ")[0]).replace("/", "-");
        } else if (userAgent.contains("rv")) {
            String ieversion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            return "ie" + ieversion.substring(0, ieversion.length() - 1);
        }
        return "UnKnown, More-Info: " + userAgent;
    }

    /**
     * 获取web浏览设备信息
     *
     * @param request
     * @return
     */
    public static String getH5Os(HttpServletRequest request) {
        String userAgent = (request.getHeader("user-Agent") == null ? "undefined" : request.getHeader("user-Agent")).toLowerCase();
        if (userAgent.contains("iphone") || userAgent.contains("pad") || userAgent.contains("max")) {
            return "IPhone";
        } else if (userAgent.contains("android")) {
            return "Android";
        }
        return "Web";
    }

    /**
     * 获取userAgent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        return (request.getHeader("user-Agent") == null ? "undefined" : request.getHeader("user-Agent")).toLowerCase();
    }

    public static InputStream http(String url, byte[] postData) {
        URL u = null;
        HttpURLConnection con = null;
        InputStream inputStream = null;
        //尝试发送请求
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "binary/octet-stream");
            con.setRequestProperty("token", "");
            con.setRequestProperty("os_version", "1");
            OutputStream outStream = con.getOutputStream();
            outStream.write(postData);
            outStream.flush();
            outStream.close();
            //读取返回内容
            inputStream = con.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return inputStream;
    }
}
