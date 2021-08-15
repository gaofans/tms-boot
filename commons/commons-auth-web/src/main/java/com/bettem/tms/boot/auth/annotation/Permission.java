package com.bettem.tms.boot.auth.annotation;

import java.lang.annotation.*;

/**
 * 权限注解 用于检查权限 规定访问权限
 * @author GaoFans
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    String[] roles() default {};

    String[] authorities() default {};
}
