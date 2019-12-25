//package com.jacken.ttsbadmin.config;
//
//import com.jacken.ttsbadmin.handler.ApiInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//*
// * 增加拦截器配置
//
//
//@Configuration
//public class WebSecurityConfig extends WebMvcConfigurerAdapter {
//
//    @Bean
//    public ApiInterceptor getSecurityInterceptor(){
//        return  new ApiInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
//        // 拦截配置
//        addInterceptor.addPathPatterns("/**");
//        // 排除配置
//        addInterceptor.excludePathPatterns("/error");
//        addInterceptor.excludePathPatterns("/login**");
//    }
//}
