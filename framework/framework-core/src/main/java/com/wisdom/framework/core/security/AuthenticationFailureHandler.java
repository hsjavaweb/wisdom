package com.wisdom.framework.core.security;

import com.alibaba.fastjson.JSON;
import com.wisdom.framework.core.bean.ResponseData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hyberbin on 2016/11/2.
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseData responseData=new ResponseData();
        responseData.setCode("fail");
        responseData.setSuccess(false);
        responseData.setMsg(exception.getMessage());
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(responseData));
        writer.close();
    }
}
