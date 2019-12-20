package com.jacken.wqttsbcommon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 	淘宝联盟配置
 * @author honglongsun
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tbk")
public class TbkConfig {
    private String appKey;
    private String appSecret;
    private String serverUrl;
    private Long adzoneId;
    private String vkKey;
    private String vkServerUrl;
    private String tokenUrl;
    private String redirectUrl;
}
