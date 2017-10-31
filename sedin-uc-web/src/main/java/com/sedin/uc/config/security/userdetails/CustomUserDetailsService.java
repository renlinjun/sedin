package com.sedin.uc.config.security.userdetails;

import com.sedin.model.MRes;
import com.sedin.model.MUser;
import com.sedin.type.MResType;
import com.sedin.type.MUserStatusEnum;
import com.sedin.uc.service.ResService;
import com.sedin.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by liuhan on 2017/7/3.
 */
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private UserService userService;

    private ResService resService;

    public CustomUserDetailsService(UserService userService , ResService resService) {
        this.userService = userService;
        this.resService = resService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        MUser user = userService.findByUserId(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        List<MRes> menuList = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        if (!MUser.isAdmin(user.getUserId())) {
            List<MRes> roleList = resService.getRes(user.getResId() + "", MResType.role.getType());
            for (MRes res : roleList) {
                authorities.add(new SimpleGrantedAuthority(String.valueOf(res.getId())));
                roles.add(String.valueOf(res.getId()));
            }
            //取得角色下面的菜单
            menuList = resService.getRes(getIds(roleList) , MResType.menu.getType());
        } else {
            menuList = resService.getAllResByType(MResType.menu.getType());
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            roles.add("ADMIN");
        }
        for (MRes res : menuList) {
            authorities.add(new SimpleGrantedAuthority(String.valueOf(res.getId())));
            roles.add(String.valueOf(res.getId()));
        }
        return new JwtUser(  authorities , user , menuList , roles);
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
}