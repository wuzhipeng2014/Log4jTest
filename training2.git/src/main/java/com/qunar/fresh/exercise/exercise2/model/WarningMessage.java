package com.qunar.fresh.exercise.exercise2.model;

/**
 * 报警信息
 * Created by chebo on 16-2-29.
 */
public class WarningMessage {
    private String hostName;//报警主机名称
    private String warningTime;//报警时间
    public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public WarningMessage(String warningTime, String hostName) {
        this.warningTime = warningTime;
        this.hostName = hostName;
    }
}
