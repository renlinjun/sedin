package com.sedin.oa.util;

import com.sedin.dto.UserIdentity;
import com.sedin.oa.third.service.UserRemoteService;
import com.sedin.util.JsonUtil;
import com.sedin.util.Utility;
import com.sedin.util.constant.CecurityConst;
import com.sedin.util.constant.RedisKey;
import com.sedin.util.redis.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by syl on 2016/4/13.
 */
@Component
public class SystemUtil {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserRemoteService userRemoteService;

    /**
     * 获取当前登录用户对象模型
     * @param request
     * @return
     */
    public UserIdentity getUserTokenModel(HttpServletRequest request , HttpServletResponse response ){
        UserIdentity ui = (UserIdentity)request.getAttribute(Const.REQUEST_USER_ATTR);
        if (ui != null) {
            return  ui;
        }

        String ticket = request.getParameter("ticket");
        if (!StringUtils.isEmpty(ticket)) {
            String userJson = userRemoteService.ticket(ticket);
            if (StringUtils.isEmpty(ticket)) {
                return null;
            }
            UserIdentity user = JsonUtil.getObject(userJson ,UserIdentity.class );
            String token = this.createToken(user.getId());
            user.setToken(token);
            //放到redis
            redisUtil.setex(RedisKey.user_oa_login_res_prefix + user.getId(), RedisKey.user_login_valid_time, JsonUtil.toJson(user));
            response.setHeader("SET-COOKIE", "token=" + user.getToken() + ";path=/; HttpOnly");
            return user;
        }

        Cookie[] cookies = request.getCookies();
        String authToken = "";
        if(cookies != null){
            for (Cookie cookie : cookies){
                String token =  cookie.getName();
                if(token.equals("token")){
                    authToken = cookie.getValue();
                    break;
                }
            }
        }
        return getUserTokenModelByToken(authToken);
    }
    

    /**
     * 获取当前登录用户对象模型
     * @param authToken
     * @return
     */
    public UserIdentity getUserTokenModelByToken(String authToken ){
        UserIdentity userIdentity = null;
        String oldAuthToken = authToken;
        if(!StringUtils.isEmpty(authToken)  && authToken.startsWith(Const.OAUTH_TOKEN_PREFIX)){
            authToken = authToken.substring(Const.OAUTH_TOKEN_PREFIX.length() );
            try {
                final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(authToken).getBody();
                String subject = claims.getSubject();
                if(!Utility.isNullorEmpty(subject)){
                    //读取redis
                    String user =   redisUtil.get(RedisKey.user_oa_login_res_prefix + subject);
                    if(!StringUtils.isEmpty(user)){
                        userIdentity = JsonUtil.getObject(user ,UserIdentity.class );

                        //不同的地方登录，前后登录的token变了，返回null;
                        if (!oldAuthToken.equals(userIdentity.getToken())) {
                            return null;
                        }
                    }
                }
            } catch (final SignatureException e) {
            	return null;
                // throw new ServletException("Invalid token.");
            }catch (Exception
                    e) {
                e.printStackTrace();
                return null;
            }

        }
        return userIdentity;
    }

    public String createToken(long userid) {
        return  Const.OAUTH_TOKEN_PREFIX + Jwts.builder().setSubject(userid +"").claim(
                "roles", userid).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,
                "secretkey").compact();
    }
}

