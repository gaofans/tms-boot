package com.bettem.tms.boot.auth.jwt.payload;


import java.util.HashMap;
import java.util.Map;

/**
 * jwt的载荷
 * @author GaoFans
 * */
public class JwtPayLoad {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    public JwtPayLoad() {
    }

    public JwtPayLoad(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * payload转化为map形式
     *
     * @author fengshuonan
     * @Date 2019/7/20 20:50
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", this.userId);
        map.put("username", this.username);
        return map;
    }

    /**
     * payload转化为map形式
     *
     * @author fengshuonan
     * @Date 2019/7/20 20:50
     */
    public static JwtPayLoad toBean(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return new JwtPayLoad();
        } else {
            JwtPayLoad jwtPayLoad = new JwtPayLoad();

            Object userId = map.get("userId");
            if (userId instanceof String) {
                jwtPayLoad.setUserId(map.get("userId").toString());
            }

            jwtPayLoad.setUsername((String) map.get("username"));
            return jwtPayLoad;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
