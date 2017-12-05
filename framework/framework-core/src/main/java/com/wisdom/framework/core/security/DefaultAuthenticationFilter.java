package com.wisdom.framework.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hyberbin on 2016/12/6.
 */
public class DefaultAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL=new ThreadLocal<HttpServletRequest>();


    public static HttpServletRequest getRequest(){
        return REQUEST_THREAD_LOCAL.get();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        REQUEST_THREAD_LOCAL.set(request);
        return super.attemptAuthentication(request, response);
    }
}
