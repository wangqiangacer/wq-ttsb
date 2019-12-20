package com.jacken.ttsbadmin;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TtsbAdminApplication.class)
@RunWith(SpringRunner.class)
public class TtsbAdminApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public  void test(){
        String s = stringRedisTemplate.opsForValue().get("name");
        System.out.println(s);
    }
    @Test
    public  void test01(){
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
