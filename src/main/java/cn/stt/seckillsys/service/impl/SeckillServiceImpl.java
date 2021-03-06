package cn.stt.seckillsys.service.impl;

import cn.stt.seckillsys.dao.SeckillDao;
import cn.stt.seckillsys.dao.SuccessKilledDao;
import cn.stt.seckillsys.dao.cache.RedisDao;
import cn.stt.seckillsys.dto.Exposer;
import cn.stt.seckillsys.dto.SeckillExecution;
import cn.stt.seckillsys.entity.Seckill;
import cn.stt.seckillsys.entity.SuccessKilled;
import cn.stt.seckillsys.enums.SeckillStateEnum;
import cn.stt.seckillsys.exception.RepeatKillException;
import cn.stt.seckillsys.exception.SeckillCloseException;
import cn.stt.seckillsys.exception.SeckillException;
import cn.stt.seckillsys.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stt on 2016/10/15.
 */
//@Component @Service @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //md5盐值字符串，用户混淆MD5
    private final String slat = "hdsadgashgda@%^&*(dbsadRRDINM";

    //注入Service依赖
    @Autowired  //@Resource,@Inject //符合j2ee规范的
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    public List<Seckill> getScekillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化缓存
        /**
         * get from cache
         * if null
         *  get db
         *  else
         *      put cache
         *  logic
         */
        //优化点：缓存优化：超时的基础上维护一致性
        //1.访问redis,降低数据库访问压力
        Seckill seckill = redisDao.getSeckill(seckillId);

        if(seckill==null){
            //2.访问数据库
            System.out.println("assess database");
            seckill = seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }
            else {
                //3.放入redis
                System.out.println("put into redis");
                redisDao.putSeckill(seckill);
            }
        }
       /* Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false,seckillId);
        }*/
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    /*
        使用注解控制事务方法的优点：
        1：开发团队达成一致约定，明确标注事务方法的编程风格
        2：保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
        3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try {
            //执行秒杀逻辑：减库存 + 记录购买行为
            Date nowTime = new Date();
            //优化点2，先记录购买行为在减库存，减少update行级锁占用时间
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //唯一：seckillId,userPhone
            if(insertCount <= 0){
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            }else{
                //减库存，热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if(updateCount <= 0){
                    //没有更新到记录，秒杀结束
                    throw new SeckillCloseException("seckill is closed");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }

            }
            /*//减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if(updateCount <= 0){
                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("seckill is closed");
            }else{
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //唯一：seckillId,userPhone
                if(insertCount <= 0){
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }*/
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //将所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }

    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {

        if( md5==null || !md5.equals(getMD5(seckillId)) ){
            return new SeckillExecution(seckillId,SeckillStateEnum.DATE_REWRITE);
        }

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",nowTime);
        map.put("result", null);

        try{
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if(result == 1){
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,sk);
            }
            else{
                return new SeckillExecution(seckillId,SeckillStateEnum.stateOf(result));
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
        }
    }
}
