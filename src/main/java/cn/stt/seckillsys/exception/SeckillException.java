package cn.stt.seckillsys.exception;

/**
 * Created by Administrator on 2016-09-14.
 */

/**
 * 秒杀相关业务通用异常
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
