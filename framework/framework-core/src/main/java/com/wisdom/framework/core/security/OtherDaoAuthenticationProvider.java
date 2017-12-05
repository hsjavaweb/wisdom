package com.wisdom.framework.core.security;

import com.wisdom.framework.core.bean.LoginUser;
import com.wisdom.framework.core.context.SpringContextUtil;
import com.wisdom.framework.core.service.UpdateUserLoginService;
import com.wisdom.framework.core.util.IPUtils;
import com.wisdom.framework.core.util.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.lang.reflect.Method;



/**
 * @author hyberbin on 14-1-8.
 */
public class OtherDaoAuthenticationProvider extends DaoAuthenticationProvider implements Serializable {
    private static Logger log = LoggerFactory.getLogger(OtherDaoAuthenticationProvider.class);
    @Autowired
    protected UpdateUserLoginService updateUserLoginService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String form_password = authentication.getCredentials().toString();
        String user_password = userDetails.getPassword();
        LoginUser loginUser = (LoginUser) userDetails;
        try {
            boolean pswEquals = user_password.equals(SecureUtil.MD5String(form_password + "{" + authentication.getName() + "}", "utf-8"));
            if (!pswEquals) {
                throw new BadCredentialsException("用户名密码错误");
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        loginUser.setEnabled(true);
        SessionRegistry sessionRegistry= SpringContextUtil.getBean(SessionRegistry.class);
        sessionRegistry.registerNewSession(IPUtils.getRequest().getRequestedSessionId(),loginUser.getUsername());
        log.debug("用户通过密码验证登录成功");
    }
}
