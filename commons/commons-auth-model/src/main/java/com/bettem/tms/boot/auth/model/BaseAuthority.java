package com.bettem.tms.boot.auth.model;

/**
 * 权限对象接口
 * @author GaoFans
 */
public interface BaseAuthority extends Comparable<BaseAuthority> {

    /**
     * 权限标识符
     * @return
     */
    String getCode();

    /**
     * 按照code排序
     * @param o
     * @return
     */
    @Override
    default int compareTo(BaseAuthority o){
        return this.getCode().compareTo(o.getCode());
    }

}
