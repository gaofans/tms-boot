package com.bettem.tms.boot.session;

import com.bettem.tms.boot.commons.utils.AbstractExclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * 替换掉 RequestContextHolder 中的值
 * @author GaoFans
 */
public class RequestHolderFilter extends AbstractExclusion implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHolderFilter.class);

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

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String requestURI = request.getRequestURI();
        if (!isExclusion(requestURI)) {
            try {
                ServletRequestAttributes attributes = new ServletRequestAttributes(request);
                request.setAttribute(RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES", attributes);
                LocaleContextHolder.setLocale(request.getLocale());
                RequestContextHolder.setRequestAttributes(attributes);
            }catch (Exception e){
                LOGGER.warn("requestHolder赋值失败",e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
