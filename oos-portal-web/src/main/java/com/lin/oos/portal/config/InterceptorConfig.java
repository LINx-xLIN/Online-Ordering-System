package com.lin.oos.portal.config;

import com.lin.oos.portal.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //需要拦截的路径，/**表示需要拦截所有请求
        String[] addPathPatterns = {"/order/**"};
        //不需要拦截的路径
        String[] excludePathPaterns = {"/**/*.html",  //html静态资源
                                       "/**/*.js",              //js静态资源
                                       "/**/*.css",             //css静态资源
                                       "/**/*.woff",
                                       "/**/*.ttf"};
        //注册一个登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPaterns);

        //注册一个权限拦截器  如果有多个拦截器 ，只需要添加以下一行代码
        //registry.addInterceptor(new LoginInterceptor())
        // .addPathPatterns(addPathPatterns)
        // .excludePathPatterns(excludePathPatterns);
    }

}
