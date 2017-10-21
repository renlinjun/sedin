package com.sedin.uc.service.impl;

import com.sedin.util.constant.RedisKey;
import com.sedin.util.redis.RedisUtil;
import com.sedin.model.MRes;
import com.sedin.uc.dao.MResMapper;
import com.sedin.uc.service.ResService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("resService")
public class ResServiceImpl implements ResService {
    
//    @Autowired
//    RedisUtil redisUtil;
//    @Value("${SERVER_DOMAIN}")
//    private String serverDomain = "localhost";
//
    @Autowired
    private MResMapper resDao;

    @Override
    @Transactional(readOnly = true)
    public List<MRes> getAllResByType(String type) {
        return resDao.selectByType(type);
    }
//
    @Override
    @Transactional(readOnly = true)
    public List<MRes> getRes(String ids, String type) {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }
        return resDao.selectByRes(ids, type);
    }
//
//    @Override
//    public String getResServerDomain() {
//        return serverDomain;
//    }
//
//    @Override
//    public String getResAccessToken(Long userId) {
//        String accessToken = UUID.randomUUID().toString();
//        accessToken =accessToken.replace("-", "");
//        redisUtil.setex(RedisKey.user_res_access + accessToken , 3 * 60 , String.valueOf(userId));
//        return accessToken;
//    }
//
//    @Override
//    public String getMenuUpdateTime() {
//        String time = redisUtil.get(RedisKey.longlian_res_system_menu_time);
//        return time;
//    }
}
