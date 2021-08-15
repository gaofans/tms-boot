package com.bettem.tms.boot.session;

import org.springframework.session.web.http.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

public class HeaderCookieHttpSessionIdResolver implements HttpSessionIdResolver {
    private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
    private static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";
    private final String idName;
    private static final String WRITTEN_SESSION_ID_ATTR = CookieHttpSessionIdResolver.class.getName().concat(".WRITTEN_SESSION_ID_ATTR");
    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();

    public static HeaderHttpSessionIdResolver xAuthToken() {
        return new HeaderHttpSessionIdResolver("X-Auth-Token");
    }

    public static HeaderHttpSessionIdResolver authenticationInfo() {
        return new HeaderHttpSessionIdResolver("Authentication-Info");
    }

    public HeaderCookieHttpSessionIdResolver(String idName) {
        if (idName == null) {
            throw new IllegalArgumentException("headerName cannot be null");
        } else {
            this.idName = idName;
        }
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(this.idName);
        return headerValue != null ? Collections.singletonList(headerValue) : this.cookieSerializer.readCookieValues(request);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.idName, sessionId);
        if (!sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR))) {
            request.setAttribute(WRITTEN_SESSION_ID_ATTR, sessionId);
            this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, sessionId));
        }
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.idName, "");
        this.cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, ""));
    }

    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        if (cookieSerializer == null) {
            throw new IllegalArgumentException("cookieSerializer cannot be null");
        } else {
            this.cookieSerializer = cookieSerializer;
        }
    }
}
