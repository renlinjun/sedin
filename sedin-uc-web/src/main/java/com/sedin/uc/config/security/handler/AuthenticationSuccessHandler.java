package com.sedin.uc.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.sedin.model.MRes;
import com.sedin.uc.config.security.handler.utils.JwtTokenUtil;
import com.sedin.uc.config.security.handler.utils.RequestUtils;
import com.sedin.uc.config.security.handler.utils.ResponseUtils;
import com.sedin.uc.config.security.userdetails.JwtUser;
import com.sedin.uc.service.MenuService;
import com.sedin.util.ActResult;
import com.sedin.util.JsonUtil;
import com.sedin.util.constant.RedisKey;
import com.sedin.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhan on 2017/7/4.
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    MenuService menuService;

    RedisUtil redisUtil;

    JwtTokenUtil jwtTokenUtil;

    public AuthenticationSuccessHandler(MenuService menuService , RedisUtil redisUtil , JwtTokenUtil jwtTokenUtil) {
        this.menuService = menuService;
        this.redisUtil = redisUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        //if (RequestUtils.isAjax(request)) {
        JwtUser user = (JwtUser)auth.getPrincipal();
        user.setPassword("");
        Map resultData = new HashMap();
        resultData.put("user" , user);
        Map roles = new HashMap();
        roles.put("menus", menuService.menuToComp(user.getMenu()));
        resultData.put("authority" , roles);

        String token = jwtTokenUtil.generateToken(user);
        user.setToken(token);

        //System.out.println(JsonUtil.toJson(user));
        //放到redis
        redisUtil.setex(RedisKey.user_login_res_prefix + user.getUsername(), RedisKey.user_login_valid_time, JsonUtil.toJson(user));
        //response.setHeader("SET-COOKIE", "token=" + token + ";path=/; HttpOnly");

        ResponseUtils.print(response, JsonUtil.toJson(ActResult.successSetMsg("登录成功").setData(resultData)));
        //} else {
       //     super.onAuthenticationSuccess(request, response, auth);
       // }
    }

}
