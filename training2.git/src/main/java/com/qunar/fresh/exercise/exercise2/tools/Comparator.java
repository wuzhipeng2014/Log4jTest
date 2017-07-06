package com.qunar.fresh.exercise.exercise2.tools;

import com.qunar.fresh.exercise.exercise2.model.Host;

import java.util.Collections;
import java.util.List;

/**
 * Created by chebo on 16-2-29.
 */
public class Comparator {
    /**
     * 按总使用内存量来从高到低进行排序
     * @param hostList　主机信息
     */
    public static void sortHostListOrderByTotalMemoryUsage(List<Host> hostList){
        java.util.Comparator<Host> comparatorByTotalMemoryUsage = new java.util.Comparator<Host>(){
            @Override
            public int compare(Host h1, Host h2) {
                return (int)(h2.getTotalMemoryUsage()-h1.getTotalMemoryUsage());

            }};//按照总共使用内存量进行排序
        Collections.sort(hostList, comparatorByTotalMemoryUsage);
    }

    /**
     * 按平均使用内存量来从低到高进行排序
     * @param hostList
     */
    public static void sortHostListOrderByAverageMemoryUsage(List<Host>hostList){
        java.util.Comparator<Host> comparatorByAverageMemoryUsage = new java.util.Comparator<Host>(){
            @Override
            public int compare(Host h1, Host h2) {
                return (int)(h1.getAverageMemoryUsage()-h2.getAverageMemoryUsage());

            }};//按照平均使用内存量进行排序
        Collections.sort(hostList, comparatorByAverageMemoryUsage);
    }
}
