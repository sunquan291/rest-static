package com.zte.sdn.oscp.statics.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
}