package com.wisdom.framework.core.security;

import com.alibaba.fastjson.JSON;
import com.wisdom.framework.core.bean.LoginUser;
import com.wisdom.framework.core.bean.ResponseData;
import com.wisdom.framework.core.service.UpdateUserLoginService;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hyberbin on 2016/11/2.
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    protected static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
    @Autowired
    protected UpdateUserLoginService updateUserLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String code="success";
        String msg="登录成功";
        LoginUser currentUser = LoginUser.getCurrentUser();
        if (currentUser == null) {
            code="fail";
            msg="登录失败";
        }
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setSuccess(true);
        responseData.setMsg(StringEscapeUtils.escapeXml(msg));

        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(responseData));
        writer.close();
    }


}
