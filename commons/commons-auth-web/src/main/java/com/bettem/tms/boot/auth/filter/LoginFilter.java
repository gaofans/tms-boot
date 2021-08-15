package com.bettem.tms.boot.auth.filter;

import com.bettem.tms.boot.auth.contants.WebConstant;
import com.bettem.tms.boot.auth.model.BaseUser;
import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.AbstractExclusion;
import com.bettem.tms.boot.commons.dto.RR;
import com.bettem.tms.boot.commons.utils.MapperUtils;
import com.bettem.tms.boot.commons.utils.SpringContextHolder;
import com.bettem.tms.boot.commons.utils.StringUtil;
import com.bettem.tms.boot.commons.utils.exception.AuthorityException;
import com.bettem.tms.boot.commons.utils.exception.constant.BizExceptionEnum;
import com.bettem.tms.boot.utils.HttpKit;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 登录过滤器
 * @author GaoFans
 */
public class LoginFilter extends AbstractExclusion implements Filter {

    private String loginUrl;

    private CurrentUserKit currentUserKit;

    public LoginFilter(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String exclusions = filterConfig.getInitParameter("exclusions");
        if (exclusions != null && exclusions.trim().length() != 0) {
            addUrlExclusion(Arrays.asList(exclusions.split("\\s*,\\s*")));
            setContextPath(filterConfig.getServletContext().getContextPath());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        if (!isExclusion(requestURI)) {
            if(currentUserKit == null){
                currentUserKit = SpringContextHolder.getBean(CurrentUserKit.class);
            }
            BaseUser user = currentUserKit.currentUser();
            if(user == null){
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                String message = "尚未登录";
                if(StringUtil.isNotEmpty(request.getAttribute(WebConstant.COOKIE_NAME))){
                    response.addHeader("sessionstatus","timeout");
                    message = "登录超时";
                }
                if(StringUtil.isNotEmpty(loginUrl)){
                    if(HttpKit.isAcceptHtmlRequest(request)){
                        response.sendRedirect(StringUtil.isEmpty(getContextPath(),"") + loginUrl);
                        return;
                    }
                }
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                try {
                    response.getWriter().write(MapperUtils.obj2json(new RR<>(HttpStatus.UNAUTHORIZED.value(),message)));
                    return;
                } catch (Exception e) {
                    throw new AuthorityException(BizExceptionEnum.NOT_LOGIN_ERROR);
                }

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public String getLoginUrl() {
        return loginUrl;
    }
}
