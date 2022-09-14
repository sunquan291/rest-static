package com.zte.sdn.oscp.statics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rest.statics")
public class RestStaticsProperties {
    /**
     * 是否启动该starter
     */
    private boolean enable = true;
    /**
     * 配置不进行统计的URL列表
     */
    private List<String> skipUrls;
    /**
     * 配置滚动统计访问记录条目数(2的指数)
     */
    private int count = 1024;
    /**
     * 配置慢REST执行时间阀值(单位ms)
     */
    private int longRestTime = -1;

    public List<String> getSkipUrls() {
        return skipUrls;
    }

    public void setSkipUrls(List<String> skipUrls) {
        this.skipUrls = skipUrls;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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