package com.bettem.tms.boot.data.config;

import com.bettem.tms.boot.config.RedisConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

/**
 * redis session配置
 * @author GaoFans
 */
@Configuration
@AutoConfigureAfter({CommonWebConfig.class, RedisConfig.class})
@ConditionalOnClass(RedisOperations.class)
public class RedisSessionConfig {

    /**
     * 如果容器中有redis，就启用redis缓存session
     * @param redisTemplate
     * @return
     */
    @Bean
    @SuppressWarnings({"unchecked","rawtypes"})
    public FindByIndexNameSessionRepository sessionRepository(RedisTemplate redisTemplate){
        return new RedisIndexedSessionRepository(redisTemplate);
    }
}
