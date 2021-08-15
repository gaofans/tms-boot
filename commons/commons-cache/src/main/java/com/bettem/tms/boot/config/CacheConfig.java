package com.bettem.tms.boot.config;

import com.bettem.tms.boot.commons.constant.SysPropertiesPrefix;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 缓存配置
 * @author GaoFans
 */
@Configuration
@EnableCaching
@PropertySource("classpath:cache-default.properties")
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    @ConfigurationProperties(prefix = SysPropertiesPrefix.NAME + "cache")
    public CacheProperties cacheProperties(){
        return new CacheProperties();
    }


    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            //格式化缓存key字符串
            StringBuilder sb = new StringBuilder();
            //追加类名
            sb.append(o.getClass().getName());
            sb.append(":");
            //追加方法名
            sb.append(method.getName());
            sb.append(":");
            //遍历参数并且追加
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
