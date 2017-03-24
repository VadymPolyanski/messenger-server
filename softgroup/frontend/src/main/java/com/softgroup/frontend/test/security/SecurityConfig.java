package com.softgroup.frontend.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by vadym_polyanski on 20.03.17.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.softgroup.frontend.test.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Filter filter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/messenger/public/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
