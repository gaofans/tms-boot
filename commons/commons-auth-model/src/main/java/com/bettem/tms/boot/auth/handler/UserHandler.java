package com.bettem.tms.boot.auth.handler;

import com.bettem.tms.boot.auth.model.BaseUser;

/**
 * 根据用户名加载用户
 * @author GaoFans
 * @param <T>
 */
@FunctionalInterface
public interface UserHandler<T extends BaseUser> {

    T loadUserByName(String username);

}
