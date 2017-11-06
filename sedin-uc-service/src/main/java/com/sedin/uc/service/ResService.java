package com.sedin.uc.service;

import com.github.pagehelper.PageInfo;
import com.sedin.model.MRes;
import com.sedin.util.ActResult;

import java.util.List;

public interface ResService {
    /**
     * 根据类型取得所有的资源
     * @param type
     * @return
     */
    public List<MRes> getAllResByType(String type);

    public List<MRes> getRes(String ids, String type);
    public void saveMRes(MRes res);

    PageInfo<MRes> getListPage(Integer page, Integer pageSize, MRes res );

    void deleteRealById(Long id,boolean delRefRel);

    void createOrUpdate(MRes res);

    void setTypeByIds(String ids, String type);

    List<MRes> getResByParentId(Long parentId , String type);
}
