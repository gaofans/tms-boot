package com.bettem.tms.boot.config;

import com.bettem.tms.boot.cache.ICache;
import com.bettem.tms.boot.cache.impl.LocalCacheImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author GaoFans
 */
@Configuration
@AutoConfigureAfter(RedisConfig.class)
@ConditionalOnMissingBean(ICache.class)
public class LocalCacheConfig{
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object key) throws Exception { return null;}
            // 重写这个方法将oldValue值返回回去，进而刷新缓存
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };
    }

    @Bean
    public Caffeine<Object,Object> cache(CacheProperties cacheProperties, CacheLoader cacheLoader){
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().maximumSize(100);
        if(cacheProperties.getTimeOutPolicy().equals(CacheProperties.TimeOutPolicy.READ_AND_WRITE)){
            caffeine.expireAfterAccess(cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }else if(cacheProperties.getTimeOutPolicy().equals(CacheProperties.TimeOutPolicy.WRITE)){
            caffeine.expireAfterWrite(cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }else if(cacheProperties.getTimeOutPolicy().equals(CacheProperties.TimeOutPolicy.READ)) {
            caffeine.expireAfterAccess(cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }
        return caffeine;
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine, CacheLoader cacheLoader){
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setAllowNullValues(false);
        manager.setCacheLoader(cacheLoader);
        manager.setCaffeine(caffeine);
        return manager;
    }

    @Bean
    public ICache<String,String,Object> iCache(Caffeine caffeine){
        Cache<Object,Object> build = caffeine.build();
        return new LocalCacheImpl<>(build);
    }
}