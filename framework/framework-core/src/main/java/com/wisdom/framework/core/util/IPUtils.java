package com.wisdom.framework.core.util;

import com.wisdom.framework.core.context.SpringContextUtil;
import com.wisdom.framework.core.security.DefaultAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author hyberbin on 2016/11/4.
 */
public class IPUtils {

    private static Logger log = LoggerFactory.getLogger(IPUtils.class);
    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (log.isInfoEnabled()) {
            log.trace("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip={}", ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (log.isInfoEnabled()) {
                    log.trace("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip={}", ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (log.isInfoEnabled()) {
                    log.trace("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip={}", ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (log.isInfoEnabled()) {
                    log.trace("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip={}", ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (log.isInfoEnabled()) {
                    log.trace("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDwED_FOR - String ip={}", ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (log.isInfoEnabled()) {
                    log.trace("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip={}", ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip.trim();
    }

    public static String getIpAddress() {
        try {
            HttpServletRequest request=getRequest();
            return getIpAddress(request);
        }catch (Exception e){
            log.warn("获取IP错误");
            return SpringContextUtil.getSystemProperties("host");
        }
    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest request;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            request= DefaultAuthenticationFilter.getRequest();
        }else {
            request = requestAttributes.getRequest();
        }
        return request;
    }

    public static String getHost(){
        HttpServletRequest request=getRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
    }
}
