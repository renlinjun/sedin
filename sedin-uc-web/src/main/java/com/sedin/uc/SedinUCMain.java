package com.sedin.uc;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Created by liuhan on 2017-09-20.
 */
@SpringBootApplication
public class SedinUCMain {
    private static final Logger logger = LoggerFactory
            .getLogger(SedinUCMain.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    com.github.pagehelper.PageInterceptor pageHelperPageInterceptor;

    @PostConstruct
    public void initialization() {
        sqlSessionFactory.getConfiguration().addInterceptor( pageHelperPageInterceptor);
        logger.info("完成初史化");
    }

        public static void main(String[] args) {
            SpringApplication.run(SedinUCMain.class, args);
        }
    }
