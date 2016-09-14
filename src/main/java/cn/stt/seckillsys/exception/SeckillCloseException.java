package cn.stt.seckillsys.exception;

/**
 * Created by Administrator on 2016-09-14.
 */

/**
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
