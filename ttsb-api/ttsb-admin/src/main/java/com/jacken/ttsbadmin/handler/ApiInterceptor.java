package com.jacken.ttsbadmin.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jacken.wqttsbcommon.annontation.BaseControllerNote;
import com.jacken.wqttsbcommon.support.ApiRequestSupport;
import com.jacken.wqttsbcommon.support.ControllerToModeConstant;
import com.jacken.wqttsbcommon.support.PageModel;
import com.jacken.wqttsbcommon.support.SysUserToken;
import com.jacken.wqttsbcommon.utils.ExceptionEnum;
import com.jacken.wqttsbcommon.utils.HttpUtil;
import com.jacken.wqttsbcommon.utils.SignUtils;
import com.jacken.wqttsbcommon.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final String SOLT = "fed1d9fa-297e-437c-97f3-192a259412a2";

    @Autowired
    private TokenUtil tokenUtil;
    /**
     * preHandle在业务处理器处理请求之前被调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified, token, api_version, channel_id, os_version, platform");

        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod hm = (HandlerMethod) handler;
                Method method = hm.getMethod();
                log.info("=getMethod=" + request.getMethod() + "++getContentType++" + request.getContentType());
                BaseControllerNote annotation = method.getAnnotation(BaseControllerNote.class);
                if (annotation == null) {
                    return true;
                }
                String ip = HttpUtil.getIpAddr(request);
                String token = request.getParameter("token");
                String url = request.getRequestURI();
                String data = request.getParameter("data");
                SysUserToken sysUserToken = null;
                if (annotation.checkToken()) {
                    if (StringUtils.isEmpty(token)) {
                        log.info("防问：{} token不存在:{}", url, token);
                        ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.TOKENRERROR);
                        return false;
                    }
                    sysUserToken = tokenUtil.getSysUserToken(token);
                    if (!tokenUtil.checkAdminToken(sysUserToken, token)) {
                        log.info("防问：{} token失效:{}", url, token);
                        ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.TOKENRERROR);
                        return false;
                    }
                }
                String params = "";
                if (annotation.checkParameter()) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        if (StringUtils.isNotEmpty(data)) {
                            params = new String(Base64Utils.decode(data.replaceAll(" ", "+").getBytes()), StandardCharsets.UTF_8);
                            log.info("防问：{}  获取到的参数:{}", url, params);
                            jsonObject = JSONObject.parseObject(params);
                        }
                        if (annotation.checkSign()) {
                            if (StringUtils.isEmpty(params)) {
                                log.info("防问：{} 签名有误:{}", url, params);
                                ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.SIGNERROR);
                            }
                            boolean checkSign = SignUtils.checkParam((Map) JSON.parse(params), SOLT);
                            if (!checkSign) {
                                log.info("防问：{} 签名有误:{}", url, params);
                                ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.SIGNERROR);
                                return false;
                            }
                        }
                        jsonObject.put("sysUserId", sysUserToken == null ? "" : sysUserToken.getSysUserId());
                        jsonObject.put("ip", ip);
                        setPageParams(jsonObject, request);
                        log.info("得到参数：" + JSONObject.toJSONString(jsonObject));
                        request.setAttribute("request", ControllerToModeConstant.getModel(JSONObject.toJSONString(jsonObject), annotation.beanClazz()));
                    } catch (Exception e) {
                        log.info(e.getMessage());
                        e.printStackTrace();
                        log.info("防问：{} 参数有误", url);
                        ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.PARAMEMPTYERROR);
                        return false;
                    }
                } else {
                    if (annotation.checkToken()) {
                        if (sysUserToken == null) {
                            ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.TOKENRERROR);
                            return false;
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sysUserId", sysUserToken.getSysUserId());
                        jsonObject.put("ip", ip);
                        request.setAttribute("req" +
                                "uest", ControllerToModeConstant.getModel(JSONObject.toJSONString(jsonObject), annotation.beanClazz()));
                    }
                }
                log.info("【后台管理接口访问 -> sysUserToken:" + (sysUserToken != null ? JSONObject.toJSONString(sysUserToken) : "") + "  访问接口：" + url + "  访问参数：" + params + "】");
            }
        } catch (Exception e) {
            log.error("出现异常 -> " + e.getMessage());
            ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.PARAMEMPTYERROR);
            return false;
        }
        return true;
    }

    private void setPageParams(JSONObject jsonObject, HttpServletRequest request) {
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        if (currentPage != null && pageSize != null) {
            request.setAttribute("pageModel", ControllerToModeConstant.getModel(JSONObject.toJSONString(jsonObject), PageModel.class));
        }
    }

    /**
     * postHandle在业务处理器处理请求执行完成后，生成视图之前执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 我被执行了");
        super.postHandle(request, response, handler, modelAndView);
    }


    /**
     * afterCompletion在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面），
     * 可以根据ex是否为null判断是否发生了异常，进行日志记录；
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("我被执行了");
        super.afterCompletion(request, response, handler, ex);
    }
}
