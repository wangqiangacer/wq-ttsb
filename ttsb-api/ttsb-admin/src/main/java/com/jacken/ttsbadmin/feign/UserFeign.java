package com.jacken.ttsbadmin.feign;

import com.jacken.wqttsbmodel.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "ttsb-service-core")
public interface UserFeign {


}
