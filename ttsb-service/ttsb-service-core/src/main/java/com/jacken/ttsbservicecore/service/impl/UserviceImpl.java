package com.jacken.ttsbservicecore.service.impl;

import com.jacken.ttsbservicecore.mapper.UserMapper;
import com.jacken.ttsbservicecore.result.Result;
import com.jacken.ttsbservicecore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("ALL")
@Slf4j
public class UserviceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result selectUser() {
        return Result.success(userMapper.selectList(null));
    }
}
