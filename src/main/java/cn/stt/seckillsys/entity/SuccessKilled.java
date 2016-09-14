package cn.stt.seckillsys.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2016-09-08.
 */
@Data
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;

    //变通，多对一
    private Seckill seckill;

}
