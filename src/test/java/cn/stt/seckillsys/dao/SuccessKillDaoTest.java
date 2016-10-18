package cn.stt.seckillsys.dao;

import cn.stt.seckillsys.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016-09-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SuccessKillDaoTest {

    @Resource
    SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled(){
        Long seckillId = 1000L;
        Long userPhone = 13916593205L;
        int i = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        System.out.println("插入"+i+"成功");
    }

    @Test
    public void testQueryByIdWithSeckill(){
        Long seckillId = 1000L;
        Long userPhone = 13916593205L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(successKilled);
    }
}
