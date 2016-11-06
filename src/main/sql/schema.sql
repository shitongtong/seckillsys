/*数据库初始化脚本*/

/*创建数据库*/
CREATE DATABASE seckillsys DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
/*使用数据库*/
USE seckillsys;

DROP TABLE IF EXISTS seckill;
DROP TABLE IF EXISTS success_killed;
/*创建秒杀库存表*/
CREATE TABLE seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number INT NOT NULL COMMENT '库存数量',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
start_time TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

/*初始化数据*/
INSERT INTO seckill(name,number,start_time,end_time)
    VALUES
      ('1000元秒杀iPad6',1000,'2016-09-04 00:00:00','2016-09-05 00:00:00'),
      ('500元秒杀iPhone7',500,'2016-09-04 00:00:00','2016-09-05 00:00:00'),
      ('300元秒杀华为4c',300,'2016-09-04 00:00:00','2016-09-05 00:00:00'),
      ('100元秒杀小米6',100,'2016-09-04 00:00:00','2016-09-05 00:00:00');

/*秒杀成功明细表*/
/*用户登录认证相关的信息*/
CREATE TABLE success_killed(
  seckill_id BIGINT NOT NULL COMMENT '秒杀商品id',
  user_phone BIGINT NOT NULL COMMENT '用户手机号',
  state TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识，-1：无效 0：成功 1：已付款 2：已发货',
  create_time TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
  KEY idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT='秒杀成功明细表';

/*连接数据库控制台*/
/*mysql -uroot -p*/