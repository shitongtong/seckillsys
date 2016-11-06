/**
 * Created by stt on 2016/11/6.
 */
//存放主要交互逻辑js代码
//javascript 模块化
//seckill.detail.init(params);
var seckill = {
    //封装秒杀相关ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handleSeckill: function (seckillId, node) {
        //处理秒杀逻辑 //获取秒杀地址，控制显示逻辑，执行秒杀
        node.hide()
            .html('<button class="btn bg-primary btn-lg" id="killBtn">开始秒杀</button>');//按钮
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //在回调函数中，执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //start seckill 开启秒杀
                    //get seckill address   获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl:" + killUrl);
                    //只绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1：先禁用按钮
                        $(this).addClass('disabled');
                        //2：发送秒杀请求执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var status = killResult['status'];
                                var stateInfo = killResult['stateInfo'];
                                //3：显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    //do not start seckill  未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result : ' + result);
            }
        });
    },
    //验证手机号
    validatePhone: function (phone) {
        //phone 为undefined即为false   isNaN(phone)：判断是否为非数字
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //时间判断
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束！');
        } else if (nowTime < startTime) {
            //秒杀未开始，计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                //var format = event.strftime('Distance seckill Time： %DDay %HHour %MMin %SSec');
                seckillBox.html(format);
                /*时间完成后回调事件*/
            }).on('finish.countdown', function () {
                //获取秒杀地址，控制显示逻辑，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登陆,计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie("killPhone");
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                //绑定phone
                //控制输出
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false  //关闭键盘事件
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    if (seckill.validatePhone(inputPhone)) {
                        //手机号写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});//只在seckill下有效
                        //刷新页面
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<lable class="label label-danger">手机号错误!</lable>').show(300);
                    }
                });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];//==params.startTime
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断,计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result : ' + result);
                }
            });
        }
    }
}