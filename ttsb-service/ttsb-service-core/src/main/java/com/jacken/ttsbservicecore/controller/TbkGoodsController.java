package com.jacken.ttsbservicecore.controller;


import com.jacken.ttsbservicecore.service.JdGoodsService;
import com.jacken.ttsbservicecore.service.TbkGoodsService;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.request.TbkGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/tbkGoods")
public class TbkGoodsController {

    @Resource
    private TbkGoodsService tbkGoodsService;


    @PostMapping("/selectTbkGoodsPage")
    public  Result selectTbkGoodsPage(@RequestBody TbkGoodsRequest request){
        return tbkGoodsService.selectTbkGoodsPage(request);
    }

    @PostMapping("/getTbkGoodsItem")
    public  Result getTbkGoodsItem(){
        return tbkGoodsService.getTbkGoodsItem();
    }
}
