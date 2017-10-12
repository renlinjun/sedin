package com.sedin.uc.config;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liuhan on 2017-09-27.
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class TkMapperScannerConfigurer {
//    @Bean(name="tkMapperScannerConfigurer")
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setBasePackage("com.longlian.live.tk.dao");
//        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        return configurer;
//    }
    /*
        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
            mapperScannerConfigurer.setBasePackage("com.longlian.live.tk.dao");

            //初始化扫描器的相关配置，这里我们要创建一个Mapper的父类
            Properties properties = new Properties();
            //properties.setProperty("mappers", "com.example.MyMapper");
            properties.setProperty("notEmpty", "false");
            properties.setProperty("IDENTITY", "MYSQL");

            mapperScannerConfigurer.setProperties(properties);

            return mapperScannerConfigurer;
        }*/

}
