package com.qunar.fresh.exercise.exercise2.model;

/**
 * 主机信息
 * Created by chebo on 16-2-29.
 *
 */
public class Host {
    private String hostName;//主机名
    private int useCount;//使用JVM堆内存的次数
    private float totalMemoryUsage;//总JVM堆内存使用量
    private float averageMemoryUsage;//平均JVM堆内存使用量

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public float getTotalMemoryUsage() {
        return totalMemoryUsage;
    }

    public void setTotalMemoryUsage(float totalMemoryUsage) {
        this.totalMemoryUsage = totalMemoryUsage;
    }

    public float getAverageMemoryUsage() {
        return averageMemoryUsage;
    }

    public void setAverageMemoryUsage(float averageMemoryUsage) {
        this.averageMemoryUsage = averageMemoryUsage;
    }

    public Host(String hostName, int useCount, float totalMemoryUsage, float averageMemoryUsage) {
        this.hostName = hostName;
        this.useCount = useCount;
        this.totalMemoryUsage = totalMemoryUsage;
        this.averageMemoryUsage = averageMemoryUsage;
    }

    /**
     * 更新host信息
     * @param memoryUsage
     */
    public void updateHost(float memoryUsage){
        this.setTotalMemoryUsage(this.getTotalMemoryUsage()+memoryUsage);
        this.setUseCount(this.getUseCount()+1);
    }

    public Host(String hostName, int useCount, float totalMemoryUsage) {
        this.hostName = hostName;
        this.useCount = useCount;
        this.totalMemoryUsage = totalMemoryUsage;
    }

    public Host() {
    }
}
