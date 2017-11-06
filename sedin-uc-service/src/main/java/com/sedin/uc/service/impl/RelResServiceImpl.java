package com.sedin.uc.service.impl;

import com.sedin.model.MResRel;
import com.sedin.uc.dao.MResMapper;
import com.sedin.uc.dao.MResRelMapper;
import com.sedin.uc.service.RelResService;
import com.sedin.uc.service.RelResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liuhan on 2017-11-04.
 */
@Service("relResService")
public class RelResServiceImpl implements RelResService {

    @Autowired
    private MResRelMapper resRelDao;


    /**
     * 有资源引用这个
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasResRefThis(Long id) {
        Long count = resRelDao.selectResRefThisCount(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除和这个资源有关系的引用
     *
     * @param id
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delResRelByRef(Long id) {
        resRelDao.deleteByResId(id);
    }

    /**
     * 删除和这个资源有关系的引用Rel
     *
     * @param id
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delResRelByRefRel(Long id) {
        resRelDao.delResRelByRefRel(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void saveResRels(Long roleId, String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            if ("0".equals(id)) continue;
            MResRel rel = new MResRel();
            rel.setResId(roleId);
            rel.setRelId(Long.parseLong(id));
            resRelDao.insert(rel);
        }
    }
}
