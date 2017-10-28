package com.sedin.uc.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.sedin.model.MRes;
import com.sedin.uc.config.security.handler.utils.RequestUtils;
import com.sedin.uc.config.security.handler.utils.ResponseUtils;
import com.sedin.uc.config.security.userdetails.UCGrantedAuthority;
import com.sedin.uc.service.MenuService;
import com.sedin.util.ActResult;
import com.sedin.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthenticationSuccessHandler(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        //if (RequestUtils.isAjax(request)) {
            User user = (User)auth.getPrincipal();
            Map resultData = new HashMap();
            resultData.put("user" , user);
            Map roles = new HashMap();

            List<MRes> list = new ArrayList();
            for (GrantedAuthority ga : user.getAuthorities()) {
                UCGrantedAuthority ua = (UCGrantedAuthority)ga;
                list.add(ua.getRes());
            }

            roles.put("menus", menuService.menuToComp(list));
            resultData.put("authority" , roles);



            ResponseUtils.print(response, JsonUtil.toJson(ActResult.successSetMsg("登录成功").setData(resultData)));
        //} else {
       //     super.onAuthenticationSuccess(request, response, auth);
       // }
    }

}
