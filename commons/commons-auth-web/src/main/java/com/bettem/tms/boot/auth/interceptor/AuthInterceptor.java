package com.bettem.tms.boot.auth.interceptor;

import com.bettem.tms.boot.auth.annotation.Permission;
import com.bettem.tms.boot.auth.model.BaseUser;
import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.commons.utils.SpringContextHolder;
import com.bettem.tms.boot.commons.utils.exception.AuthorityException;
import com.bettem.tms.boot.commons.utils.exception.constant.BizExceptionEnum;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;

/**
 * 权限拦截器
 * @author GaoFans
 */
public class AuthInterceptor implements HandlerInterceptor {


    private CurrentUserKit currentUserKit;

    /**
     * 在controller前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if(object instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();
            Permission permission = method.getAnnotation(Permission.class);
            //如果该注解上标注了菜单code或是角色的enName的话，进行校验
            if(permission != null
                    && (permission.authorities().length != 0 || permission.roles().length != 0)){
                if(currentUserKit == null){
                    currentUserKit = SpringContextHolder.getBean(CurrentUserKit.class);
                }
                BaseUser user = currentUserKit.currentUser();
                //获取不到当前用户，抛出异常
                if(Objects.nonNull(user)){
                    throw new AuthorityException(BizExceptionEnum.AUTHORITY_ERROR);
                }
                //查看当前用户是否有该菜单code
                boolean flag = false;
                String[] authorities = permission.authorities();
                if(authorities.length != 0){
                    for (String authority : authorities) {
                        //用户中的权限集合必须是从小到大有序的，以便于使用二分查找
                        if(Collections.binarySearch(user.getAuthorities(), () -> authority) >= 0){
                            flag = true;
                            break;
                        }
                    }
                }
                //没有的话再判断是否有这个角色
                String[] roles = permission.roles();
                if(roles.length != 0 && !flag){
                    for (String role : roles) {
                        //用户中的角色也是必须从小到大的，以便于二分查找
                        if( Collections.binarySearch(user.getRoles(), () -> role) >= 0){
                            flag = true;
                            break;
                        }
                    }
                }
                if(!flag){
                    throw new AuthorityException(BizExceptionEnum.AUTHORITY_ERROR);
                }
            }
        }
        return true;
    }

}

