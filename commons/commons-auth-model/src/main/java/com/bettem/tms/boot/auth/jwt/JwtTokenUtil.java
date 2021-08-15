package com.bettem.tms.boot.auth.jwt;

import cn.hutool.crypto.SecureUtil;
import com.bettem.tms.boot.auth.jwt.payload.JwtPayLoad;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * jwt token工具类,RSA加密
 * jwt的claim里一般包含以下几种数据:
 * 1. iss -- token的发行者
 * 2. sub -- 该JWT所面向的用户
 * 3. aud -- 接收该JWT的一方
 * 4. exp -- token的失效时间
 * 5. nbf -- 在此时间段之前,不会被处理
 * 6. iat -- jwt发布时间
 * 7. jti -- jwt唯一标识,防止重复使用
 *
 * @author GaoFans
 */
public class JwtTokenUtil {

    /**
     * 生成token,根据userId和过期时间
     */
    public static String generateToken(JwtPayLoad jwtPayLoad, Long expiredSeconds, String privateKey) {
        final Date expirationDate = new Date(System.currentTimeMillis() + expiredSeconds * 1000);
        return generateToken(String.valueOf(jwtPayLoad.getUserId()), expirationDate, jwtPayLoad.toMap(),privateKey);
    }

    /**
     * 获取jwt的payload部分
     */
    public static JwtPayLoad getJwtPayLoad(String token,String publicKey) {
        Claims claimFromToken = getClaimFromToken(token,publicKey);
        return JwtPayLoad.toBean(claimFromToken);
    }

    /**
     * 解析token是否正确(true-正确, false-错误)
     */
    public static Boolean checkToken(String token,String publicKey) {
        try {
            Jwts.parser().setSigningKey(SecureUtil.generatePublicKey("RSA", SecureUtil.decode(publicKey))).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 验证token是否失效
     */
    public static Boolean isTokenExpired(String token,String publicKey) {
        try {
            final Date expiration = getExpirationDateFromToken(token,publicKey);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token,String publicKey) {
        return getClaimFromToken(token,publicKey).getExpiration();
    }

    /**
     * 生成token,根据userId和过期时间
     */
    public static String generateToken(String userId, Date expiredDate, Map<String, Object> claims,String privateKey) {

        final Date createdDate = new Date();

        if (claims == null) {
            return Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.RS256, SecureUtil.generatePrivateKey("RSA", SecureUtil.decode(privateKey)))
                    .compact();
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.RS256, SecureUtil.generatePrivateKey("RSA", SecureUtil.decode(privateKey)))
                    .compact();
        }
    }

    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token,String publicKey) {

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token参数为空！");
        }

        return Jwts.parser()
                .setSigningKey(SecureUtil.generatePublicKey("RSA", SecureUtil.decode(publicKey)))
                //允许超时一小时
                .setAllowedClockSkewSeconds(3600)
                .parseClaimsJws(token)
                .getBody();
    }
}