package com.jacken.ttsbservicecore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jacken.ttsbservicecore.mapper.UserMapper;
import com.jacken.ttsbservicecore.service.UserService;
import com.jacken.wqttsbmodel.entity.User;
import com.jacken.wqttsbmodel.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("ALL")
@Slf4j
public class UserviceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result selectUser() {
        return Result.success(userMapper.selectList(null));
    }
}
