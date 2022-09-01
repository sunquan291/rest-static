package com.zte.sdn.oscp.statics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rest.statics")
public class RestStaticsProperties {
    /**
     * 不进行统计的URL
     */
    private List<String> skipUrls;
    /**
     * 统计条目数  2的指数
     */
    private int count = 1024;
    /**
     * 慢执行时间
     */
    private int longRestTime = -1;

    public List<String> getSkipUrls() {
        return skipUrls;
    }

    public void setSkipUrls(List<String> skipUrls) {
        this.skipUrls = skipUrls;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLongRestTime() {
        return longRestTime;
    }

    public void setLongRestTime(int longRestTime) {
        this.longRestTime = longRestTime;
    }
}