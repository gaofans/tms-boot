package com.bettem.tms.boot.auth.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 匿名访问
 * @author GaoFans
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Anonymous {

    /**
     * true 表示该方法登录后不可访问
     * false 表示该方法无论登录与否都可以访问
     */
    boolean notLogin() default false;
}
