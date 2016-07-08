package cn.stt.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Administrator on 2016-07-08.
 * junit集成测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestCase1.class, TestCase2.class })
//@Suite.SuiteClasses({
//        cn.stt.test.TestCase1.class,  //要测试的类
//        cn.stt.test.TestCase2.class  //要测试的类
//})
public class TestAll{
    public static void main(String[] args){
        JUnitCore.main("cn.stt.test.TestAll");  //触发
    }
}
