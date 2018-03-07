package com.sedin.oa.interceptor;

import com.sedin.dto.UserIdentity;
import com.sedin.oa.util.Const;
import com.sedin.oa.util.SystemUtil;
import com.sedin.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
/**
 * 身份URL拦截
 * Created by syl on 2016/4/13.
 */
@Component("springMVCInterceptor")
public class SpringMVCInterceptor implements HandlerInterceptor {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SystemUtil systemUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String requestUri =   request.getRequestURI();

        //判断是否是需要拦截的url
        //从redis里 取出身份信息
        UserIdentity identify =  systemUtil.getUserTokenModel(request , response);
        ///是否已登录
        if (identify == null) {
            response.sendRedirect("http://localhost:80/#/?uri=" + URLEncoder.encode(request.getRequestURL().toString()));
            return false;
        }
        //将身份信息存到request里面方便后面 controller使用
        request.setAttribute(Const.REQUEST_USER_ATTR, identify);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {


    }

    @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                          Object handler, Exception ex) throws Exception {
    }
}
