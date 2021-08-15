package com.bettem.tms.boot.interceptor;

import com.bettem.tms.boot.utils.HttpKit;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置页面上的base href
 * @author GaoFans
 */
public class BasePathInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(HttpKit.isAcceptHtmlRequest(request)){
            String basePath = HttpKit.getBasePath(request);
            request.setAttribute("_base_path", basePath);
        }
        return true;
    }
}
