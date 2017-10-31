package com.sedin.uc;

import com.corundumstudio.socketio.SocketIONamespace;
import com.sedin.model.MRes;
import com.sedin.type.MResType;
import com.sedin.uc.service.ResService;
import com.sedin.util.JsonUtil;
import com.sedin.util.constant.RedisKey;
import com.sedin.util.redis.RedisUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by liuhan on 2017-09-20.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.sedin"})
public class SedinUCMain implements CommandLineRunner {
    private static final Logger logger = LoggerFactory
            .getLogger(SedinUCMain.class);

    @Autowired
    ResService resService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(String... args) {
        List<MRes> menuList = resService.getAllResByType(MResType.menu.getType());
        List<MRes> roleList = resService.getAllResByType(MResType.role.getType());

        for (MRes res : menuList) {
            redisUtil.hset(RedisKey.all_res , String.valueOf(res.getId())  , JsonUtil.toJson(res));
        }
        for (MRes res : roleList) {
            redisUtil.hset(RedisKey.all_res , String.valueOf(res.getId())  , JsonUtil.toJson(res));
        }
    }


    @PostConstruct
    public void initialization() {
        logger.info("完成初史化");
    }
    public static void main(String[] args) {
        SpringApplication.run(SedinUCMain.class, args);
    }
}
