package com.bettem.tms.boot.auth.config;

import com.bettem.tms.boot.commons.constant.SysPropertiesPrefix;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * 鉴权配置
 * @author GaoFans
 */
@Configuration
@PropertySource("classpath:auth-default.properties")
@ConfigurationProperties(prefix = SysPropertiesPrefix.NAME + "auth")
public class WebAuthProperties extends AuthProperties{

    private String indexUrl;

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}
