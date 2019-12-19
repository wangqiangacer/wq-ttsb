package com.jacken.ttsbadmin.feign;

import com.jacken.ttsbadmin.result.Result;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "ttsb-service-core")
public interface JdGoodsService {

    @PostMapping("/jdGoods/selectJdGoods")
    Result selectAllJdGoods();

    @PostMapping("/jdGoods/selectJdGoodsPage")
    Result selectJdGoodsPage(JdGoodsRequest request);
}
