package com.zte.sdn.oscp.statics.filter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestStaticRecord {

    private static AtomicLong INDEX = new AtomicLong(0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("YY-MM-dd HH:mm:ss");

    private static final int COUNT = 1000;
    private static List<StaticRecordInfo> recordInfos = new LinkedList<>();

    public static void addRecord(LocalDateTime localDateTime, HttpServletRequest servletRequest, HttpServletResponse servletResponse, Exception e) {
        if (recordInfos.size() > COUNT) {
            recordInfos.subList(0, (recordInfos.size() - COUNT)).clear();
        }
        //build static record info
        long costInMs = Duration.between(localDateTime, LocalDateTime.now()).toMillis();
        StaticRecordInfo info = new StaticRecordInfo(INDEX.get(), localDateTime.format(FORMATTER),
                servletRequest.getRemoteAddr() + ":" + servletRequest.getRemotePort(),
                servletRequest.getMethod(),
                servletRequest.getRequestURI(),
                servletRequest.getQueryString(),
                servletResponse.getStatus(),
                e,
                costInMs);
        recordInfos.add(info);
        INDEX.getAndIncrement();
    }

    public static List<StaticRecordInfo> getRecordInfos() {
        return recordInfos;
    }

    public static StaticRecordInfo getRecordInfos(long index) {
        StaticRecordInfo info = recordInfos.stream().filter(p -> p.getIndex() == index).findFirst().orElse(null);
        if (info != null && info.getException() != null) {
            StringWriter stringWriter = new StringWriter();
            info.getException().printStackTrace(new PrintWriter(stringWriter));
            System.out.println(stringWriter.toString());
            info.setExtInfo(stringWriter.toString());
        }
        return recordInfos.stream().filter(p -> p.getIndex() == index).findFirst().orElse(null);
    }
}