package com.bettem.tms.boot.commons.config;

import com.bettem.tms.boot.commons.utils.ThreadLocalHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author GaoFans
 */
@Configuration
@EnableAsync
@EnableScheduling
@PropertySource("classpath:utils-default.properties")
public class TaskExecutorConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.task-executor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置默认线程名称
        executor.setThreadNamePrefix("default-");
        // 设置拒绝策略,抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

    @Bean
    public ThreadLocalHolder threadLocalHolder(){
	    return ThreadLocalHolder.getInstance();
    }
}
