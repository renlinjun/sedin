package com.sedin.uc.service;

import com.github.pagehelper.PageInfo;
import com.sedin.dto.UserIdentity;
import com.sedin.model.MRes;
import com.sedin.model.MUser;
import com.sedin.util.ActResult;
import com.sedin.util.page.DatagridRequestModel;
import com.sedin.util.page.DatagridResponseModel;

import java.util.List;

public interface UserService {
    /**
     * 查看所有的员工
     * @param mUser
     * @return
     */
    PageInfo<MUser> getListPage(Integer pageNum , Integer pageSize , MUser mUser);

    /**
     * 登录接口
     * @return
     */
    public ActResult loginLocal(UserIdentity user) throws Exception ;
    public ActResult login(UserIdentity user , boolean hasMenu) throws Exception;
    public ActResult logout(UserIdentity user);

    void createOrUpdate(MUser mUser);
//
//    List<MRes> getAllRole(String type);
//
//    MUser findById(long id);
//
    void deleteByIds(String ids);
//
//    void passwordReset(String ids);
//
//    MUser findUserId(String userId);
//
//    /**
//     * 根据当前登录人来修改密码
//     * @param userId
//     */
//    void updatePassWord(long userId, String passWord);
//
//    boolean checkPassword(MUser user, String password) throws Exception;
//
//    public String createToken(long userid);
}
