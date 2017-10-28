package com.sedin.uc.config.security.userdetails;

import com.sedin.model.MRes;
import com.sedin.model.MUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

/**
 * Created by liuhan on 2017-10-28.
 */
public class UCGrantedAuthority implements GrantedAuthority {

    private MRes res;

    public MRes getRes() {
        return res;
    }

    public void setRes(MRes res) {
        this.res = res;
    }

    public UCGrantedAuthority(MRes res) {
        //Assert.hasText(role, "A granted authority textual representation is required");
        this.res = res;
    }

    public String getAuthority() {
        return String.valueOf(res.getId());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof UCGrantedAuthority) {
            return res.getId().equals(((UCGrantedAuthority) obj).res.getId());
        }

        return false;
    }

    public int hashCode() {
        return this.res.getId().hashCode();
    }

    public String toString() {
        return  String.valueOf(this.res.getId());
    }
}
