package com.jacken.ttsbadmin.feign;

import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "ttsb-service-core")
public interface TbkGoodsService {

    @PostMapping("/tbkGoods/selectTbkGoodsPage")
    Result selectTbkGoodsPage(TbkGoodsRequest request);

    @PostMapping("/tbkGoods/getTbkGoodsItem")
    Result getTbkGoodsItem();
}
