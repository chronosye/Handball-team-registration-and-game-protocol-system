package com.handball.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/**", "/login-error/**", "/register/**", "/img/**", "/css/**", "/js/**", "/resources/**", "/calendar/**", "/tournaments/**", "/teams/**", "/favicon.ico").permitAll()
                .antMatchers("/h2-console/**", "/admin/**").access("hasAuthority('ADMIN')")
                .antMatchers("/organizer/**").access("hasAnyAuthority('ADMIN','ORGANIZER')")
                .antMatchers("/manager/**").access("hasAnyAuthority('ADMIN','MANAGER')")
                .antMatchers("/protocolist/**").access("hasAnyAuthority('ADMIN','PROTOCOLIST')")
                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/",true).failureUrl("/login-error")
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .logoutUrl("/logout")
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }
}