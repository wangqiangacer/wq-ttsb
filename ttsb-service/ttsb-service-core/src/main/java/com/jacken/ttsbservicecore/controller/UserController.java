package com.jacken.ttsbservicecore.controller;


import com.jakcen.wqttsbcommon.support.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getUser")
    public Result getUser(){
        return Result.success("this is user");
    }
}
