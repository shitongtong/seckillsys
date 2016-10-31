package cn.stt.seckillsys.dao;

import cn.stt.seckillsys.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-09-09.
 */
//配置spring和junit整合，junit启动时加载springIOC容器
//spring-test,junit
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    //注入dao实现类依赖
    @Resource
    SeckillDao seckillDao;

    @Test
    public void testReduceNumber(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse("2016-09-04 10:00:00");
            int i = seckillDao.reduceNumber(1000, date);
            System.out.println(i);
            System.out.println("秒杀成功");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQueryById(){
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll(){
        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for (int i = 0; i <seckillList.size() ; i++) {
            System.out.println(seckillList.get(i));
        }

//        SeckillExecution.getInstance()
    }
}
