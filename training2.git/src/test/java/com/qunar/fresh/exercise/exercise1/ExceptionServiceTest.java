package com.qunar.fresh.exercise.exercise1;

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
public class ExceptionServiceTest {
    private ExceptionService exceptionService = new ExceptionService();
    private static Logger EXCEPTIONLOGGER = LoggerFactory.getLogger(ExceptionService.class);//日志

    @Test
    public void test() {
        LoggerUtil.initConfig();
        ClassLoader classLoader = getClass().getClassLoader();
        String path=null;
        try {
             path = ExceptionServiceTest.class.getResource("/exercise/exercise1/exception.txt").getPath();
        }catch (NullPointerException e){
            if (path==null)
                System.out.println("无法找到文件");
        }

//        String filePath = classLoader.getResource("/exercise/exercise1/exception.txt").getPath();
        String filePath= ExceptionServiceTest.class.getResource("/exercise/exercise1/exception.txt").getPath();
        try {
            exceptionService.analyseExceptionFile(filePath);
            EXCEPTIONLOGGER.error("文件分析正常进行．．．");
        } catch (Exception e) {
            EXCEPTIONLOGGER.error("文件异常", e);
        }
    }
}
