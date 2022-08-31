package com.zte.sdn.oscp.statics.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.sdn.oscp.statics.config.RestStaticsProperties;

/**
 * @Author 10184538
 * @Date 2022/8/31 10:14
 **/
public class RestLogFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(RestLogFilter.class);
    private RestStaticsProperties restStaticsProperties;
    private List<Pattern> skipUrsPattern;

    public RestLogFilter(RestStaticsProperties restStaticsProperties) {
        this.restStaticsProperties = restStaticsProperties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> skipUrls = restStaticsProperties.getSkipUrls();
        if (skipUrls == null) {
            skipUrsPattern = new ArrayList<>(0);
        } else {
            skipUrsPattern = skipUrls.stream().map(Pattern::compile).collect(Collectors.toList());
        }
        skipUrsPattern.add(Pattern.compile("/rest-statics/*"));
        int count = restStaticsProperties.getCount();
        RestStaticRecordSenior.init(count);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StringBuffer requestURL = ((HttpServletRequest) servletRequest).getRequestURL();
        boolean needSkip = skipUrsPattern.stream().anyMatch(p -> p.matcher(requestURL).find());
        if (needSkip) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            doRestLogFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
        }
    }

    private void doRestLogFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            RestStaticRecordSenior.addRecord(localDateTime, servletRequest, servletResponse,null);
        } catch (Exception e) {
            RestStaticRecordSenior.addRecord(localDateTime, servletRequest,servletResponse, e);
            throw e;
        }
    }
}
