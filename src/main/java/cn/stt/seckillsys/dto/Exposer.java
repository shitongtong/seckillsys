package cn.stt.seckillsys.dto;

/**
 * Created by Administrator on 2016-09-14.
 */

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 暴露秒杀地址DTO
 */

/**
 * @Data
 *@RequiredArgsConstructor(staticName = "getInstance") → 生成一个获取对象的getInstance静态方法参数为有参构造方法的参数。
 * 标记了@NonNull的字段会生成包含这些字段的private构造方法
 */
//@Data
//@RequiredArgsConstructor(staticName = "getInstance")
public class Exposer {

//    @NonNull
    private boolean exposed;    //暴露开关
//    @NonNull
    private String md5; //一种加密措施
//    @NonNull
    private long seckillId; //秒杀Id

    private long now;   //系统当前时间（毫秒）

    private long start; //开始时间

    private long end;   //结束时间

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long now, long start, long end) {
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
