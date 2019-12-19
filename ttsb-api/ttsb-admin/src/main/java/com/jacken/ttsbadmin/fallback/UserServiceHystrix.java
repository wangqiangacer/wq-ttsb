package com.jacken.ttsbadmin.fallback;
import com.jacken.ttsbadmin.feign.UserService;
import com.jacken.ttsbadmin.result.Result;
import com.jacken.ttsbadmin.result.exception.ExceptionEnum;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {

    @Override
    public Result selectUser() {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }
}
