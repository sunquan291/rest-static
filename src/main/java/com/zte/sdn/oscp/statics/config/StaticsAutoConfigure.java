package com.zte.sdn.oscp.statics.config;

import com.zte.sdn.oscp.statics.control.RestStaticInfosControl;
import com.zte.sdn.oscp.statics.filter.RestLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(RestStaticsProperties.class)
@Configuration
public class StaticsAutoConfigure {

    @Autowired
    private RestStaticsProperties restStaticsProperties;


    @ConditionalOnProperty(prefix = "rest.statics", name = "enable", havingValue = "true", matchIfMissing = true)
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