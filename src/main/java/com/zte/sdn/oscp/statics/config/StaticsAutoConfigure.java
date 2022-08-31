package com.zte.sdn.oscp.statics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zte.sdn.oscp.statics.control.RestStaticInfosControl;
import com.zte.sdn.oscp.statics.filter.RestLogFilter;

@EnableConfigurationProperties(RestStaticsProperties.class)
@Configuration
public class StaticsAutoConfigure {

    @Autowired
    private RestStaticsProperties restStaticsProperties;

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RestLogFilter(restStaticsProperties));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public RestStaticInfosControl restStaticInfosControl() {
        RestStaticInfosControl restStaticInfosControl = new RestStaticInfosControl(restStaticsProperties);
        return restStaticInfosControl;
    }
}