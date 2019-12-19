package com.jacken.ttsbservicecore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jacken.wqttsbmodel.entity.User;
import com.jacken.wqttsbmodel.result.Result;

public interface UserService  extends IService<User> {
     Result selectUser();
}
