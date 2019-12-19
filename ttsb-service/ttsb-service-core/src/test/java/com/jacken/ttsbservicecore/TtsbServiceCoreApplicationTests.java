package com.jacken.ttsbservicecore;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jacken.ttsbservicecore.mapper.JdGoodsMapper;
import com.jacken.ttsbservicecore.mapper.UserMapper;
import com.jacken.ttsbservicecore.service.impl.JdGoodsServiceImpl;
import com.jacken.wqttsbmodel.entity.User;
import com.jacken.wqttsbmodel.request.JdGoodsRequest;
import com.jacken.wqttsbmodel.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public  class TtsbServiceCoreApplicationTests {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JdGoodsMapper jdGoodsMapper;

    @Autowired
    private JdGoodsServiceImpl jdGoodsService;

    @Test
    public  void selectUser(){
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(user -> System.out.println(user));
    }

    //分页查询
    @Test
    public  void selectJdGoods(){
        jdGoodsMapper.selectList(null).stream().forEach(jdGoods -> System.out.println(jdGoods));
    }
    @Test
    public  void selectByCondition(){
        JdGoodsRequest request = new JdGoodsRequest();
        request.setGoodsName("美的");
        Result result = jdGoodsService.selectJdGoodsPage(request);
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public  void selectUserList(){
        IPage<User> userPage = new Page<>(2, 2);
        userPage = userMapper.selectPage(userPage, null);
        List<User> list = userPage.getRecords();
        for (User user : list) {
            System.out.println(user);
        }
    }
}
