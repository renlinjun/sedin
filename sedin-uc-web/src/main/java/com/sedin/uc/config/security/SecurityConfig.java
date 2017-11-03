package com.sedin.uc.config.security;

import com.sedin.uc.config.security.handler.AuthenticationFailureHandler;
import com.sedin.uc.config.security.handler.AuthenticationLogoutSuccessHandler;
import com.sedin.uc.config.security.handler.AuthenticationSuccessHandler;
import com.sedin.uc.config.security.handler.utils.JwtTokenUtil;
import com.sedin.uc.config.security.userdetails.CustomUserDetailsService;
import com.sedin.uc.service.MenuService;
import com.sedin.uc.service.ResService;
import com.sedin.uc.service.UserService;
import com.sedin.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import javax.sql.DataSource;


/**
 * Created by liuhan on 2017/7/3.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResService resService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable().addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() ; // 基于token，所以不需要session

        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/images/**"
                )
                .permitAll()//访问：无需登录认证权限
                .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
                .and()
             .formLogin()
                .successHandler(authenticationSuccessHandler()).failureHandler(authenticationFailureHandler())
                .loginProcessingUrl("/j_spring_security_check")
                .and()
            .logout()
                .logoutSuccessHandler(authenticationLogoutSuccessHandler())
                .permitAll()
                .and()
            .rememberMe()
                .rememberMeCookieName("rememberMe")
                .tokenValiditySeconds(24 * 60 * 60 * 30) // expired time = 30 day
                    ;

        // 禁用缓存
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Bean
    public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler(menuService , redisUtil , jwtTokenUtil);
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    @Bean
    public SimpleUrlLogoutSuccessHandler authenticationLogoutSuccessHandler() {
        return new AuthenticationLogoutSuccessHandler();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(userService , resService);
    }

    /**
     * 密码加密
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





}
