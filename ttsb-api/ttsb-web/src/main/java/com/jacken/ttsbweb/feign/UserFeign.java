package com.jacken.ttsbweb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "ttsb-service-core")
public interface UserFeign {


}
