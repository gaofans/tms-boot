package com.bettem.tms.boot.auth.model;

import java.util.List;

/**
 * 角色接口
 * @author GaoFans
 */
public interface BaseRole extends Comparable<BaseRole>{

    /**
     * 角色名称
     * @return
     */
    String getEnName();

    /**
     * 该角色拥有的权限
     * @return
     */
    default List<? extends BaseAuthority> getAuthorities(){
        return null;
    }

    /**
     * 按照角色名称排序
     * @param o
     * @return
     */
    @Override
    default int compareTo(BaseRole o){
        return this.getEnName().compareTo(o.getEnName());
    }
}
