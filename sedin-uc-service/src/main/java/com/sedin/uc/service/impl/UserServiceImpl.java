package com.sedin.uc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sedin.dto.UserIdentity;
import com.sedin.type.MResType;
import com.sedin.type.MUserStatusEnum;
import com.sedin.util.ActResult;
import com.sedin.util.BeanUtilEx;
import com.sedin.util.JsonUtil;
import com.sedin.util.constant.CecurityConst;
import com.sedin.util.constant.RedisKey;
import com.sedin.util.page.DatagridRequestModel;
import com.sedin.util.page.DatagridResponseModel;
import com.sedin.util.redis.RedisUtil;
import com.sedin.util.security.MD5PassEncrypt;
import com.sedin.model.MRes;
import com.sedin.model.MResRel;
import com.sedin.model.MUser;
import com.sedin.uc.dao.MResMapper;
import com.sedin.uc.dao.MResRelMapper;
import com.sedin.uc.dao.MUserMapper;
import com.sedin.uc.service.ResService;
import com.sedin.uc.service.ThirdUserService;
import com.sedin.uc.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务
 *
 * @author lh
 */
@Service("userService")
public class UserServiceImpl implements UserService , ThirdUserService{
    @Autowired
    private MUserMapper userDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ResService resService;
    @Autowired
    private MResMapper mResMapper;
    @Autowired
    private MResRelMapper mResRelMapper;

    /**
     * 密码校验
     *
     * @param user
     * @param password
     * @return
     * @throws Exception
     */
    public boolean checkPassword(MUser user, String password) throws Exception {
        if ( password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * 登录方法
     */
    @Override
    @Transactional(readOnly = true)
    public ActResult login(UserIdentity user) throws Exception {
        ActResult result = new ActResult();
        MUser muser = userDao.selectByUserId(user.getUserId(), true);
        if (muser == null) {
            result.setMsg("账号或密码错误！");
            result.setSuccess(false);
            return result;
        }
        if (!checkPassword(muser, user.getPassword())) {
            result.setMsg("账号或者密码错误！");
            result.setSuccess(false);
            return result;
        }
        if (!muser.getStatus().equals(MUserStatusEnum.muser_permit.getValue())) {
            result.setMsg("账号被禁用！");
            result.setSuccess(false);
            return result;
        }

        String token =  createToken(muser.getId());
        user.setId(muser.getId());
        user.setToken(token);
        user.setUserId(muser.getUserId() == null ? "" : muser.getUserId());
        user.setId(muser.getId());
        user.setName(muser.getName() == null ? "" : muser.getName());
        user.setPassword("");
        user.setAvatar(muser.getAvatar());

        //不是管理员
        if (!MUser.isAdmin(muser.getUserId())) {
//            //取得人员有的角色
//            List<MRes> roleList = resService.getRes(muser.getResId() + "", MResType.role.getType());
//            String roles = this.getIds(roleList);
//            map.put("role", roles);
//            //取得角色下面的菜单
//            List<MRes> menuList = resService.getRes(roles, MResType.menu.getType());
//            map.put("menu", this.getIds(menuList));
        } else {
        }

        //放到redis
        redisUtil.setex(RedisKey.user_login_res_prefix + muser.getId(), RedisKey.user_login_valid_time, JsonUtil.toJson(user));
        result.setData(user);
        result.setSuccess(true);
        return result;
    }

    private String getIds(List<MRes> list) {
        StringBuffer sb = new StringBuffer();
        if (list == null) return "";
        for (MRes r : list) {
            sb.append(r.getId()).append(",");
        }
        if (list.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    /**
     * 退出登录，清理掉redis
     */
    @Override
    public ActResult logout(UserIdentity user) {
        redisUtil.del(RedisKey.user_login_res_prefix + user.getId());
        return ActResult.success();
    }

//    @Transactional(readOnly = true)
//    public String findRoleName(long resId) {
//        return mResRelMapper.findRoleName(resId);
//    }
//
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(MUser mUser) {
        if (mUser.getId() == 0) {   //添加

        } else {

        }
    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<MRes> getAllRole(String type) {
//        return mResMapper.selectByType(type);
//    }
//
//    @Transactional(readOnly = true)
//    public MUser findMuserResId(long id) {
//        return userDao.selectByPrimaryKey(id);
//    }
//
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(String ids) {
         //
    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void passwordReset(String ids) {
//        String password = "";
//        try {
//            password = MD5PassEncrypt.crypt(Const.emp_init_password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        userDao.passwordReset(ids, password);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public MUser findUserId(String userId) {
//        return userDao.findUserId(userId);
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param userId
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void updatePassWord(long userId, String passWord) {
//        String newPassword = null;
//        try {
//            newPassword = MD5PassEncrypt.crypt(passWord);
//            userDao.passwordReset(userId + "", newPassword);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    public long createMres(MUser mUser) {
//        MRes mRes = new MRes();
//        mRes.setName(mUser.getName());
//        mRes.setParentId(0L);
//        mRes.setType("001");
//        mRes.setSort(0);
//        mRes.setStatus(0);
//        mRes.setUrl("0");
//        mRes.setResPic("");
//        mResMapper.insert(mRes);
//        return mRes.getId();
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void createMresRel(long resId, long relId) {
//        MResRel mResRel = new MResRel();
//        mResRel.setResId(resId);
//        mResRel.setRelId(relId);
//        mResRelMapper.insert(mResRel);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public MUser findById(long id) {
//        MUser mUser = userDao.selectByPrimaryKey(id);
//        long relId = findroleId(mUser.getResId());       //根据resId去中间表中查所对应的角色Id
//        mUser.setResId(relId);                           //把角色ID赋值给Muser表中的resId
//        return mUser;
//    }
//
//    @Transactional(readOnly = true)
//    public long findroleId(long resId) {
//        MResRel mResRel = mResRelMapper.findRelId(resId);
//        if (mResRel == null) {
//            return 0;
//        }
//        return mResRel.getRelId();
//    }
//
    public String createToken(long userid) {
       return  CecurityConst.OAUTH_TOKEN_PREFIX + Jwts.builder().setSubject(userid +"").claim(
                "roles", userid).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,
                "secretkey").compact();
    }

    /**
     * 查看所有的员工
     *
     * @param mUser
     * @return
     */
    @Override
    public PageInfo<MUser> getListPage(Integer pageNum , Integer pageSize , MUser mUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<MUser> list = userDao.query(mUser);

        return new PageInfo<MUser>(list);
    }
}