package com.bettem.tms.boot.xss;
import com.bettem.tms.boot.commons.utils.AbstractExclusion;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * XSS过滤器
 * @author GaoFans
 */
public class XssFilter extends AbstractExclusion implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusions = filterConfig.getInitParameter("exclusions");
        if (exclusions != null && exclusions.trim().length() != 0) {
            addUrlExclusion(Arrays.asList(exclusions.split("\\s*,\\s*")));
            setContextPath(filterConfig.getServletContext().getContextPath());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String requestURI = httpServletRequest.getRequestURI();
        if (isExclusion(requestURI)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), response);
        }

    }
}
