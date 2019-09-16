package com.licslan.config;

import com.licslan.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import static javax.servlet.DispatcherType.REQUEST;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        registration.addInitParameter("targetFilterLifecycle", "true"); //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setOrder(Integer.MAX_VALUE);
        registration.setDispatcherTypes(REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }

}
