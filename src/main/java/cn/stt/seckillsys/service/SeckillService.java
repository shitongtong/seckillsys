package cn.stt.seckillsys.service;

/**
 * Created by Administrator on 2016-09-14.
 */

import cn.stt.seckillsys.dto.Exposer;
import cn.stt.seckillsys.dto.SeckillExecution;
import cn.stt.seckillsys.entity.Seckill;
import cn.stt.seckillsys.exception.RepeatKillException;
import cn.stt.seckillsys.exception.SeckillCloseException;
import cn.stt.seckillsys.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"的角度来设计接口
 * 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getScekillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出接口地址，
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * 执行秒杀操作By存储过程
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
