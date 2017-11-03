package com.sedin.uc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sedin.conversion.CustomerObjectMapper;
import com.sedin.util.spring.SpringContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

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
        //registry.addInterceptor((HandlerInterceptor) SpringContextUtil.getBeanByName("springMVCInterceptor")).addPathPatterns("/**/*").excludePathPatterns("/chatRoom/receiveMsg" ,"/web/res/**");
        //registry.addInterceptor((HandlerInterceptor) SpringContextUtil.getBeanByName("springMVCIsLoginInterceptor")).addPathPatterns("/**/*.user").excludePathPatterns("/chatRoom/receiveMsg" ,"/web/res/**");
    }

    @Bean
    public ObjectMapper ObjectMapper(){
        CustomerObjectMapper objectMapper=new CustomerObjectMapper();
        return objectMapper;
    }

    @Value("${web.upload-path}")
    private String mImagesPath;
    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + mImagesPath + File.separator+ "images" + File.separator);
        super.addResourceHandlers(registry);
    }
}
