package com.sedin.uc.dao;

import com.sedin.model.MRes;
import com.sedin.model.MResRel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MResRelMapper extends Mapper<MResRel> {
    Long selectResRefThisCount(@Param("relId") Long id);

    void deleteByResId(@Param("resId") Long id);

    void delResRelByRefRel(@Param("relId") Long id);
}