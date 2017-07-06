package com.qunar.fresh.exercise.exercise2;

import com.qunar.fresh.common.LoggerUtil;
import com.qunar.fresh.exercise.exercise2.model.Host;
import com.qunar.fresh.exercise.exercise2.service.HostInfoService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by chebo on 16-2-29.
 */
public class HostInfoServiceTest {
    private HostInfoService hostInfoService = new HostInfoService();
    private static Logger HEAPLOGGER = LoggerFactory.getLogger(HostInfoService.class);//日志
    private static Logger logger = LoggerFactory.getLogger(HostInfoService.class);//日志
    @Test
    public  void test(){
        LoggerUtil.initConfig();
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource("exercise/exercise2/metrics.txt").getPath();
        List<Host> hostList = null;//处理信息
        try {
            System.out.println("############################");
            HEAPLOGGER.error("文件异常");

            hostList = hostInfoService.analyseFile(filePath);
            hostInfoService.printTopNAndBottomN(hostList);//打印top-n和bottom-n
            hostInfoService.printWarningMessage();//印警告信息
        } catch (IOException e) {
            HEAPLOGGER.error("文件异常,{}",e);
            HEAPLOGGER.error("文件异常");
        }
    }


    @Test
    public void loggerTest(){
        logger.info("输出info日志");
        logger.error("输出error日志");
    }
}
