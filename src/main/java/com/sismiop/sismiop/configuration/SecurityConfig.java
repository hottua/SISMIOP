package com.sismiop.sismiop.configuration;

import com.sismiop.sismiop.service.PendudukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PendudukService pendudukService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**", "/static/**", "/bower_components/**","/dist/**", "/img/**", "/fontawesome/**", "/plugins/**",
                        "/plugins/**", "/file/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/penduduk").hasAnyAuthority("ROLE_PENDUDUK", "ROLE_ADMIN")
                .anyRequest().hasAnyAuthority("ROLE_PENDUDUK", "ROLE_ADMIN")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true")
                    .usernameParameter("no_telp")
                    .passwordParameter("password")
                    .permitAll()
                .and()
                .logout().
                    logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                    logoutSuccessUrl("/login").and().exceptionHandling()
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and().csrf().ignoringAntMatchers("/admin/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(pendudukService).passwordEncoder(bCryptPasswordEncoder);
    }


}
