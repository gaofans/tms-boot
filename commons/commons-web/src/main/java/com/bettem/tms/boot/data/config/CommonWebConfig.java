package com.bettem.tms.boot.data.config;

import com.bettem.tms.boot.commons.constant.SysPropertiesPrefix;
import com.bettem.tms.boot.constant.FilterOrder;
import com.bettem.tms.boot.interceptor.BasePathInterceptor;
import com.bettem.tms.boot.session.HeaderCookieHttpSessionIdResolver;
import com.bettem.tms.boot.session.RequestHolderFilter;
import com.bettem.tms.boot.xss.XssFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用的web配置类
 * @author GaoFans
 */
@PropertySource("classpath:web-default.properties")
@EnableSpringHttpSession
@Configuration
public class CommonWebConfig implements WebMvcConfigurer {


    /**
     * RequestContextListener注册
     */
    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerRegistration() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

    /**
     * web配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = SysPropertiesPrefix.NAME + "web")
    public WebProperties webProperties(){
        return new WebProperties();
    }

    /**
     * requestContextFilter
     */
    @Bean
    public FilterRegistrationBean requestHolderFilter() {
        RequestHolderFilter requestHolderFilter = new RequestHolderFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(requestHolderFilter);
        registration.addUrlPatterns("/*");
        //静态资源放行
        registration.addInitParameter("exclusions","/assets/*");
        registration.setOrder(FilterOrder.REQUEST_HOLDER_ORDER);
        return registration;
    }

    /**
     * xssFilter注册
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration(WebProperties webProperties) {
        XssFilter xssFilter = new XssFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
        registration.addUrlPatterns("/*");
        //静态资源放行
        registration.addInitParameter("exclusions","/assets/*");
        xssFilter.addUrlExclusion(webProperties.getXssExcludes());
        registration.setOrder(FilterOrder.XSS_ORDER);
        return registration;
    }

    /**
     * 设置根路径拦截器与防重复提交拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasePathInterceptor()).excludePathPatterns("/assets/**");
    }

    /**
     * 允许跨域请求
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        // 2允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 3允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        // 4允许cookie
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * 使用header存储sessionId
     * @return
     */
    @Bean
    public HeaderCookieHttpSessionIdResolver headerCookieHttpSessionIdResolver() {
        return new HeaderCookieHttpSessionIdResolver("SESSION");//设置session名称
    }
}
