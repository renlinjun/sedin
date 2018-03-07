package com.sedin.uc.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.sedin.uc.config.security.handler.utils.RequestUtils;
import com.sedin.uc.config.security.handler.utils.ResponseUtils;
import com.sedin.util.ActResult;
import com.sedin.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liuhan on 2017/7/4.
 */
public class AuthenticationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static final String LOGIN_ERROR_RESULT =  JsonUtil.toJson(ActResult.successSetMsg("退出成功"));

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
      //  if (RequestUtils.isAjax(request)) {
            ResponseUtils.print(response,LOGIN_ERROR_RESULT);
       // } else {
      //      super.onLogoutSuccess(request, response, auth);
       // }
    }
}
