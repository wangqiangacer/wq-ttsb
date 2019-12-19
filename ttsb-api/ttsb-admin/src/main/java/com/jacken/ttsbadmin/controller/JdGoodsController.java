package com.jacken.ttsbadmin.controller;

import com.jacken.ttsbadmin.feign.JdGoodsService;
import com.jacken.ttsbadmin.result.Result;
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

    @RequestMapping("/selectAllJdGoods")
    public Result selectJdGoods(){
        return jdGoodsService.selectAllJdGoods();
    }

    @PostMapping("/selectJdGoodsPage")
    public  Result selectJdGoodsPage(@RequestBody JdGoodsRequest request){
        return jdGoodsService.selectJdGoodsPage(request);
    }
}
