package com.qunar.fresh.common;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by chebo on 16-2-29.
 */
public class FileUtil {
    /**
     * 读文件
     * @param filePath　文件路径
     * @return readLines 行信息集合
     */
    public static List<String> readFile(String filePath) throws IOException{
        checkNotNull(filePath);//检查文件路径是否为空
        File file = new File(filePath);
        List<String>readLines  = Files.readLines(file, Charsets.UTF_8);
        return readLines;
    }
}
