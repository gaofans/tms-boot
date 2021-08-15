package com.bettem.tms.boot.auth.config;

import java.util.List;

/**
 * 鉴权配置
 * @author GaoFans
 */
public class AuthProperties {

    private String publicKey;

    private String privateKey;

    private long expireTime;

    private String loginUrl;

    private List<String> parseExcludes;

    private List<String> loginExcludes;

    private List<String> createExcludes;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public List<String> getParseExcludes() {
        return parseExcludes;
    }

    public void setParseExcludes(List<String> parseExcludes) {
        this.parseExcludes = parseExcludes;
    }

    public List<String> getLoginExcludes() {
        return loginExcludes;
    }

    public void setLoginExcludes(List<String> loginExcludes) {
        this.loginExcludes = loginExcludes;
    }

    public List<String> getCreateExcludes() {
        return createExcludes;
    }

    public void setCreateExcludes(List<String> createExcludes) {
        this.createExcludes = createExcludes;
    }
}
