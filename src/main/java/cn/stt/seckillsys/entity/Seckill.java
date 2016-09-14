package cn.stt.seckillsys.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2016-09-08.
 */
@Data
public class Seckill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;
    private Date endTime;
    private Date createTime;
}
