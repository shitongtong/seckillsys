package cn.stt.seckillsys.dao.cache;

import cn.stt.seckillsys.dao.SeckillDao;
import cn.stt.seckillsys.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by stt on 2016/11/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private int id = 7;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {
        //Get and Put
        Seckill seckill = redisDao.getSeckill(id);

        if (seckill == null) {

            seckill = seckillDao.queryById(id);

            if (seckill != null) {

                String result = redisDao.putSeckill(seckill);
                System.out.println(result);

                seckill = redisDao.getSeckill(id);
                System.out.println("from redis：" + seckill);

            }
        } else {
            System.out.println("from redis：" + seckill);
        }
    }
}