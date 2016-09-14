package cn.stt.seckillsys.exception;

/**
 * Created by Administrator on 2016-09-14.
 */

/**
 * 重复秒杀异常(运行期异常)
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
