package com.qunar.fresh.common;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

/**
 * Created by chebo on 16-2-29.
 */
public class LoggerUtil {
    /**
     * 装载配置文件，初始化配置信息
     */
    public static void initConfig(){

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();

        try {
            /* 配置文件路径 */
            configurator.doConfigure("src/main/resources/common/slf4j_logback/logback.xml");
        } catch (JoranException e) {
            e.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);

        System.out.println("logger init completed");
        for(int i = 0 ; i < 120 ; i++){
            System.out.print("-");
        }
        System.out.println();

    }
}
