package cn.stt.seckillsys.dto;

/**
 * Created by Administrator on 2016-09-14.
 */

import cn.stt.seckillsys.entity.SuccessKilled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 封装秒杀执行后的结果
 */
@Data
@RequiredArgsConstructor(staticName = "getInstance")
@AllArgsConstructor
public class SeckillExecution {

    @NonNull
    private long seckillId;
    @NonNull
    private int state;  //秒杀执行结果状态
    @NonNull
    private String stateInfo;   //状态表示

    private SuccessKilled successKilled;    //秒杀成功对象

}
