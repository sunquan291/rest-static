package com.zte.sdn.oscp.statics.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author 10184538
 * @Date 2022/8/31 11:33
 **/
public class StaticRecordInfo {
    private long index = -1;
    private String invokeTime = "99-99-99 99:99:99";
    private String from = "0.0.0.0:0000";
    private String method = "DEFAULT";
    private String path = "this is unknown default url path";
    private String queryString = "";
    private int resultCode = -1;
    private String extInfo = "";
    private long costInMs = -1;


    @JsonIgnore
    private Exception exception;

    public StaticRecordInfo() {
    }

    public StaticRecordInfo(long index, String invokeTime, String from, String method, String path, String queryString, int resultCode, Exception exception, long costInMs) {
        this.index = index;
        this.invokeTime = invokeTime;
        this.from = from;
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.resultCode = resultCode;
        if (exception != null) {
            this.exception = exception;
            this.extInfo = exception.getMessage();
        }
        this.costInMs = costInMs;
    }

    public StaticRecordInfo(StaticRecordInfo info) {
        this.index = info.index;
        this.invokeTime = info.invokeTime;
        this.from = info.from;
        this.method = info.method;
        this.path = info.path;
        this.queryString = info.queryString;
        this.resultCode = info.resultCode;
        this.exception = info.exception;
        this.costInMs = info.costInMs;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getInvokeTime() {
        return invokeTime;
    }

    public void setInvokeTime(String invokeTime) {
        this.invokeTime = invokeTime;
    }

    public long getCostInMs() {
        return costInMs;
    }

    public void setCostInMs(long costInMs) {
        this.costInMs = costInMs;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
        if (exception != null) {
            this.extInfo = exception.getMessage();
        }
    }
}
