package com.qunar.fresh.exercise.exercise1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhipengwu on 17-7-6.
 */
public class LoggerTest {


    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(LoggerTest.class);
        try {
            logger.info("开始计算两个数的商####");
            int a = 10;
            int b = 0;
            int c = a / b;
        }catch (Exception e){
            logger.error("计算两数的商出现错误, ",e);
        }

    }
}
