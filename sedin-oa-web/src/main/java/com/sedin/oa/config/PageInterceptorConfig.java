package com.sedin.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by liuhan on 2017-05-27.
 */
@Configuration
public class PageInterceptorConfig {

    @Bean(name="pageHelperPageInterceptor")
    public com.github.pagehelper.PageInterceptor pageHelperPageInterceptor() {
        com.github.pagehelper.PageInterceptor pageInterceptor = new com.github.pagehelper.PageInterceptor();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageInterceptor.setProperties(p);
        return pageInterceptor;
    }
}
