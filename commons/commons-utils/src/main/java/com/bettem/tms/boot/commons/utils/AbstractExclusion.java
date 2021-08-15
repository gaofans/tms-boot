package com.bettem.tms.boot.commons.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 为过滤器添加url排除的功能
 * @author GaoFans
 */
public abstract class AbstractExclusion {

    private Set<String> urlExclusion;

    private ServletPathMatcher servletPathMatcher;

    private String contextPath;

    public AbstractExclusion() {
        this.urlExclusion = new HashSet<>();
        this.servletPathMatcher = ServletPathMatcher.getInstance();
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean isExclusion(String requestURI) {
        if (urlExclusion == null || requestURI == null) {
            return false;
        }

        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }

        for (String pattern : urlExclusion) {
            if (servletPathMatcher.matches(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    public void addUrlExclusion(List<String> urlExclusion) {
        if(urlExclusion != null && urlExclusion.size() > 0){
            this.urlExclusion.addAll(urlExclusion);
        }
    }
}
