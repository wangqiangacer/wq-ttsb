package com.jacken.ttsbservicecore.controller;

import com.jacken.ttsbservicecore.result.Result;
import com.jacken.ttsbservicecore.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;
    @RequestMapping("/selectUser")
    public Result selectUser(){
        return userService.selectUser();
    }
}
