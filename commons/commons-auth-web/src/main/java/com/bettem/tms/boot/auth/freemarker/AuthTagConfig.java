package com.bettem.tms.boot.auth.freemarker;

import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import freemarker.template.Configuration;

import javax.annotation.PostConstruct;

/**
 * freemarker自定义权限标签配置
 * @author GaoFans
 */
public class AuthTagConfig {

    private CurrentUserKit currentUserKit;

    private Configuration configuration;

    public AuthTagConfig(CurrentUserKit currentUserKit, Configuration configuration) {
        this.configuration = configuration;
        this.currentUserKit = currentUserKit;
    }
    @PostConstruct
    public void freeMarkerConfig(){
        configuration.setSharedVariable("auth", new AuthTags(currentUserKit));
    }
}
