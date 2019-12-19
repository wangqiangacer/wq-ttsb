package com.jacken.ttsbservicecore.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jacken.wqttsbmodel.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}