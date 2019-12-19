package com.jacken.ttsbadmin.fallback;

import com.jacken.ttsbadmin.feign.JdGoodsService;

import com.jacken.wqttsbmodel.enums.ExceptionEnum;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import org.springframework.stereotype.Component;

@Component
public class JdGoodsServiceHystrix implements JdGoodsService {
    @Override
    public Result selectAllJdGoods() {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }

    @Override
    public Result selectJdGoodsPage(JdGoodsRequest request) {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }
}
