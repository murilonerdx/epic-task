package com.murilonerdx.epictask.config;

import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.security.AuthorizationFilter;
import com.murilonerdx.epictask.services.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(1)
public class SecurityConfigApi extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/usuario")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                        .csrf()
                        .disable()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthorizationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

}
