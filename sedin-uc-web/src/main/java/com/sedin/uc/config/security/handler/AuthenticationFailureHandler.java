package com.sedin.uc.config.security.handler;


import com.alibaba.fastjson.JSON;
import com.sedin.uc.config.security.handler.utils.RequestUtils;
import com.sedin.uc.config.security.handler.utils.ResponseUtils;
import com.sedin.util.ActResult;
import com.sedin.util.JsonUtil;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liuhan on 2017/7/4.
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String LOGIN_ERROR_RESULT = JsonUtil.toJson(ActResult.fail("登录失败,请检验密码"));

    private static final String LOGIN_FROZEN_RESULT =JsonUtil.toJson(ActResult.fail("用户已被冻结"));

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
       // if (RequestUtils.isAjax(request)) {
            String result = e instanceof DisabledException ? LOGIN_FROZEN_RESULT : LOGIN_ERROR_RESULT;
            ResponseUtils.print(response, result);
       // } else {
       //     super.onAuthenticationFailure(request, response, e);
       // }
    }
}
