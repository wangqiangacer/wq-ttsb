package com.jacken.wqttsbcommon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jingdong")
public class JdGoodsApiConfig {
    private String apiKey;
    private String apiSecret;
    private String serverUrl;
    private String token;
    private Long unionId;

}
