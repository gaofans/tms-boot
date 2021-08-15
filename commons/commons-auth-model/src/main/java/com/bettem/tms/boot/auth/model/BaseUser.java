package com.bettem.tms.boot.auth.model;

import java.util.List;

/**
 * 用户接口
 * @author GaoFans
 */
public interface BaseUser {

    /**
     * 主键
     * @return
     */
    String getId();
    /**
     * 用户名
     * @return
     */
    String getUsername();

    /**
     * 密码
     * @return
     */
    String getPassword();

    /**
     * 该用户拥有的角色,必须进行从小到大的排序
     * @return
     */
    List<? extends BaseRole> getRoles();

    /**
     * 该用户拥有的权限,必须进行从小到大的排序
     * @return
     */
    List<? extends BaseAuthority> getAuthorities();
}
