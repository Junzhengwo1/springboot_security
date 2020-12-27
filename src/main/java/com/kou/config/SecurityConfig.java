package com.kou.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author JIAJUN KOU
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 授权规则

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加请求授权规则
        http.authorizeRequests()
                // 首页所有人都可以访问
                .antMatchers("/").permitAll()
                // level1下的所有请求，vip1用户才能访问
                .antMatchers("/levelOne/**").hasRole("vip1")
                // level2下的所有请求，vip2用户才能访问
                .antMatchers("/levelTwo/**").hasRole("vip2")
                // level3下的所有请求，vip3用户才能访问
                .antMatchers("/levelThree/**").hasRole("vip3");
        // 开启登录页面，即没有权限的话跳转到登录页面，对应地址：/login
        http.formLogin()
                // 登录页面
                .loginPage("/toLogin")
                // 用户名的name
                .usernameParameter("user")
                // 密码的name
                .passwordParameter("pwd")
                // 处理登录的Controller
                .loginProcessingUrl("/login");
        http.csrf().disable();
        // 开启记住我功能，默认保存两周
        http.rememberMe()
                // name属性
                .rememberMeParameter("remember");
        // 开启注销功能
        http.logout()
                // 注销之后跳转到首页
                .logoutSuccessUrl("/");
        // 开启记住我功能，默认保存两周，底层使用cookie机制实现
        http.rememberMe();
    }

    // 认证规则

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在新版本的SpringSecurity中新增了许多加密方法，不使用加密的话就会出现异常
        // 这个例子模拟的是内存中的用户，真正开发中我们可以使用数据库
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(new BCryptPasswordEncoder().encode("12345")).roles("vip1", "vip2", "vip3")
                .and()
                .withUser("kou").password(new BCryptPasswordEncoder().encode("12345")).roles("vip1", "vip2")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("12345")).roles("vip1");
    }

}
