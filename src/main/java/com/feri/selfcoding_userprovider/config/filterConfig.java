package com.feri.selfcoding_userprovider.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;

/**
 *@Author feri
 *@Date Created in 2019/1/19 15:03
 */
@Configuration
public class filterConfig {
    //基于
    @Bean
    public FilterRegistrationBean createEncoding(){
        FilterRegistrationBean filterRegistration=new FilterRegistrationBean();
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setFilter(new CharacterEncodingFilter());
        filterRegistration.addInitParameter("encoding","UTF-8");
        return filterRegistration;
    }
}
