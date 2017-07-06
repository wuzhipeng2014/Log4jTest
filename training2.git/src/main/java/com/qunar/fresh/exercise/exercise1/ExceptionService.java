package com.qunar.fresh.exercise.exercise1;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.qunar.fresh.common.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chebo on 16-2-29.
 */
public class ExceptionService {


    /**
     * 分析异常文件
     *
     * @param filePath 　文件路径
     */
    public void analyseExceptionFile(String filePath) throws IOException {
        //读文件
        List<String> readLines = null;
        try {
            readLines = FileUtil.readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //先判断是否是单一的异常，如果是直接输出即可，否则是一个异常链
        if (readLines != null && readLines.size() > 0) {
            if (!this.isOnlyOnException(readLines)) {//是一个异常链
                //获取所有的异常
                List<String> allExceptions = this.getAllExceptions(readLines);
                //获取根异常信息
                List<String> rootExceptionInfo = this.getRootExceptionInfo(readLines);
                //打印信息
                this.printExceptionInfo(allExceptions, rootExceptionInfo);
            } else {//是一个单一的异常,直接打印
                this.printOneExceptionInfo(readLines);
            }
        } else {
            System.out.println("无异常信息");
        }
    }

    /**
     * 是否是单一的一个异常
     *
     * @param readLines 　异常信息字符串
     * @return
     */
    public boolean isOnlyOnException(List<String> readLines) {
        boolean flag = true;
        if (readLines != null && readLines.size() > 0) {
            for (int i = 0; i < readLines.size(); i++) {
                if (readLines.get(i).startsWith("Caused by")) {//以Caused by开头
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取所有的异常
     *
     * @param readLines 异常信息字符串
     * @return　包含异常名称的集合
     */
    public List<String> getAllExceptions(List<String> readLines) {
        List<String> stringList = Lists.newArrayList();
        if (readLines != null && readLines.size() > 0) {
            for (String info : readLines) {
                if (StringUtils.isNotEmpty(info) && StringUtils.isNotBlank(info) && !info.startsWith("\t") && !info.startsWith(" ")) {//是包含异常名称的信息字符串
                    String exceptionName = this.getExceptionNameFromLine(info);//获取异常信息
                    //异常名称集合为空或者不包含该异常，且异常名称不为空则讲这个异常名称加入集合中
                    if (!stringList.contains(exceptionName) && StringUtils.isNotEmpty(exceptionName) && StringUtils.isNotBlank(exceptionName)) {
                        stringList.add(exceptionName.trim());
                    }
                }
            }
        }
        return stringList;
    }


    /**
     * 从一行信息中获取异常名称
     *
     * @param line 　一行信息字符串
     * @return　exceptionName 异常名称
     */
    public String getExceptionNameFromLine(String line) {
        String exceptionName = "";//异常名称
        String[] exceptionInfo;//异常信息
        //以：分割字符串，第一个为包含异常名称的字符串
        if ((StringUtils.isNotEmpty(line) && StringUtils.isNotBlank(line))) {
            if (line.trim().startsWith("Caused by")) {//不是第一行信息
                exceptionInfo = line.split(":");
                if (exceptionInfo != null && exceptionInfo.length > 0) {
                    exceptionName = exceptionInfo[1].trim();
                }
            } else {//信息来源于第一行
                exceptionInfo = line.split(":")[0].split("\\s");
                exceptionName = exceptionInfo[exceptionInfo.length - 1];//异常名称
            }
        }
        return exceptionName;
    }

    /**
     * 从异常信息字符串集合中获取根异常信息
     *
     * @param readLines 文件读取获得的字符串集合
     * @return　rootExceptionInfo　根异常信息字符串信息集合
     */
    public List<String> getRootExceptionInfo(List<String> readLines) {
        List<String> rootExceptionInfo = Lists.newArrayList();//根异常信息字符串集合
        //从list集合后面向前面查找，找到第一个包含Caused by的位置，从这个地方开始到list结束，均为根异常信息
        int lastCausedByIndex = 0;//最后一个Caused by　的位置
        if (readLines != null && readLines.size() > 0) {
            for (int i = readLines.size() - 1; i >= 0; i--) {
                if (readLines.get(i).startsWith("Caused by")) {
                    lastCausedByIndex = i;
                    break;
                }
            }
            for (int i = lastCausedByIndex; i < readLines.size(); i++) {
                rootExceptionInfo.add(readLines.get(i));
            }
        }
        return rootExceptionInfo;
    }

    /**
     * 当只有一个异常时，打印异常信息
     *
     * @param readLines 异常信息字符串集合
     */
    public void printOneExceptionInfo(List<String> readLines) {
        //从第一行中获取出异常名称。
        System.out.println("Exception is : " + this.getExceptionNameFromLine(readLines.get(0)));
        System.out.println("Root Exception is :");
        for (String string : readLines) {
            System.out.println(string);
        }
    }

    /**
     * 打印异常链信息
     *
     * @param allExceptionsName 所有异常名称集合
     * @param rootExceptionInfo 　根异常信息
     */
    public void printExceptionInfo(List<String> allExceptionsName, List<String> rootExceptionInfo) {
        if (allExceptionsName != null && allExceptionsName.size() > 0) {
            StringBuffer content = new StringBuffer();
            StringBuffer allExceptionBuffer = new StringBuffer();
            allExceptionBuffer.append("Exceptions are:");
            for (String exceptionName : allExceptionsName) {
                allExceptionBuffer.append(exceptionName).append(",");
            }
            content.append(allExceptionBuffer.substring(0, allExceptionBuffer.length() - 1));
            content.append("\n\n");
            content.append("Root exception is:\n\n");
            for (int i = 0; i < rootExceptionInfo.size(); i++) {
                if (i != 0) {
                    content.append(rootExceptionInfo.get(i)).append("\n");
                } else {
                    content.append(rootExceptionInfo.get(i).replace("Caused by: ", "").concat("\n"));
                }
            }
            System.out.println(content.toString());
        }
    }
}
