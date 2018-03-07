package com.sedin.uc.config.security.userdetails;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sedin.model.MRes;
import com.sedin.model.MUser;
import com.sedin.type.MUserStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhan on 2017-10-29.
 */
public class JwtUser  implements UserDetails {
    private   String id;
    private   String username;//userId
    private String name ;
    @JSONField(serialize=false)
    private String password;
    private   String avatar;
    private String token;
    private   List<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @JSONField(serialize=false)
    private   Collection<? extends GrantedAuthority> authorities;

    public void setPassword(String password) {
        this.password = password;
    }
    @JSONField(serialize=false)
    private  List<MRes> menu;
    @JSONField(serialize=false)
    private   boolean accountNonExpired;

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setMenu(List<MRes> menu) {
        this.menu = menu;
    }

    public String getAvatar() {
        return avatar;
    }
    @JSONField(serialize=false)
    private   boolean accountNonLocked;
    @JSONField(serialize=false)
    private   boolean credentialsNonExpired;
    @JSONField(serialize=false)
    private   boolean enabled;

    public List<String> getRoles() {
        return roles;
    }

    public JwtUser(){}

    public JwtUser(Collection<? extends GrantedAuthority> authorities, MUser mUser , List<MRes> menu , List<String> roles) {
        this.id = String.valueOf(mUser.getId());
        this.username = mUser.getUserId();
        this.password = mUser.getPassword();
        this.name = mUser.getName();
        this.avatar = mUser.getAvatar();
        this.enabled = mUser.getStatus().equals(MUserStatusEnum.muser_permit.getValue());
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
        this.authorities = authorities;
        this.menu = menu;
        this.roles = roles;
    }


    public List<MRes> getMenu() {
        return menu;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    
    public String getId() {
        return id;
    }

    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public boolean isEnabled() {
        return this.enabled;
    }
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
}

