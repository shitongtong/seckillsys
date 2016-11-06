package cn.stt.seckillsys.service;

import cn.stt.seckillsys.dto.Exposer;
import cn.stt.seckillsys.dto.SeckillExecution;
import cn.stt.seckillsys.entity.Seckill;
import cn.stt.seckillsys.exception.RepeatKillException;
import cn.stt.seckillsys.exception.SeckillException;
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
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        //秒杀未开始：Exposer{exposed=false, md5='null', seckillId=1000, now=1478327587750, start=1472918400000, end=1473004800000}
        //秒杀已开始：Exposer{exposed=true, md5='f17aa88135cf74633ee1880d78996147', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        long phone = 13916593206l;
        String md5 = "f17aa88135cf74633ee1880d78996147";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("seckillExecution={}", seckillExecution);
            //秒杀成功：SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled(seckillId=1000, userPhone=13916593206, state=0, createTime=Sat Nov 05 14:43:31 CST 2016, seckill=Seckill(seckillId=1000, name=1000元秒杀iPad6, number=998, startTime=Sat Nov 05 00:00:00 CST 2016, endTime=Sun Nov 06 00:00:00 CST 2016, createTime=Sat Nov 05 14:43:31 CST 2016))}
        } catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
        } catch (SeckillException e){
            logger.error(e.getMessage(),e);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    //集成测试代码完整逻辑，注意可重复执行。
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){//秒杀已开启
            logger.info("exposer={}",exposer);
            long phone = 13916593206l;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("seckillExecution={}", seckillExecution);
                //秒杀成功：SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled(seckillId=1000, userPhone=13916593206, state=0, createTime=Sat Nov 05 14:43:31 CST 2016, seckill=Seckill(seckillId=1000, name=1000元秒杀iPad6, number=998, startTime=Sat Nov 05 00:00:00 CST 2016, endTime=Sun Nov 06 00:00:00 CST 2016, createTime=Sat Nov 05 14:43:31 CST 2016))}
            } catch (RepeatKillException e){
                logger.error(e.getMessage(),e);
            } catch (SeckillException e){
                logger.error(e.getMessage(),e);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }else{//秒杀未开启
            logger.warn("exposer={}", exposer);
        }

    }

    @Test
    public void testExecuteSeckillProcedure() throws Exception {
        int seckillId = 1001;
        long userPhone = 11111111118l;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();

            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);

            logger.info(execution.getStateInfo());
        }
    }
}