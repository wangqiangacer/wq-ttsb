package com.jacken.ttsbadmin.fallback;

import com.jacken.ttsbadmin.feign.TbkGoodsService;
import com.jacken.wqttsbmodel.enums.ExceptionEnum;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import org.springframework.stereotype.Component;

@Component
public class TbkGoodsServiceHystrix implements TbkGoodsService {

    @Override
    public Result selectTbkGoodsPage(TbkGoodsRequest request) {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }

    @Override
    public Result getTbkGoodsItem() {
        return Result.error(ExceptionEnum.SYS_EXCEPTION);
    }
}
