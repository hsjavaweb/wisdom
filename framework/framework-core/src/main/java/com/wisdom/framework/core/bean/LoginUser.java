package com.wisdom.framework.core.bean;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author hyberbin on 2016/11/2.
 */
@Setter
@JSONType(includes = {"mercId","roleId","username","email","ipLimit","admType","enabled","loginFromEmail","loginIp","host"})
public class LoginUser implements UserDetails {
    public static final String ROLE_ADMIN="hasRole('ROLE_ADMIN')";
    public static final String ROLE_OPERATOR="hasRole('ROLE_OPERATOR')";
    public static final String ROLE_SUPERADMIN="hasRole('ROLE_SUPERADMIN')";

    static class Authority implements GrantedAuthority {
        private int admType = 0;

        public Authority(int admType) {
            this.admType = admType;
        }

        @Override
        public String getAuthority() {
            switch (admType) {
                default:
                case 0:
                    return "ROLE_ANONYMOUS";
                case 1:
                    return "ROLE_OPERATOR";
                case 2:
                    return "ROLE_ADMIN";
                case 3:
                    return "ROLE_SUPERADMIN";
            }
        }
    }

    private static final List<Authority>[] authorities = new List[]{Arrays.asList(new Authority(0))
            , Arrays.asList(new Authority(0), new Authority(1))
            , Arrays.asList(new Authority(0), new Authority(1), new Authority(2))
            , Arrays.asList(new Authority(0), new Authority(1), new Authority(2), new Authority(3))};

    @Setter
    @Getter
    private String mercId;

    @Setter
    @Getter
    private Integer roleId;

    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String ipLimit;
    @Setter
    @Getter
    private int admType = 0;
    private boolean enabled;
    @Setter
    @Getter
    private boolean loginFromEmail=false;
    @Getter
    private String loginIp;
    @Setter
    @Getter
    private String host;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities[admType];
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static LoginUser getCurrentUser() {
        SecurityContext sci = SecurityContextHolder.getContext();
        if (null == sci) {
            return null;
        }
        Object principal = sci.getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginUser loginUser = (LoginUser) o;

        return username != null ? username.equals(loginUser.username) : loginUser.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
