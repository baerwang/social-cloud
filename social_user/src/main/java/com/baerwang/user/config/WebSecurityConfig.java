package com.baerwang.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 17:56
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*所有security全注解配置实现的开端,表示开始说明需要的权限,需要的权限分两部分,第一部分是拦截的路径,第二部分访问该路径需要的权限*/
        http.authorizeRequests()
                /*antMatchers表示拦截什么路径*/
                .antMatchers("/**")
                /*permitAll表示任何权限都可以访问*/
                .permitAll()
                /*anRequest任何请求*/
                .anyRequest()
                /*authenticated认证后才能访问*/
                .authenticated()
                /*.and().csrf().disable();固定写法，使得csrf拦截失效*/
                .and().csrf().disable();
    }
}
