package com.sedin.uc.dao;

import com.sedin.model.MRes;
import com.sedin.model.MUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MResMapper extends Mapper<MRes> {

    List<MRes> selectByType(String type);

    List<MRes> selectByRes(@Param("ids") String ids, @Param("type") String type);

    List<MRes> query(@Param("res") MRes res);

    void setTypeByIds(@Param("ids") String ids,@Param("status")  String type);

    List<MRes> getResByParentId(@Param("parentId")  Long parentId, @Param("type") String type);
}