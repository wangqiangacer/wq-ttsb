package com.jacken.ttsbcodegenerator.dao;

import com.jacken.ttsbcodegenerator.entity.User;
import java.util.List;

public interface UserMapper {
    int insert(User record);

    List<User> selectAll();
}