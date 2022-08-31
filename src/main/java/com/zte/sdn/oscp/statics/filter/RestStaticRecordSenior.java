package com.zte.sdn.oscp.statics.filter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 10184538
 * @Date 2022/8/31 11:32
 **/
public class RestStaticRecordSenior {
    static final int MAXIMUM_CAPACITY = 1 << 30;
    private static AtomicLong INDEX = new AtomicLong(0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YY-MM-dd HH:mm:ss");

    private static int COUNT;

    private static StaticRecordInfo[] recordInfos;

    private static int current_index = -1;

    public static void init(int count) {

        COUNT = tableSizeFor(count);
        recordInfos = new StaticRecordInfo[COUNT];
        for (int i = 0; i < recordInfos.length; i++) {
            recordInfos[i] = new StaticRecordInfo();
        }
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void addRecord(LocalDateTime localDateTime, HttpServletRequest servletRequest, HttpServletResponse servletResponse, Exception e) {
        current_index = (int) (INDEX.get() & (COUNT - 1));
        //build static record info
        long costInMs = Duration.between(localDateTime, LocalDateTime.now()).toMillis();
        //内存重复使用
        StaticRecordInfo recordInfo = recordInfos[current_index];
        recordInfo.setIndex(INDEX.get());
        recordInfo.setInvokeTime(localDateTime.format(FORMATTER));
        recordInfo.setFrom(servletRequest.getRemoteAddr() + ":" + servletRequest.getRemotePort());
        recordInfo.setMethod(servletRequest.getMethod());
        recordInfo.setPath(servletRequest.getRequestURI());
        recordInfo.setQueryString(servletRequest.getQueryString());
        recordInfo.setResultCode(servletResponse.getStatus());
        recordInfo.setException(e);
        recordInfo.setCostInMs(costInMs);

        INDEX.getAndIncrement();
    }

    public static List<StaticRecordInfo> getRecordInfos() {
        List<StaticRecordInfo> infos = new ArrayList<>(COUNT);
        for (int i = 1; i <= COUNT; i++) {
            infos.add(recordInfos[(current_index + i) & (COUNT - 1)]);
        }
        return infos;
    }

    public static StaticRecordInfo getRecordInfos(long index) {
        StaticRecordInfo info = Arrays.stream(recordInfos).filter(p -> p.getIndex() == index).findFirst().orElse(null);
        if (info != null && info.getException() != null) {
            StaticRecordInfo newInfo = new StaticRecordInfo(info);
            StringWriter stringWriter = new StringWriter();
            info.getException().printStackTrace(new PrintWriter(stringWriter));
            newInfo.setExtInfo(stringWriter.toString());
            return newInfo;
        }
        return info;
    }


}