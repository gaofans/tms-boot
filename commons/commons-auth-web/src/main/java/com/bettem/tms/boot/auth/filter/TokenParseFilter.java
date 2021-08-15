package com.bettem.tms.boot.auth.filter;

import com.bettem.tms.boot.auth.contants.WebConstant;
import com.bettem.tms.boot.auth.jwt.JwtTokenUtil;
import com.bettem.tms.boot.auth.jwt.payload.JwtPayLoad;
import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.AbstractExclusion;
import com.bettem.tms.boot.commons.utils.SpringContextHolder;
import com.bettem.tms.boot.commons.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * jwt 解析过滤器
 * @author GaoFans
 */
public class TokenParseFilter extends AbstractExclusion implements Filter {

    private String publicKey;

    private CurrentUserKit currentUserKit;

    public TokenParseFilter(String publicKey) {
        super();
        this.publicKey = publicKey;
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

        if(currentUserKit == null){
            currentUserKit = SpringContextHolder.getBean(CurrentUserKit.class);
        }
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String requestURI = request.getRequestURI();
            if (!isExclusion(requestURI)) {
                String token = Optional.ofNullable(request.getAttribute(WebConstant.COOKIE_NAME)).orElse("").toString();
                if(!StringUtil.isEmpty(token)){
                    try {
                        JwtPayLoad payLoad = JwtTokenUtil.getJwtPayLoad(token, publicKey);
                        currentUserKit.set(payLoad);
                    }catch (Exception e){}
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Throwable e){
            throw e;
        }finally {
            //返回后立即清空当前线程中的存储的用户信息，防止受到之前数据的影响
            currentUserKit.remove();
        }
    }

}
