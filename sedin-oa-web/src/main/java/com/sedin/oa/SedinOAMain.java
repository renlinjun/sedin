package com.sedin.oa;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * Created by liuhan on 2017-09-20.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages={"com.sedin"})
public class SedinOAMain {
    private static final Logger logger = LoggerFactory
            .getLogger(SedinOAMain.class);
    @PostConstruct
    public void initialization() {
        logger.info("完成初史化");
    }

        public static void main(String[] args) {
            SpringApplication.run(SedinOAMain.class, args);
        }
    }
