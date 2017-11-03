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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("resService")
public class ResServiceImpl implements ResService {
    
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
    @Override
    @Transactional(readOnly=false,rollbackFor = Exception.class)
    public void saveMRes(MRes res) {
        resDao.insert(res);
    }
}
