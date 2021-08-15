package com.bettem.tms.boot.auth.utils;

/**
 * 获取登录鉴权的 redis 存储key工具类
 * @author GaoFans
 */
public class AuthNameSpaceUtil {

    public static final String AUTH_NAMESPACE = "AUTH:";

    public static final String AUTH_SESSION = "SESSION:";

    public static final String AUTH_USER_INFO = "USER_INFO:";

    public static String getSessionName(String sessionId){
        return new StringBuilder().append(AUTH_NAMESPACE).append(AUTH_SESSION).append(sessionId).toString();
    }

    public static String getUserInfoName(String userId){
        return new StringBuilder().append(AUTH_NAMESPACE).append(AUTH_USER_INFO).append(userId).toString();
    }

    public static String getSessionSpace(){
        return new StringBuilder().append(AUTH_NAMESPACE).append(AUTH_USER_INFO).toString();
    }

    public static String getUserInfoSpace(){
        return new StringBuilder().append(AUTH_NAMESPACE).append(AUTH_USER_INFO).toString();
    }
}
