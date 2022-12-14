package com.noriental.oksbaserver.arthas.conf;


import com.noriental.oksbaserver.arthas.app.configuration.ArthasProperties;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author ：yzg
 * @date ：Created in 2022/8/22 14:56
 * @description：
 * @modified By：
 * @version: $
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;

    @Autowired
    private ArthasProperties arthasProperties;

    public SecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        // allow iframe
        if (arthasProperties.isEnableIframeSupport()) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(adminContextPath + "/assets/**").permitAll()//Grants public access to all static assets and the login page.
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()//	Every other request must be authenticated.
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()//Configures login and logout.
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()//Enables HTTP-Basic support. This is needed for the Spring Boot Admin Client to register.
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//	Enables CSRF-Protection using Cookies
                .ignoringAntMatchers(
                        adminContextPath + "/instances",//	Disables CRSF-Protection the endpoint the Spring Boot Admin Client uses to register.
                        adminContextPath + "/actuator/**"//Disables CRSF-Protection for the actuator endpoints.
                );
    }
}