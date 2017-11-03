package com.sedin.uc.service;

import com.sedin.model.MRes;

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
}
