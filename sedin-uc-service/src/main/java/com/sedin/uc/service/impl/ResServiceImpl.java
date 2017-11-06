package com.sedin.uc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sedin.uc.service.RelResService;
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

    @Autowired
    RelResService relResService;

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
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveMRes(MRes res) {
        resDao.insert(res);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<MRes> getListPage(Integer pageNum, Integer pageSize, MRes res) {
        PageHelper.startPage(pageNum, pageSize);

        List<MRes> list = resDao.query(res);

        return new PageInfo<MRes>(list);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteRealById(Long id , boolean delRefRel) {
        if (delRefRel) {
            relResService.delResRelByRef(id);
        }
        MRes res = resDao.selectByPrimaryKey(id);
        Long parentId = res.getParentId();
        resDao.delete(res);

        if (parentId != null && parentId > 0 ) {

            List<MRes> list = resDao.getResByParentId(parentId , null) ;
            if (list.size() == 0) {
                MRes parent = resDao.selectByPrimaryKey(parentId);
                parent.setIsLeaf("0");
                resDao.updateByPrimaryKey(parent);
            }
        }


    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void createOrUpdate(MRes res) {
        if (res.getId() == null || res.getId() == 0l) {   //添加
            resDao.insert(res);
            if (res.getParentId() != null && res.getParentId() > 0 ) {
                MRes parent = resDao.selectByPrimaryKey(res.getParentId());
                parent.setIsLeaf("1");
                resDao.updateByPrimaryKey(parent);
            }
        } else {
            resDao.updateByPrimaryKey(res);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void setTypeByIds(String ids, String type) {
        if (StringUtils.isEmpty(ids)) return ;
        resDao.setTypeByIds(ids , type);
    }

    @Override
    public List<MRes> getResByParentId(Long parentId, String type) {
        return resDao.getResByParentId(parentId , type) ;
    }
}
