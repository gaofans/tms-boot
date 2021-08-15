package com.bettem.tms.boot.data.config;

import com.bettem.tms.boot.session.LocalSessionRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.SessionRepository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地session配置
 * @author GaoFans
 */
@Configuration
@AutoConfigureAfter(RedisSessionConfig.class)
public class LocalSessionConfig {

    /**
     * 否则直接map内存中存储session
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionRepository.class)
    @SuppressWarnings("rawtypes")
    public FindByIndexNameSessionRepository sessionRepository(){
        return new LocalSessionRepository(new ConcurrentHashMap<>());
    }
}
