package com.jacken.ttsbadmin.feign;

import com.jakcen.wqttsbcommon.support.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value ="ttsb-service-core")
public interface UserService {

    @PostMapping("/user/getUser")
    Result getUser();
}
