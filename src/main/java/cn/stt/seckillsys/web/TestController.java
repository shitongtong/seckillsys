package cn.stt.seckillsys.web;

import cn.stt.seckillsys.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/4/1.
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private JedisUtil jedisUtil;

    @RequestMapping("testJedis")
    public void testJedis(){
        jedisUtil.set("haha","hahha");
        jedisUtil.incr("hahaha");
    }
}
