package com.jacken.ttsbadmin.controller;

import com.jacken.ttsbadmin.feign.UserService;
import com.jakcen.wqttsbcommon.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    public Result getUser(){
     return    userService.getUser();
    }
}
