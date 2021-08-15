package com.bettem.tms.boot.auth.config;

import com.bettem.tms.boot.auth.contants.TokenFilterOrder;
import com.bettem.tms.boot.auth.filter.LoginFilter;
import com.bettem.tms.boot.auth.filter.TokenDeferFilter;
import com.bettem.tms.boot.auth.filter.TokenParseFilter;
import com.bettem.tms.boot.auth.interceptor.AuthInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 权限配置
 * @author GaoFans
 */
@Configuration
public class WebAuthConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    /**
     * 注册token生成过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean tokenCreateFilterBean(WebAuthProperties properties){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        TokenDeferFilter tokenDeferFilter = new TokenDeferFilter(properties.getPrivateKey(), properties.getPublicKey(), properties.getExpireTime());

        //在此可配置放行的具体url
        tokenDeferFilter.addUrlExclusion(properties.getCreateExcludes());
        bean.setFilter(tokenDeferFilter);

        //拦截优先级为最高
        bean.setOrder(TokenFilterOrder.TOKEN_CREATE_FILTER_ORDER);
        bean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        bean.addInitParameter(
                "exclusions", "/assets/*");
        return bean;
    }

    /**
     * 注册token解析过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean tokenParseFilterBean(WebAuthProperties properties){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        //添加不需要忽略的格式信息.
        bean.addInitParameter(
                "exclusions", "/assets/*");
        TokenParseFilter tokenParseFilter = new TokenParseFilter(properties.getPublicKey());

        //在此可配置放行的具体url
        tokenParseFilter.addUrlExclusion(properties.getParseExcludes());
        bean.setFilter(tokenParseFilter);

        //拦截优先级
        bean.setOrder(TokenFilterOrder.TOKEN_PARSE_FILTER_ORDER);
        bean.addUrlPatterns("/*");
        return bean;
    }

    /**
     * 注册登录过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilterBean(WebAuthProperties properties){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        //添加不需要忽略的格式信息.
        bean.addInitParameter(
                "exclusions", "/assets/*");
        LoginFilter loginFilter = new LoginFilter(properties.getLoginUrl());

        //在此可配置放行的具体url
        loginFilter.addUrlExclusion(properties.getLoginExcludes());
        bean.setFilter(loginFilter);

        //拦截优先级
        bean.setOrder(TokenFilterOrder.LOGIN_FILTER_ORDER);
        bean.addUrlPatterns("/*");
        return bean;
    }

    /**
     * 配置登录与权限控制拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要忽略的静态资源的路径
        List<String> staticResources = Arrays.asList("/assets/**");
        registry.addInterceptor(authInterceptor()).excludePathPatterns(staticResources);
    }
}
