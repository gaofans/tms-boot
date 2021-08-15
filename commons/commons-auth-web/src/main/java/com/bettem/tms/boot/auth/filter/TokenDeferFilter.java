package com.bettem.tms.boot.auth.filter;

import com.bettem.tms.boot.auth.contants.WebConstant;
import com.bettem.tms.boot.auth.jwt.JwtTokenUtil;
import com.bettem.tms.boot.auth.jwt.payload.JwtPayLoad;
import com.bettem.tms.boot.commons.utils.AbstractExclusion;
import com.bettem.tms.boot.commons.utils.StringUtil;
import com.bettem.tms.boot.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 为token延期的过滤器
 * @author GaoFans
 */
public class TokenDeferFilter extends AbstractExclusion implements Filter {

    private String privateKey;

    private String publicKey;

    private long expireTime;

    private final static Logger LOGGER =  LoggerFactory.getLogger(TokenDeferFilter.class);

    public TokenDeferFilter(String privateKey, String publicKey, long expireTime) {
        super();
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.expireTime = expireTime;
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
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if (!isExclusion(requestURI)) {
            String token = request.getHeader(WebConstant.COOKIE_NAME);
            if(StringUtil.isEmpty(token)){
                token = CookieUtils.getCookieValue(request, WebConstant.COOKIE_NAME);
                //从cookie中取得的token可以直接续租
                if(StringUtil.isNotEmpty(token) && JwtTokenUtil.isTokenExpired(token,publicKey)) {
                    //token续租
                    JwtPayLoad jwtPayLoad = null;
                    try {
                        //如果还没过token超时时间,拿出其中的信息续上
                        jwtPayLoad = JwtTokenUtil.getJwtPayLoad(token, publicKey);
                        LOGGER.debug("user[{}]续租",jwtPayLoad.getUsername());
                        token = JwtTokenUtil.generateToken(jwtPayLoad,expireTime, privateKey);
                        CookieUtils.setCookie(request,response,WebConstant.COOKIE_NAME,token);
                    }catch (Exception e){}
                }
            }
            request.setAttribute(WebConstant.COOKIE_NAME,token);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        request.removeAttribute(WebConstant.COOKIE_NAME);
    }
}
