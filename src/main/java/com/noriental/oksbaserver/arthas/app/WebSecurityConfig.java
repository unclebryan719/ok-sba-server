package com.noriental.oksbaserver.arthas.app;

import com.alibaba.arthas.tunnel.server.app.configuration.ArthasProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author hengyunabc 2021-08-11
 *
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ArthasProperties arthasProperties;
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated().anyRequest()
        .permitAll().and().formLogin();
        // allow iframe
        if (arthasProperties.isEnableIframeSupport()) {
            httpSecurity.headers().frameOptions().disable();
        }
    }
}