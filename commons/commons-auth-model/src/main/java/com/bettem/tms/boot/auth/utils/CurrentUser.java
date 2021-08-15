package com.bettem.tms.boot.auth.utils;

import com.bettem.tms.boot.auth.model.BaseUser;

import java.util.Map;

/**
 * 获取与绑定当前用户的接口
 * @author GaoFans
 */
public interface CurrentUser<T extends BaseUser> {

    /**
     * 获取当前用户
     * @return
     */
    T currentUser();

    /**
     *
     * 设置session属性
     * @param name
     * @param value
     * @return
     */
    boolean setSessionAttribute(String name,Object value);

    /**
     * 获取全部session属性
     * @return
     */
    Map<String,Object> getSessionAttributes();

    /**
     * 获取session中的属性
     * @param name
     * @return
     */
    Object getSessionAttribute(String name);

    /**
     * 销毁session
     */
    void invalidateSession();

    /**
     * 添加用户信息
     * @param user
     */
    void setUserInfo(T user);
}
