package com.sedin.uc.service;
//

import com.sedin.dto.UserIdentity;
import com.sedin.util.ActResult;

public interface ThirdUserService {
    
    /**
     * 登录接口
     * @return
     */
   public ActResult login(UserIdentity user) throws Exception ;
   public ActResult logout(UserIdentity user);
}
