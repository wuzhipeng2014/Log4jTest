package com.qunar.fresh.exercise.exercise2.service;


import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.qunar.fresh.common.FileUtil;
import com.qunar.fresh.exercise.exercise2.model.Host;
import com.qunar.fresh.exercise.exercise2.model.WarningMessage;

import com.qunar.fresh.exercise.exercise2.tools.Comparator;
import org.joda.time.DateTime;



import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理文件信息
 * Created by chebo on 16-2-29.
 */
public class HostInfoService {
    private static final float VALUE = 1023;//报警边界值
    private static final String DATEFORMAT = "yyyy-MM-dd HH-mm-ss";//日期格式
    private static final int N = 3;//TOP3-host和BOTTOM3-host

    private Map<String,Host> hostMap = Maps.newHashMap(); //主机使用JVM堆内存信息（key:主机名称　value:Host对象）
    private List<WarningMessage> warningMessageList = Lists.newArrayList();//报警信息集合


    /**
     * 分析metrics.txt,获取主机使用JVM堆内存情况、报警信息
     * @param filePath　metrics.txt文件路径
     * @return hostList 包含主机的信息的list
     */
    public List<Host> analyseFile(String filePath)throws IOException{
        List<Host> hostList = Lists.newArrayList();
        String useTime = "";//时间点
        List<String>readLines = FileUtil.readFile(filePath);//读文件
        //处理每行信息
        for(String line:readLines){
            if(!Strings.isNullOrEmpty(line)){
                if(isTimeStamp(line)){//是时间戳
                    //将时间戳换成相应格式的日期
                    DateTime dateTime = new DateTime(new Date(Long.parseLong(line.trim())));
                    useTime = dateTime.toString(DATEFORMAT);
                }else if(isMemoryUsageInfo(line)){//如果是内存使用情况
                    dealMemoryUsageInfo(line,useTime);//处理内存使用信息
                }
            }
        }
        hostList = getHostInfoWithAverageMemoryUsage();//计算平均内存使用量
        return hostList;
    }

    /**
     * 获取带有平均内存使用量的主机信息
     * @return　hostList　Host对象集合
     */
    public List getHostInfoWithAverageMemoryUsage(){
        List<Host> hostList = Lists.newArrayList();
        if(hostMap!=null&&hostMap.size()>0){
            for(Host host:new ArrayList<Host>(hostMap.values())){
                float averageMemoryUsage = host.getTotalMemoryUsage()/host.getUseCount();
                host.setAverageMemoryUsage(averageMemoryUsage);
                hostList.add(host);
            }
        }
        return hostList;
    }


    /**
     * 打印topN-host和bottomN-host
     * @param hostList 未排序的host对象集合
     *
     */
    public void printTopNAndBottomN(List<Host> hostList){

        //按照总和进行排序
        Comparator.sortHostListOrderByTotalMemoryUsage(hostList);
        //打印top-n
        int showNum = N<=hostList.size()?N:hostList.size();//显示的数量
        System.out.println("按照总和进行从高到低排序，显示"+showNum+"条数据");
        System.out.println("主机－总和");
        for(int i = 0;i<showNum;i++){
            System.out.println(hostList.get(i).getHostName()+"-"+hostList.get(i).getTotalMemoryUsage());
        }

        //按照平均值进行排序
        Comparator.sortHostListOrderByAverageMemoryUsage(hostList);
        //打印bottom-n
        System.out.println("按照平均使用量进行从低到高排序，显示"+showNum+"条数据");
        System.out.println("主机－平均值");
        for(int i = 0;i<showNum;i++){
            System.out.println(hostList.get(i).getHostName()+"-"+hostList.get(i).getAverageMemoryUsage());
        }
    }
    /**
     * 打印警告信息
     */
    public void printWarningMessage(){
        System.out.println("警报信息");
        System.out.println("主机--时间");
        if(warningMessageList!=null&&warningMessageList.size()>0){
            for(WarningMessage warningMessage:warningMessageList){
                System.out.println(warningMessage.getHostName()+"--"+warningMessage.getWarningTime());
            }
        }
    }

    /**
     * 是否是时间戳
     * @param line　每行信息
     * @return　true:是　false:不是
     */
    public boolean isTimeStamp(String line){
        Pattern pattern = Pattern.compile("^[0-9]*$");//全是数字
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }

    /**
     * 是否是内存使用情况的信息
     * @param line　每行信息
     * @return true:是　false:不是
     */
    public boolean isMemoryUsageInfo(String line){
        boolean flag = false;
        String[] strings = line.split(" ");
        if(strings!=null&&strings.length>0&& strings[0].startsWith("host")){
            flag = true;
        }
        return flag;
    }
    /**
     * 处理内存使用情况信息
     * @param line　行数据
     * @param useTime　使用时间
     */
    public void dealMemoryUsageInfo(String line,String useTime){
        Iterable<String> stringList = Splitter.on(" ").omitEmptyStrings().trimResults().split(line);//分离出主机名和内存使用量
        List<String> list = Lists.newArrayList(stringList);
        String hostName = list.get(0);//主机名
        float memoryUsage = Float.parseFloat(list.get(1));//内存使用量
        Host host = new Host();
        //更新hostMap
        if(hostMap.containsKey(hostName)){//hostName存在,更新使用次数和总使用量
            host = hostMap.get(hostName);
            host.updateHost(memoryUsage);
        }else{//hostName不存在，新构建一个
            host = new Host(hostName,1,memoryUsage);
            hostMap.put(hostName,host);
        }
        //查看是否报警
        if(memoryUsage>VALUE){//报警
            WarningMessage warningMessage = new WarningMessage(useTime,hostName);
            warningMessageList.add(warningMessage);
        }
    }

    public Map<String, Host> getHostMap() {
        return hostMap;
    }

    public List<WarningMessage> getWarningMessageList() {
        return warningMessageList;
    }
}
