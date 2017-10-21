package com.sedin.oa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sedin.conversion.CustomerObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by liuhan on 2017-09-21.
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Bean
    public ObjectMapper ObjectMapper(){
        CustomerObjectMapper objectMapper=new CustomerObjectMapper();
        return objectMapper;
    }
}
