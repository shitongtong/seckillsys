package cn.stt.seckillsys.service;

import cn.stt.seckillsys.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by stt on 2016/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetScekillList() throws Exception {
        List<Seckill> scekillList = seckillService.getScekillList();
        logger.info("scekillList={}",scekillList);
    }

    @Test
    public void testGetById() throws Exception {

    }

    @Test
    public void testExportSeckillUrl() throws Exception {

    }

    @Test
    public void testExecuteSeckill() throws Exception {

    }
}