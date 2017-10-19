package com.sedin.uc.dao;

import com.sedin.model.MUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MUserMapper extends Mapper<MUser> {

    /**
     *
     * @param userId
     * @param onlyCanLogin 是否只查能登录的人员
     * @return
     */
    MUser selectByUserId(@Param("userId") String userId, @Param("onlyCanLogin") boolean onlyCanLogin);

    List<MUser> query(@Param("user") MUser user);
}