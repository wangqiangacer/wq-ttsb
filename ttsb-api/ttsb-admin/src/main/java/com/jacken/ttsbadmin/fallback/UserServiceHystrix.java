package com.jacken.ttsbadmin.fallback;

import com.jacken.ttsbadmin.feign.UserService;
import com.jakcen.wqttsbcommon.support.Result;
import com.jakcen.wqttsbcommon.utils.ExceptionEnum;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {
    @Override
    public Result getUser() {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }
}
