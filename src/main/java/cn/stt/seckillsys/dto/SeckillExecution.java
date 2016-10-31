package cn.stt.seckillsys.dto;

/**
 * Created by Administrator on 2016-09-14.
 */

import cn.stt.seckillsys.entity.SuccessKilled;
import cn.stt.seckillsys.enums.SeckillStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 封装秒杀执行后的结果
 */
//@Data
//@RequiredArgsConstructor(staticName = "getInstance")
//@AllArgsConstructor
public class SeckillExecution {

//    @NonNull
    private long seckillId;
//    @NonNull
    private int state;  //秒杀执行结果状态
//    @NonNull
    private String stateInfo;   //状态表示

    private SuccessKilled successKilled;    //秒杀成功对象

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
