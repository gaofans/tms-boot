package com.bettem.tms.boot.auth.utils;


import com.bettem.tms.boot.auth.jwt.payload.JwtPayLoad;
import com.bettem.tms.boot.auth.model.BaseUser;
import com.bettem.tms.boot.cache.ICache;
import com.bettem.tms.boot.commons.utils.MapperUtils;
import com.bettem.tms.boot.commons.utils.StringUtil;
import com.bettem.tms.boot.utils.HttpKit;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 当前用户工具类
 * @author GaoFans
 */
public class CurrentUserKit<E extends BaseUser> implements CurrentUser<E> {

    /**
     * 当前token信息与当前线程进行绑定
     */
    private final ThreadLocal<JwtPayLoad> CURRENT_PAY_LOAD;
    private final ICache<String,String,Object> cache;
    /**
     * 指定子类类型便于json转换
     */
    private final Class<E> classType;

    public CurrentUserKit(ICache<String,String,Object> cache, Class<E> classType) {

        CURRENT_PAY_LOAD = new ThreadLocal<>();
        this.cache = Objects.requireNonNull(cache);
        this.classType = Objects.requireNonNull(classType);
    }

    public void set(JwtPayLoad jwtPayLoad) {
        CURRENT_PAY_LOAD.set(jwtPayLoad);
    }

    public JwtPayLoad get() {
        return CURRENT_PAY_LOAD.get();

    }

    public void remove() {
        CURRENT_PAY_LOAD.remove();
    }

    /**
     * 获取当前用户
     * @return
     */
    @Override
    public E currentUser(){

        JwtPayLoad payLoad = get();
        E user = null;
        if(payLoad != null && StringUtil.isNotEmpty(payLoad.getUserId())){
            try {
                user =  formatUser(cache.get(AuthNameSpaceUtil.getUserInfoName(payLoad.getUserId())));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return user;
    }

    /**
     * 从session中获取值
     * @param name
     * @return
     */
    @Override
    public Object getSessionAttribute(String name){

        if(name != null){
            return HttpKit.getSession().getAttribute(name);
        }
        return null;
    }

    /**
     * 获取session全部值
     * @return
     */
    public HttpSession getSession(){
        return HttpKit.getSession();
    }

    /**
     * 销毁当前session
     */
    @Override
    public void invalidateSession(){
        getSession().invalidate();
    }
    /**
     * 为session添加属性
     * @param name
     * @param value
     * @return
     */
    @Override
    public boolean setSessionAttribute(String name,Object value){
        if(name != null){
            HttpKit.getSession().setAttribute(name,value);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getSessionAttributes() {
        Map<String,Object> map = new HashMap<>();
        Enumeration<String> attributeNames = HttpKit.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            map.put(attributeNames.nextElement(),HttpKit.getSession().getAttribute(attributeNames.nextElement()));
        }
        return map;
    }

    public E formatUser(Object obj){
        if(obj instanceof String && StringUtil.isNotEmpty(obj)){
            try {
                return MapperUtils.json2pojo((String) obj, classType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 在缓存中存放用户信息
     * @param user
     * @throws Exception
     */
    @Override
    public void setUserInfo(E user) {
        if(user != null){
            try {
                String userInfoName = AuthNameSpaceUtil.getUserInfoName(user.getId());
                cache.put(userInfoName,MapperUtils.obj2json(user));
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 从缓存中取得某个用户信息
     * @param userId
     * @return
     */
    public E getUserInfo(String userId){
        if(userId != null){
            String userInfoName = AuthNameSpaceUtil.getUserInfoName(userId);
            Object o = cache.get(userInfoName);
            return formatUser(o);
        }
        return null;
    }
}
