package com.jacken.ttsbservicecore.controller;

import com.jacken.ttsbservicecore.result.Result;
import com.jacken.ttsbservicecore.service.JdGoodsService;
import com.jacken.ttsbservicecore.service.UserService;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/jdGoods")
public class JdGoodsController {

    @Resource
    private JdGoodsService jdGoodsService;

    @RequestMapping("/selectJdGoods")
    public Result selectJdGoods(){
        return jdGoodsService.selectAllJdGoods();
    }

    @PostMapping("/selectJdGoodsPage")
    public  Result selectJdGoodsPage(@RequestBody JdGoodsRequest request){
        return jdGoodsService.selectJdGoodsPage(request);
    }
}
