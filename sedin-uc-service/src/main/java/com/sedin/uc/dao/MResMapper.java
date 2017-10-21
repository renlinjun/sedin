package com.sedin.uc.dao;

import com.sedin.model.MRes;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MResMapper extends Mapper<MRes> {

    List<MRes> selectByType(String type);

    List<MRes> selectByRes(@Param("ids") String ids, @Param("type") String type);

}