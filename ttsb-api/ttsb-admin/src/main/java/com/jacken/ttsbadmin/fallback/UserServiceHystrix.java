package com.jacken.ttsbadmin.fallback;
import com.jacken.ttsbadmin.feign.UserService;

import com.jacken.wqttsbmodel.enums.ExceptionEnum;
import com.jacken.wqttsbmodel.result.Result;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {

    @Override
    public Result selectUser() {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }
}
